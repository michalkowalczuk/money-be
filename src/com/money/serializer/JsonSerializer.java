package com.money.serializer;

import com.google.gson.*;
import com.money.model.Account;
import com.money.model.Entry;
import com.money.model.Journal;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;

/**
 * Created by michal on 5/3/14.
 */
public class JsonSerializer {

    private HttpServletRequest request;
    private JsonObject pathData;
    private Gson gson;

    public JsonSerializer(HttpServletRequest request, JsonObject pathData) {
        this.request = request;
        this.pathData = pathData;
        gson = new GsonBuilder()
                .registerTypeAdapter(Account.class, new AccountSerializer())
                .registerTypeAdapter(Journal.class, new JournalSerializer())
                .registerTypeAdapter(Entry.class, new EntrySerializer())
                .create();
    }

    public JsonElement serialize(Object object) {
        return gson.toJsonTree(object);
    }

    private class AccountSerializer implements com.google.gson.JsonSerializer<Account> {
        @Override
        public JsonElement serialize(Account account, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject returnValue = new JsonObject();

            returnValue.addProperty("id", account.getId());
            returnValue.addProperty("balance", account.getBalance());

            String href = StringUtils.removeEnd(request.getRequestURL().toString(), request.getPathInfo());
            returnValue.addProperty("href", href + "/account/" + account.getId());
            JsonObject journals = new JsonObject();
            journals.addProperty("href", href + "/account/" + account.getId() + "/journals");
            returnValue.add("journals", journals);

            return returnValue;
        }
    }

    private class JournalSerializer implements com.google.gson.JsonSerializer<Journal> {
        @Override
        public JsonElement serialize(Journal journal, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject returnValue = new JsonObject();

            returnValue.addProperty("id", journal.getId());
            returnValue.addProperty("name", journal.getName());
            returnValue.addProperty("balance", journal.getBalance());

            if(pathData.has("accountId")) {
                String accountId = pathData.get("accountId").getAsString();
                String href = StringUtils.removeEnd(request.getRequestURL().toString(), request.getPathInfo());
                returnValue.addProperty("href", href + "/account/" + accountId + "/journal/" + journal.getId());

                JsonObject entries = new JsonObject();
                entries.addProperty("href", href + "/account/" + accountId +
                        "/journal/" + journal.getId() + "/entries");
                returnValue.add("entries",entries);
            }

            return returnValue;
        }
    }

    private class EntrySerializer implements com.google.gson.JsonSerializer<Entry> {
        @Override
        public JsonElement serialize(Entry entry, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject returnValue = new JsonObject();

            returnValue.addProperty("id", entry.getId());
            returnValue.addProperty("amount", entry.getAmount());
            returnValue.addProperty("dateTime", entry.getDateFormatted());

            if(pathData.has("accountId") && pathData.has("journalId")) {
                String accountId = pathData.get("accountId").getAsString();
                String journalId = pathData.get("journalId").getAsString();
                String href = StringUtils.removeEnd(request.getRequestURL().toString(), request.getPathInfo());
                returnValue.addProperty("href",
                        href + "/account/" + accountId + "/journal/" + journalId + "/entry/" + entry.getId());
            }

            return returnValue;
        }
    }


}

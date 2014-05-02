package com.money.endpoints;

import com.endpoints.Endpoint;
import com.google.gson.*;
import com.money.model.Account;
import com.money.services.AccountsService;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by michal on 02/05/14.
 */
public class AccountEndpoint extends Endpoint {

    private Gson gson;
    private String id;

    public AccountEndpoint(HttpServletRequest request) {
        super(request);
        gson = new GsonBuilder()
                .registerTypeAdapter(Account.class, new AccountSerializer())
                .create();
    }

    @Override
    public boolean validateAndPopulateData() {
        boolean returnValue = false;

        String[] pathInfoArray =
                StringUtils.split(getRequest().getPathInfo(), '/');

        if(pathInfoArray!=null &&
                pathInfoArray.length==2 &&
                pathInfoArray[0].equals("account") &&
                StringUtils.isNotBlank(pathInfoArray[1]) &&
                getRequest().getMethod().equals("GET")) {
            this.id = pathInfoArray[1];
            returnValue = true;
        }

        return returnValue;
    }

    @Override
    public String process() {
        Account account = AccountsService.INSTANCE
                .get(id);
        return gson.toJsonTree(account).toString();
    }

    private class AccountSerializer implements JsonSerializer<Account> {
        @Override
        public JsonElement serialize(Account account,
                                     Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject returnValue = new JsonObject();
            returnValue.addProperty("id", account.getId());
            return returnValue;
        }
    }
}

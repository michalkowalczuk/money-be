package com.money.endpoints.account;

import com.endpoints.Endpoint;
import com.endpoints.EndpointPathUtils;
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

        JsonObject pathData = EndpointPathUtils.INSTANCE
                .getDataFromPath(getRequest().getPathInfo(), "/account/{accountId}");

        if(pathData!=null && getRequest().getMethod().equals("GET")) {
            this.id = pathData.get("accountId").getAsString();
            returnValue = true;
        }

        return returnValue;
    }

    @Override
    public String process() {
        Account account = AccountsService.INSTANCE.get(id);
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

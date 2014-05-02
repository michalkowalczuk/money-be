package com.money.endpoints.account;

import com.endpoints.Endpoint;
import com.endpoints.EndpointPathUtils;
import com.google.gson.*;
import com.money.model.Account;
import com.money.services.AccountsService;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;

/**
 * Created by michal on 02/05/14.
 */
public class AddAccountEndpoint extends Endpoint {

    private Gson gson;

    public AddAccountEndpoint(HttpServletRequest request) {
        super(request);
        gson = new GsonBuilder()
                .registerTypeAdapter(Account.class, new AccountSerializer())
                .create();
    }

    @Override
    public boolean validateAndPopulateData() {
        JsonObject pathData = EndpointPathUtils.INSTANCE
                .getDataFromPath(getRequest().getPathInfo(), "/accounts");
        return pathData!=null &&
                getRequest().getMethod().equals("POST");
    }

    @Override
    public String process() {
        Account newAccount = Account.newInstance();
        AccountsService.INSTANCE.add(newAccount);
        return gson.toJsonTree(newAccount).toString();
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

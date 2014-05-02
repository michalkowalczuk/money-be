package com.money.endpoints.account;

import com.endpoints.Endpoint;
import com.endpoints.EndpointPathUtils;
import com.google.gson.*;
import com.money.model.Account;
import com.money.services.AccountsService;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by michal on 02/05/14.
 */
public class AccountsEndpoint extends Endpoint {

    private Gson gson;

    public AccountsEndpoint(HttpServletRequest request) {
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
                getRequest().getMethod().equals("GET");
    }

    @Override
    public String process() {
        List<Account> accountList =
                AccountsService.INSTANCE.getAll();
        return gson.toJsonTree(accountList).toString();
    }

    private class AccountSerializer implements JsonSerializer<Account> {
        @Override
        public JsonElement serialize(Account account,
                                     Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject returnValue = new JsonObject();
            String href =
                    StringUtils.removeEnd(getRequest().getRequestURL().toString(), getRequest().getPathInfo());
            returnValue.addProperty("href", href + "/account/" + account.getId());
            return returnValue;
        }
    }
}

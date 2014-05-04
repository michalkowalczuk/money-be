package com.money.endpoints;

import com.endpoints.Endpoint;
import com.google.gson.JsonObject;
import com.money.model.Account;
import com.money.serializer.JsonSerializer;
import com.money.services.AccountsService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by michal on 5/3/14.
 */
public class AccountGet extends Endpoint {

    public AccountGet(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getPath() {
        return "/account/{accountId}";
    }

    @Override
    public String getMethod() {
        return "GET";
    }

    @Override
    public String process() {
        String returnValue = new JsonObject().toString();
        Account account = AccountsService.INSTANCE.get(getPathData().get("accountId").getAsString());
        if(account!=null) {
            returnValue = new JsonSerializer(getRequest(), getPathData()).serialize(account).toString();
        }
        return returnValue;
    }
}
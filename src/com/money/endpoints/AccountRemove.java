package com.money.endpoints;

import com.endpoints.Endpoint;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.money.model.Account;
import com.money.services.AccountsService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by michal on 5/3/14.
 */
public class AccountRemove extends Endpoint {

    public AccountRemove(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getPath() {
        return "/account/{accountId}";
    }

    @Override
    public String getMethod() {
        return "DELETE";
    }

    @Override
    public String process() {
        AccountsService.INSTANCE.remove(getPathData().get("accountId").getAsString());
        return new JSONObject().toString();
    }
}

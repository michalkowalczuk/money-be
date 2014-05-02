package com.money.endpoints.account;

import com.endpoints.Endpoint;
import com.endpoints.EndpointPathUtils;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by michal on 02/05/14.
 */
public class AccountsJournalsEndpoint extends Endpoint {

    public AccountsJournalsEndpoint(HttpServletRequest request) {
        super(request);
    }

    @Override
    public boolean validateAndPopulateData() {
        JsonObject data = EndpointPathUtils.INSTANCE
                .getDataFromPath(getRequest().getPathInfo(), "/account/{accountId}/journals");
        return data!=null;
    }

    @Override
    public String process() {
        return new JsonObject().toString();
    }
}

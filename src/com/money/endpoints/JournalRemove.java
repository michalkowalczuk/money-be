package com.money.endpoints;

import com.endpoints.Endpoint;
import com.google.gson.JsonObject;
import com.money.services.JournalService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by michal on 5/3/14.
 */
public class JournalRemove extends Endpoint {

    public JournalRemove(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getPath() {
        return "/account/{accountId}/journal/{journalId}";
    }

    @Override
    public String getMethod() {
        return "DELETE";
    }

    @Override
    public String process() {
        JournalService.INSTANCE.remove(getPathData().get("journalId").getAsString());
        return new JsonObject().toString();
    }
}
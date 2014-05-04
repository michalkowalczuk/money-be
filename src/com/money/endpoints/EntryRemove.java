package com.money.endpoints;

import com.endpoints.Endpoint;
import com.google.gson.JsonObject;
import com.money.services.EntryService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by michal on 5/3/14.
 */
public class EntryRemove extends Endpoint {

    public EntryRemove(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getPath() {
        return "/account/{accountId}/journal/{journalId}/entry/{entryId}";
    }

    @Override
    public String getMethod() {
        return "DELETE";
    }

    @Override
    public String process() {
        EntryService.INSTANCE.remove(getPathData().get("entryId").getAsString());
        return new JsonObject().toString();
    }

}

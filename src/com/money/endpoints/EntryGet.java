package com.money.endpoints;

import com.endpoints.Endpoint;
import com.google.gson.JsonObject;
import com.money.model.Entry;
import com.money.serializer.JsonSerializer;
import com.money.services.EntryService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by michal on 5/3/14.
 */
public class EntryGet extends Endpoint {

    public EntryGet(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getPath() {
        return "/account/{accountId}/journal/{journalId}/entry/{entryId}";
    }

    @Override
    public String getMethod() {
        return "GET";
    }

    @Override
    public String process() {

        String returnValue = new JsonObject().toString();

        Entry entry = EntryService.INSTANCE.get(getPathData().get("entryId").getAsString());
        if(entry!=null) {
            returnValue = new JsonSerializer(getRequest(), getPathData()).serialize(entry).toString();
        }

        return returnValue;
    }

}

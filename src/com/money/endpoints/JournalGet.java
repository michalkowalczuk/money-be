package com.money.endpoints;

import com.endpoints.Endpoint;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.money.model.Journal;
import com.money.serializer.JsonSerializer;
import com.money.services.JournalService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by michal on 5/3/14.
 */
public class JournalGet extends Endpoint {

    public JournalGet(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getPath() {
        return "/account/{accountId}/journal/{journalId}";
    }

    @Override
    public String getMethod() {
        return "GET";
    }

    @Override
    public String process() {
        String returnValue = new JSONObject().toString();
        Journal journal = JournalService.INSTANCE
                .get(getPathData().get("journalId").getAsString());
        if(journal!=null) {
            returnValue = new JsonSerializer(getRequest(), getPathData())
                    .serialize(journal).toString();
        }
        return returnValue;
    }
}
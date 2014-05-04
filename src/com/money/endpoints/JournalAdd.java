package com.money.endpoints;

import com.endpoints.Endpoint;
import com.google.appengine.repackaged.com.google.api.client.json.Json;
import com.money.model.Journal;
import com.money.serializer.JsonSerializer;
import com.money.services.JournalService;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Created by michal on 5/3/14.
 */
public class JournalAdd extends Endpoint {

    public JournalAdd(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getPath() {
        return "/account/{accountId}/journals";
    }

    @Override
    public String getMethod() {
        return "POST";
    }

    @Override
    public String process() {
        Journal journal = new Journal();
        journal.setId(UUID.randomUUID().toString());
        JournalService.INSTANCE.add(
                getPathData().get("accountId").getAsString(),
                journal
        );
        String returnValue = new JsonSerializer(getRequest(), getPathData())
                .serialize(journal).toString();
        return returnValue;
    }
}

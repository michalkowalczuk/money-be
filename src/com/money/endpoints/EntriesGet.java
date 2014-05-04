package com.money.endpoints;

import com.endpoints.Endpoint;
import com.money.model.Entry;
import com.money.serializer.JsonSerializer;
import com.money.services.EntryService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by michal on 5/3/14.
 */
public class EntriesGet extends Endpoint {

    public EntriesGet(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getPath() {
        return "/account/{accountId}/journal/{journalId}/entries";
    }

    @Override
    public String getMethod() {
        return "GET";
    }

    @Override
    public String process() {

        List<Entry> entryList = EntryService.INSTANCE
                .getAll(getPathData().get("journalId").getAsString());

        return new JsonSerializer(getRequest(), getPathData()).serialize(entryList).toString();
    }
}

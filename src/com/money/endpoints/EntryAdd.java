package com.money.endpoints;

import com.endpoints.Endpoint;
import com.money.model.Entry;
import com.money.serializer.JsonSerializer;
import com.money.services.EntryService;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Created by michal on 5/3/14.
 */
public class EntryAdd extends Endpoint {

    public EntryAdd(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getPath() {
        return "/account/{accountId}/journal/{journalId}/entries";
    }

    @Override
    public String getMethod() {
        return "POST";
    }

    @Override
    public String process() {
        Entry entry = new Entry();
        entry.setId(UUID.randomUUID().toString());

        EntryService.INSTANCE.add(getPathData().get("journalId").getAsString(), entry);

        return new JsonSerializer(getRequest(), getPathData()).serialize(entry).toString();
    }
}

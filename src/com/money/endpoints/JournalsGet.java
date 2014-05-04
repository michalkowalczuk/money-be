package com.money.endpoints;

import com.endpoints.Endpoint;
import com.money.model.Journal;
import com.money.serializer.JsonSerializer;
import com.money.services.JournalService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by michal on 5/3/14.
 */
public class JournalsGet extends Endpoint {

    public JournalsGet(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getPath() {
        return "/account/{accountId}/journals";
    }

    @Override
    public String getMethod() {
        return "GET";
    }

    @Override
    public String process() {
        List<Journal> journalList =
                JournalService.INSTANCE.getAll(getPathData().get("accountId").getAsString());
        String returnValue = new JsonSerializer(getRequest(), getPathData())
                .serialize(journalList).toString();
        return returnValue.toString();
    }
}
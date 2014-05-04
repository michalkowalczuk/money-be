package com.money.endpoints;

import com.endpoints.Endpoint;
import com.google.gson.JsonObject;
import com.money.model.Journal;
import com.money.serializer.JsonSerializer;
import com.money.services.JournalService;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by michal on 5/4/14.
 */
public class JournalUpdate extends Endpoint {


    public JournalUpdate(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getPath() {
        return "/account/{accountId}/journal/{journalId}";
    }

    @Override
    public String getMethod() {
        return "PUT";
    }

    @Override
    public String process() {

        String returnValue = new JsonObject().toString();

        if(StringUtils.isNotBlank(getRequest().getParameter("name"))) {
            Journal journal = JournalService.INSTANCE.get(getPathData().get("journalId").getAsString());
            if(journal!=null) {
                journal.setName(getRequest().getParameter("name"));
                JournalService.INSTANCE.update(journal);

                returnValue = new JsonSerializer(getRequest(), getPathData()).serialize(journal).toString();
            }
        }

        return returnValue;
    }
}

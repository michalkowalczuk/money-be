package com.money.servlets;

import com.endpoints.EndpointController;
import com.endpoints.EndpointServlet;
import com.money.endpoints.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by michal on 02/05/14.
 */
public class ApiServletV1 extends EndpointServlet {

    @Override
    public void processRequest(HttpServletRequest request,
                               HttpServletResponse response) throws IOException {

        response.addHeader("Access-Control-Allow-Origin", "*");

        String result = new EndpointController()
                .addEndpoint(new AccountAdd(request))
                .addEndpoint(new AccountGet(request))
                .addEndpoint(new AccountRemove(request))
                .addEndpoint(new AccountsGet(request))
                .addEndpoint(new EntriesGet(request))
                .addEndpoint(new EntryAdd(request))
                .addEndpoint(new EntryGet(request))
                .addEndpoint(new EntryRemove(request))
                .addEndpoint(new JournalAdd(request))
                .addEndpoint(new JournalGet(request))
                .addEndpoint(new JournalRemove(request))
                .addEndpoint(new JournalsGet(request))
                .getEndpointResult();

        response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(result);

    }
}

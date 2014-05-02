package com.money.servlets;

import com.endpoints.EndpointController;
import com.endpoints.EndpointServlet;
import com.money.endpoints.AccountEndpoint;
import com.money.endpoints.AccountsEndpoint;
import com.money.endpoints.AddAccountEndpoint;

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
                .addEndpoint(new AccountsEndpoint(request))
                .addEndpoint(new AccountEndpoint(request))
                .addEndpoint(new AddAccountEndpoint(request))
                .getEndpointResult();

        response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(result);

    }
}

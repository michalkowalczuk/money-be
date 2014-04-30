package com.test;

import com.google.gson.JsonObject;
import com.michal.endpoints.Endpoint;
import com.michal.endpoints.EndpointController;
import com.michal.endpoints.EndpointsServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by michal on 4/30/14.
 */
public class Test extends EndpointsServlet {

    @Override
    public void processRequest(final HttpServletRequest request,
                               HttpServletResponse response) throws IOException {

        response.addHeader("Access-Control-Allow-Origin", "*");

        String result = new EndpointController()
                .addEndpoint(new Endpoint(request) {

                    @Override
                    public boolean validateAndPopulateData() {
                        return getRequest().getPathInfo().matches("/test$");
                    }

                    @Override
                    public String process() {
                        JsonObject test = new JsonObject();
                        test.addProperty("hello","world");
                        return test.toString();
                    }

                })
                .getEndpointResult();

        response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(result);

    }
}

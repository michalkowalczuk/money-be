package com.endpoints;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by michal on 02/05/14.
 */
public abstract class Endpoint {
    private HttpServletRequest request;

    public Endpoint(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletRequest getRequest() {
        return this.request;
    }

    public abstract boolean validateAndPopulateData();

    public abstract String process();
}

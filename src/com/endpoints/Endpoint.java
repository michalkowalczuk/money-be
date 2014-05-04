package com.endpoints;

import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by michal on 02/05/14.
 */
public abstract class Endpoint {
    private HttpServletRequest request;
    private JsonObject pathData;

    public Endpoint(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletRequest getRequest() {
        return this.request;
    }

    public void setPathData(JsonObject pathData) {
        this.pathData = pathData;
    }

    public JsonObject getPathData() {
        return pathData;
    }

    public boolean validateAndPopulateData() {
        boolean returnValue = false;
        boolean correctMethod = getRequest().getMethod().equals(getMethod());
        JsonObject data = EndpointPathUtils.INSTANCE.getDataFromPath(
                getRequest().getPathInfo(),
                getPath()
        );
        if(data!=null && correctMethod) {
            setPathData(data);
            returnValue = true;
        }

        return returnValue;
    };

    public abstract String getPath();

    public abstract String getMethod();

    public abstract String process();



}

package com.endpoints;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michal on 02/05/14.
 */
public class EndpointController {
    private List<Endpoint> endpoints;

    private JsonObject defaultNoEndpoint;
    private JsonObject defaultError;

    public EndpointController() {
        this.endpoints = new ArrayList<Endpoint>();
        defaultError = new JsonObject();
        defaultNoEndpoint = new JsonObject();
        defaultNoEndpoint.addProperty("error", "endpoint not found");
    }

    public EndpointController addEndpoint(Endpoint endpoint) {
        this.endpoints.add(endpoint);
        return this;
    }

    public String getEndpointResult() {
        String returnValue = defaultNoEndpoint.toString();
        for (Endpoint endpoint : this.endpoints) {
            if (endpoint.validateAndPopulateData()) {
                try {
                    returnValue = endpoint.process();
                } catch (Exception e) {
                    defaultError.addProperty("error", e.getMessage());
                    returnValue = defaultError.toString();
                }
                break;
            }
        }
        return returnValue;
    }
}

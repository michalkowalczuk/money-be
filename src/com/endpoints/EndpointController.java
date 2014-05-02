package com.endpoints;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michal on 02/05/14.
 */
public class EndpointController {
    private List<Endpoint> endpoints;

    public EndpointController() {
        this.endpoints = new ArrayList();
    }

    public EndpointController addEndpoint(Endpoint endpoint) {
        this.endpoints.add(endpoint);
        return this;
    }

    public String getEndpointResult() {
        String returnValue = null;
        for (Endpoint endpoint : this.endpoints) {
            if (endpoint.validateAndPopulateData()) {
                returnValue = endpoint.process();
                break;
            }
        }
        return returnValue;
    }
}

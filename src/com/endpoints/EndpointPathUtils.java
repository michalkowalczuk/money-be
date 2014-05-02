package com.endpoints;

import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by michal on 02/05/14.
 */
public enum EndpointPathUtils {

    INSTANCE;

    public JsonObject getDataFromPath(String pathInfo, String pathPattern) {

        JsonObject returnValue = null;

        String[] pathInfoElements = StringUtils.split(pathInfo, '/');
        String[] pathPatternElements = StringUtils.split(pathPattern, '/');

        if (pathInfoElements != null && pathPatternElements != null &&
                pathInfoElements.length == pathPatternElements.length) {

            returnValue = new JsonObject();

            for (int i = 0; i < pathPatternElements.length; i++) {
                if (pathPatternElements[i].matches("^\\{.*\\}$")) {
                    String parameterName = StringUtils.mid(
                            pathPatternElements[i],
                            1, pathPatternElements[i].length() - 2
                    );
                    returnValue.addProperty(parameterName, pathInfoElements[i]);
                } else if (!pathPatternElements[i].equals(pathInfoElements[i])) {
                    returnValue = null;
                    break;
                }
            }
        }

        return returnValue;
    }

}

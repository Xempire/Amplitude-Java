package com.amplitude;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public abstract class Payload {

    public String userId;
    public String deviceId;

    public long timestamp;

    public Payload(String userId) {
        this(userId, null);
    }

    public Payload(String userId, String deviceId) {
        if (userId == null && deviceId == null) {
            throw new IllegalArgumentException("Event must have one defined userId and/or deviceId");
        }
        this.userId = userId;
        this.deviceId = deviceId;
    }

    abstract String getApiUrl();
    abstract String getJsonPropertyKey();
    abstract JSONObject toJsonObject();

    /**
     internal method
     */
    protected Object replaceWithJSONNull(Object obj) {
        return obj == null ? JSONObject.NULL : obj;
    }

    protected JSONObject truncate(JSONObject object) {
        if (object == null) {
            return new JSONObject();
        }

        if (object.length() > Constants.MAX_PROPERTY_KEYS) {
            throw new IllegalArgumentException("Too many properties (more than " + Constants.MAX_PROPERTY_KEYS + ") in JSON");
        }

        Iterator<?> keys = object.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();

            try {
                Object value = object.get(key);
                if (value.getClass().equals(String.class)) {
                    object.put(key, truncate((String) value));
                } else if (value.getClass().equals(JSONObject.class)) {
                    object.put(key, truncate((JSONObject) value));
                } else if (value.getClass().equals(JSONArray.class)) {
                    object.put(key, truncate((JSONArray) value));
                }
            } catch (JSONException e) {
                throw new IllegalArgumentException("JSON parsing error. Too long (>" +
                        Constants.MAX_STRING_LENGTH + " chars) or invalid JSON");
            }
        }

        return object;
    }

    protected JSONArray truncate(JSONArray array) throws JSONException {
        if (array == null) {
            return new JSONArray();
        }

        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value.getClass().equals(String.class)) {
                array.put(i, truncate((String) value));
            } else if (value.getClass().equals(JSONObject.class)) {
                array.put(i, truncate((JSONObject) value));
            } else if (value.getClass().equals(JSONArray.class)) {
                array.put(i, truncate((JSONArray) value));
            }
        }
        return array;
    }

    protected static String truncate(String value) {
        return value.length() <= Constants.MAX_STRING_LENGTH ? value :
                value.substring(0, Constants.MAX_STRING_LENGTH);
    }
}

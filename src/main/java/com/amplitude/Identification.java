package com.amplitude;

import org.json.JSONException;
import org.json.JSONObject;

public class Identification extends Payload {

    public JSONObject set;
    public JSONObject setOnce;
    public JSONObject add;
    public JSONObject append;
    public JSONObject prepend;
    public JSONObject unset;

    public Identification(String userId) {
        super(userId);
    }

    @Override
    String getApiUrl() {
        return Constants.IDENTIFY_URL;
    }

    @Override
    String getJsonPropertyKey() {
        return "identification";
    }

    public JSONObject toJsonObject() {
        //curl --data 'api_key=API_KEY' --data 'identification=[{"user_properties":{"Age":"35"}, "country":"United States"}]' https://api.amplitude.com/identify
        JSONObject userProperties = new JSONObject();
        if (set != null && set.length() > 0) {
            userProperties.put("$set", truncate(set));
        }
        if (setOnce != null && setOnce.length() > 0) {
            userProperties.put("$setOnce", truncate(setOnce));
        }
        if (add != null && add.length() > 0) {
            userProperties.put("$add", truncate(add));
        }
        if (append != null && append.length() > 0) {
            userProperties.put("$append", truncate(append));
        }
        if (prepend != null && prepend.length() > 0) {
            userProperties.put("prepend", truncate(prepend));
        }
        if (unset != null && unset.length() > 0) {
            userProperties.put("$unset", truncate(unset));
        }
        JSONObject identification = new JSONObject();
        try {
            identification.put("user_id", userId);
            identification.put("user_properties", truncate(userProperties));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return identification;
    }

}

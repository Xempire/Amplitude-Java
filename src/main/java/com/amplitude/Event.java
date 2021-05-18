package com.amplitude;

import org.json.JSONException;
import org.json.JSONObject;

public class Event extends Payload {

    public String eventType;
    public double locationLat;
    public double locationLng;

    public String appVersion;
    public String versionName;

    public String platform;
    public String osName;
    public String deviceBrand;
    public String deviceManufacturer;
    public String deviceModel;
    public String carrier;

    public String country;
    public String region;
    public String city;
    public String dma;

    public String idfa;
    public String idfv;
    public String adid;
    public String androidId;

    public String language;
    public String ip;
    public JSONObject eventProperties;
    public JSONObject userProperties;

    public double price;
    public int quantity;
    public double revenue;
    public int productId;
    public String revenueType;

    public int eventId;
    public int sessionId;
    public int insertId;

    public JSONObject groups;
    public JSONObject groupProperties;

    public Event(String eventType, String userId) {
        super(userId);
        this.eventType = eventType;
    }

    public Event(String eventType, String userId, String deviceId) {
        super(userId, deviceId);
        this.eventType = eventType;
    }

    @Override
    String getApiUrl() {
        return Constants.API_URL;
    }

    @Override
    String getJsonPropertyKey() {
        return "events";
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject event = new JSONObject();
        try {
            event.put("event_type", eventType);
            event.put("user_id", replaceWithJSONNull(userId));
            event.put("device_id", replaceWithJSONNull(deviceId));
            event.put("time", timestamp);
            event.put("location_lat", locationLat);
            event.put("location_lng", locationLng);
            event.put("app_version", appVersion);
            event.put("version_name", replaceWithJSONNull(versionName));
            event.put("library", Constants.SDK_LIBRARY + "/" + Constants.SDK_VERSION);
            event.put("platform", replaceWithJSONNull(platform));
            event.put("os_name", replaceWithJSONNull(osName));
            event.put("device_brand", replaceWithJSONNull(deviceBrand));
            event.put("device_manufacturer", replaceWithJSONNull(deviceManufacturer));
            event.put("device_model", replaceWithJSONNull(deviceModel));
            event.put("carrier", replaceWithJSONNull(carrier));
            event.put("country", replaceWithJSONNull(country));
            event.put("region", replaceWithJSONNull(region));
            event.put("city", replaceWithJSONNull(city));
            event.put("dma", replaceWithJSONNull(dma));
            event.put("idfa", replaceWithJSONNull(idfa));
            event.put("idfv", replaceWithJSONNull(idfv));
            event.put("adid", replaceWithJSONNull(adid));
            event.put("android_id", replaceWithJSONNull(androidId));
            event.put("language", replaceWithJSONNull(language));
            event.put("ip", replaceWithJSONNull(ip));
            event.put("event_properties", (eventProperties == null) ? new JSONObject() : truncate(eventProperties));
            event.put("user_properties",(userProperties == null) ? new JSONObject() : truncate(userProperties));
            event.put("price", price);
            event.put("quantity", quantity);
            event.put("revenue", revenue);
            event.put("productId", productId);
            event.put("revenueType", revenueType);
            event.put("event_id", replaceWithJSONNull(eventId));
            event.put("session_id", sessionId); // session_id = -1 if outOfSession = true;
            event.put("insert_id", insertId);
            event.put("groups", (groups == null) ? new JSONObject() : truncate(groups));
            event.put("group_properties", (groupProperties == null) ? new JSONObject()
                    : truncate(groupProperties));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return event;
    }
}

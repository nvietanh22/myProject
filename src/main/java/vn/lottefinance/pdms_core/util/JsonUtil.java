package vn.lottefinance.pdms_core.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JsonUtil {
    public static String getStringValue(String key, JsonObject json) {
        if (json.has(key) && !json.get(key).isJsonNull()) {
            return json.get(key).getAsString();
        }
        return null;
    }

    public static Integer getIntValue(String key, JsonObject json) {
        if (json.has(key) && !json.get(key).isJsonNull()) {
            return json.get(key).getAsInt();
        }
        return null;
    }

    public static Boolean getBooleanValue(String key, JsonObject json) {
        if (json.has(key) && !json.get(key).isJsonNull()) {
            return json.get(key).getAsBoolean();
        }
        return null;
    }

    public static JsonObject getJsonObject(String key, JsonObject json) {
        if (json.has(key) && !json.get(key).isJsonNull()) {
            return json.get(key).getAsJsonObject();
        }
        return null;
    }

    public static JsonArray getJsonArray(String key, JsonObject json) {
        if (json.has(key) && !json.get(key).isJsonNull()) {
            return json.get(key).getAsJsonArray();
        }
        return null;
    }

    public static Long getLongValue(String key, JsonObject json) {
        if (json.has(key) && !json.get(key).isJsonNull()) {
            return json.get(key).getAsLong();
        }
        return null;
    }
}

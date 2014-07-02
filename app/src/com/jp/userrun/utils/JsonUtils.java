package com.jp.userrun.utils;

import android.util.JsonReader;
import android.util.JsonToken;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;

public class JsonUtils {
    private static Gson sGson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ")
            .create();

    public static String toJson(Object entity) {
        return sGson.toJson(entity);
    }

    public static JSONObject toJsonObj(Object entity) throws JSONException {
        return new JSONObject(toJson(entity));
    }

    public static Object fromJson(String jsonStr, Type type) {
        return sGson.fromJson(jsonStr, type);
    }

    public static <T> T fromJson(Reader reader, Class<T> type) {
        return sGson.fromJson(reader, type);
    }

    /**
     * 从JsonReader读取一个JsonObject出来
     *
     * @param reader instance of JsonReader
     * @return JSONObject
     * @throws java.io.IOException
     * @throws JSONException
     */
    public static JSONObject readJSONObject(JsonReader reader) throws IOException, JSONException {
        reader.beginObject();
        JSONObject result = new JSONObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            Object value = null;
            JsonToken next = reader.peek();
            if (next == JsonToken.BEGIN_ARRAY) {
                value = new JSONArray();
                reader.beginArray();
                while (reader.hasNext()) {
                    ((JSONArray) value).put(readJSONObject(reader));
                }
                reader.endArray();
            } else if (next == JsonToken.BEGIN_OBJECT) {
                value = readJSONObject(reader);
            } else if (next == JsonToken.BOOLEAN) {
                value = reader.nextBoolean();
            } else if (next == JsonToken.NUMBER) {
                value = reader.nextLong();
            } else if (next == JsonToken.STRING) {
                value = reader.nextString();
            } else if (next == JsonToken.NULL) {
                reader.nextNull();
            } else {
                reader.skipValue();
            }

            result.put(name, value);
        }
        reader.endObject();

        return result;
    }
}

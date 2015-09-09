package com.lizeda.library.http.callback;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.lizeda.library.core.utils.GenericsUtils;
import com.orhanobut.logger.Logger;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 作者：lemon
 * 日期：2015-09-08
 */
public class GsonCallback<T> extends ResponseCallback {

    private Type type;
    private Gson gson = new Gson();

    public GsonCallback() {
        type = GenericsUtils.getSuperClassGenricType(getClass());
        Logger.d("type == " + type);
    }

    @Override
    public void onResponse(final Response response) throws IOException {
        try {
            final int statusCode = response.code();
            final String responseString = response.body().string();
            T t = parse(responseString);
            sendOnSuccessMessage(statusCode, t);
        } catch (final IOException e) {
            sendOnErrorMessage(response.request(), e);
            e.printStackTrace();
        } catch (final com.google.gson.JsonParseException e) {
            sendOnErrorMessage(response.request(), new JsonParseException("parse failure"));
        }
    }

    @Override
    public void onSuccess(int statusCode, Object response) {
        onSuccess((T) response);
    }

    public void onSuccess(T response) {
    }

    public T parse(String json) {
        Logger.d(json);
        return gson.fromJson(json, type);
    }
}

package com.lizeda.library.http.callback;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.$Gson$Types;
import com.orhanobut.logger.Logger;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 作者：lemon
 * 日期：2015-09-08
 */
public class GsonCallback<T> extends ResponseCallback {

    private final static String TAG = GsonCallback.class.getSimpleName();

    private Gson gson = new Gson();
    private Type mType;

    public GsonCallback() {
        mType = getSuperclassTypeParameter(getClass());
        Logger.d("ParameterizedType == " + mType);
    }

   private Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    @Override
    public void onSuccess(int statusCode, byte[] responseBytes) {

        T t = parse(new String(responseBytes));
        if (t != null) {
            onSuccess(t);
        } else {
            onFailure(null, new JsonParseException("parse failure"));
        }
    }

    public void onSuccess(T responseJson) {
    }

    public T parse(String json) {
        Logger.d("GsonCallback json == " + json);
        return gson.fromJson(json, mType);
    }

}

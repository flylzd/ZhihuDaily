package com.lizeda.library.okhttp;

import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Request;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 作者：lemon
 * 日期：2015-09-06
 */
public abstract class ResultCallback<T> {

    private Type mType;

    public ResultCallback() {
        mType = getSuperclassTypeParameter(getClass());
    }

    Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public Type getType() {
        return mType;
    }

    public void onStart(Request request) {

    }

    public void onEnd() {
    }

    public abstract void onError(Request request, Exception e);

    public abstract void onResponse(T response);

}

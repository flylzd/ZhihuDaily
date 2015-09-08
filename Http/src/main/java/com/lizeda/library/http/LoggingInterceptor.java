package com.lizeda.library.http;

import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;


public class LoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(final Chain chain) throws IOException {
        long t1 = System.nanoTime();
        final Request request = chain.request();
        Log.v(AsyncOKHttpClient.TAG, String.format("Sending http request --> %s %s on %s%n%s",
                request.method(), request.url(), chain.connection(), request.headers()));
        final Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        Log.v(AsyncOKHttpClient.TAG, String.format("Received http response --> %s %s (%s:%s) in %.1fms%n%s",
                request.method(), request.url(), response.code(), response.message()
                , (t2 - t1) / 1e6d, response.headers()));
        return response;
    }
}

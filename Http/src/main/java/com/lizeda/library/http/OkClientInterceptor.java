package com.lizeda.library.http;

import com.squareup.okhttp.OkHttpClient;

public interface OkClientInterceptor {

    void intercept(OkHttpClient client);
}

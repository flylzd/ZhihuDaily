package com.lizeda.library.http;



public interface RequestInterceptor {
    void intercept(final XRequest request);
}
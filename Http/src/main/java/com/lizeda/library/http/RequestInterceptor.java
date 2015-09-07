package com.lizeda.library.http;

/**
 * User: mcxiaoke
 * Date: 14-2-8
 * Time: 11:22
 */
public interface RequestInterceptor {
    void intercept(final XRequest request);
}
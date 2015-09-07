package com.lizeda.library.http;


import com.lizeda.library.http.callback.ResponseCallback;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

public final class HttpClient {

    static class SingletonHolder {
        static HttpClient INSTANCE = new HttpClient();
    }

    public static HttpClient getDefault() {
        return SingletonHolder.INSTANCE;
    }

    public static final String TAG = HttpClient.class.getSimpleName();
    private boolean mDebug;
    private final OkHttpClient mClient;
    private OkClientInterceptor mInterceptor;
    private Map<String, String> mParams;
    private Map<String, String> mHeaders;

    public HttpClient() {
        mClient = new OkHttpClient();
        mClient.setFollowRedirects(true);
        mParams = new NoEmptyValuesHashMap();
        mHeaders = new NoEmptyValuesHashMap();
    }


    /**
     * ********************************************************
     * CLIENT PARAMS AND HEADERS
     * **********************************************************
     */

    public String getParam(final String key) {
//        AssertUtils.notEmpty(key, "key must not be null or empty.");
        return mParams.get(key);
    }

    public HttpClient addParam(final String key, final String value) {
//        AssertUtils.notEmpty(key, "key must not be null or empty.");
        mParams.put(key, value);
        return this;
    }

    public HttpClient addParams(final Map<String, String> params) {
//        AssertUtils.notNull(params, "params must not be null.");
        mParams.putAll(params);
        return this;
    }

    public HttpClient removeParam(final String key) {
//        AssertUtils.notEmpty(key, "key must not be null or empty.");
        mParams.remove(key);
        return this;
    }


    public int getParamsSize() {
        return mParams.size();
    }

    public String getHeader(final String key) {
//        AssertUtils.notEmpty(key, "key must not be null or empty.");
        return mHeaders.get(key);
    }

    public HttpClient addHeader(final String key, final String value) {
//        AssertUtils.notEmpty(key, "key must not be null or empty.");
        mHeaders.put(key, value);
        return this;
    }

    public HttpClient addHeaders(final Map<String, String> headers) {
//        AssertUtils.notNull(headers, "headers must not be null.");
        mHeaders.putAll(headers);
        return this;
    }

    public HttpClient removeHeader(final String key) {
//        AssertUtils.notEmpty(key, "key must not be null or empty.");
        mHeaders.remove(key);
        return this;
    }


    public int getHeadersSize() {
        return mHeaders.size();
    }

    public HttpClient setInterceptor(final OkClientInterceptor interceptor) {
        mInterceptor = interceptor;
        return this;
    }

    public HttpClient setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        mClient.setHostnameVerifier(hostnameVerifier);
        return this;
    }

    public HttpClient setSocketFactory(SocketFactory socketFactory) {
        mClient.setSocketFactory(socketFactory);
        return this;
    }

    public HttpClient setSslSocketFactory(SSLSocketFactory sslSocketFactory) {
        mClient.setSslSocketFactory(sslSocketFactory);
        return this;
    }

    public HttpClient setFollowRedirects(boolean followRedirects) {
        mClient.setFollowRedirects(followRedirects);
        return this;
    }

    public HttpClient setFollowSslRedirects(boolean followProtocolRedirects) {
        mClient.setFollowSslRedirects(followProtocolRedirects);
        return this;
    }

    public HttpClient setRetryOnConnectionFailure(boolean retryOnConnectionFailure) {
        mClient.setRetryOnConnectionFailure(retryOnConnectionFailure);
        return this;
    }

    public int getConnectTimeout() {
        return mClient.getConnectTimeout();
    }

    public int getReadTimeout() {
        return mClient.getReadTimeout();
    }

    public int getWriteTimeout() {
        return mClient.getWriteTimeout();
    }

    public HttpClient setConnectTimeout(long timeout, TimeUnit unit) {
        mClient.setConnectTimeout(timeout, unit);
        return this;
    }

    public HttpClient setReadTimeout(long timeout, TimeUnit unit) {
        mClient.setReadTimeout(timeout, unit);
        return this;
    }

    public HttpClient setWriteTimeout(long timeout, TimeUnit unit) {
        mClient.setWriteTimeout(timeout, unit);
        return this;
    }

    public String getUserAgent() {
        return getHeader(HttpConsts.USER_AGENT);
    }

    public HttpClient setUserAgent(final String userAgent) {
        if (userAgent == null) {
            removeHeader(HttpConsts.USER_AGENT);
        } else {
            addHeader(HttpConsts.USER_AGENT, userAgent);
        }
        return this;
    }

    public String getAuthorization() {
        return getHeader(HttpConsts.AUTHORIZATION);
    }

    public HttpClient setAuthorization(final String authorization) {
        addHeader(HttpConsts.AUTHORIZATION, authorization);
        return this;
    }

    public HttpClient removeAuthorization() {
        removeHeader(HttpConsts.AUTHORIZATION);
        return this;
    }

    public String getRefer() {
        return getHeader(HttpConsts.REFERER);
    }

    public HttpClient setReferer(final String referer) {
        addHeader(HttpConsts.REFERER, referer);
        return this;
    }


    /**
     * ********************************************************
     * HTTP REQUEST METHODS
     * **********************************************************
     */

    public void head(final String url, final ResponseCallback responseCallback,
                     final Object tag) throws IOException {
        head(url, null, responseCallback, tag);
    }

    public void head(final String url, final Map<String, String> queries, final ResponseCallback responseCallback,
                     final Object tag) throws IOException {
        head(url, queries, null, responseCallback, tag);
    }

    public void head(final String url,
                     final Map<String, String> queries,
                     final Map<String, String> headers,
                     final ResponseCallback responseCallback,
                     final Object tag) throws IOException {
        request(HttpMethod.HEAD, url, queries, null, headers, responseCallback, tag);
    }

    public void get(final String url, final ResponseCallback responseCallback,
                    final Object tag) throws IOException {
        get(url, null, responseCallback, tag);
    }

    public void get(final String url, final Map<String, String> queries, final ResponseCallback responseCallback,
                    final Object tag) throws IOException {
        get(url, queries, null, responseCallback, tag);
    }

    public void get(final String url,
                    final Map<String, String> queries,
                    final Map<String, String> headers,
                    final ResponseCallback responseCallback,
                    final Object tag) throws IOException {
        request(HttpMethod.GET, url, queries, null, headers, responseCallback, tag);
    }


    public void post(final String url,
                     final Map<String, String> params,
                     final ResponseCallback responseCallback,
                     final Object tag) throws IOException {
        post(url, params, null, responseCallback, tag);
    }

    public void post(final String url,
                     final Map<String, String> params,
                     final Map<String, String> headers,
                     final ResponseCallback responseCallback,
                     final Object tag) throws IOException {
        request(HttpMethod.POST, url, null, params, headers, responseCallback, tag);
    }


    public void put(final String url, final Map<String, String> params, final ResponseCallback responseCallback,
                    final Object tag) throws IOException {
        put(url, params, null, responseCallback, tag);
    }

    public void put(final String url,
                    final Map<String, String> params,
                    final Map<String, String> headers,
                    final ResponseCallback responseCallback,
                    final Object tag) throws IOException {
        request(HttpMethod.PUT, url, null, params, headers, responseCallback, tag);
    }

    // put params into url queries
    public void deleteByQueryParams(final String url, final ResponseCallback responseCallback,
                                    final Object tag) throws IOException {
        deleteByQueryParams(url, null, null, responseCallback, tag);
    }

    // put params into url queries
    public void deleteByQueryParams(final String url, final Map<String, String> queries, final ResponseCallback responseCallback,
                                    final Object tag) throws IOException {
        deleteByQueryParams(url, queries, null, responseCallback, tag);
    }

    // put params into url queries
    public void deleteByQueryParams(final String url,
                                    final Map<String, String> queries,
                                    final Map<String, String> headers,
                                    final ResponseCallback responseCallback,
                                    final Object tag) throws IOException {
        request(HttpMethod.DELETE, url, queries, null, headers, responseCallback, tag);
    }

    // put params into  http request body
    public void deleteByBodyParams(final String url, final Map<String, String> params, final ResponseCallback responseCallback,
                                   final Object tag) throws IOException {
        deleteByBodyParams(url, params, null, responseCallback, tag);
    }

    // put params into  http request body
    public void deleteByBodyParams(final String url,
                                   final Map<String, String> params,
                                   final Map<String, String> headers,
                                   final ResponseCallback responseCallback,
                                   final Object tag) throws IOException {
        request(HttpMethod.DELETE, url, null, params, headers, responseCallback, tag);
    }


//    public void request(final HttpMethod method, final String url,
//                        final Map<String, String> queries,
//                        final Map<String, String> forms)
//            throws IOException {
//        request(method, url, queries, forms, null);
//    }
//
//
//    public void request(final HttpMethod method, final String url,
//                        final Map<String, String> forms,
//                        final ResponseCallback responseCallback
//    ) throws IOException {
//        callRequest(createRequest(method, url,
//                null, forms, null), responseCallback);
//    }

//    public void request(final HttpMethod method, final String url,
//                        final Map<String, String> queries,
//                        final Map<String, String> forms,
//                        final ResponseCallback responseCallback
//    ) throws IOException {
//        callRequest(createRequest(method, url,
//                queries, forms, null), responseCallback);
//    }

    public void request(final HttpMethod method, final String url,
                        final Map<String, String> queries,
                        final Map<String, String> forms,
                        final Map<String, String> headers,
                        final ResponseCallback responseCallback,
                        final Object tag
    ) throws IOException {
        callRequest(createRequest(method, url,
                queries, forms, headers, tag), responseCallback);
    }

    public void request(final HttpMethod method, final String url,
                        final RequestParams requestParams,
                        final ResponseCallback responseCallback,
                        final Object tag) throws IOException {
        callRequest(createRequest(method, url, requestParams, tag), responseCallback);
    }

    protected void callRequest(final XRequest xRequest, final ResponseCallback responseCallback) throws IOException {

        final Request request = createOkRequest(xRequest);
        final OkHttpClient client = mClient.clone();
        responseCallback.sendStartMessage();
//        if (mDebug || nr.debug()) {
//            Log.v(NextClient.TAG, "execute() " + nr.dump());
//            // intercept for logging
//            client.networkInterceptors().add(new LoggingInterceptor());
//        }
        // intercept for progress callback
//        if (xRequest.listener() != null) {
//            client.interceptors().add(new ProgressInterceptor(nr.listener()));
//        }
        if (mInterceptor != null) {
            mInterceptor.intercept(client);
        }

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                responseCallback.sendFailureMessage(request, e);
                responseCallback.sendFinishMessage();
            }

            @Override
            public void onResponse(Response response) throws IOException {

                int statusCode = response.code();
                InputStream responseInputStream = response.body().byteStream();
                responseCallback.sendSuccessMessage(statusCode, responseInputStream);
                responseCallback.sendFinishMessage();
            }
        });
    }

    protected XRequest createRequest(final HttpMethod method, final String url,
                                     final RequestParams params, final Object tag) {
        final XRequest request = new XRequest(method, url, tag)
                .headers(mHeaders);
        if (request.supportBody()) {
            request.forms(mParams);
        } else {
            request.queries(mParams);
        }
        return request.params(params);
    }

    protected XRequest createRequest(final HttpMethod method, final String url,
                                     final Map<String, String> queries,
                                     final Map<String, String> forms,
                                     final Map<String, String> headers,
                                     final Object tag) {
        final XRequest request = new XRequest(method, url, tag)
                .headers(mHeaders);
        if (request.supportBody()) {
            request.forms(mParams);
            request.forms(forms);
        } else {
            request.queries(mParams);
        }
        return request.headers(headers).queries(queries);
    }


    protected Request createOkRequest(final XRequest request) throws IOException {
        return new Request.Builder()
                .url(request.url())
                .headers(Headers.of(request.headers()))
                .method(request.method().name(), request.getRequestBody())
                .tag(request.getTag())
                .build();
    }
}

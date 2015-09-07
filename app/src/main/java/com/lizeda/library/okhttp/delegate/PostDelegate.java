package com.lizeda.library.okhttp.delegate;

import com.lizeda.library.okhttp.AsyncHttpResponseHandler;
import com.lizeda.library.okhttp.ResultCallback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.File;

/**
 * 作者：lemon
 * 日期：2015-09-06
 */
public class PostDelegate extends Delegate {

    private final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream;charset=utf-8");
    private final MediaType MEDIA_TYPE_STRING = MediaType.parse("text/plain;charset=utf-8");

    private OkHttpClient client;

    public PostDelegate(OkHttpClient client) {
        this.client = client;
    }




    /**
     * 直接将bodyStr以写入请求体
     */
    public void postAsynWithMediaType(String url, String bodyStr, MediaType type, final AsyncHttpResponseHandler responseHandler, Object tag) {
        RequestBody body = RequestBody.create(type, bodyStr);
        Request request = buildPostRequest(url, body, tag);
        deliveryResult(client, request, responseHandler);
    }

    /**
     * 直接将bodyBytes以写入请求体
     */
    public void postAsynWithMediaType(String url, byte[] bodyBytes, MediaType type, final AsyncHttpResponseHandler responseHandler, Object tag) {
        RequestBody body = RequestBody.create(type, bodyBytes);
        Request request = buildPostRequest(url, body, tag);
        deliveryResult(client, request, responseHandler);
    }

    /**
     * 直接将bodyFile以写入请求体
     */
    public void postAsynWithMediaType(String url, File bodyFile, MediaType type, final AsyncHttpResponseHandler responseHandler, Object tag) {
        RequestBody body = RequestBody.create(type, bodyFile);
        Request request = buildPostRequest(url, body, tag);
        deliveryResult(client, request, responseHandler);
    }

    /**
     * post构造Request的方法
     *
     * @param url
     * @param body
     * @return
     */
    private Request buildPostRequest(String url, RequestBody body, Object tag) {
        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(body);
        if (tag != null) {
            builder.tag(tag);
        }
        Request request = builder.build();
        return request;
    }


}

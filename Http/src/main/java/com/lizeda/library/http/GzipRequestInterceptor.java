package com.lizeda.library.http;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import okio.Buffer;
import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;

/**
 * 作者：lemon
 * 日期：2015-09-08
 */
public class GzipRequestInterceptor implements Interceptor {
    @Override public Response intercept(Interceptor.Chain chain) throws IOException {
        Request originalRequest = chain.request();
        if (originalRequest.body() == null || originalRequest.header("Content-Encoding") != null) {
            return chain.proceed(originalRequest);
        }

        Request compressedRequest = originalRequest.newBuilder()
                .header("Content-Encoding", "gzip")
                .method(originalRequest.method(), forceContentLength(gzip(originalRequest.body())))
                .build();
        return chain.proceed(compressedRequest);
    }

    /** https://github.com/square/okhttp/issues/350 */
    private RequestBody forceContentLength(final RequestBody requestBody) throws IOException {
        final Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return requestBody.contentType();
            }

            @Override
            public long contentLength() {
                return buffer.size();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.write(buffer.snapshot());
            }
        };
    }

    private RequestBody gzip(final RequestBody body) {
        return new RequestBody() {
            @Override public MediaType contentType() {
                return body.contentType();
            }

            @Override public long contentLength() {
                return -1; // We don't know the compressed length in advance!
            }

            @Override public void writeTo(BufferedSink sink) throws IOException {
                BufferedSink gzipSink = Okio.buffer(new GzipSink(sink));
                body.writeTo(gzipSink);
                gzipSink.close();
            }
        };
    }
}

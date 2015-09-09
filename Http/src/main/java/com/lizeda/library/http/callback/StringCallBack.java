package com.lizeda.library.http.callback;

import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * 作者：lemon
 * 日期：2015-09-08
 */
public class StringCallback extends ResponseCallback {

    @Override
    public void onResponse(final Response response) throws IOException {
        try {
            final int statusCode = response.code();
            final String responseString = response.body().string();
            sendOnSuccessMessage(statusCode, responseString);

        } catch (final IOException e) {
            sendOnErrorMessage(response.request(), e);
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(int statusCode, Object response) {
        onSuccess((String) response);
    }

    public void onSuccess(String response) {
    }

}

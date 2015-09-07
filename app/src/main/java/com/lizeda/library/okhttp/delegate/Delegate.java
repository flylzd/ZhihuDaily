package com.lizeda.library.okhttp.delegate;

import com.lizeda.library.okhttp.AsyncHttpResponseHandler;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * 作者：lemon
 * 日期：2015-09-07
 */
public class Delegate {


    protected void deliveryResult(OkHttpClient client, Request request, final AsyncHttpResponseHandler responseHandler) {

        responseHandler.sendStartMessage();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

                if (e instanceof UnknownHostException) {
                    responseHandler.sendFailureMessage("can't resolve host", e);
                } else if (e instanceof SocketException) {
                    responseHandler.sendFailureMessage("can't resolve host", e);
                }else if (e instanceof SocketTimeoutException) {
                    responseHandler.sendFailureMessage("socket time out", e);
                }else if (e instanceof IOException) {
                    responseHandler.sendFailureMessage(null, e);
                }
                responseHandler.sendFinishMessage();
            }

            @Override
            public void onResponse(Response response) throws IOException {

                int statusCode = response.code();
                byte[] responseBytes = response.body().bytes();
                responseHandler.sendSuccessMessage(statusCode,responseBytes);
                responseHandler.sendFinishMessage();
            }
        });
    }

}

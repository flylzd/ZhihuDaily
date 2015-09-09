package com.lizeda.library.http.data;

import android.os.Handler;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * 作者：lemon
 * 日期：2015-09-08
 */
public interface  IDataDecode {

    public void handleOnFailure(Handler handler, Request req, IOException e);

    public void handleOnSuccess(Handler handler, Response res);
}

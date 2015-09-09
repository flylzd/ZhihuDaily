package com.lizeda.library.http.data;

import android.os.Handler;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * 作者：lemon
 * 日期：2015-09-08
 */
public class DataDecodeString implements IDataDecode {

    public static final String TAG=DataDecodeString.class.getSimpleName();

    @Override
    public void handleOnFailure(Handler handler, Request req, IOException e) {

    }

    @Override
    public void handleOnSuccess(Handler handler, Response res) {

    }
}

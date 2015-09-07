package com.lizeda.library.http.callback;


import com.lizeda.library.core.Charsets;
import com.lizeda.library.core.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class StringCallback extends ResponseCallback {

    @Override
    public void onSuccess(int statusCode, InputStream responseInputStream) {

        try {
//            String responseString = IOUtils.readString(responseInputStream, Charsets.UTF_8);
            String responseString = IOUtils.readString(responseInputStream);

            onSuccess(statusCode, responseString);
            onSuccess(responseString);
        } catch (IOException e) {
            e.printStackTrace();
            onFailure(null, e);
        }
    }

    public void onSuccess(int statusCode, String responseString) {

    }

    public void onSuccess(String responseString) {

    }
}

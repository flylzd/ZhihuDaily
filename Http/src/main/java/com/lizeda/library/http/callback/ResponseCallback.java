package com.lizeda.library.http.callback;


import android.os.Handler;
import android.os.Message;

import com.squareup.okhttp.Request;

import java.io.IOException;
import java.io.InputStream;

public abstract class ResponseCallback {

    protected static final int SUCCESS_MESSAGE = 0;
    protected static final int FAILURE_MESSAGE = 1;
    protected static final int START_MESSAGE = 2;
    protected static final int FINISH_MESSAGE = 3;

    protected static final int BUFFER_SIZE = 4 * 1024;


    final public void sendSuccessMessage(int statusCode, InputStream responseInputStream) {
        sendMessage(obtainMessage(SUCCESS_MESSAGE, new Object[]{statusCode, responseInputStream}));
    }

    final public void sendFailureMessage(Request request, IOException e) {
        sendMessage(obtainMessage(FAILURE_MESSAGE, new Object[]{request, e}));
    }

    final public void sendStartMessage() {
        sendMessage(obtainMessage(START_MESSAGE, null));
    }


    final public void sendFinishMessage() {
        sendMessage(obtainMessage(FINISH_MESSAGE, null));
    }

    protected final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            Object[] response;
            switch (message.what) {
                case SUCCESS_MESSAGE:
                    response = (Object[]) message.obj;
                    if (response != null && response.length >= 2) {
                        onSuccess((Integer) response[0], (InputStream) response[1]);
                    } else {
//                        Logger.e(LOG_TAG, "SUCCESS_MESSAGE didn't got enough params");
                    }
                    break;
                case FAILURE_MESSAGE:
                    response = (Object[]) message.obj;
                    if (response != null && response.length >= 2) {
                        onFailure((Request) response[0], (IOException) response[1]);
                    } else {
//                        Logger.e(LOG_TAG, "FAILURE_MESSAGE didn't got enough params");
                    }
                    break;
                case START_MESSAGE:
                    onStart();
                    break;
                case FINISH_MESSAGE:
                    onFinish();
                    break;
            }

        }
    };

    protected void sendMessage(Message msg) {
        handler.sendMessage(msg);
    }

    /**
     * Helper method to create Message instance from handler
     *
     * @param responseMessageId   constant to identify Handler message
     * @param responseMessageData object to be passed to message receiver
     * @return Message instance, should not be null
     */
    protected Message obtainMessage(int responseMessageId, Object responseMessageData) {
        return Message.obtain(handler, responseMessageId, responseMessageData);
    }


    public void onStart() {
    }

    public void onFinish() {
    }

    public void onCancel() {
    }

    public void onFailure(Request request, IOException e) {
    }

    public abstract void onSuccess(int statusCode, InputStream responseInputStream);

}

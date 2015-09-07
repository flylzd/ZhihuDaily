package com.lizeda.library.okhttp;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.orhanobut.logger.Logger;
import com.squareup.okhttp.internal.framed.Header;

public abstract class AsyncHttpResponseHandler implements ResponseHandlerInterface {

    private static final String LOG_TAG = "AsyncHttpResponseHandler";

    protected static final int SUCCESS_MESSAGE = 0;
    protected static final int FAILURE_MESSAGE = 1;
    protected static final int START_MESSAGE = 2;
    protected static final int FINISH_MESSAGE = 3;
    protected static final int PROGRESS_MESSAGE = 4;
    protected static final int RETRY_MESSAGE = 5;
    protected static final int CANCEL_MESSAGE = 6;

    protected static final int BUFFER_SIZE = 4096;

    /**
     * Fired when the request is started, override to handle in your own code
     */
    public void onStart() {
        // default log warning is not necessary, because this method is just optional notification
    }

    /**
     * Fired in all cases when the request is finished, after both success and failure, override to
     * handle in your own code
     */
    public void onFinish() {
        // default log warning is not necessary, because this method is just optional notification
    }

    /**
     * Fired when a request returns successfully, override to handle in your own code
     *
     * @param statusCode   the status code of the response
     * @param headers      return headers, if any
     * @param responseBody the body of the HTTP response from the server
     */
    public abstract void onSuccess(int statusCode, byte[] responseBody);

    /**
     * Fired when a request fails to complete, override to handle in your own code
     *
     * @param responseBody the response body, if any
     * @param error        the underlying cause of the failure
     */
    public abstract void onFailure(String responseBody, Throwable error);


    public void onCancel() {
        Logger.d(LOG_TAG, "Request got cancelled");
    }

    public void onUserException(Throwable error) {
        Logger.e(LOG_TAG, "User-space exception detected!", error);
        throw new RuntimeException(error);
    }

//    @Override
//    final public void sendProgressMessage(long bytesWritten, long bytesTotal) {
//        sendMessage(obtainMessage(PROGRESS_MESSAGE, new Object[]{bytesWritten, bytesTotal}));
//    }

    @Override
    final public void sendSuccessMessage(int statusCode, byte[] responseBytes) {
        sendMessage(obtainMessage(SUCCESS_MESSAGE, new Object[]{statusCode, responseBytes}));
    }

    @Override
    final public void sendFailureMessage(String responseBody, Throwable throwable) {
        sendMessage(obtainMessage(FAILURE_MESSAGE, new Object[]{responseBody, throwable}));
    }

    @Override
    final public void sendStartMessage() {
        sendMessage(obtainMessage(START_MESSAGE, null));
    }

    @Override
    final public void sendFinishMessage() {
        sendMessage(obtainMessage(FINISH_MESSAGE, null));
    }

//    @Override
//    final public void sendRetryMessage(int retryNo) {
//        sendMessage(obtainMessage(RETRY_MESSAGE, new Object[]{retryNo}));
//    }

    @Override
    final public void sendCancelMessage() {
        sendMessage(obtainMessage(CANCEL_MESSAGE, null));
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            Object[] response;

            try {
                switch (message.what) {
                    case SUCCESS_MESSAGE:
                        response = (Object[]) message.obj;
                        if (response != null && response.length >= 2) {
                            onSuccess((Integer) response[0], (byte[]) response[1]);
                        } else {
                            Logger.e(LOG_TAG, "SUCCESS_MESSAGE didn't got enough params");
                        }
                        break;
                    case FAILURE_MESSAGE:
                        response = (Object[]) message.obj;
                        if (response != null && response.length >= 2) {
                            onFailure((String) response[0], (Throwable) response[1]);
                        } else {
                            Logger.e(LOG_TAG, "FAILURE_MESSAGE didn't got enough params");
                        }
                        break;
                    case START_MESSAGE:
                        onStart();
                        break;
                    case FINISH_MESSAGE:
                        onFinish();
                        break;
//                    case PROGRESS_MESSAGE:
//                        response = (Object[]) message.obj;
//                        if (response != null && response.length >= 2) {
//                            try {
////                                onProgress((Long) response[0], (Long) response[1]);
//                            } catch (Throwable t) {
//                                Logger.e(LOG_TAG, "custom onProgress contains an error", t);
//                            }
//                        } else {
//                            Logger.e(LOG_TAG, "PROGRESS_MESSAGE didn't got enough params");
//                        }
//                        break;
//                    case RETRY_MESSAGE:
//                        response = (Object[]) message.obj;
//                        if (response != null && response.length == 1) {
//                            onRetry((Integer) response[0]);
//                        } else {
//                            Logger.e(LOG_TAG, "RETRY_MESSAGE didn't get enough params");
//                        }
//                        break;
                    case CANCEL_MESSAGE:
                        onCancel();
                        break;
                }
            } catch (Throwable error) {
                onUserException(error);
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
}

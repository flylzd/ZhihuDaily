package com.lizeda.library.http.callback;


import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public abstract class ResponseCallback implements Callback {

    protected static final int SUCCESS_MESSAGE = 0;
    protected static final int FAILURE_MESSAGE = 1;
    protected static final int START_MESSAGE = 2;
    protected static final int FINISH_MESSAGE = 3;

    protected Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void onFailure(Request request, IOException e) {
        onError(request, e);
    }

    @Override
    public void onResponse(Response response) throws IOException {

    }

    public void onStart() {
    }

    public void onFinish() {
    }

    public void onError(Request request, Exception e) {
    }

    public void onSuccess(int statusCode, Object response) {

    }


    protected void sendOnSuccessMessage(final int statusCode, final Object response) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onSuccess(statusCode, response);
                onFinish();
            }
        });
    }

    protected void sendOnErrorMessage(final Request request, final Exception e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
//                    onSuccess(response, responseString);
                onError(request, e);
                onFinish();
            }
        });
    }

//        public void onFailure(final Request request, final IOException e) {
////        sendFailureMessage(request, e);
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
////                onUIFinish();
//                onUIFailure(request,e);
//            }
//        });
//    }

//    protected static final int BUFFER_SIZE = 4 * 1024;

//    final public void sendSuccessMessage(int statusCode, InputStream responseInputStream) {
//        sendMessage(obtainMessage(SUCCESS_MESSAGE, new Object[]{statusCode, responseInputStream}));
//    }
//
//    final public void sendSuccessMessage(int statusCode, byte[] responseBytes) {
//        sendMessage(obtainMessage(SUCCESS_MESSAGE, new Object[]{statusCode, responseBytes}));
//    }
//
//    final public void sendFailureMessage(Request request, IOException e) {
//        sendMessage(obtainMessage(FAILURE_MESSAGE, new Object[]{request, e}));
//    }
//
//    final public void sendStartMessage() {
//        sendMessage(obtainMessage(START_MESSAGE, null));
//    }
//
//
//    final public void sendFinishMessage() {
//        sendMessage(obtainMessage(FINISH_MESSAGE, null));
//    }
//
//    protected final Handler handler = new Handler(Looper.getMainLooper()) {
//        @Override
//        public void handleMessage(Message message) {
//            final Object[] response;
//            switch (message.what) {
//                case SUCCESS_MESSAGE:
//                    response = (Object[]) message.obj;
//                    if (response != null && response.length >= 2) {
//
////                        new Thread(new Runnable() {
////                            @Override
////                            public void run() {
//////                                onSuccess((Integer) response[0], (byte[]) response[1]);
////                            }
////                        }).start();
//
//                        System.out.println("handleMessage SUCCESS_MESSAGE");
////                        onUISuccess((Integer) response[0], (InputStream) response[1]);
////                        onSuccess((Integer) response[0], (byte[]) response[1]);
//                    } else {
////                        Logger.e(LOG_TAG, "SUCCESS_MESSAGE didn't got enough params");
//                    }
//                    break;
//                case FAILURE_MESSAGE:
//                    response = (Object[]) message.obj;
//                    if (response != null && response.length >= 2) {
////                        onFailure((Request) response[0], (IOException) response[1]);
//                        onUIFailure((Request) response[0], (IOException) response[1]);
//                    } else {
////                        Logger.e(LOG_TAG, "FAILURE_MESSAGE didn't got enough params");
//                    }
//                    break;
//                case START_MESSAGE:
////                    onStart();
//                    onUIStart();
//                    break;
//                case FINISH_MESSAGE:
////                    onFinish();
//                    onUIFinish();
//                    break;
//            }
//
//        }
//    };
//
//    protected void sendMessage(Message msg) {
//        handler.sendMessage(msg);
//    }
//
//    /**
//     * Helper method to create Message instance from handler
//     *
//     * @param responseMessageId   constant to identify Handler message
//     * @param responseMessageData object to be passed to message receiver
//     * @return Message instance, should not be null
//     */
//    protected Message obtainMessage(int responseMessageId, Object responseMessageData) {
//        return Message.obtain(handler, responseMessageId, responseMessageData);
//    }
//
//
//    public void onStart() {
////        sendStartMessage();
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                onUIStart();
//            }
//        });
//    }
//
//    public void onFinish() {
////        sendFinishMessage();
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//              onUIFinish();
//            }
//        });
//    }
//
////    public void onCancel() {
////    }
//
//    public void onFailure(final Request request, final IOException e) {
////        sendFailureMessage(request, e);
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
////                onUIFinish();
//                onUIFailure(request,e);
//            }
//        });
//    }
//
////    public abstract void onSuccess(int statusCode, InputStream responseInputStream);
//
////    public abstract void onSuccess(int statusCode, byte[] responseBytes);
//
//    public  void onSuccess(int statusCode, InputStream responseInputStream){
//
//        System.out.println("ResponseCallback onSuccess");
//    }
//
//    public  void onSuccess(Response response){
//
//        System.out.println("ResponseCallback onSuccess");
//
//    }
//
//    public void onUIStart() {
//
//    }
//
//    public void onUIFinish() {
//
//    }
//
//    public void onUIFailure(Request request, Exception e) {
//
//    }
//
////    public void onUISuccess(int statusCode, InputStream responseInputStream) {
////
////    }
//
//    public void onUISuccess(String responseString) {
//    }


}

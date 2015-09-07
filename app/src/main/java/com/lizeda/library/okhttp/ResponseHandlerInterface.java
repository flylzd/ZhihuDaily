package com.lizeda.library.okhttp;


import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.framed.Header;


import java.io.IOException;

public interface  ResponseHandlerInterface {


    /**
     * Returns data whether request completed successfully
     *
     * @param response HttpResponse object with data
     * @throws java.io.IOException if retrieving data from response fails
     */
    void sendResponseMessage(Response response) throws IOException;

    /**
     * Notifies callback, that request started execution
     */
    void sendStartMessage();

    /**
     * Notifies callback, that request was completed and is being removed from thread pool
     */
    void sendFinishMessage();

    /**
     * Notifies callback, that request (mainly uploading) has progressed
     *
     * @param bytesWritten number of written bytes
     * @param bytesTotal   number of total bytes to be written
     */
//    void sendProgressMessage(long bytesWritten, long bytesTotal);

    /**
     * Notifies callback, that request was cancelled
     */
    void sendCancelMessage();

    /**
     * Notifies callback, that request was handled successfully
     *
     * @param statusCode   HTTP status code
     * @param headers      returned headers
     * @param responseBody returned data
     */
    void sendSuccessMessage(int statusCode,  byte[] responseBody);

    /**
     * Returns if request was completed with error code or failure of implementation
     *
     * @param statusCode   returned HTTP status code
     * @param headers      returned headers
     * @param responseBody returned data
     * @param error        cause of request failure
     */
//    void sendFailureMessage(int statusCode, Header[] headers, byte[] responseBody, Throwable error);


    void sendFailureMessage(String responseBody, Throwable error);

}

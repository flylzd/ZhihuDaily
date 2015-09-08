package com.lizeda.library.http.progress;

/**
 * 作者：lemon
 * 日期：2015-09-08
 */
public interface  ProgressListener {

    void onProgress(long currentBytes, long contentLength, boolean done);
}

package com.lizeda.library.okhttp;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.CacheControl;

import java.io.File;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

/**
 * http����������
 */
public class HttpConfig {

    public long connectTimeout=10 * 1000; // ���ӳ�ʱ

    public long writeTimeout=10 * 1000; // д��ʱ

    public long readTimeout=20 * 1000; // ����ʱ

    public int maxConnections=10; // ���ӳ����� 1

    public long keepAliveDurationMs=5 * 60 * 1000; // ���ӳ����� 2

    private HttpPoolConfig httpPoolConfig; // ���ӳ�����

    private CacheControl requestCacheControl; // request�������

    private Cache responseCache; // response ����

//    private PersistentCookieStore cookieStore; // cookie handler

    private Proxy proxy; // proxy

    private String[][] headers;


    public Proxy getProxy() {
        return proxy;
    }

    public void setProxy(Proxy proxy) {
        this.proxy=proxy;
    }
//
//    public PersistentCookieStore getCookieStore() {
//        return cookieStore;
//    }
//
//    public void setCookieStore(PersistentCookieStore cookieStore) {
//        this.cookieStore=cookieStore;
//    }

    public Cache getResponseCache() {
        return responseCache;
    }

    public String[][] getHeaders() {
        return headers;
    }

    public void setHeaders(String[][] headers) {
        this.headers=headers;
    }

    /**
     * ������Ӧ�Ļ���
     * @param directory
     * @param maxSize
     * @throws Exception
     */
    public void setResponseCache(File directory, long maxSize) throws Exception {
        this.responseCache=new Cache(directory, maxSize);
    }

    /**
     * ��ȡ���ӳ�����
     * @return
     */
    public HttpPoolConfig getHttpPoolConfig() {
        return httpPoolConfig;
    }

    /**
     * ���ӳ�����
     */
    public void setHttpPoolConfig() {
        this.httpPoolConfig=new HttpPoolConfig();
        this.httpPoolConfig.setMaxIdleConnections(maxConnections);
        this.httpPoolConfig.setKeepAliveDurationMs(keepAliveDurationMs);
    }

    public CacheControl getRequestCacheControl() {
        return requestCacheControl;
    }

    /**
     * ����cacheCotrol
     * @param maxAge
     */
    public void setNewCacheControlWithAge(int maxAge) {
        this.requestCacheControl=new CacheControl.Builder().maxAge(maxAge, TimeUnit.SECONDS).build();
    }

    /**
     * ����cacheCotrol nocache
     */
    public void setNewCacheControlWithNoCache() {
        this.requestCacheControl=new CacheControl.Builder().noCache().build();
    }

    /**
     * ����cacheCotrol onlyifcached
     */
    public void setNewCacheControlWithOnlyIfCached() {
        this.requestCacheControl=new CacheControl.Builder().onlyIfCached().build();
    }

    /**
     * ����cacheCotrol stale days
     * @param days
     */
    public void setNewCacheControlWithStale(int days) {
        this.requestCacheControl=new CacheControl.Builder().maxStale(days, TimeUnit.DAYS).build();
    }



    /**
     * ���ӳ�������
     */
    public class HttpPoolConfig {

        private int maxIdleConnections;

        private long keepAliveDurationMs;

        public int getMaxIdleConnections() {
            return maxIdleConnections;
        }

        public void setMaxIdleConnections(int maxIdleConnections) {
            this.maxIdleConnections=maxIdleConnections;
        }

        public long getKeepAliveDurationMs() {
            return keepAliveDurationMs;
        }

        public void setKeepAliveDurationMs(long keepAliveDurationMs) {
            this.keepAliveDurationMs=keepAliveDurationMs;
        }
    }
}

package com.lizeda.daily;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.lizeda.library.http.AsyncOKHttpClient;
import com.lizeda.library.http.callback.GsonCallback;
import com.lizeda.library.http.callback.StringCallback;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        OkHttpClient okHttpClient = new OkHttpClient();
//        Request.Builder builder = new Request.Builder();
//        builder.url("http://www.baidu.com");
//
//        Request request = builder.build();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//
//                System.out.println(response.body().string());
//            }
//        });

        AsyncOKHttpClient httpClient = AsyncOKHttpClient.getDefault();
        try {
            httpClient.get("http://www.baidu.com", new StringCallback() {

                @Override
                public void onUIStart() {
                    System.out.println("httpClient.get.StringCallback onStart  ");
                }

                @Override
                public void onUIFinish() {
                    System.out.println("httpClient.get.StringCallback onFinish  ");
                }

                @Override
                public void onUISuccess(String responseString) {

                    System.out.println("responseString == " + responseString);
                    System.out.println("httpClient.get.StringCallback onSuccess  ");
                }
            }, MainActivity.this);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        try {
//            httpClient.get("http://www.weather.com.cn/adat/sk/101010100.html", new GsonCallback<Weather>() {
//
//                @Override
//                public void onFinish() {
//                    System.out.println("httpClient.get.StringCallback onFinish  ");
//                }
//
//                @Override
//                public void onStart() {
//                    System.out.println("httpClient.get.StringCallback onStart  ");
//                }
//
//                @Override
//                public void onSuccess(Weather responseJson) {
////                    super.onSuccess(responseJson);
//
//                    System.out.println("httpClient.get.StringCallback onSuccess  " + responseJson.toString());
//
//                }
//
//                //                @Override
////                public void onSuccess(String responseString) {
////
////                    System.out.println("responseString == " + responseString);
////                    System.out.println("httpClient.get.StringCallback onSuccess  ");
////                }
//            }, MainActivity.this);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

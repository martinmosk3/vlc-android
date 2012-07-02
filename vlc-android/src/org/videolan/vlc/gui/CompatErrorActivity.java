package org.videolan.vlc.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.videolan.vlc.R;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

public class CompatErrorActivity extends Activity {
    public final static String TAG = "VLC/CompatErrorActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.no_neon);
        super.onCreate(savedInstanceState);

        AsyncHttpRequest asyncHttpRequest = new AsyncHttpRequest();
        asyncHttpRequest.execute(Build.MODEL, Build.DEVICE);
    }

    public class AsyncHttpRequest extends AsyncTask<String, String, Boolean> {

        public AsyncHttpRequest() { }

        @Override
        protected Boolean doInBackground(String... params) {
            if (params[0].length() == 0)
                return false;
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://people.videolan.org/~jb/blacklist/vlc-devices.php");

            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("model", params[0]));
                nameValuePairs.add(new BasicNameValuePair("device", params[1]));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                httpClient.execute(httpPost);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            Log.d(TAG, "Device model sent.");
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {

        }
    }
}

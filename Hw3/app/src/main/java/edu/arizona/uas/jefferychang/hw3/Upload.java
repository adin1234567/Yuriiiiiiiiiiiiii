package edu.arizona.uas.jefferychang.hw3;

import android.net.Uri;
import android.util.Log;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.io.IOException;


import edu.arizona.uas.jefferychang.hw3.BloodGlucose;

public class Upload {
    private static final String TAG = "BloodGlucose_Upload";

    public boolean upload(BloodGlucose mBloodGlucose) {
        try {
            JSONObject js;
            js = mBloodGlucose.toJSON();
            Log.d("///JSON String",js.toString());
            URL url = new URL("http://u.arizona.edu/~lxu/cscv381/local_glucose.php");
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("username", "rejeffery9311");
            params.put("password", "a7450");
            params.put("data", js.toString());
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            Log.d(TAG,  "conn set POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            Log.d(TAG,  "conn set request");
            conn.setDoOutput(true);
            Log.d(TAG,  "conn set output true");
            conn.getOutputStream().write(postDataBytes);
            Log.d(TAG,  "conn get output stream");
            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            for (int c; (c = in.read()) >= 0; )
                System.out.print((char) c);
            return true;
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to upload items", ioe);
            return false;
        }
    }
}

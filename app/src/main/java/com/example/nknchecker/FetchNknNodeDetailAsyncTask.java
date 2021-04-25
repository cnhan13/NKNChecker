package com.example.nknchecker;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchNknNodeDetailAsyncTask extends AsyncTask<String, Integer, JSONObject> {

    private static final String TAG = FetchNknNodeDetailAsyncTask.class.getSimpleName();
    private static final String CHARSET_NAME = "UTF-8";

    private CompleteListener completeListener;
    Integer position;

    FetchNknNodeDetailAsyncTask(CompleteListener completeListener) {
        this.completeListener = completeListener;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        String ipAddress = strings[0];
        if (strings.length > 1) {
            position = Integer.valueOf(strings[1]);
        }

        HttpURLConnection urlConnection = null;
        JSONObject resultJSONObject = new JSONObject();
        String errorMsg = "";
        try {
            URL url = new URL(NknNode.getNodeEndpoint(ipAddress));
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true); // POST is used
            urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");

            String outJsonString = QueryUtils.getNodeStateJsonString();
            OutputStream out = urlConnection.getOutputStream();
            byte[] outBytes = outJsonString.getBytes(CHARSET_NAME);
            out.write(outBytes, 0, outBytes.length);

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, CHARSET_NAME));
            StringBuilder inStringBuilder = new StringBuilder();

            String line;
            while ((line = streamReader.readLine()) != null) {
                inStringBuilder.append(line);
            }
            return new JSONObject(inStringBuilder.toString());

        } catch (IOException | JSONException e) {
            errorMsg = e.getMessage();
            Log.e(TAG, errorMsg, e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                resultJSONObject.put(NknNode.FIELD_ERROR, errorMsg);
            } catch (JSONException e) {
                Log.e(TAG, "Problem creating result JSON.", e);
                e.printStackTrace();
            }
        }
        return resultJSONObject;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        completeListener.onComplete(jsonObject, position);
    }

    interface CompleteListener {
        void onComplete(JSONObject jsonObject, Integer position);
    }
}
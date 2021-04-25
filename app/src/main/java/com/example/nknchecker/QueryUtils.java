package com.example.nknchecker;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public final class QueryUtils {

    private static final String TAG = QueryUtils.class.getSimpleName();

    private static final String NKN_INSTANCES_JSON = "{\"type\":\"InstanceCollection\",\"instances\":[" +
            "{\"name\":\"nkn-full-node-1-vm\",\"hostService\":\"Google Cloud\",\"ipAddress\":\"34.87.78.17\"}," +
            "{\"name\":\"nkn-full-node-2-vm\",\"hostService\":\"Google Cloud\",\"ipAddress\":\"34.93.126.40\"}," +
            "{\"name\":\"nkn-full-node-3-vm\",\"hostService\":\"Google Cloud\",\"ipAddress\":\"34.93.43.167\"}," +
            "{\"name\":\"nkn-full-node-4-vm\",\"hostService\":\"Google Cloud\",\"ipAddress\":\"35.187.231.185\"}," +
            "{\"name\":\"nkn-full-node-5-vm\",\"hostService\":\"Google Cloud\",\"ipAddress\":\"35.198.242.36\"}," +
            "{\"name\":\"nkn-full-node-6-vm\",\"hostService\":\"Google Cloud\",\"ipAddress\":\"35.187.247.254\"}," +
            "{\"name\":\"nkn-full-node-7-vm\",\"hostService\":\"Google Cloud\",\"ipAddress\":\"34.87.24.209\"}," +
            "{\"name\":\"nkn-full-node-8-vm\",\"hostService\":\"Google Cloud\",\"ipAddress\":\"35.240.230.77\"}," +
            "{\"name\":\"nkn-full-node-9-vm\",\"hostService\":\"Google Cloud\",\"ipAddress\":\"34.93.172.106\"}," +
            "{\"name\":\"nkn-full-node-10-vm\",\"hostService\":\"Google Cloud\",\"ipAddress\":\"35.240.219.84\"}," +
            "{\"name\":\"Node_1\",\"hostService\":\"Digital Ocean\",\"ipAddress\":\"68.183.188.198\"}," +
            "{\"name\":\"Node_2\",\"hostService\":\"Digital Ocean\",\"ipAddress\":\"157.230.33.120\"}," +
            "{\"name\":\"Node_3\",\"hostService\":\"Digital Ocean\",\"ipAddress\":\"157.230.33.79\"}," +
            "{\"name\":\"Node_4\",\"hostService\":\"Digital Ocean\",\"ipAddress\":\"157.230.33.114\"}," +
            "{\"name\":\"Node_5\",\"hostService\":\"Digital Ocean\",\"ipAddress\":\"157.230.33.127\"}," +
            "{\"name\":\"Node_6\",\"hostService\":\"Digital Ocean\",\"ipAddress\":\"157.230.33.89\"}," +
            "{\"name\":\"Node_7\",\"hostService\":\"Digital Ocean\",\"ipAddress\":\"157.230.33.132\"}," +
            "{\"name\":\"Node_8\",\"hostService\":\"Digital Ocean\",\"ipAddress\":\"157.230.33.110\"}," +
            "{\"name\":\"Node_9\",\"hostService\":\"Digital Ocean\",\"ipAddress\":\"68.183.187.50\"}," +
            "{\"name\":\"Node_10\",\"hostService\":\"Digital Ocean\",\"ipAddress\":\"157.230.35.5\"}]}";

    private QueryUtils() {
    }

    public static ArrayList<NknNode> extractNknNodes() {
        ArrayList<NknNode> nknNodes = new ArrayList<>();

        try {
            JSONObject response = new JSONObject(NKN_INSTANCES_JSON);
            JSONArray instances = response.optJSONArray("instances");
            for (int i = 0; i < instances.length(); i++) {
                JSONObject instance = instances.optJSONObject(i);

                String name = instance.optString("name");
                String hostService = instance.optString("hostService");
                String ipAddress = instance.optString("ipAddress");

                nknNodes.add(new NknNode(name, hostService, ipAddress));
            }
        } catch (JSONException e) {
            Log.e(TAG, "Problem parsing the NKN JSON nodes", e);
            e.printStackTrace();
        }

        return nknNodes;
    }

    public static final String METHOD_GET_NODE_STATE = "getnodestate";

    public static String getRpcJsonString(String method, HashMap<String,Object> params) {
        return "{\"jsonrpc\":\"2.0\",\"method\":\"" + method + "\",\"params\":{},\"id\":1}";
    }

    public static String getNodeStateJsonString() {
        return "{\"jsonrpc\":\"2.0\",\"method\":\"getnodestate\",\"params\":{},\"id\":1}";
    }
}

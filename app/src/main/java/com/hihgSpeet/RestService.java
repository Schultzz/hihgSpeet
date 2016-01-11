package com.hihgSpeet;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import self.philbrown.droidQuery.$;
import self.philbrown.droidQuery.AjaxOptions;
import self.philbrown.droidQuery.Function;

/**
 * Created by asd on 14-12-2015.
 */
public class RestService {

    private boatInfoFragment context;

    public RestService(boatInfoFragment context) {
        this.context = context;
    }

    public String getJSON() {

        String rsp = "";
        //http://10.0.3.2 = genymotion's IP til computerens localhost
        $.ajax(new AjaxOptions().url("http://jsonplaceholder.typicode.com/posts/1")
                .type("GET")
                .dataType("json")
                .context(context.getContext())
                .success(new Function() {
                    //                    @Override
//                    public void invoke($ droidQuery, Object... params) {
//                        droidQuery.alert((String) params[0]);
//                    }
                    @Override
                    public void invoke($ droidQuery, Object... params) {
                        JSONObject response = (JSONObject) params[0];

                        try {
                            Toast.makeText(context.getActivity(), response.getString("body"), Toast.LENGTH_SHORT).show();
                            context.addItem(response.getString("body"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //System.out.println(response + " 11111111111111111111111111");

                        //droidQuery.alert(response.toString())-*;
                    }
                }).error(new Function() {
                    @Override
                    public void invoke($ droidQuery, Object... params) {
                        int statusCode = (Integer) params[1];
                        String error = (String) params[2];
                        Log.e("Ajax", statusCode + " " + error);
                    }
                }));
        return "";

    }


}

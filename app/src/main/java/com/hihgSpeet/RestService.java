package com.hihgSpeet;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import self.philbrown.droidQuery.$;
import self.philbrown.droidQuery.AjaxOptions;
import self.philbrown.droidQuery.Function;

/**
 * Created by asd on 14-12-2015.
 */
public class RestService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public RestService() {
        super("RestService");
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent,flags,startId);
    }
    /**
     * The IntentService calls this method from the default worker thread with
     * the intent that started the service. When this method returns, IntentService
     * stops the service, as appropriate.
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        System.out.println("IN HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        String data = intent.getExtras().getString("json");

        String rsp = "";
        //http://10.0.3.2 = genymotion's IP til computerens localhost
        $.ajax(new AjaxOptions()
                .url("https://pure-cove-4683.herokuapp.com/api/route")
                .type("POST")
                .dataType("JSON")
                .data(data)
                .contentType("application/json")
                .success(new Function() {
                    @Override
                    public void invoke($ droidQuery, Object... params) {
                        JSONObject response = (JSONObject) params[0];
                        System.out.println("11111111111111111111111111111111111111111111");
                        Log.e("ServerDb-createList-suc", response.toString());
                    }
                })
                .error(new Function() {
                    @Override
                    public void invoke($ droidQuery, Object... params) {
                        int statusCode = (Integer) params[1];
                        String error = (String) params[2];
                        System.out.println("22222222222222222222222222222222222222222222222");
                        Log.e("ServerDb-createList-err", statusCode + " " + error);
                    }
                }).debug(true));

    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }


}

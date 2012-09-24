package com.pocktalk;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: shiva
 * Date: 23/9/12
 * Time: 6:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class FollowActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.follow);
        
        SharedPreferences globalSettings = getSharedPreferences(ServerConstants.POCKTALK_GLOBAL_PREFS, 0);
        String userId = globalSettings.getString("userId", null);
        String email = globalSettings.getString("email", null);
        
        AsyncHTTPClient client = new AsyncHTTPClient(URLHelper.list(userId)) {

            public void onSuccessResponse(String response) {
                try {

                    JSONObject content = new JSONObject(response);

                    Log.e("Testing API", content.toString());

                    String emailArray = content.getJSONArray("email").toString();

                    SharedPreferences globalSettings = getSharedPreferences(ServerConstants.POCKTALK_GLOBAL_PREFS, 0);
                    SharedPreferences.Editor editor = globalSettings.edit();
                    editor.putString("userId", userId);
                    editor.putString("email", email);
                    editor.commit();

                    Log.e("LoginActivity", "UserId: " + userId);

                    Intent intentToGo = new Intent();
                    intentToGo.setClass(LoginActivity.this, HomeActivity.class);
                    startActivity(intentToGo);
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onErrorResponse(Exception e) {
                Log.e("LoginActivity", "Error", e);
            }
        };

        client.execute();
    }
}
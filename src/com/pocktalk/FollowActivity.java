package com.pocktalk;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

                	Log.e("Testing API",response.toString());
                	
                    /*JSONObject content = new JSONObject(response);*/
                	JSONArray content = new JSONArray(response);

                    Log.e("Testing API", content.toString());
                    
                    /*JSONArray userIdJSONArray = content.getJSONArray("userId");
                    JSONArray emailJSONArray = content.getJSONArray("email");*/

                    ArrayList emailArray = new ArrayList();
                    ListView emaillist = (ListView) findViewById(R.id.userlist);
                    ArrayAdapter<String> emailAdapter = new ArrayAdapter<String>(FollowActivity.this,R.layout.listrow,R.id.listrowText,emailArray);
                    /*for (int i= 0; i < emailJSONArray.length(); i++)
                    {
                    	emailAdapter.add(emailJSONArray.getJSONObject(i).toString());
                    	Log.e("Email ", emailJSONArray.getJSONObject(i).toString());
                    }*/
                    for (int i= 0; i < content.length(); i++)
                    {
                    	emailAdapter.add(content.getJSONObject(i).getString("email"));
                    	Log.e("Email ", content.getJSONObject(i).getString("email"));
                    }
                    
                    
                    emaillist.setAdapter(emailAdapter);
                    
                    /*SharedPreferences globalSettings = getSharedPreferences(ServerConstants.POCKTALK_GLOBAL_PREFS, 0);
                    SharedPreferences.Editor editor = globalSettings.edit();
                    editor.putString("userId", userId);
                    editor.putString("email", email);
                    editor.commit();

                    Log.e("FollowActivity", "UserId: " + userId);

                    Intent intentToGo = new Intent();
                    intentToGo.setClass(FollowActivity.this, HomeActivity.class);
                    startActivity(intentToGo);
                    finish(); */

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onErrorResponse(Exception e) {
                Log.e("FollowActivity", "Error", e);
            }
        };

        client.execute();
    }
}
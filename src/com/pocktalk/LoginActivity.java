package com.pocktalk;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity {

    ProgressDialog progressBar;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        SharedPreferences globalSettings = getSharedPreferences(ServerConstants.POCKTALK_GLOBAL_PREFS, 0);
        String userId = globalSettings.getString("userId", null);
        if (userId != null) {
            Intent intentToGo = new Intent();
            intentToGo.setClass(LoginActivity.this, HomeActivity.class);
            startActivity(intentToGo);
            finish();
        }

        setContentView(R.layout.login);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                final String email = ((EditText) findViewById(R.id.email)).getText().toString();
                final String password = ((EditText) findViewById(R.id.password)).getText().toString();

                if (email == null || email.equals("") || password == null || password.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                } else {

                    progressBar = new ProgressDialog(LoginActivity.this);
                    progressBar.setIndeterminate(true);
                    progressBar.setMessage("Logging in...");
                    progressBar.show();

                    Log.e("Testing API", URLHelper.login(email));

                    AsyncHTTPClient client = new AsyncHTTPClient(URLHelper.login(email)) {

                        public void onSuccessResponse(String response) {
                            try {

                                JSONObject content = new JSONObject(response);

                                Log.e("Testing API", content.toString());

                                String userId = content.getJSONObject("_id").getString("$oid");

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
        });
    }

}
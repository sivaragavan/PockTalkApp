package com.pocktalk;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends Activity implements OnClickListener {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        SharedPreferences globalSettings = getSharedPreferences(ServerConstants.POCKTALK_GLOBAL_PREFS, 0);
        String email = globalSettings.getString("email", null);
        ((TextView) this.findViewById(R.id.welcome)).setText("Welcome, " + email);

        Button talk = (Button) this.findViewById(R.id.talk);
        talk.setOnClickListener(this);

        Button listen = (Button) this.findViewById(R.id.listen);
        listen.setOnClickListener(this);

        Button follow = (Button) this.findViewById(R.id.follow);
        follow.setOnClickListener(this);
    }

    public void onClick(View v) {

        Intent intentToGo = new Intent();

        switch (v.getId()) {

            case R.id.talk:
                intentToGo = new Intent(this,TalkActivity.class);
                break;

            case R.id.listen:
                intentToGo = new Intent(this,ListenActivity.class);
                break;

            case R.id.follow:
                intentToGo = new Intent(this,FollowActivity.class);
                break;

        }

        startActivity(intentToGo);
    }
}

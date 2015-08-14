package com.meyouhealth.learning.flickrinfo;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.oauth.OAuthLoginActionBarActivity;
import com.meyouhealth.learning.flickrinfo.com.RestClient;
import com.meyouhealth.learning.flickrinfo.util.AuthenticationUtil;

public class MainActivity extends OAuthLoginActionBarActivity<RestClient> {

    public static final int LOGIN_REQUEST_CODE = 1234;

    private Context mContext;
    private TextView mHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mHeader = (TextView) findViewById(R.id.hello_world);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        String authToken = AuthenticationUtil.getAuthToken();
        if (authToken == null || authToken.isEmpty()) {
            //show Login
            getMenuInflater().inflate(R.menu.menu_main_logged_out, menu);
        } else {
            //show Logout
            getMenuInflater().inflate(R.menu.menu_main_logged_in, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_login) {
            getClient().connect();
            this.invalidateOptionsMenu();
            return true;
        }
        if (id == R.id.action_logout) {
            getClient().clearAccessToken();
            AuthenticationUtil.clearAuthToken();
            this.invalidateOptionsMenu();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    //this is on UI thread
    @Override
    public void onLoginSuccess() {
        String token = getClient().checkAccessToken().getToken();
        //save token for later
        AuthenticationUtil.setAuthToken(token);
        this.invalidateOptionsMenu();
        mHeader.setText("You're logged in");
        //TODO then load this user's connections
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                fetchConnections();
            }
        });
        thread.start(); //starts to run on background thread

    }

    @Override
    public void onLoginFailure(Exception e) {
        e.printStackTrace();
        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void fetchConnections() {
        
    }
}

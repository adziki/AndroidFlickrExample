package com.meyouhealth.learning.flickrinfo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.oauth.OAuthBaseClient;
import com.codepath.oauth.OAuthLoginActionBarActivity;
import com.google.gson.Gson;
import com.meyouhealth.learning.flickrinfo.com.FlickrService;
import com.meyouhealth.learning.flickrinfo.com.RestClient;
import com.meyouhealth.learning.flickrinfo.model.ContactsResponse;
import com.meyouhealth.learning.flickrinfo.util.AuthenticationUtil;
import com.meyouhealth.learning.flickrinfo.util.EncryptUtil;
import com.meyouhealth.learning.flickrinfo.util.LenientGsonConverter;
import com.meyouhealth.learning.flickrinfo.util.Secrets;

import org.scribe.extractors.HeaderExtractorImpl;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Verb;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

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
        getClient().authorize(Uri.parse("https://api.flickr.com/services/rest/?method=flickr.contacts.getList"), new OAuthBaseClient.OAuthAccessHandler() {
            @Override
            public void onLoginSuccess() {

            }

            @Override
            public void onLoginFailure(Exception e) {

            }
        });//.fetchAccessToken(getClient().checkAccessToken(), Uri.parse(""));
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
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.flickr.com/services/rest")
                .setConverter(new LenientGsonConverter(new Gson()))
                .build();

        String authToken = getClient().checkAccessToken().getToken();
        String signature = Secrets.FLICKR_APPLICATION_KEY+"api_key"+Secrets.FLICKR_APPLICATION_KEY+"formatjsonauth_token" + authToken;

        HeaderExtractorImpl authorization = new HeaderExtractorImpl();
        OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.flickr.com/services/rest");
        request.addOAuthParameter("oauth_consumer_key","697513971bb18fc09eb60e905f3ced89");
        request.addOAuthParameter("oauth_signature_method", "HMAC-SHA1");
        request.addOAuthParameter("oauth_timestamp","1439999912");
        request.addOAuthParameter("oauth_nonce","466635617");
        request.addOAuthParameter("oauth_version","1.0");
        request.addOAuthParameter("oauth_token","72157657452140442-779ae91ac587b602");
        request.addOAuthParameter("oauth_signature","msXcmw03WWb6pOy90qDQruClvsw%3D");
        String authenticationString1 = authorization.extract(request);
        int startLoc = authenticationString1.indexOf("oauth_signature");
        if (startLoc > 0) {
            int endLoc = authenticationString1.indexOf("\"", startLoc + 20);
            if (endLoc > 0) {
                String value = authenticationString1.substring(startLoc +17, endLoc);
                String fixedValue = value.replace("%25", "%");
                authenticationString1 = authenticationString1.replace(value, fixedValue);
            }
        }
        String authenticationString = "OAuth oauth_consumer_key=\"697513971bb18fc09eb60e905f3ced89\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"1439999912\",oauth_nonce=\"466635617\",oauth_version=\"1.0\",oauth_token=\"72157657452140442-779ae91ac587b602\",oauth_signature=\"msXcmw03WWb6pOy90qDQruClvsw%3D\"";
        FlickrService service = restAdapter.create(FlickrService.class);
        ContactsResponse response =
        //String response =
                service.getContactList(
                        authenticationString1);
        if (response == null) {
            Log.d("test", "test");
        }
    }
}

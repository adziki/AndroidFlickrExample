package com.meyouhealth.learning.flickrinfo;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.codepath.oauth.OAuthLoginActionBarActivity;
import com.koushikdutta.ion.Ion;
import com.meyouhealth.learning.flickrinfo.com.RestClient;
import com.meyouhealth.learning.flickrinfo.com.flickr.UserApi;
import com.meyouhealth.learning.flickrinfo.model.FlickrUser;
import com.meyouhealth.learning.flickrinfo.util.AuthenticationUtil;
import com.meyouhealth.learning.flickrinfo.flippers.UserSearchViewFlipperController;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends OAuthLoginActionBarActivity<RestClient> {

    public static final int LOGIN_REQUEST_CODE = 1234;
    public static final int REQUEST_IMAGE_CAPTURE = 1235;

    private Context mContext;
    private UserSearchViewFlipperController mFlipperController;
    private FlickrUser mCurrentSearchResult;
    private String mCurrentPhotoPath;

    EditText mUserName;
    ImageButton mSearchUser;
    ViewFlipper mFlipper;
    TextView mUserDisplayName;
    TextView mUserPhotoCount;
    ImageView mUserProfilePic;
    ImageView mWaitImage;
    View mUserCard;
    TextView mDescriptionButton;

    public View.OnClickListener mClickUserProfile = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO in Exercise 3
        }
    };

    public View.OnClickListener mClickUserDescription = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO in Exercise 2
        }
    };

    public View.OnClickListener mClickSearchButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mFlipperController.show(UserSearchViewFlipperController.POSITION_WAITING);
            String username = mUserName.getText().toString();
            UserApi.searchByUsername(getClient(), username, new Callback<UserApi.SingleSimpleUserResponse>() {
                @Override
                public void success(UserApi.SingleSimpleUserResponse singleSimpleUserResponse, Response response) {
                    //we have success, so let's get that user data

                    if (singleSimpleUserResponse.user == null) {
                        mFlipperController.show(UserSearchViewFlipperController.POSITION_NO_USERS_FOUND);
                    } else {
                        String userId = singleSimpleUserResponse.user.nsid;
                        getUserDetails(userId);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_LONG).show();
                    mFlipperController.show(UserSearchViewFlipperController.POSITION_INITIAL);
                }
            });
        }
    };

    // Second stage of API calls to get user Profile info.
    private void getUserDetails(String userId) {
        UserApi.getDetailedUser(getClient(), userId, new Callback<UserApi.SingleFlickrUserResponse>() {
            @Override
            public void success(UserApi.SingleFlickrUserResponse singleFlickrUserResponse, Response response) {
                if (singleFlickrUserResponse.person == null) {
                    mFlipperController.show(UserSearchViewFlipperController.POSITION_NO_USERS_FOUND);
                } else {
                    showUserDetails(singleFlickrUserResponse.person);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_LONG).show();
                mFlipperController.show(UserSearchViewFlipperController.POSITION_INITIAL);
            }
        });
    }

    private void showUserDetails(FlickrUser user) {
        //TODO as Exercise 1
        mCurrentSearchResult = user;
        //Set display name TextView to be user's Real Name

        //Set user's photo count TextView to be user's Photo Count

        //change ViewFlipper to display the Current User info

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        mUserName = (EditText)findViewById(R.id.user_name);
        mSearchUser = (ImageButton)findViewById(R.id.search_user);
        mFlipper = (ViewFlipper)findViewById(R.id.user_search_result_flipper);
        mUserDisplayName = (TextView)findViewById(R.id.user_display_name);
        mUserPhotoCount = (TextView)findViewById(R.id.picture_count_label);
        mUserProfilePic = (ImageView)findViewById(R.id.user_image);
        mWaitImage = (ImageView)findViewById(R.id.waiting_image);
        mUserCard = findViewById(R.id.user_card_wrapper);
        mDescriptionButton = (TextView)findViewById(R.id.description_button);
        mFlipperController = new UserSearchViewFlipperController(mFlipper, this);

        mSearchUser.setOnClickListener(mClickSearchButton);
        mUserCard.setOnClickListener(mClickUserProfile);
        mDescriptionButton.setOnClickListener(mClickUserDescription);

        Ion.with(mWaitImage).load("android.resource://" + getPackageName() + "/" + R.drawable.batman);//.load("http://i.imgur.com/QNLMtPK.gif");
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
        String secret = getClient().checkAccessToken().getSecret();
        //save token for later
        AuthenticationUtil.setAuthToken(token);
        this.invalidateOptionsMenu(); //this re-loads the menu
    }

    @Override
    public void onLoginFailure(Exception e) {
        e.printStackTrace();
        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
    }
}

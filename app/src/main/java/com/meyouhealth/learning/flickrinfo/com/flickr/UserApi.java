package com.meyouhealth.learning.flickrinfo.com.flickr;

import com.google.gson.Gson;
import com.meyouhealth.learning.flickrinfo.com.RestClient;
import com.meyouhealth.learning.flickrinfo.model.FlickrUser;
import com.meyouhealth.learning.flickrinfo.model.SimpleUser;
import com.meyouhealth.learning.flickrinfo.util.LenientGsonConverter;
import com.meyouhealth.learning.flickrinfo.util.Secrets;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;

public class UserApi extends BaseFlickrApi {

    public static final String METHOD_USER_BY_NAME = "flickr.people.findbyusername";
    public static final String METHOD_USER_BY_ID = "flickr.people.getinfo";

    private interface PeopleInterface {
        @GET("/?api_key=" + Secrets.FLICKR_APPLICATION_KEY + "&method=" + METHOD_USER_BY_NAME + "&format=json&nojsoncallback=1")
        public void getByUsername(@Query("username") String userName, Callback<SingleSimpleUserResponse> callback);

        @GET("/?api_key=" + Secrets.FLICKR_APPLICATION_KEY + "&method=" + METHOD_USER_BY_ID + "&format=json&nojsoncallback=1")
        void getDetailedUserByNsid(@Query("user_id") String nsid, Callback<SingleFlickrUserResponse> callback);
    }

    public static void searchByUsername(RestClient restClient, String userName, Callback<SingleSimpleUserResponse> callback) {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(REST_URL)
                //.setConverter(new LenientGsonConverter(new Gson()))
                .build();

        PeopleInterface service = restAdapter.create(PeopleInterface.class);
        service.getByUsername(userName, callback);
    }

    public static void getDetailedUser(RestClient restClient, String nsid, Callback<SingleFlickrUserResponse> callback) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(REST_URL)
                        // .setConverter(new LenientGsonConverter(new Gson()))
                .build();

        PeopleInterface service = restAdapter.create(PeopleInterface.class);
        service.getDetailedUserByNsid(nsid, callback);
    }

    public static class SingleSimpleUserResponse {
        public SimpleUser user;
    }

    public static class SingleFlickrUserResponse {
        public FlickrUser person;

    }
}

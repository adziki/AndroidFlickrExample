package com.meyouhealth.learning.flickrinfo.com;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.meyouhealth.learning.flickrinfo.util.Secrets;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;

public class RestClient extends OAuthBaseClient {

    public static final Class<? extends Api> REST_API_CLASS = FlickrApi.class;
    public static final String REST_URL = "http://api.flickr.com/services";
    public static final String REST_CONSUMER_KEY = Secrets.FLICKR_APPLICATION_KEY;
    public static final String REST_CONSUMER_SECRET = Secrets.FLICKR_APPLICATION_SECRET;
    public static final String REST_CALLBACK_URL = "oauth://flickr.meyouhealth.com"; // Change this (here and in manifest)

    public RestClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }

    /*
    // CHANGE THIS
    // DEFINE METHODS for different API endpoints here
    public void getInterestingnessList(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("format", "json");
        client.get(apiUrl, params, handler);
    }
*/
	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}

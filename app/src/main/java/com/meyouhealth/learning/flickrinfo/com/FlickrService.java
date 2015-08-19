package com.meyouhealth.learning.flickrinfo.com;

import com.meyouhealth.learning.flickrinfo.model.ContactsResponse;
import com.meyouhealth.learning.flickrinfo.util.Secrets;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;
import retrofit.http.Query;

public interface FlickrService {
//api_key=18596cc9f58bfd5d3c928708a15912ec&format=json&method=flickr.contacts.getlist&nojsoncallback=1
    @GET("/?api_key="+ Secrets.FLICKR_APPLICATION_KEY+"&format=json&method=flickr.contacts.getlist&nojsoncallback=1")
    public ContactsResponse getContactList(
    //public String getContactList(
            @Header("Authorization") String authHeader);
}

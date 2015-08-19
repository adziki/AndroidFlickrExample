package com.meyouhealth.learning.flickrinfo.model;

import com.google.gson.annotations.SerializedName;

public class ContactsResponse {

    @SerializedName("contacts")
    public FlickrContacts contacts;

    @SerializedName("stat")
    public String stat;
}

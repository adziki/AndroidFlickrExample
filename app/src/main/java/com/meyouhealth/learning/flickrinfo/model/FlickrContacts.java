package com.meyouhealth.learning.flickrinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FlickrContacts {

    @Expose
    private Integer page;
    @Expose
    private Integer pages;
    @SerializedName("per_page")
    @Expose
    private String perPage;
    @Expose
    private String perpage;
    @Expose
    private Integer total;
    @Expose
    private List<FlickrContact> contact = new ArrayList<FlickrContact>();
}

package com.meyouhealth.learning.flickrinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FlickrContact {

    @Expose
    public String nsid;
    @Expose
    public String username;
    @Expose
    public String iconserver;
    @Expose
    public Integer iconfarm;
    @Expose
    public Integer ignored;
    @SerializedName("rev_ignored")
    @Expose
    public Integer revIgnored;
    @Expose
    public String realname;
    @Expose
    public Integer friend;
    @Expose
    public Integer family;
    @SerializedName("path_alias")
    @Expose
    public String pathAlias;
    @Expose
    public String location;
}

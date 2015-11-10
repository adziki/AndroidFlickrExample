package com.meyouhealth.learning.flickrinfo.model;

import java.util.Date;

public class FlickrUser {

    private String mProfilePicturePath = "https://farm%s.staticflickr.com/%s/buddyicons/%s.jpg";

    private String nsid;
    private int ispro;
    private int iconserver;
    private int iconfarm;
    private NestedFlickrParameter<String> username;
    private NestedFlickrParameter<String> realname;
    private NestedFlickrParameter<String> location;
    private NestedFlickrParameter<String> description;
    private SimpleUserPhotos photos;

    public static class SimpleUserPhotos {
        public NestedFlickrParameter<Long> firstdate;
        //public String firstdatetaken;
        public NestedFlickrParameter<Long> count;
    }

    public String getNsid() {
        return nsid;
    }

    public String getUserName() {
        try {
            return username.getContent();
        } catch (Exception ex) {}
        return "";
    }

    public String getRealName() {
        try {
            return realname.getContent();
        } catch (Exception ex) {}
        return "";
    }

    public String getDescription() {
        try {
            return description.getContent();
        } catch (Exception ex) {}
        return "";
    }

    public String getFullFilePath() {
        if (iconserver > 0) {
            return String.format(mProfilePicturePath, iconfarm, iconserver, nsid);
        } else {
            return "https://www.flickr.com/images/buddyicon.gif";
        }
    }

    public long getPhotoCount() {
        try {
            return photos.count.getContent();
        } catch (Exception ex) {}
        return 0;
    }


}

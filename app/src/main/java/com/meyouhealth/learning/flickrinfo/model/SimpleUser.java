package com.meyouhealth.learning.flickrinfo.model;

import java.util.Map;

public class SimpleUser {

    public String id;
    public String nsid;
    //public Map<String, String> username;

    public String getUserName() {
        try {
            //return username.get("_content");
        } catch (Exception ex) {

        }
        return "";
    }
}

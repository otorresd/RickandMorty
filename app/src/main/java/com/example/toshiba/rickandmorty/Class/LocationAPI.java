
package com.example.toshiba.rickandmorty.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationAPI {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("url")
    @Expose
    private String url;

    /**
     * No args constructor for use in serialization
     * 
     */
    public LocationAPI() {
    }

    /**
     * 
     * @param name
     * @param url
     */
    public LocationAPI(String name, String url) {
        super();
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}


package com.example.toshiba.rickandmorty.Database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class Location {

    @Id(autoincrement = true)
    private Long locationId;


    private String name;

    @Unique
    private String url;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Location() {
    }

    /**
     * 
     * @param name
     * @param url
     */
    public Location(String name, String url) {
        super();
        this.name = name;
        this.url = url;
    }

    @Generated(hash = 624263779)
    public Location(Long locationId, String name, String url) {
        this.locationId = locationId;
        this.name = name;
        this.url = url;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
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

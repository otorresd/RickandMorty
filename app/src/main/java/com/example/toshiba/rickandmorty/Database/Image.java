package com.example.toshiba.rickandmorty.Database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by toshiba on 6/6/2018.
 */
@Entity
public class Image {

    @Id(autoincrement = true)
    private Long id;

    @Unique
    private String url;

    private byte[] img;

    public Image(String url, byte[] img) {
        this.url = url;
        this.img = img;
    }

    public Image(String url) {
        this.url = url;
    }

    @Generated(hash = 754113891)
    public Image(Long id, String url, byte[] img) {
        this.id = id;
        this.url = url;
        this.img = img;
    }

    @Generated(hash = 1590301345)
    public Image() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}

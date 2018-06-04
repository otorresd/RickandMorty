package com.example.toshiba.rickandmorty.Database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by toshiba on 6/3/2018.
 */
@Entity
public class Episode {

    @Id(autoincrement = true)
    private Long episodeId;
    @Unique
    private String url;

    @Generated(hash = 1865562222)
    public Episode(Long episodeId, String url) {
        this.episodeId = episodeId;
        this.url = url;
    }

    public Episode(String url) {
        this.url = url;
    }

    @Generated(hash = 1336866052)
    public Episode() {
    }

    public Long getEpisodeId() {
        return this.episodeId;
    }

    public void setEpisodeId(Long episodeId) {
        this.episodeId = episodeId;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

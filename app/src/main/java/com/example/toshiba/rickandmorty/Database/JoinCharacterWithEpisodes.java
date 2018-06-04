package com.example.toshiba.rickandmorty.Database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by toshiba on 6/3/2018.
 */
@Entity
public class JoinCharacterWithEpisodes {
    @Id(autoincrement = true)
    private Long id;

    private Long characterId;
    private Long episodeId;

    @Generated(hash = 1318837533)
    public JoinCharacterWithEpisodes(Long id, Long characterId, Long episodeId) {
        this.id = id;
        this.characterId = characterId;
        this.episodeId = episodeId;
    }

    @Generated(hash = 582803957)
    public JoinCharacterWithEpisodes() {
    }

    public JoinCharacterWithEpisodes(Long characterId, Long episodeId) {
        this.characterId = characterId;
        this.episodeId = episodeId;
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getCharacterId() {
        return this.characterId;
    }
    public void setCharacterId(Long characterId) {
        this.characterId = characterId;
    }
    public Long getEpisodeId() {
        return this.episodeId;
    }
    public void setEpisodeId(Long episodeId) {
        this.episodeId = episodeId;
    }
}

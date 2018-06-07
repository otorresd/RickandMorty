
package com.example.toshiba.rickandmorty.Database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.DaoException;

@Entity
public class Character {

    @Id
    private Long id;

    private String name;

    private String status;

    private String species;

    private String type;

    private String gender;

    private Long originId;

    private Long locationId;

    private Long imageId;

    @ToOne(joinProperty = "imageId")
    private Image image;

    @ToMany
    @JoinEntity(
            entity = JoinCharacterWithEpisodes.class,
            sourceProperty = "characterId",
            targetProperty = "episodeId"
    )
    private List<Episode> episode;

    @Unique
    private String url;

    private String created;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 898307126)
    private transient CharacterDao myDao;

    @Generated(hash = 1517498479)
    private transient Long image__resolvedKey;

    /**
     * No args constructor for use in serialization
     *
     */
    public Character() {
    }

    @Generated(hash = 764679917)
    public Character(Long id, String name, String status, String species, String type, String gender, Long originId, Long locationId, Long imageId, String url, String created) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.species = species;
        this.type = type;
        this.gender = gender;
        this.originId = originId;
        this.locationId = locationId;
        this.imageId = imageId;
        this.url = url;
        this.created = created;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1513476086)
    public Image getImage() {
        Long __key = this.imageId;
        if (image__resolvedKey == null || !image__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ImageDao targetDao = daoSession.getImageDao();
            Image imageNew = targetDao.load(__key);
            synchronized (this) {
                image = imageNew;
                image__resolvedKey = __key;
            }
        }
        return image;
    }

    public void setImage(Long imageId) {
        this.imageId = imageId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public long getOriginId() {
        return this.originId;
    }

    public void setOriginId(long originId) {
        this.originId = originId;
    }

    public long getLocationId() {
        return this.locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public void setOriginId(Long originId) {
        this.originId = originId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 38376985)
    public List<Episode> getEpisode() {
        if (episode == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            EpisodeDao targetDao = daoSession.getEpisodeDao();
            List<Episode> episodeNew = targetDao._queryCharacter_Episode(id);
            synchronized (this) {
                if (episode == null) {
                    episode = episodeNew;
                }
            }
        }
        return episode;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 978067064)
    public synchronized void resetEpisode() {
        episode = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 162219484)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCharacterDao() : null;
    }

    public Long getImageId() {
        return this.imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 641415871)
    public void setImage(Image image) {
        synchronized (this) {
            this.image = image;
            imageId = image == null ? null : image.getId();
            image__resolvedKey = imageId;
        }
    }

}


package com.example.toshiba.rickandmorty.Database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.List;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class Character {

    @Id
    private Long id;

    private String name;

    private String status;

    private String species;

    private String type;

    private String gender;

    private long originId;

    @ToOne(joinProperty = "originId")
    private Origin origin;

    private long locationId;

    @ToOne(joinProperty = "locationId")
    private Location location;

    private String image;

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

    @Generated(hash = 1207152639)
    private transient Long origin__resolvedKey;

    @Generated(hash = 1068795426)
    private transient Long location__resolvedKey;

    /**
     * No args constructor for use in serialization
     *
     */
    public Character() {
    }

    /**
     *
     * @param id
     * @param episode
     * @param species
     * @param created
     * @param location
     * @param status
     * @param name
     * @param origin
     * @param image
     * @param gender
     * @param type
     * @param url
     */
    public Character(Long id, String name, String status, String species, String type, String gender, Origin origin, Location location, String image, List<Episode> episode, String url, String created) {
        super();
        this.id = id;
        this.name = name;
        this.status = status;
        this.species = species;
        this.type = type;
        this.gender = gender;
        this.origin = origin;
        this.location = location;
        this.image = image;
        this.episode = episode;
        this.url = url;
        this.created = created;
    }

    @Generated(hash = 284728545)
    public Character(Long id, String name, String status, String species, String type, String gender, long originId, long locationId, String image, String url, String created) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.species = species;
        this.type = type;
        this.gender = gender;
        this.originId = originId;
        this.locationId = locationId;
        this.image = image;
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
    @Generated(hash = 1381079393)
    public Origin getOrigin() {
        long __key = this.originId;
        if (origin__resolvedKey == null || !origin__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            OriginDao targetDao = daoSession.getOriginDao();
            Origin originNew = targetDao.load(__key);
            synchronized (this) {
                origin = originNew;
                origin__resolvedKey = __key;
            }
        }
        return origin;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 967553348)
    public void setOrigin(@NotNull Origin origin) {
        if (origin == null) {
            throw new DaoException("To-one property 'originId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.origin = origin;
            originId = origin.getId();
            origin__resolvedKey = originId;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 469564222)
    public Location getLocation() {
        long __key = this.locationId;
        if (location__resolvedKey == null || !location__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            LocationDao targetDao = daoSession.getLocationDao();
            Location locationNew = targetDao.load(__key);
            synchronized (this) {
                location = locationNew;
                location__resolvedKey = __key;
            }
        }
        return location;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1500142957)
    public void setLocation(@NotNull Location location) {
        if (location == null) {
            throw new DaoException("To-one property 'locationId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.location = location;
            locationId = location.getLocationId();
            location__resolvedKey = locationId;
        }
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

}

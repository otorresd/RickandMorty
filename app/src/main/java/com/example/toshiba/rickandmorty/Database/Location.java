
package com.example.toshiba.rickandmorty.Database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;
import org.greenrobot.greendao.DaoException;

@Entity
public class Location {

    @Id(autoincrement = true)
    private Long id;


    private String name;

    @Unique
    private String url;

    @ToMany(referencedJoinProperty = "originId")
    private List<Character> characterOriginList;

    @ToMany(referencedJoinProperty = "locationId")
    private List<Character> characterLocationList;


    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;


    /** Used for active entity operations. */
    @Generated(hash = 842527347)
    private transient LocationDao myDao;

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

    @Generated(hash = 1835751150)
    public Location(Long id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public Long Id() {
        return id;
    }

    public void Id(Long locationId) {
        this.id = locationId;
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

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 2046915596)
    public List<Character> getCharacterOriginList() {
        if (characterOriginList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CharacterDao targetDao = daoSession.getCharacterDao();
            List<Character> characterOriginListNew = targetDao._queryLocation_CharacterOriginList(id);
            synchronized (this) {
                if (characterOriginList == null) {
                    characterOriginList = characterOriginListNew;
                }
            }
        }
        return characterOriginList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 268908159)
    public synchronized void resetCharacterOriginList() {
        characterOriginList = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 906943752)
    public List<Character> getCharacterLocationList() {
        if (characterLocationList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CharacterDao targetDao = daoSession.getCharacterDao();
            List<Character> characterLocationListNew = targetDao
                    ._queryLocation_CharacterLocationList(id);
            synchronized (this) {
                if (characterLocationList == null) {
                    characterLocationList = characterLocationListNew;
                }
            }
        }
        return characterLocationList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 600366954)
    public synchronized void resetCharacterLocationList() {
        characterLocationList = null;
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
    @Generated(hash = 1046799944)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getLocationDao() : null;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

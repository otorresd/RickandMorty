package com.example.toshiba.rickandmorty.Database;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.toshiba.rickandmorty.Database.Character;
import com.example.toshiba.rickandmorty.Database.Episode;
import com.example.toshiba.rickandmorty.Database.JoinCharacterWithEpisodes;
import com.example.toshiba.rickandmorty.Database.Location;
import com.example.toshiba.rickandmorty.Database.Image;

import com.example.toshiba.rickandmorty.Database.CharacterDao;
import com.example.toshiba.rickandmorty.Database.EpisodeDao;
import com.example.toshiba.rickandmorty.Database.JoinCharacterWithEpisodesDao;
import com.example.toshiba.rickandmorty.Database.LocationDao;
import com.example.toshiba.rickandmorty.Database.ImageDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig characterDaoConfig;
    private final DaoConfig episodeDaoConfig;
    private final DaoConfig joinCharacterWithEpisodesDaoConfig;
    private final DaoConfig locationDaoConfig;
    private final DaoConfig imageDaoConfig;

    private final CharacterDao characterDao;
    private final EpisodeDao episodeDao;
    private final JoinCharacterWithEpisodesDao joinCharacterWithEpisodesDao;
    private final LocationDao locationDao;
    private final ImageDao imageDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        characterDaoConfig = daoConfigMap.get(CharacterDao.class).clone();
        characterDaoConfig.initIdentityScope(type);

        episodeDaoConfig = daoConfigMap.get(EpisodeDao.class).clone();
        episodeDaoConfig.initIdentityScope(type);

        joinCharacterWithEpisodesDaoConfig = daoConfigMap.get(JoinCharacterWithEpisodesDao.class).clone();
        joinCharacterWithEpisodesDaoConfig.initIdentityScope(type);

        locationDaoConfig = daoConfigMap.get(LocationDao.class).clone();
        locationDaoConfig.initIdentityScope(type);

        imageDaoConfig = daoConfigMap.get(ImageDao.class).clone();
        imageDaoConfig.initIdentityScope(type);

        characterDao = new CharacterDao(characterDaoConfig, this);
        episodeDao = new EpisodeDao(episodeDaoConfig, this);
        joinCharacterWithEpisodesDao = new JoinCharacterWithEpisodesDao(joinCharacterWithEpisodesDaoConfig, this);
        locationDao = new LocationDao(locationDaoConfig, this);
        imageDao = new ImageDao(imageDaoConfig, this);

        registerDao(Character.class, characterDao);
        registerDao(Episode.class, episodeDao);
        registerDao(JoinCharacterWithEpisodes.class, joinCharacterWithEpisodesDao);
        registerDao(Location.class, locationDao);
        registerDao(Image.class, imageDao);
    }
    
    public void clear() {
        characterDaoConfig.clearIdentityScope();
        episodeDaoConfig.clearIdentityScope();
        joinCharacterWithEpisodesDaoConfig.clearIdentityScope();
        locationDaoConfig.clearIdentityScope();
        imageDaoConfig.clearIdentityScope();
    }

    public CharacterDao getCharacterDao() {
        return characterDao;
    }

    public EpisodeDao getEpisodeDao() {
        return episodeDao;
    }

    public JoinCharacterWithEpisodesDao getJoinCharacterWithEpisodesDao() {
        return joinCharacterWithEpisodesDao;
    }

    public LocationDao getLocationDao() {
        return locationDao;
    }

    public ImageDao getImageDao() {
        return imageDao;
    }

}

package com.abed.avg.data;

import com.abed.avg.data.local.LocalFilesHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataManager {


    private final LocalFilesHelper mLocalFilesHelper;
//    private final DatabaseHelper mDatabaseHelper;
//    private final PreferencesHelper mPreferencesHelper;
//    private final EventPosterHelper mEventPoster;

    @Inject
    public DataManager(LocalFilesHelper localFilesHelper) {
        mLocalFilesHelper = localFilesHelper;
    }

    public LocalFilesHelper getLocalFilesHelper() {
        return mLocalFilesHelper;
    }

}

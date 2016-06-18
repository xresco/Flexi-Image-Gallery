package com.abed.avg.injection.component;

import android.app.Application;
import android.content.Context;

import com.abed.avg.data.DataManager;
import com.abed.avg.injection.ApplicationContext;
import com.abed.avg.injection.module.ApplicationModule;


import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {


    @ApplicationContext
    Context context();

    Application application();

    DataManager dataManager();
}

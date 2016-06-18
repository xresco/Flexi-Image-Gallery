package com.abed.avg.injection.component;

import com.abed.avg.ui.Main.MainActivity;
import com.abed.avg.injection.PerActivity;
import com.abed.avg.injection.module.ActivityModule;

import dagger.Component;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);

}

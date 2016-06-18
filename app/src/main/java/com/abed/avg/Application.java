package com.abed.avg;

import android.content.Context;

import com.abed.avg.injection.component.ApplicationComponent;
import com.abed.avg.injection.component.DaggerApplicationComponent;
import com.abed.avg.injection.module.ApplicationModule;


public class Application extends android.app.Application {

    ApplicationComponent mApplicationComponent;


    public static Application get(Context context) {
        return (Application) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}

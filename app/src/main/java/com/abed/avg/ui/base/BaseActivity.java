package com.abed.avg.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.abed.avg.Application;
import com.abed.avg.injection.component.ActivityComponent;
import com.abed.avg.injection.component.DaggerActivityComponent;
import com.abed.avg.injection.module.ActivityModule;

public class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(Application.get(this).getComponent())
                    .build();
        }
        return mActivityComponent;
    }
}

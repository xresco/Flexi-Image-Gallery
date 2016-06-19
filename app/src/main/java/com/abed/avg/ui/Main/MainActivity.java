package com.abed.avg.ui.Main;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.abed.avg.R;
import com.abed.avg.controller.util.ViewUtil;
import com.abed.avg.data.model.Photo;
import com.abed.avg.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainMvpView {

    @Inject
    MainPresenter mMainPresenter;
    @Inject
    MainAdapter mainAdapter;

    @BindView(R.id.recylcer_imgs)
    RecyclerView recyclerImgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mMainPresenter.attachView(this);
        mMainPresenter.loadData(this);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerImgs.setLayoutManager(mLayoutManager);
        recyclerImgs.setAdapter(mainAdapter);
        mainAdapter.setClicksListener(new MainAdapter.ViewHolderClicks() {
            @Override
            public void onImageClick(View view, int position) {
                Toast.makeText(view.getContext(), position + "", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.detachView();
    }

    @Override
    public void showPhotos(List<Photo> photos) {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        mainAdapter.updateList(photos, width, width, ViewUtil.dpToPx(5));
    }
}

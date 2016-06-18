package com.abed.avg.ui.Main;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.widget.LinearLayout;

import com.abed.avg.R;
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

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        mMainPresenter.loadData(this, width);


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
//        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                if ((position % itemsInTwoRows >= 0) && (position % itemsInTwoRows <= itemsCountInSmallRow - 1))
//                    return row_size;
//                return itemsCountInSmallRow;
//            }
//        });

        recyclerImgs.setLayoutManager(mLayoutManager);
        recyclerImgs.setAdapter(mainAdapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.detachView();
    }

    @Override
    public void showPhotos(List<Photo>[] photos_rows) {
        mainAdapter.updateList(photos_rows);
    }
}

package com.xiaoqiang.bezier;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xiaoqiang.bezier.view.BezierCurveThreeView;

public class MainActivity extends AppCompatActivity {

    private BezierCurveThreeView mBottleViewSmall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mBottleViewSmall = (BezierCurveThreeView) findViewById(R.id.bottle_view_small);
        mBottleViewSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottleViewSmall.start(0);
            }
        });
    }
}

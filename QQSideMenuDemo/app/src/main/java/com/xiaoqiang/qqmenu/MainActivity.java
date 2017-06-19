package com.xiaoqiang.qqmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xiaoqiang.qqmenu.view.VDHLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,VDHLayout.VDHLayoutListener{



    private VDHLayout vDHLayout;
    private RelativeLayout left;
    private RelativeLayout main;
    private ImageView left_image;
    private ImageView main_image;
//    private ImageView main_top_bg;

    private ListView left_list, main_list;

    private static final String[] data = {"北京", "上海", "武汉", "广州", "西安", "南京", "合肥", "上海", "武汉", "广州", "西安", "南京", "合肥",
            "北京", "上海", "武汉", "广州", "西安", "南京", "合肥", "上海", "武汉", "广州", "西安", "南京", "合肥", "上海", "武汉", "广州", "西安", "南京", "合肥", "上海", "武汉", "广州", "西安", "南京", "合肥"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vDHLayout = (VDHLayout) findViewById(R.id.vDHLayout);
        vDHLayout.setVdhLayoutListener(this);
        left = (RelativeLayout) findViewById(R.id.left);
        main = (RelativeLayout) findViewById(R.id.main);

        left_image = (ImageView) findViewById(R.id.left_image);
        left_image.setOnClickListener(this);

        main_image = (ImageView) findViewById(R.id.main_image);
        main_image.setOnClickListener(this);


        left_list = (ListView) findViewById(R.id.left_list);
        left_list.setAdapter(new ArrayAdapter<String>(this, R.layout.online_user_list_item, data));
        left_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toasts("left_list" + ">>>单击>>>>" + position);
            }
        });

        main_list = (ListView) findViewById(R.id.main_list);
        main_list.setAdapter(new ArrayAdapter<String>(this, R.layout.online_user_list_item, data));
        main_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toasts("main_list" + ">>>单击>>>>" + position);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_image:
                Toasts("单击left_image");
                break;
            case R.id.main_image:
                Toasts("单击main_image");
                break;
        }
    }


    @Override
    public void open() {
    }

    @Override
    public void close() {
    }

    @Override
    public void onDrag(float percent) {
    }

    /**
     * 提示信息
     * @param stu
     */
    public void Toasts(String stu) {
        Toast.makeText(this, stu, Toast.LENGTH_SHORT).show();
    }
}

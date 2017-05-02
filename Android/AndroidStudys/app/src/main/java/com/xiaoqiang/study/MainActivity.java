package com.xiaoqiang.study;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private ViewHolder viewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewHolder = new ViewHolder(getRootView(MainActivity.this));
        // Example of a call to a native method
        viewHolder.sample_text.setText(stringFromJNI());
    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public static class ViewHolder {
        public View rootView;
        public TextView sample_text,sample_texts;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.sample_text = (TextView) rootView.findViewById(R.id.sample_text);
            this.sample_texts = (TextView) rootView.findViewById(R.id.sample_texts);
        }

    }

    private static View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }

}

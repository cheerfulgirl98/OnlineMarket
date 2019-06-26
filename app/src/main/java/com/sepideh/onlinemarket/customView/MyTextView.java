package com.sepideh.onlinemarket.customView;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.sepideh.onlinemarket.utils.MyApplication;

/**
 * Created by pc on 4/16/2019.
 */

public class MyTextView extends AppCompatTextView {
    public MyTextView(Context context) {
        super(context);
        setTypeface(MyApplication.NORMAL_TYPEFACE);


    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(MyApplication.NORMAL_TYPEFACE);

    }
}

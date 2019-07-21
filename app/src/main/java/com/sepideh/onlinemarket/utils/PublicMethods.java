package com.sepideh.onlinemarket.utils;


import androidx.fragment.app.FragmentTransaction;
import android.content.Context;
import android.os.Build;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import com.orhanobut.hawk.Hawk;
import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.data.UserInfo;

/**
 * Created by pc on 4/27/2019.
 */

public class PublicMethods {



    public static String changeToPersianNumber(String number) {
        String tmp = "";
        for (char mChar : number.toCharArray()) {

            if (mChar >= '0' && mChar <= '9') {
                tmp += new String(Character.toChars(mChar + 0x06f1 - 49));
            }else {
                tmp += mChar;
            }
        }

        return tmp;

    }

    public static void setSnackbar(View viewId, String msg, int colorId){
        Snackbar snackbar = Snackbar.make(viewId,msg , Snackbar.LENGTH_LONG);
        snackbar.show();
        View snackView = snackbar.getView();
        TextView snackTextview=snackView.findViewById(R.id.snackbar_text);
        snackView.setBackgroundColor(colorId);
        snackTextview.setTextSize(12);
        snackView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            snackTextview.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        else
            snackTextview.setGravity(Gravity.CENTER_HORIZONTAL);


    }


   public static boolean checkLogin() {

        UserInfo userInfo;
        userInfo = Hawk.get(MyApplication.appInstance.getString(R.string.loginUserInfoTag));
        if (userInfo == null) {
            return false;
        }
        return true;
    }

    public static void goNewFragment(Context context,int viewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction;
        fragmentTransaction =((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(viewId, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static void setBadgeNotif(Context mContext,TextView badgeNotif) {
        int sabadsize = Hawk.get(mContext.getString(R.string.Hawk_sabad_size), 0);
        if (sabadsize > 0) {
            badgeNotif.setVisibility(View.VISIBLE);
            badgeNotif.setText(String.valueOf(sabadsize));
        } else badgeNotif.setVisibility(View.GONE);
    }





}

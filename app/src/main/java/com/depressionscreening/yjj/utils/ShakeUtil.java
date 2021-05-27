package com.depressionscreening.yjj.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.depressionscreening.yjj.R;

public class ShakeUtil {
    public static void shake(Context context, View view){
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake_anim));
    }
}

package net.nikonorov.translator.translator;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * Created by vitaly on 30.09.15.
 */
public class Direction {
    int langFrom;
    int langTo;
    String direction;

    Direction(String direction, Context context){
        /*
        if(direction.substring(0,2).equals("az")){
            this.langFrom = R.drawable.az;
        }else {
            this.langFrom = R.drawable.ic_settings_applications_black_48dp;
        }
        */
        this.langFrom = getIntAndroidDrawable(direction.substring(0,2), context);
        this.langTo = getIntAndroidDrawable(direction.substring(3,5), context);
        this.direction = direction;
    }

    static public int getIntAndroidDrawable(String pDrawableName, Context context){
        int resourceId= context.getResources().getIdentifier(pDrawableName, "drawable", context.getPackageName());
        if(resourceId==0){
            return R.drawable.ic_settings_applications_black_48dp;
        } else {
            return resourceId;
        }
    }

}

package team.ideart.shiguang_app.app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.Preference;

/**
 * Created by yestin on 2015/11/21.
 */
public class StaticHolder {

    private static String token = null;

    private static String SP_NAME="sp";

    public static SharedPreferences sp;

    public static String getToken(Activity act){
        sp = act.getSharedPreferences(SP_NAME,0);
        return sp.getString("token",null);
    }

    public static void setToken(Activity act,String token){
        sp = act.getSharedPreferences(SP_NAME,0);
        StaticHolder.token = token;
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token",token);
        editor.commit();
    }


}

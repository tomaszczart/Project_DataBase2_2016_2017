package com.nowak01011111.damian.bunchoftools.apiClient;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by utche on 06.01.2017.
 */

public class SaveSharedPreference {
    static final String PREF_USER_ID= "user_id";
    static final String PREF_EMPLOYEE_ID= "employee_id";
    static final String PREF_TOKEN= "token";


    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserId(Context ctx, int id)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt(PREF_USER_ID, id);
        editor.commit();
    }

    public static int getUserId(Context ctx)
    {
        return getSharedPreferences(ctx).getInt(PREF_USER_ID, -1);
    }

    public static void setEmpolyeeId(Context ctx, int id)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt(PREF_EMPLOYEE_ID, id);
        editor.commit();
    }

    public static int getEmpolyeeId(Context ctx)
    {
        return getSharedPreferences(ctx).getInt(PREF_EMPLOYEE_ID, -1);
    }

    public static void setToken(Context ctx, String token)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_TOKEN, token);
        editor.commit();
    }

    public static String getToken(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_TOKEN, "");
    }
}

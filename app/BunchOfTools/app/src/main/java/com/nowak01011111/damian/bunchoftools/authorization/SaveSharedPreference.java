package com.nowak01011111.damian.bunchoftools.authorization;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by utche on 06.01.2017.
 */

public class SaveSharedPreference {
    static final String PREF_TOKEN = "token";
    static final String PREF_TOKEN_IS_EMPLOYEE= "is_employee";


    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
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

    public static void setIsEmployee(Context ctx, boolean isEmployee)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(PREF_TOKEN_IS_EMPLOYEE, isEmployee);
        editor.commit();
    }

    public static boolean getIsEmployee(Context ctx)
    {
        return getSharedPreferences(ctx).getBoolean(PREF_TOKEN_IS_EMPLOYEE, false);
    }
}

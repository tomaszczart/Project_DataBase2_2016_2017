package com.nowak01011111.damian.bunchoftools.authorization;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by utche on 06.01.2017.
 */

public class SaveSharedPreference {
    static final String PREF_TOKEN_USERNAME= "username";
    static final String PREF_TOKEN_IS_EMPLOYEE= "is_employee";


    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUsername(Context ctx, String username)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_TOKEN_USERNAME, username);
        editor.commit();
    }

    public static String getUsername(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_TOKEN_USERNAME, "");
    }

    public static void setIsEmployee(Context ctx, int isEmployee)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt(PREF_TOKEN_IS_EMPLOYEE, isEmployee);
        editor.commit();
    }

    public static int getEmployeeId(Context ctx)
    {
        return getSharedPreferences(ctx).getInt(PREF_TOKEN_IS_EMPLOYEE, -1);
    }
}

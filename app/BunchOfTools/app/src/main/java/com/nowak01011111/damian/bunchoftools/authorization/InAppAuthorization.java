package com.nowak01011111.damian.bunchoftools.authorization;

import android.content.Context;

import com.nowak01011111.damian.bunchoftools.activity.MainActivity;

/**
 * Created by utche on 07.01.2017.
 */

public class InAppAuthorization {

    public static boolean isUserLoggedIn(Context context){
        String token = SaveSharedPreference.getToken(context);
        boolean isEmployee = SaveSharedPreference.getIsEmployee(context);
        return ((token !=null && !token.isEmpty()) && !isEmployee);
    }

    public static boolean isEmployeeLoggedIn(Context context){
        String token = SaveSharedPreference.getToken(context);
        boolean isEmployee = SaveSharedPreference.getIsEmployee(context);
        return ((token !=null && !token.isEmpty()) && isEmployee);
    }
}

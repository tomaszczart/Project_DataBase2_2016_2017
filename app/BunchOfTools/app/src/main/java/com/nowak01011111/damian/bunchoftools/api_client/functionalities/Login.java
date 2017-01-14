package com.nowak01011111.damian.bunchoftools.api_client.functionalities;

import android.content.Context;
import com.nowak01011111.damian.bunchoftools.authorization.SaveSharedPreference;

/**
 * Created by utche on 13.01.2017.
 */

public class Login {

    public static void saveToken(String token, boolean isEmployee, Context context){
        SaveSharedPreference.setToken(context,token);
        SaveSharedPreference.setIsEmployee(context,isEmployee);
    }
    public static void logout(Context context){
        SaveSharedPreference.setToken(context,"");
        SaveSharedPreference.setIsEmployee(context,false);
    }

}

package com.nowak01011111.damian.bunchoftools.api_client.functionalities;

import com.nowak01011111.damian.bunchoftools.entity.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by utche on 14.01.2017.
 */

public class Categories {
    public static List<Category> parseResult(String result){
        List<Category> categoryList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0;i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("category_id");
                String name = jsonObject.getString("name");
                String description = jsonObject.getString("description");
                Category category = new Category(id,name,description);
                categoryList.add(category);
            }
        } catch (JSONException e) {
        }
        return categoryList;
    }
}

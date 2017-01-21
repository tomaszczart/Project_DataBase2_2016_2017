package com.nowak01011111.damian.bunchoftools.api_client.functionalities;
import com.nowak01011111.damian.bunchoftools.api_client.ApiConnectionFragment;
import com.nowak01011111.damian.bunchoftools.entity.Model;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by utche on 14.01.2017.
 */

public class Models {
    public static List<Model> parseResult(String result) {
        List<Model> modelList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("model_id");
                String name = jsonObject.getString("name");
                String description = jsonObject.getString("description");
                float pricePerHour = jsonObject.getLong("price_per_hour");
                int categoryId = jsonObject.getInt("category_id");
                String imageUrl = ApiConnectionFragment.URL_API + ApiConnectionFragment.URL_API_GET_MODEL_IMAGE + id;
                Model model = new Model(id, name, description, pricePerHour, categoryId,imageUrl);
                modelList.add(model);
            }
        } catch (JSONException e) {
        }
        return modelList;
    }
}

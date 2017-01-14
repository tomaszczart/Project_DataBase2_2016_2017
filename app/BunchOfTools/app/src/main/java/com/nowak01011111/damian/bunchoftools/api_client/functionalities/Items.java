package com.nowak01011111.damian.bunchoftools.api_client.functionalities;

import com.nowak01011111.damian.bunchoftools.entity.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by utche on 14.01.2017.
 */

public class Items {
    public static List<Item> parseResult(String result) {
        List<Item> itemList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int itemId = jsonObject.getInt("item_id");
                int modelId = jsonObject.getInt("model_id");
                Item.Condition condition = Item.Condition.valueOf(jsonObject.getString("condition"));
                Item.Status status = Item.Status.valueOf(jsonObject.getString("itemstatus"));
                Item item = new Item(itemId, modelId, condition, status);
                itemList.add(item);
            }
        } catch (JSONException e) {
        }
        return itemList;
    }
}

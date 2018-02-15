package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String NAME_OBJECT_KEY = "name";
    private static final String MAIN_NAME_KEY = "mainName";
    private static final String ALSO_KNOWN_AS_LIST_KEY = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN_KEY = "placeOfOrigin";
    private static final String DESCRIPTION_KEY = "description";
    private static final String IMAGE_KEY = "image";
    private static final String INGREDIENTS_LIST_KEY = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        try {
            Sandwich sandwich = new Sandwich();
            JSONObject sandwichJsonObject = new JSONObject(json);

            JSONObject nameJsonObject = sandwichJsonObject.getJSONObject(NAME_OBJECT_KEY);
            sandwich.setMainName(nameJsonObject.getString(MAIN_NAME_KEY));
            sandwich.setAlsoKnownAs(getAlsoKnownAsList(nameJsonObject.getJSONArray(ALSO_KNOWN_AS_LIST_KEY)));

            sandwich.setPlaceOfOrigin(sandwichJsonObject.getString(PLACE_OF_ORIGIN_KEY));
            sandwich.setDescription(sandwichJsonObject.getString(DESCRIPTION_KEY));
            sandwich.setImage(sandwichJsonObject.getString(IMAGE_KEY));
            sandwich.setIngredients(getIngredientsList(sandwichJsonObject.getJSONArray(INGREDIENTS_LIST_KEY)));

            return sandwich;
        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }
    }

    private static List<String> getAlsoKnownAsList(JSONArray alsoKnownAsJsonArray) throws JSONException {
        List<String> alsoKnownAsList = new ArrayList<>();

        if (alsoKnownAsJsonArray == null) {
            return alsoKnownAsList;
        }

        for (int i = 0; i < alsoKnownAsJsonArray.length(); i++) {
            alsoKnownAsList.add(alsoKnownAsJsonArray.getString(i));
        }

        return alsoKnownAsList;
    }

    private static List<String> getIngredientsList(JSONArray ingredientsJsonArray) throws JSONException {
        List<String> ingredientsList = new ArrayList<>();

        if (ingredientsJsonArray == null) {
            return ingredientsList;
        }

        for (int i = 0; i < ingredientsJsonArray.length(); i++) {
            ingredientsList.add(ingredientsJsonArray.getString(i));
        }

        return ingredientsList;
    }
}

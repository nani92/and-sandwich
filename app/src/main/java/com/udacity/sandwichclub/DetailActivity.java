package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        populateAlsoKnownAs(sandwich.getAlsoKnownAs());
        populatePlaceOfOrigin(sandwich.getPlaceOfOrigin());
        ((TextView) findViewById(R.id.description_tv)).setText(sandwich.getDescription());
        populateIngredients(sandwich.getIngredients());
    }

    private void populateAlsoKnownAs(List<String> alsoKnownAsList) {
        TextView alsoKnownAsTextView = findViewById(R.id.also_known_tv);

        if (alsoKnownAsList.isEmpty()) {
            alsoKnownAsTextView.setVisibility(View.GONE);
            findViewById(R.id.also_known_label_tv).setVisibility(View.GONE);
        }

        for (int i = 0; i < alsoKnownAsList.size(); i++) {
            alsoKnownAsTextView.append(alsoKnownAsList.get(i));

            if (i != alsoKnownAsList.size() - 1) {
                alsoKnownAsTextView.append(", ");
            }
        }
    }

    private void populatePlaceOfOrigin(String placeOfOrigin) {
        TextView placeOfOriginTextView = findViewById(R.id.origin_tv);

        if (TextUtils.isEmpty(placeOfOrigin)) {
            placeOfOriginTextView.setVisibility(View.GONE);
            findViewById(R.id.origin_label_tv).setVisibility(View.GONE);

            return;
        }

        placeOfOriginTextView.setText(placeOfOrigin);
    }

    private void populateIngredients(List<String> ingredientsList) {
        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);

        for (int i = 0; i < ingredientsList.size(); i++) {
            ingredientsTextView.append(ingredientsList.get(i));

            if (i != ingredientsList.size() - 1) {
                ingredientsTextView.append(", ");
            }
        }
    }
}

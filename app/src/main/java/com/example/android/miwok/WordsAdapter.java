package com.example.android.miwok;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.parseColor;


public class WordsAdapter extends ArrayAdapter<english> {

    private int mbackgroundColor;

    public WordsAdapter(Activity context, ArrayList<english> words, int backgroundColor) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, words);
        mbackgroundColor = backgroundColor;
    }

    @Override
    public View getView ( int position, View convertView, ViewGroup parent){
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_list, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        final english currentWord = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView miwokText = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        miwokText.setText(currentWord.getMiwokTranslation());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView englishText = (TextView) listItemView.findViewById(R.id.english_text_view);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        englishText.setText(currentWord.getEnglishTranslation());

        ImageView imageView= (ImageView) listItemView.findViewById(R.id.image);

        if (currentWord.hasImage()) {
            imageView.setImageResource(currentWord.getImageResourceId());
        } else {
            imageView.setVisibility(View.GONE);
        }

        listItemView.findViewById(R.id.item).setBackgroundColor(ContextCompat.getColor(getContext(), mbackgroundColor));

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView

        return listItemView;


    }
}


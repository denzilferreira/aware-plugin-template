package com.aware.plugin.template;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.aware.utils.IContextCard;

public class ContextCard implements IContextCard {

    //Empty constructor used to instantiate this card
    public ContextCard(){};

    @Override
    public View getContextCard(Context context) {
        //Inflate and return your card's layout. See LayoutInflater documentation.
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View card = (View) inflater.inflate(R.layout.card, null);

        //Modify card's content

        return card;
    }
}

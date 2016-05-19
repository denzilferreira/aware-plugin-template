package com.aware.plugin.template;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.aware.utils.IContextCard;

import java.util.Timer;
import java.util.TimerTask;

public class ContextCard implements IContextCard {

    //DEMO: we are demo'ing a counter incrementing in real-time
    int counter = 0;

    final Handler uiUpdater = new Handler();
    TextView counter_txt;

    //Constructor used to instantiate this card
    public ContextCard() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateUI();
            }
        }, 0, 1000); //update the UI every 1 second
    }

    @Override
    public View getContextCard(Context context) {
        //Load card information to memory
        LayoutInflater sInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View card = sInflater.inflate(R.layout.card, null);

        //Initialize UI elements from the card
        counter_txt = (TextView) card.findViewById(R.id.counter);

        //Return the card to AWARE/apps
        return card;
    }

    private void updateUI() {
        //increment counter
        counter++;

        //update UI
        uiUpdater.post(new Runnable() {
            @Override
            public void run() {
                counter_txt.setText(String.valueOf(counter));
            }
        });
    }
}

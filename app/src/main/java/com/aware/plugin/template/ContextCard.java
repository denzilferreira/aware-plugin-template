package com.aware.plugin.template;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.aware.Aware;
import com.aware.ui.Stream_UI;
import com.aware.utils.IContextCard;

public class ContextCard implements IContextCard {

    //Set how often your card needs to refresh if the stream is visible (in milliseconds)
    private int refresh_interval = 1 * 1000; //1 second = 1000 milliseconds

    private int counter = 0;

    private Handler uiRefresher = new Handler(Looper.getMainLooper());
    private Runnable uiChanger = new Runnable() {
        @Override
        public void run() {
            counter++;

            //Modify card's content here once it's initialized
            if( card != null ) {
                counter_txt.setText(""+counter);
            }

            //Ask this to run again at refresh_interval
            uiRefresher.postDelayed(uiChanger, refresh_interval);
        }
    };

    //Empty constructor used to instantiate this card
    public ContextCard(){};

    //Used to allow integration with Android API's
    private Context sContext;

    //Used to load your context card
    private LayoutInflater sInflater;

    //Declare here all the UI elements you'll need access on the uiChanger Runnable
    private View card;
    private TextView counter_txt;

    @Override
    public View getContextCard(Context context) {
        sContext = context;

        //Monitor Stream status
        IntentFilter filter = new IntentFilter();
        filter.addAction(Stream_UI.ACTION_AWARE_STREAM_OPEN);
        filter.addAction(Stream_UI.ACTION_AWARE_STREAM_CLOSED);
        context.registerReceiver(streamObs, filter);

        //Load card information to memory
        sInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        card = sInflater.inflate(R.layout.card, null);

        //Fetch UI elements from the card
        counter_txt = (TextView) card.findViewById(R.id.counter);

        //Start refreshing at designed refresh rate
        uiRefresher.postDelayed(uiChanger, refresh_interval);

        return card;
    }

    //Receiver that keeps track of stream status. Used to stop the refresh when user leaves the stream
    private StreamObs streamObs = new StreamObs();
    public class StreamObs extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if( intent.getAction().equals(Stream_UI.ACTION_AWARE_STREAM_OPEN) ) {
                //start refreshing
                counter = 0;
                uiRefresher.postDelayed(uiChanger, refresh_interval);
            }
            if( intent.getAction().equals(Stream_UI.ACTION_AWARE_STREAM_CLOSED) ) {
                //stop refreshing
                uiRefresher.removeCallbacks(uiChanger);
                uiRefresher.removeCallbacksAndMessages(null);
            }
        }
    }
}

package com.mobius.legend.battle.ui;

import com.mobius.legend.R;
import com.mobius.legend.battle.BattleEngine;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RoundResultsActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_round);
        
        String roundText = BattleEngine.getInstance().getResults();
        TextView text = (TextView) this.findViewById(R.id.text);
        text.setText(roundText);
        
        Button proceed = (Button) this.findViewById(R.id.proceed);
        proceed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
				if (BattleEngine.getInstance().isRunning()) {
					BattleEngine.getInstance().doRound();
				}
			}
        	
        });
    }
}
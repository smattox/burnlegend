package com.mobius.legend.battle.brain.local;

import com.mobius.legend.R;
import com.mobius.legend.battle.BattleEngine;
import com.mobius.legend.battle.ChosenAction;
import com.mobius.legend.battle.brain.ITechniqueSelectionCallback;
import com.mobius.legend.character.BattleCharacter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PreviewRoundActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_round);
        
        final BattleCharacter character = BattleEngine.getInstance().getCurrentCharacter();
        String roundText = BattleEngine.getInstance().getRoundReview();
        TextView text = (TextView) this.findViewById(R.id.text);
        text.setText(roundText);
        
        Button proceed = (Button) this.findViewById(R.id.proceed);
        proceed.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
				HumanPlayer.reviewCallback.choose(null);
			}
        });
        
        Button reversal = (Button) this.findViewById(R.id.reversal);
        if (!BattleEngine.getInstance().canReversal(character)) {
        	reversal.setEnabled(false);
        }
        reversal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				BattleEngine.getInstance().doReversal(character, new ITechniqueSelectionCallback() {
					@Override
					public void choose(ChosenAction action) {
						finish();
						action.useReversal();
						HumanPlayer.reviewCallback.choose(action);
					}
				});
			}
        	
        });
    }
}
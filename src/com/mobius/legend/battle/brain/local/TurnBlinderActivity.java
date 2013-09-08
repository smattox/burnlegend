package com.mobius.legend.battle.brain.local;

import com.mobius.legend.R;
import com.mobius.legend.battle.BattleEngine;
import com.mobius.legend.character.ICharacter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TurnBlinderActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.turn_blinder_screen);
        
        ICharacter character = BattleEngine.getInstance().getCurrentCharacter();
        
        TextView name = (TextView) this.findViewById(R.id.name);
        name.setText(character.getName());
        
        Button proceed = (Button) this.findViewById(R.id.proceed);
        proceed.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
				HumanPlayer.proceed();
			}
        });
	}
}

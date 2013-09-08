package com.mobius.legend;

import com.mobius.legend.battle.ui.BattleSetupActivity;
import com.mobius.legend.management.CharacterRosterActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BurnLegendMainMenuActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        
        Button manageButton = (Button)this.findViewById(R.id.manageButton);
        manageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				BurnLegendMainMenuActivity.this.startActivity(new Intent(BurnLegendMainMenuActivity.this,
						CharacterRosterActivity.class));
			}
        });
        
        Button battleButton = (Button)this.findViewById(R.id.battleButton);
        battleButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				BurnLegendMainMenuActivity.this.startActivity(new Intent(BurnLegendMainMenuActivity.this,
						BattleSetupActivity.class));
			}
        });
    }
}
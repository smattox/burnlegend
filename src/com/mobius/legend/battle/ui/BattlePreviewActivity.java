package com.mobius.legend.battle.ui;

import com.mobius.legend.R;
import com.mobius.legend.battle.BattleEngine;
import com.mobius.legend.character.ICharacter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class BattlePreviewActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.battle_preview_screen);
        
        ICharacter top = BattleEngine.getInstance().getCharacters()[0];
        ICharacter bottom = BattleEngine.getInstance().getCharacters()[1];
        
        TextView topName = (TextView) this.findViewById(R.id.top_name);
        TextView topType = (TextView) this.findViewById(R.id.top_type);
        TextView bottomName = (TextView) this.findViewById(R.id.bottom_name);
        TextView bottomType = (TextView) this.findViewById(R.id.bottom_type);
        
        topName.setText(top.getName());
        topType.setText(top.getType().getLabel(top));
        bottomName.setText(bottom.getName());
        bottomType.setText(bottom.getType().getLabel(bottom));
        
        Button proceed = (Button) this.findViewById(R.id.proceed);
        proceed.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
				BattleEngine.getInstance().begin();
			}
        });
	}
}

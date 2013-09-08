package com.mobius.legend.battle.ui;

import com.mobius.legend.R;
import com.mobius.legend.battle.BattleEngine;
import com.mobius.legend.battle.PlayerType;
import com.mobius.legend.battle.brain.IBrain;
import com.mobius.legend.battle.brain.ai.RobotPlayer;
import com.mobius.legend.battle.brain.local.HumanPlayer;
import com.mobius.legend.character.BattleCharacter;
import com.mobius.legend.character.CharacterData;
import com.mobius.legend.character.CharacterRoster;
import com.mobius.legend.character.CharacterStatus;
import com.mobius.legend.character.RandomCharacterGenerator;
import com.mobius.legend.management.CharacterManagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class BattleSetupActivity extends Activity {
	private int topStocks = CharacterManagement.ABSOLUTE_MAX_STOCKS;
	private int topWillpower = CharacterManagement.ABSOLUTE_MAX_WILLPOWER;
	//private int bottomStocks = CharacterManagement.ABSOLUTE_MAX_STOCKS;
	//private int bottomWillpower = CharacterManagement.ABSOLUTE_MAX_WILLPOWER;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battle_setup_screen);
        
        final Spinner topPlayerSpinner = (Spinner) this.findViewById(R.id.player1);
        final Spinner bottomPlayerSpinner = (Spinner) this.findViewById(R.id.player2);
        
        ArrayAdapter<PlayerType> topPlayerAdapter = new ArrayAdapter<PlayerType>(this, android.R.layout.simple_spinner_item,
        		PlayerType.values());
        ArrayAdapter<PlayerType> bottomPlayerAdapter = new ArrayAdapter<PlayerType>(this, android.R.layout.simple_spinner_item,
        		PlayerType.values());
        topPlayerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bottomPlayerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        topPlayerSpinner.setAdapter(topPlayerAdapter);
        bottomPlayerSpinner.setAdapter(bottomPlayerAdapter);
        topPlayerSpinner.setSelection(PlayerType.Human.ordinal());
        bottomPlayerSpinner.setSelection(PlayerType.CPU.ordinal());
 
        final Spinner topCharacterSpinner = (Spinner) this.findViewById(R.id.character1);
        final Spinner bottomCharacterSpinner = (Spinner) this.findViewById(R.id.character2);
        
        BaseAdapter first = new CharacterSelectAdapter();
        BaseAdapter second = new CharacterSelectAdapter();
        topCharacterSpinner.setAdapter(first);
        bottomCharacterSpinner.setAdapter(second);
        
        SeekBar firstStockBar = (SeekBar) this.findViewById(R.id.topCharacterStocks);
        SeekBar firstWillpowerBar = (SeekBar) this.findViewById(R.id.topCharacterWillpower);
        firstStockBar.setMax(topStocks - 1);
        firstWillpowerBar.setMax(topWillpower - 1);
        
        firstStockBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
				topStocks = progress + 1;
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
			}
 
        });
        
        firstStockBar.setProgress(firstStockBar.getMax());
        firstWillpowerBar.setProgress(firstWillpowerBar.getMax());
        
        Button fightButton = (Button) this.findViewById(R.id.proceed);
        fightButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				CharacterData one = (CharacterData) topCharacterSpinner.getSelectedItem();
				CharacterData two = (CharacterData) bottomCharacterSpinner.getSelectedItem();
				
				int randXP = 0;
				if (one == null && two != null) {
					randXP = CharacterManagement.getTotalXPSpent(two);
				}
				if (two == null && one != null) {
					randXP = CharacterManagement.getTotalXPSpent(one);
				}
				one = one != null ? one : RandomCharacterGenerator.getRandomCharacter(randXP);
				two = two != null ? two : RandomCharacterGenerator.getRandomCharacter(randXP);
				
				boolean hotseat = (PlayerType)topPlayerSpinner.getSelectedItem() == PlayerType.Human &&
								  (PlayerType)bottomPlayerSpinner.getSelectedItem() == PlayerType.Human;
				IBrain topBrain = null, bottomBrain = null;
				switch ((PlayerType)topPlayerSpinner.getSelectedItem()) {
				case Human:
					topBrain = new HumanPlayer(hotseat);
					break;
				case CPU:
					topBrain = new RobotPlayer();
					break;
				}
				switch ((PlayerType)bottomPlayerSpinner.getSelectedItem()) {
				case Human:
					bottomBrain = new HumanPlayer(hotseat);
					break;
				case CPU:
					bottomBrain = new RobotPlayer();
					break;
				}
				
				finish();
				BattleEngine.getInstance().setContext(BattleSetupActivity.this);
				BattleEngine.getInstance().setupBattle(
						new BattleCharacter(topBrain, one, new CharacterStatus(CharacterManagement.ABSOLUTE_MAX_STOCKS)),
						new BattleCharacter(bottomBrain, two, new CharacterStatus(CharacterManagement.ABSOLUTE_MAX_STOCKS)));
				
				Intent activity = new Intent(BattleSetupActivity.this, BattlePreviewActivity.class);
				BattleSetupActivity.this.startActivity(activity);
			}
        });
    }
	
	private class CharacterSelectAdapter extends BaseAdapter {

		private CharacterData[] getRoster() {
			return CharacterRoster.getInstance(BattleSetupActivity.this).getAllCharacters();
		}
		
		@Override
		public int getCount() {
			return getRoster().length + 1;
		}

		@Override
		public Object getItem(int arg0) {
			if (arg0 == 0) {
				return null;
			}
			return getRoster()[arg0 - 1];
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int characterIndex, View convertView, ViewGroup parent) {
			View view = null;
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(BattleSetupActivity.this);
	        	view = inflater.inflate(parent instanceof Spinner ? android.R.layout.simple_spinner_item :
	        		android.R.layout.simple_spinner_dropdown_item, null, false);
			} else {
				view = convertView;
			}
			TextView text = (TextView) view.findViewById(android.R.id.text1);
			//text.setTextSize(StringUtils.getLargeListSize());
			CharacterData character = (CharacterData)getItem(characterIndex);
			String label;
			if (character != null) {
				int xp = 0;
				if (character.isAdvancing()) {
					xp = CharacterManagement.getTotalXPSpent(character);
				}
				label = character.getName() + (xp > 0 ? " (" + xp + " XP)" : "");
			} else {
				label = BattleSetupActivity.this.getString(R.string.RandomNew);
			}
			text.setText(label);
			return view;
		}
		
	}
}


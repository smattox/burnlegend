package com.mobius.legend.management;

import com.mobius.legend.R;
import com.mobius.legend.character.CharacterData;
import com.mobius.legend.character.CharacterRoster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CharacterRosterActivity extends Activity {
	
	private BaseAdapter adapter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.character_roster);
		
		ListView roster = (ListView) this.findViewById(R.id.roster);
		adapter = new CharacterRosterAdapter();
		roster.setAdapter(adapter);
		roster.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				CharacterData character = (CharacterData) adapter.getItem(arg2);
				if (character == null) {
					character = new CharacterData();
				}
				CharacterManagement.character = character;
				CharacterRosterActivity.this.startActivity(new Intent(CharacterRosterActivity.this,
						CharacterManagementBasicsActivity.class));
			}
		});
	}
	
	@Override
	public void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
	}
	
	private class CharacterRosterAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return CharacterRoster.getInstance(CharacterRosterActivity.this).getAllCharacters().length + 1;
		}

		@Override
		public Object getItem(int arg0) {
			CharacterData[] characters = CharacterRoster.getInstance(CharacterRosterActivity.this).getAllCharacters();
			return arg0 == characters.length ? null : characters[arg0];
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int characterIndex, View convertView, ViewGroup arg2) {
			View view = null;
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(CharacterRosterActivity.this);
	        	view = inflater.inflate(android.R.layout.activity_list_item, null, false);
			} else {
				view = convertView;
			}
			TextView text = (TextView) view.findViewById(android.R.id.text1);
			text.setTextSize(24);
			if (!isNewCharacterIndex(characterIndex)) {
				text.setText(((CharacterData)getItem(characterIndex)).getName());
			} else {
				text.setText(R.string.AddNewCharacter);
			}
			return view;
		}
		
		private boolean isNewCharacterIndex(int characterIndex) {
			return characterIndex == CharacterRoster.getInstance(CharacterRosterActivity.this).getAllCharacters().length;
		}
	}
}

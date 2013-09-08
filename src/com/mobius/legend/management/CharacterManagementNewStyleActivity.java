package com.mobius.legend.management;

import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.mobius.legend.R;
import com.mobius.legend.technique.Style;
import com.mobius.legend.technique.TechniqueRegistry;
import com.mobius.legend.utilities.StringUtils;

public class CharacterManagementNewStyleActivity extends Activity {
		
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.character_new_style_selection);
		
		ListView roster = (ListView) this.findViewById(R.id.list);
		BaseAdapter adapter = new CharacterNewStyleAdapter();
		roster.setAdapter(adapter);
		roster.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int styleIndex,
					long arg3) {
				Intent intent = new Intent(CharacterManagementNewStyleActivity.this,
						CharacterManagementNewTechniqueActivity.class);
				intent.putExtra(CharacterManagementNewTechniqueActivity.KNOWN_STYLE, false);
				intent.putExtra(CharacterManagementNewTechniqueActivity.STYLE_INDEX, styleIndex);
				CharacterManagementNewStyleActivity.this.startActivity(intent);
				finish();
			}
		});
	}
	
	private class CharacterNewStyleAdapter extends BaseAdapter {

		private final Style[] styles;
		
		public CharacterNewStyleAdapter() {
			styles = TechniqueRegistry.getInstance().getNewStyles(CharacterManagement.character.getKnownStyles(),
					CharacterManagement.character.getType(), CharacterManagement.character.getSubtype());
			Arrays.sort(styles);
		}
		
		@Override
		public int getCount() {
			return styles.length;
		}

		@Override
		public Object getItem(int arg0) {
			return styles[arg0];
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int styleIndex, View convertView, ViewGroup arg2) {
			View view = null;
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(CharacterManagementNewStyleActivity.this);
	        	view = inflater.inflate(android.R.layout.activity_list_item, null, false);
			} else {
				view = convertView;
			}
			TextView text = (TextView) view.findViewById(android.R.id.text1);
			text.setTextSize(StringUtils.getLargeListSize());
			text.setText(((Style)getItem(styleIndex)).getName());
			return view;
		}
		
	}
}

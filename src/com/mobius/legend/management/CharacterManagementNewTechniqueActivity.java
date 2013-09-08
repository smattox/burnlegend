package com.mobius.legend.management;

import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.mobius.legend.R;
import com.mobius.legend.character.technique.UnlinkedKnownTechnique;
import com.mobius.legend.technique.Style;
import com.mobius.legend.technique.Technique;
import com.mobius.legend.technique.TechniqueRegistry;
import com.mobius.legend.technique.view.TechniqueView;

public class CharacterManagementNewTechniqueActivity extends Activity {
	public static final String STYLE_INDEX = "style";
	public static final String KNOWN_STYLE = "known";
	private Style myStyle;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.character_new_style_selection);
		
		int myStyleIndex = getIntent().getIntExtra(STYLE_INDEX, 0);
		if (getIntent().getBooleanExtra(KNOWN_STYLE, true)) {
			myStyle = CharacterManagement.character.getKnownStyles()[myStyleIndex];
		} else {
			Style[] styles = TechniqueRegistry.getInstance().getNewStyles(CharacterManagement.character.getKnownStyles(),
					CharacterManagement.character.getType(), CharacterManagement.character.getSubtype());
			Arrays.sort(styles);
			myStyle = styles[myStyleIndex];
		}
		
		ListView roster = (ListView) this.findViewById(R.id.list);
		final BaseAdapter adapter = new CharacterNewTechniqueAdapter();
		roster.setAdapter(adapter);
		roster.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int techniqueIndex,
					long arg3) {
				Technique newTechnique = (Technique) adapter.getItem(techniqueIndex);
				CharacterManagement.character.addKnownTechnique(new UnlinkedKnownTechnique(newTechnique,
						CharacterManagement.STARTING_OTHER_MA_DOTS,
						CharacterManagement.STARTING_OTHER_MA_DOTS));
				finish();
			}
		});
	}
	
	private class CharacterNewTechniqueAdapter extends BaseAdapter {

		private Technique[] getTechniqueList() {
			return TechniqueRegistry.getInstance().getNewTechniquesForStyle(CharacterManagement.character, myStyle);
		}
		
		@Override
		public int getCount() {
			return getTechniqueList().length;
		}

		@Override
		public Object getItem(int arg0) {
			return getTechniqueList()[arg0];
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int techniqueIndex, View convertView, ViewGroup arg2) {
			TechniqueView view;
			Technique technique = (Technique)getItem(techniqueIndex);
			if (convertView == null) {
				view = new TechniqueView(CharacterManagementNewTechniqueActivity.this, technique);
			} else {
				view = (TechniqueView) convertView;
				view.setData(technique, technique.toString(), null);
			}
			return view;
		}
		
	}
}

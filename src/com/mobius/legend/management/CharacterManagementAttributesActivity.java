package com.mobius.legend.management;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mobius.legend.R;
import com.mobius.legend.character.Attribute;

public class CharacterManagementAttributesActivity extends AbstractCharacterCreationPageActivity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.character_creation_attributes);
        
        TextView okamiAttributePrompt = (TextView) this.findViewById(R.id.bonusAttributePrompt);
        Spinner okamiAttributeSpinner = (Spinner) this.findViewById(R.id.bonusAttribute);
        okamiAttributeSpinner.setAdapter(new ArrayAdapter<Attribute>(this,
        		android.R.layout.simple_list_item_1, Attribute.values()));
        okamiAttributeSpinner.setVisibility(View.GONE);
        okamiAttributePrompt.setVisibility(View.GONE);
        
        ListView attributeList = (ListView) this.findViewById(R.id.attributeList);
        attributeList.setAdapter(new CreationAttributeAdapter());
        
        CharacterManagementNavigationView nav = (CharacterManagementNavigationView) this.findViewById(R.id.navigation);
        nav.setLeftButton(CharacterManagementPage.Details, this);
        nav.setRightButton(CharacterManagementPage.Styles, this);
	}
	
	private class CreationAttributeAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return Attribute.values().length;
		}

		@Override
		public Object getItem(int arg0) {
			return Attribute.values()[arg0];
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			Attribute attribute = (Attribute) getItem(position);
			AttributeControlView attributeView;
			if (convertView == null) {
				attributeView = new AttributeControlView(CharacterManagementAttributesActivity.this,
						getCharacter(), attribute,
						getCharacter().isAdvancing() ? CharacterManagement.ADVANCEMENT_TRAIT_MAX : CharacterManagement.CREATION_TRAIT_MAX);
			} else {
				attributeView = (AttributeControlView) convertView;
				attributeView.setAttribute(attribute);
			}
			return attributeView;			
		}
	}
}

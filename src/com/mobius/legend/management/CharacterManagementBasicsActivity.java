package com.mobius.legend.management;

import java.util.Arrays;

import com.mobius.legend.R;
import com.mobius.legend.character.Attribute;
import com.mobius.legend.character.CharacterType;
import com.mobius.legend.character.RyuujinType;
import com.mobius.legend.namegenerator.INameGenerator;
import com.mobius.legend.namegenerator.ThresholdNameGenerator;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class CharacterManagementBasicsActivity extends AbstractCharacterCreationPageActivity {

	EditText nameBox;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.character_creation_basics);
        
        
        nameBox = (EditText) this.findViewById(R.id.characterName);
        nameBox.setEnabled(!getCharacter().isAdvancing());
        nameBox.setText(getCharacter().getName());
        nameBox.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				CharacterManagement.character.setName(arg0.toString());
				
			}
        	
        });
        
        final INameGenerator nameGenerator = new ThresholdNameGenerator();
        Button randomButton = (Button) this.findViewById(R.id.randomName);
        randomButton.setEnabled(!getCharacter().isAdvancing());
        randomButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String name = nameGenerator.createNames(1)[0];
				nameBox.setText(name);
			}
        });
        
        Spinner typeSpinner = (Spinner) this.findViewById(R.id.characterType);
        ArrayAdapter<CharacterType> typeAdapter = new ArrayAdapter<CharacterType>(this,
        		android.R.layout.simple_spinner_item, CharacterType.values());
        typeSpinner.setAdapter(typeAdapter);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setEnabled(!getCharacter().isAdvancing());
        typeSpinner.setSelection(Arrays.asList(CharacterType.values()).indexOf(getCharacter().getType()));
        typeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int typeIndex, long arg3) {
				getCharacter().setType(CharacterType.values()[typeIndex]);
				updateTypeSpecificViews();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
        });
        
        Spinner subtypeSpinner = (Spinner) this.findViewById(R.id.subtypeSpinner);
        ArrayAdapter<RyuujinType> subtypeAdapter = new ArrayAdapter<RyuujinType>(this,
        		android.R.layout.simple_spinner_item, RyuujinType.values());
        subtypeSpinner.setAdapter(subtypeAdapter);
        subtypeSpinner.setEnabled(!getCharacter().isAdvancing());
        subtypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subtypeSpinner.setSelection(Arrays.asList(RyuujinType.values()).indexOf(getCharacter().getSubtype()));
        subtypeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int typeIndex, long arg3) {
				getCharacter().setSubtype(RyuujinType.values()[typeIndex]);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
        });
        
        Spinner okamiAttributeSpinner = (Spinner) this.findViewById(R.id.okamiAttributeSpinner);
        ArrayAdapter<Attribute> okamiAttributeSpinnerAdapter = new ArrayAdapter<Attribute>(this,
        		android.R.layout.simple_spinner_item, Attribute.values());
        okamiAttributeSpinner.setAdapter(okamiAttributeSpinnerAdapter);
        okamiAttributeSpinner.setEnabled(!getCharacter().isAdvancing());
        okamiAttributeSpinner.setSelection(Arrays.asList(Attribute.values()).indexOf(getCharacter().getOkamiAttribute()));
        okamiAttributeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        okamiAttributeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int attributeIndex, long arg3) {
				getCharacter().setOkamiAttribute(Attribute.values()[attributeIndex]);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
        });
        
        CheckBox okamiFormBox = (CheckBox) this.findViewById(R.id.okamiForm);
        okamiFormBox.setChecked(getCharacter().isTrueForm());
        okamiFormBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				getCharacter().setOkamiForm(arg1);
			}
        });
        
        CharacterManagementNavigationView nav = (CharacterManagementNavigationView) this.findViewById(R.id.navigation);
        nav.setLeftButton(CharacterManagementPage.Styles, this);
        nav.setRightButton(CharacterManagementPage.Attributes, this);
        
        updateTypeSpecificViews();
    }
	
	private void updateTypeSpecificViews() {
		LinearLayout subtype = (LinearLayout) this.findViewById(R.id.subtypeView);
		subtype.setVisibility(getCharacter().getType() == CharacterType.Ryuujin ?
				View.VISIBLE : View.GONE);
		LinearLayout okami = (LinearLayout) this.findViewById(R.id.okamiView);
		okami.setVisibility(getCharacter().getType() == CharacterType.Okami ?
				View.VISIBLE : View.GONE);
	}
	
}

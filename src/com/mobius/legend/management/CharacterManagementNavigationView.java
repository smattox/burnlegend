package com.mobius.legend.management;

import com.mobius.legend.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class CharacterManagementNavigationView extends LinearLayout {
	
	Button left;
	Button right;
	
	public CharacterManagementNavigationView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.initComponent(context);
		
		left = (Button) findViewById(R.id.left);
        right = (Button) findViewById(R.id.right);
	}
	
	private void initComponent(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.character_creation_navigation, null, false);
        this.addView(v);
   }
	
	public void setLeftButton(CharacterManagementPage page, Activity parent) {
		setButton(left, page, parent);
	}
	
	public void setRightButton(CharacterManagementPage page, Activity parent) {
		setButton(right, page, parent);
	}
	
	private void setButton(Button button, final CharacterManagementPage page, final Activity parent) {
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Class<?> clazz = null;
				switch (page) {
				case Details:
					clazz = CharacterManagementBasicsActivity.class;
					break;
				case Attributes:
					clazz = CharacterManagementAttributesActivity.class;
					break;
				case Styles:
					clazz = CharacterManagementStylesActivity.class;
					break;
				}
				parent.startActivity(new Intent(parent, clazz));
				parent.finish();
			}
			
		});
		button.setText(page.toString());
	}
}

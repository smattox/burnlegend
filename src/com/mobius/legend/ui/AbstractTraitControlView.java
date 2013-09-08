package com.mobius.legend.ui;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobius.legend.R;
import com.mobius.legend.character.CharacterData;
import com.mobius.legend.management.AbstractCharacterCreationPageActivity;
import com.mobius.legend.utilities.StringUtils;

public abstract class AbstractTraitControlView extends RelativeLayout {

	protected final AbstractCharacterCreationPageActivity parent;
	protected final BaseExpandableListAdapter adapter;
	protected final TextView label;
	protected final CharacterData character;
	protected final TextView removeButton;
	protected final TextView[] buttons;
	protected final int traitMax;
	
	public AbstractTraitControlView(AbstractCharacterCreationPageActivity parent,
			BaseExpandableListAdapter adapter, CharacterData character, int min, int max) {
		super(parent);
		this.parent = parent;
		this.adapter = adapter;
		this.character = character;
		this.traitMax = max;
		
		LayoutInflater inflater = LayoutInflater.from(parent);
        View v = inflater.inflate(R.layout.trait_control_view, null, false);
        this.addView(v);
		
		label = (TextView) this.findViewById(R.id.trait_name);
		LinearLayout dotList = (LinearLayout) this.findViewById(R.id.dotList);
		
		removeButton = new TextView(parent);
		removeButton.setText(StringUtils.colorize("X", Color.RED));
		removeButton.setTextSize(40);
		dotList.addView(removeButton);
		
		buttons = new TextView[max];
		for (int i = 1; i <= max; i++) {
			buttons[i-1] = new TextView(parent);
			buttons[i-1].setTextSize(40);
			dotList.addView(buttons[i-1]);
			
			if (i >= min) {
				final int index = i;
				buttons[i-1].setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						setTraitValue(index);
					}
				});
			}
		}
	}
	
	public void setRemovalListener(final ITraitRemovalListener listener) {
		if (listener != null) {
			removeButton.setVisibility(View.VISIBLE);
			removeButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					listener.removeTrait();
				}
			});
		} else {
			removeButton.setOnClickListener(null);
			removeButton.setVisibility(View.GONE);
		}
		
	}
	
	protected abstract void setTraitValue(int value);
	protected abstract int getTraitValue();
	
	protected void updateView() {
		int value = getTraitValue();
		
		for (int i = 1; i <= traitMax; i++) {
			buttons[i-1].setText("" + (i <= value ?
					StringUtils.FILLED_DOT : StringUtils.EMPTY_DOT));
		}
		
		if (adapter != null) {
			adapter.notifyDataSetChanged();
		}
		parent.updateOverview();
	}
}

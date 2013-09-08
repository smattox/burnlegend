package com.mobius.legend.management;

import android.widget.BaseExpandableListAdapter;

import com.mobius.legend.character.CharacterData;
import com.mobius.legend.character.technique.IKnownTechnique;
import com.mobius.legend.ui.AbstractTraitControlView;

public class TechniqueControlView extends AbstractTraitControlView {

	private IKnownTechnique technique;
	
	public TechniqueControlView(AbstractCharacterCreationPageActivity parent, BaseExpandableListAdapter adapter,
			CharacterData character, int min, int max, IKnownTechnique technique) {
		super(parent, adapter, character, min, max);
		setTechnique(technique);
	}
	
	public void setTechnique(IKnownTechnique newTechnique) {
		technique = newTechnique;
		label.setText(technique.getTechnique().getName() + ":");
		updateView();
	}

	@Override
	protected void setTraitValue(int value) {
		technique.setRating(value, character.isAdvancing());
		updateView();
	}

	@Override
	protected int getTraitValue() {
		return technique.getRating();
	}

}

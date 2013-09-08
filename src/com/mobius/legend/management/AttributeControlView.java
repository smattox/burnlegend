package com.mobius.legend.management;

import com.mobius.legend.character.Attribute;
import com.mobius.legend.character.CharacterData;
import com.mobius.legend.ui.AbstractTraitControlView;

public class AttributeControlView extends AbstractTraitControlView {
	private Attribute attribute;
	
	public AttributeControlView(AbstractCharacterCreationPageActivity parent,
			CharacterData character, Attribute attribute, int max) {
		super(parent, null, character, 1, max);
		setAttribute(attribute);
		setRemovalListener(null);
	}
	
	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
		label.setText(attribute.toString() + ":");
		updateView();
	}
	
	protected void setTraitValue(int value) {
		int adjustment = value - character.getAttribute(attribute); 
		character.addAttribute(attribute, adjustment, character.isAdvancing());
		updateView();
	}
	
	protected int getTraitValue() {
		return character.getAttribute(attribute);
	}
	
}

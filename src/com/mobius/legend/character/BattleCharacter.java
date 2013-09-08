package com.mobius.legend.character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobius.legend.battle.brain.IBrain;
import com.mobius.legend.battle.effect.EnterTrueFormEffect;
import com.mobius.legend.battle.effect.ExitTrueFormEffect;
import com.mobius.legend.battle.effect.ISpecialEffect;
import com.mobius.legend.character.technique.IKnownTechnique;
import com.mobius.legend.character.technique.LinkedKnownTechnique;
import com.mobius.legend.technique.Movement;
import com.mobius.legend.technique.MutableTechnique;
import com.mobius.legend.technique.Range;
import com.mobius.legend.technique.Refinement;
import com.mobius.legend.technique.Style;
import com.mobius.legend.technique.Technique;
import com.mobius.legend.technique.TechniqueOptions;
import com.mobius.legend.technique.TechniqueTag;
import com.mobius.legend.technique.Type;

public class BattleCharacter implements ICharacter {
	
	private final List<LinkedKnownTechnique> techniques;
	private final CharacterData data;
	private final CharacterStatus status;
	private IBrain brain;
	
	boolean isTrueForm;
	Style transformationStyle;
	LinkedKnownTechnique enterTrueFormTechnique;
	LinkedKnownTechnique exitTrueFormTechnique;
	
	Map<Attribute, Integer> attributes = new HashMap<Attribute, Integer>();
	
	public BattleCharacter(IBrain brain, CharacterData data, CharacterStatus status) {
		this.data = data;
		this.brain = brain;
		this.status = status;
		
		List<LinkedKnownTechnique> techniques = new ArrayList<LinkedKnownTechnique>();
		for (IKnownTechnique technique : data.getKnownTechniques()) {
			MutableTechnique revisedTechnique = new MutableTechnique(technique.getTechnique());
			for (Refinement refinement : technique.getKnownRefinements()) {
				revisedTechnique.applyRefinement(refinement);
			}
			revisedTechnique.simplify();
			techniques.add(new LinkedKnownTechnique(revisedTechnique, technique.getRating()));
		}
		this.techniques = techniques;
		
		for (Attribute attribute : Attribute.values()) {
			attributes.put(attribute, data.getAttribute(attribute));
		}
		
		if (getType() == CharacterType.Okami) {
			isTrueForm = data.isTrueForm();
			if (isTrueForm) {
				enterTrueForm();
			} else {
				exitTrueForm();
			}
		}
		
		brain.attach(this);
	}
	
	public IBrain getBrain() {
		return brain;
	}
	
	public CharacterStatus getStatus() {
		return status;
	}

	@Override
	public String getName() {
		return data.getName();
	}
	
	@Override
	public CharacterType getType() {
		return data.getType();
	}
	
	@Override
	public RyuujinType getSubtype() {
		return data.getSubtype();
	}

	@Override
	public boolean isAdvancing() {
		return data.isAdvancing();
	}
	
	@Override
	public void addAttribute(Attribute attribute, int amount) {
		attributes.put(attribute, getAttribute(attribute) + amount);
	}

	@Override
	public int getAttribute(Attribute attribute) {
		return attributes.get(attribute);
	}
	
	@Override
	public int getAttribute(Attribute attribute, boolean creationOnly) {
		return attributes.get(attribute);
	}

	@Override
	public int getTechniqueRating(Technique technique) {
		return data.getTechniqueRating(technique);
	}

	@Override
	public IKnownTechnique[] getKnownTechniques() {
		return techniques.toArray(new IKnownTechnique[0]);
	}

	@Override
	public Style[] getKnownStyles() {
		return data.getKnownStyles();
	}

	@Override
	public IKnownTechnique[] getKnownTechniquesForStyle(Style style) {
		return data.getKnownTechniquesForStyle(style);
	}

	@Override
	public void cleanupData() {
		data.cleanupData();
	}
	
	@Override
	public String toString() {
		return getName() + "[" + getStatus().toString() + "]";
	}
	
	@Override
	public void setOkamiForm(boolean form) {
		if (form == true && !isTrueForm()) {
			enterTrueForm();
		}
		if (form == false && isTrueForm()) {
			exitTrueForm();
		}
		isTrueForm = form;
	}
	
	private void enterTrueForm() {
		Attribute attribute = data.getOkamiAttribute();
		addAttribute(attribute, 1);
		techniques.remove(getEnterTrueFormTechnique());
		techniques.add(getExitTrueFormTechnique());
		isTrueForm = true;
	}
	
	private void exitTrueForm() {
		if (isTrueForm()) {
			Attribute attribute = data.getOkamiAttribute();
			addAttribute(attribute, -1);
		}
		techniques.remove(getExitTrueFormTechnique());
		techniques.add(getEnterTrueFormTechnique());
		isTrueForm = false;
	}
	
	private Style getTransformationStyle() {
		if (transformationStyle == null) {
			transformationStyle = new Style("Transformation", null, null);
		}
		return transformationStyle;
	}
	
	private LinkedKnownTechnique getEnterTrueFormTechnique() {
		if (enterTrueFormTechnique == null) {
			List<ISpecialEffect> effects = new ArrayList<ISpecialEffect>();
			effects.add(new EnterTrueFormEffect());
			Technique technique = new Technique("Enter True Form", getTransformationStyle(), 0, 0,
					Range.Mid, Movement.Still, Type.Special, new Type[0],
					null, null, null, true, new ArrayList<TechniqueOptions>(),
					effects, new Refinement[0]);
			enterTrueFormTechnique = new LinkedKnownTechnique(new MutableTechnique(technique), 0);
		}
		return enterTrueFormTechnique;
	}
	
	private LinkedKnownTechnique getExitTrueFormTechnique() {
		if (exitTrueFormTechnique == null) {
			List<ISpecialEffect> effects = new ArrayList<ISpecialEffect>();
			effects.add(new ExitTrueFormEffect());
			Technique technique = new Technique("Exit True Form", getTransformationStyle(), 0, 0,
					Range.Mid, Movement.Still, Type.Special, new Type[0],
					null, null, null, false, new ArrayList<TechniqueOptions>(),
					effects, new Refinement[0], TechniqueTag.Vulnerable);
			exitTrueFormTechnique = new LinkedKnownTechnique(new MutableTechnique(technique), 0);
		}
		return exitTrueFormTechnique;
	}

	@Override
	public boolean isTrueForm() {
		return isTrueForm;
	}

	@Override
	public Attribute getOkamiAttribute() {
		return data.getOkamiAttribute();
	}
}

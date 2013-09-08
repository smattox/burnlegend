package com.mobius.legend.technique;

import com.mobius.legend.battle.effect.ISpecialEffect;
import com.mobius.legend.battle.effect.condition.AbstractCondition;
import com.mobius.legend.math.IFormula;
import com.mobius.legend.technique.upgrade.ITechniqueUpgrade;

public class MutableTechnique extends Technique {

	public MutableTechnique(Technique technique) {
		super(technique.name, technique.style, technique.kiCost, technique.wpCost, technique.range,
			  technique.movement, technique.type, technique.defeats,
			  technique.clash, technique.damage, technique.discounts, technique.reflexive, technique.options,
			  technique.effects, technique.refinements, technique.tags.toArray(new TechniqueTag[0]));
	}

	public void applyOptionPick(ITechniqueOptionPick pick) {
		options.remove(0);
		if (pick != null) {
			pick.apply(this);
		}
	}
	
	public void setDefeats(Type[] array) {
		defeats = array;
	}
	
	public void setRange(Range range) {
		this.range = range;
	}
	
	public void setMovement(Movement movement) {
		this.movement = movement;
	}
	
	public void addEffect(ISpecialEffect newEffect) {
		for (ISpecialEffect effect : effects) {
			if (!effect.stacks(newEffect)) {
				return;
			}
		}
		effects.add(newEffect);
	}
	
	public void removeEffects(ISpecialEffect[] effectsToRemove) {
		for (ISpecialEffect effect : effectsToRemove) {
			effects.remove(effect);
		}
		for (ISpecialEffect effect : effects) {
			if (effect instanceof AbstractCondition) {
				((AbstractCondition)effect).removeEffects(effectsToRemove);
			}
		}
	}
	
	public void setClashFormula(IFormula formula) {
		this.clash = formula;
	}
	
	public void setDamageFormula(IFormula formula) {
		this.damage = formula;
	}
	
	public void addTag(TechniqueTag tag) {
		tags.add(tag);
	}
	
	private void addOption(TechniqueOptions option) {
		options.add(option);
	}

	public void applyRefinement(Refinement refinement) {
		for (ISpecialEffect effect : refinement.getEffects()) {
			addEffect(effect);
		}
		for (TechniqueOptions option : refinement.getOptions()) {
			addOption(option);
		}
		for (TechniqueTag tag : refinement.getTags()) {
			addTag(tag);
		}
		for (ITechniqueUpgrade upgrade : refinement.getUpgrades()) {
			upgrade.applyToTechnique(this);
		}
	}

	public void simplify() {
		/*for (ISpecialEffect effect : effects) {
			if (effect instanceof D)
		}*/
	}
	
	public void addKiCost(int amount) {
		this.kiCost += amount;
	}

	public void addWillpowerCost(int amount) {
		this.wpCost += amount;
	}

	

	

	
}

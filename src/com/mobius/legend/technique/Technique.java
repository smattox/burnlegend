package com.mobius.legend.technique;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mobius.legend.battle.BattleEngine;
import com.mobius.legend.battle.effect.Modifier;
import com.mobius.legend.battle.effect.ISpecialEffect;
import com.mobius.legend.battle.effect.MovementEffect;
import com.mobius.legend.battle.effect.condition.AbstractCondition;
import com.mobius.legend.character.Attribute;
import com.mobius.legend.character.BattleCharacter;
import com.mobius.legend.character.CharacterStatus;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.math.IFormula;

public class Technique {
	protected String name;
	protected Style style;
	protected int kiCost;
	protected int wpCost;
	protected Range range;
	protected Movement movement;
	protected Type type;
	protected Type[] defeats;
	protected IFormula clash;
	protected IFormula damage;
	protected String[] discounts;
	protected boolean reflexive;
	protected List<TechniqueOptions> options;
	protected List<ISpecialEffect> effects;
	protected List<TechniqueTag> tags;
	protected Refinement[] refinements;
	
	public Technique(String name, Style style, int kiCost, int wpCost,
			Range range, Movement movement, Type type, Type[] defeats,
			IFormula clash, IFormula damage, String[] discounts, boolean reflexive,
			List<TechniqueOptions> options, List<ISpecialEffect> effects,
			Refinement[] refinements, TechniqueTag... tags) {
		this.name = name;
		this.style = style;
		this.discounts = discounts;
		this.kiCost = kiCost;
		this.wpCost = wpCost;
		this.range = range;
		this.movement = movement;
		this.type = type;
		this.defeats = defeats;
		this.clash = clash != null ? clash.cloneFormula() : clash;
		this.damage = damage != null ? damage.cloneFormula() : damage;
		this.reflexive = reflexive;
		this.options = new ArrayList<TechniqueOptions>(options);
		this.effects = new ArrayList<ISpecialEffect>();
		this.refinements = refinements;
		this.tags = new ArrayList<TechniqueTag>();
		
		for (ISpecialEffect effect : effects) {
			this.effects.add(effect.cloneEffect());
		}
		this.tags.addAll(Arrays.asList(tags));
	}
	
	public String getName() {
		return name;
	}
	
	public Style getStyle() {
		return style;
	}
	
	public int getKiCost() {
		return kiCost;
	}
	
	public int getWillpowerCost(CharacterStatus status) {
		int amount = wpCost;
		amount += getKiCost() > 0 && status.isWillpowerForKi() ? 1 : 0;
		amount += getType() == Type.Projectile && status.isWillpowerToUseProjectiles() ? 1 : 0;
		return amount;
	}
	
	public Range getRange() {
		return range;
	}
	
	public Movement getMovement() {
		return movement;
	}
	
	public Type getType() {
		return type;
	}
	
	public Type[] getDefeats() {
		return defeats;
	}
	
	public IFormula getClashPool() {
		return clash;
	}
	
	public IFormula getDamagePool() {
		return damage;
	}
	
	public boolean isReflexive() {
		return reflexive;
	}
	
	public boolean hasTag(TechniqueTag tag) {
		for (TechniqueTag myTag : tags) {
			if (myTag.equals(tag)) {
				return true;
			}
		}
		return false;
	}
	
	public int getDamageMod() {
		return damage != null ? 1 : 0;
	}
	
	public int getEffectiveDamage(BattleCharacter attacker, BattleCharacter defender) {
		if (damage != null) {
			int base = damage.evaluate(attacker, defender);
			for (Modifier mod : attacker.getStatus().getDamageMods(true)) {
				base += mod.getMod();
			}
			return base;
		}
		return 0;
	}
	
	public int getEffectiveClash(BattleCharacter attacker, BattleCharacter defender) {
		int base = clash.evaluate(attacker, defender);
		for (Modifier mod : attacker.getStatus().getClashMods(!this.hasTag(TechniqueTag.NoClashPenalties))) {
			base += mod.getMod();
		}
		return base;
	}
	
	public TechniqueOptions[] getOptions() {
		return options.toArray(new TechniqueOptions[0]);
	}
	
	public TechniqueConflictResult getInteraction(Technique t2, boolean isDistant) {
		isDistant = isDistant  &&
				!t2.hasTag(TechniqueTag.AlwaysNear) &&
				(movement != Movement.Advance && t2.movement != Movement.Advance);
		if (hasTag(TechniqueTag.Counterattack) && t2.hasTag(TechniqueTag.Counterattack)) {
			return TechniqueConflictResult.NonEvent;
		}
		if (isDistant) {
			if (this.range == Range.Close && t2.range == Range.Close) {
				return TechniqueConflictResult.MutualOutOfRange;
			}
			if (this.range == Range.Close) {
				return TechniqueConflictResult.Outranged;
			}
			if (t2.range == Range.Close) {
				return TechniqueConflictResult.Outranges;
			}
		}
		if (defeats(t2) && t2.defeats(this)) {
			return TechniqueConflictResult.HitTrade;
		}
		if (defeats(t2)) {
			if (t2.hasTag(TechniqueTag.MustClashProjectile) && getType() == Type.Projectile) {
				return TechniqueConflictResult.Clash;
			}
			return TechniqueConflictResult.Defeats;
		}
		if (t2.defeats(this) || hasTag(TechniqueTag.Vulnerable)) {
			if (hasTag(TechniqueTag.MustClashProjectile) && t2.getType() == Type.Projectile) {
				return TechniqueConflictResult.Clash;
			}
			return TechniqueConflictResult.Defeated;
		}
		return TechniqueConflictResult.Clash;		
	}
	
	private boolean defeats(Technique other) {
		for (Type type : defeats) {
			if (type == other.type) {
				return true;
			}
		}
		return false;
	}
	
	public void applyEffects(BattleCharacter user) {
		if (movement != Movement.Still) {
			user.getStatus().applyEffect(new MovementEffect(movement));
		}
		Technique technique = BattleEngine.getInstance().getActionForCharacter(user).getTechnique();
		for (ISpecialEffect effect : effects) {
			user.getStatus().applyEffect(effect);
			effect.onApply(user, technique);
		}
	}
	
	public ISpecialEffect[] getEffects() {
		return effects.toArray(new ISpecialEffect[0]);
	}
	
	public Refinement[] getRefinements() {
		return refinements;
	}
	
	public TechniqueTag[] getTags() {
		return tags.toArray(new TechniqueTag[0]);
	}
	
	public String toString() {
		return name;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Technique) {
			Technique other = (Technique)obj;
			return name.equals(other.name);
		}
		return false;
	}

	public Refinement getRefinement(String refinementName) {
		for (Refinement refinement : refinements) {
			if (refinement.getName().equals(refinementName)) {
				return refinement;
			}
		}
		return null;
	}
	
	public boolean isDiscounted(ICharacter character) {
		Style[] knownStyles = character.getKnownStyles();
		for (String discount : discounts) {
			for (Style style : knownStyles) {
				if (style.getName().equals(discount)) {
					return true;
				}
			}
		}
		return false;
	}

	public Attribute getClashAttribute() {
		return getClashPool().getBaseAttribute();
	}
	
	public Attribute getDamageAttribute() {
		return getDamagePool().getBaseAttribute();
	}

	public Attribute getOpponentDamageResistAttribute() {
		return getDamagePool().getResistAttribute();
	}
	
	public boolean hasConditionalSubeffects(Class<AbstractCondition> condition, Class<ISpecialEffect>... effectTypes) {
		return getConditionalSubeffects(condition, effectTypes).length > 0;
	}
	
	public ISpecialEffect[] getConditionalSubeffects(Class<? extends AbstractCondition> condition, Class<? extends ISpecialEffect>... effectTypes) {
		for (ISpecialEffect effect : effects) {
			if (condition.isInstance(effect)) {
				List<ISpecialEffect> matchingEffects = new ArrayList<ISpecialEffect>();
				for (ISpecialEffect conditionalEffect : ((AbstractCondition)effect).getEffects()) {
					for (Class<? extends ISpecialEffect> effectType : effectTypes) {
						if (effectType.isInstance(conditionalEffect)) {
							matchingEffects.add(conditionalEffect);
						}
					}
				}
				return matchingEffects.toArray(new ISpecialEffect[0]);
			}
		}
		return new ISpecialEffect[0];
	}
}

package com.mobius.legend.technique;

import com.mobius.legend.battle.effect.ISpecialEffect;
import com.mobius.legend.technique.upgrade.ITechniqueUpgrade;
import com.mobius.legend.utilities.StringUtils;

public class Refinement {
	private final String name;
	private final int rank;
	private final int cost;
	private final boolean exclusive;
	private final ISpecialEffect[] effects;
	private final TechniqueOptions[] options;
	private final TechniqueTag[] tags;
	private final ITechniqueUpgrade[] upgrades;
	private final Refinement prerequisite;
	
	public Refinement(String name, int rank, int cost, boolean exclusive, 
			TechniqueOptions[] options, ISpecialEffect[] effects,
			TechniqueTag[] tags, ITechniqueUpgrade[] upgrades,
			Refinement prerequisite) {
		this.name = name;
		this.rank = rank;
		this.cost = cost;
		this.effects = effects;
		this.options = options;
		this.tags = tags;
		this.exclusive = exclusive;
		this.upgrades = upgrades;
		this.prerequisite = prerequisite;
	}
	
	public String getName() {
		return name;
	}

	public int getMinimumRating() {
		return rank;
	}

	public boolean isExclusive() {
		return exclusive;
	}

	public int getCost() {
		return cost;
	}
	
	public ISpecialEffect[] getEffects() {
		return effects;
	}
	
	public TechniqueOptions[] getOptions() {
		return options;
	}
	
	public TechniqueTag[] getTags() {
		return tags;
	}
	
	public ITechniqueUpgrade[] getUpgrades() {
		return upgrades;
	}
	
	public Refinement getPrerequisite() {
		return prerequisite;
	}
	
	public boolean equals(Object obj) {
		return obj instanceof Refinement && ((Refinement)obj).getName().equals(name);
	}
	
	public String toString() {
		return name + " (" + StringUtils.dots(rank) + ", " + cost + "xp" +
				(exclusive ? ", exclusive" : "") + ")";
	}
}

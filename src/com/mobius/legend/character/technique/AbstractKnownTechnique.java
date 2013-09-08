package com.mobius.legend.character.technique;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mobius.legend.technique.Refinement;
import com.mobius.legend.technique.Technique;
import com.mobius.legend.technique.TechniqueRegistry;
import com.mobius.legend.utilities.StringUtils;

public abstract class AbstractKnownTechnique implements IKnownTechnique, Comparable<IKnownTechnique>{
	
	@Override
	public void setRating(int amount) {
		setRating(amount, false);
	}
	
	@Override
	public Refinement[] getLearnableRefinements() {
		List<Refinement> availableRefinements = new ArrayList<Refinement>();
		availableRefinements.addAll(Arrays.asList(getTechnique().getRefinements()));
		for (Refinement refinement : new ArrayList<Refinement>(availableRefinements)) {
			if (isRefinementLearned(refinement) || !canLearnRefinement(refinement)) {
				availableRefinements.remove(refinement);
			}
		}
		return availableRefinements.toArray(new Refinement[0]);
	}
	
	@Override
	public boolean isRefinementLearned(Refinement refinement) {
		for (Refinement knownRefinement : getKnownRefinements()) {
			if (knownRefinement.equals(refinement)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isRefinementLearned(String name) {
		for (Refinement refinement : getAllRefinements()) {
			if (refinement.getName().equals(name)) {
				return isRefinementLearned(refinement);
			}
		}
		return false;
	}
	
	public Refinement[] getAllRefinements() {
		return getTechnique().getRefinements();
	}
	
	@Override
	public boolean canLearnRefinement(Refinement refinement) {
		if (getRating() < refinement.getMinimumRating()) {
			return false;
		}
		if (refinement.getPrerequisite() != null && !isRefinementLearned(refinement.getPrerequisite())) {
			return false;
		}
		if (refinement.isExclusive() && !isRefinementLearned(refinement)) {
			for (Refinement knownRefinement : getKnownRefinements()) {
				if (knownRefinement.isExclusive()) {
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	public int compareTo(IKnownTechnique arg0) {
		Technique t1 = this.getTechnique();
		Technique t2 = arg0.getTechnique();
		int styleCompare = t1.getStyle().compareTo(t2.getStyle());
		if (styleCompare != 0) {
			return styleCompare;
		}
		Technique[] list = TechniqueRegistry.getInstance().getTechniquesForStyle(t1.getStyle().getName());
		for (Technique technique : list) {
			if (technique.equals(t1)) {
				return -1;
			}
			if (technique.equals(t2)) {
				return 1;
			}
		}
		return 0;
	}
	
	public String toString() {
		return getTechnique().toString() + " " + StringUtils.dots(getRating());
	}
}

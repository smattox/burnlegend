package com.mobius.legend.character.technique;

import java.util.ArrayList;
import java.util.Vector;

import com.mobius.legend.technique.MutableTechnique;
import com.mobius.legend.technique.Refinement;
import com.mobius.legend.technique.Technique;

public class LinkedKnownTechnique extends AbstractKnownTechnique {

	private final MutableTechnique technique;
	private int rating;
	
	private Vector<Refinement> refinementsKnown = new Vector<Refinement>();
	
	public LinkedKnownTechnique(MutableTechnique baseTechnique, int rating) {
		this.technique = baseTechnique;
		this.rating = rating;
	}
	
	@Override
	public Technique getTechnique() {
		return technique;
	}
	
	@Override
	public int getRating() {
		return rating;
	}
	
	@Override
	public int getRating(boolean creationOnly) {
		return getRating();
	}
	
	public Refinement[] getKnownRefinements() {
		return refinementsKnown.toArray(new Refinement[0]);
	}

	@Override
	public void setRating(int value, boolean advancing) {
		setRating(value);
	}

	@Override
	public void setRating(int value) {
		rating = value;
	}

	@Override
	public void learnRefinement(Refinement refinement) {
		refinementsKnown.add(refinement);
	}

	@Override
	public void unlearnRefinement(Refinement refinement) {
		refinementsKnown.remove(refinement);
		for (Refinement refinementToCheck : new ArrayList<Refinement>(refinementsKnown)) {
			if (refinementToCheck.getPrerequisite() != null &&
				refinementToCheck.getPrerequisite().getName().equals(refinement.getName())) {
				unlearnRefinement(refinement);
			}
		}
	}
}

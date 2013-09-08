package com.mobius.legend.character.technique;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import com.mobius.legend.technique.Refinement;
import com.mobius.legend.technique.Technique;
import com.mobius.legend.technique.TechniqueRegistry;

public class UnlinkedKnownTechnique extends AbstractKnownTechnique implements Serializable {
	private static final long serialVersionUID = 1L;
	private final String techniqueName;
	private final String techniqueStyle;
	private int creationRating;
	private int advancedRating;
	
	private Vector<String> refinementsKnown = new Vector<String>();
	
	public UnlinkedKnownTechnique(Technique technique, int rating) {
		this(technique, rating, rating);
	}
	
	public UnlinkedKnownTechnique(Technique technique, int creationRating, int advancedRating) {
		this.techniqueName = technique.getName();
		this.techniqueStyle = technique.getStyle().getName();
		this.creationRating = creationRating;
		this.advancedRating = advancedRating;
	}
	
	public UnlinkedKnownTechnique(UnlinkedKnownTechnique technique) {
		this(technique.getTechnique(), technique.creationRating, technique.advancedRating);
		refinementsKnown.addAll(technique.refinementsKnown);
	}

	public Technique getTechnique() {
		return TechniqueRegistry.getInstance().getTechnique(techniqueName, techniqueStyle);
	}
	
	public int getRating() {
		return getRating(false);
	}
	
	public int getRating(boolean creationOnly) {
		return creationOnly ? creationRating : advancedRating;
	}
	
	public void setRating(int rating, boolean advanced) {
		if (!advanced) {
			this.creationRating = rating;
			this.advancedRating = rating;
		} else {
			this.advancedRating = Math.max(creationRating, rating); 
		}
	}
	
	public void learnRefinement(Refinement refinement) {
		refinementsKnown.add(refinement.getName());
	}
	
	public void unlearnRefinement(Refinement refinement) {
		refinementsKnown.remove(refinement.getName());
		for (Refinement refinementToCheck : new Vector<Refinement>(Arrays.asList(getKnownRefinements()))) {
			if (refinementToCheck.getPrerequisite() != null &&
				refinementToCheck.getPrerequisite().getName().equals(refinement.getName())) {
				unlearnRefinement(refinementToCheck);
			}
		}
	}

	public Refinement[] getKnownRefinements() {
		List<Refinement> refinements = new ArrayList<Refinement>();
		Technique underlying = getTechnique();
		for (String refinementName : refinementsKnown) {
			refinements.add(underlying.getRefinement(refinementName));
		}
		return refinements.toArray(new Refinement[0]);
	}
}

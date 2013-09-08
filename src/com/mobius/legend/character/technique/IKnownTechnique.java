package com.mobius.legend.character.technique;

import com.mobius.legend.technique.Refinement;
import com.mobius.legend.technique.Technique;

public interface IKnownTechnique {
	Technique getTechnique();
	
	int getRating();
	
	int getRating(boolean creationOnly);

	void setRating(int value, boolean advancing);
	
	void setRating(int value);
	
	Refinement[] getAllRefinements();
	
	Refinement[] getKnownRefinements();
	
	boolean canLearnRefinement(Refinement refinement);
	
	boolean isRefinementLearned(Refinement refinement);
	
	boolean isRefinementLearned(String name);
	
	void learnRefinement(Refinement refinement);
	
	void unlearnRefinement(Refinement refinement);

	Refinement[] getLearnableRefinements();
}

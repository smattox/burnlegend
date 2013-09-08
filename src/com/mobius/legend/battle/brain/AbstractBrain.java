package com.mobius.legend.battle.brain;

import java.util.ArrayList;
import java.util.List;

import com.mobius.legend.battle.BattleEngine;
import com.mobius.legend.character.BattleCharacter;
import com.mobius.legend.character.technique.IKnownTechnique;
import com.mobius.legend.technique.Technique;

public abstract class AbstractBrain implements IBrain {

	protected BattleCharacter me;
	protected IKnownTechnique[] legalTechniqueList;
	
	@Override
	public void attach(BattleCharacter character) {
		me = character;
	}
	
	public IKnownTechnique[] determineLegalTechniques() {
    	List<IKnownTechnique> legal = new ArrayList<IKnownTechnique>();
    	BattleCharacter target = BattleEngine.getInstance().getOpponent(me);
    	Technique opponentTechnique = BattleEngine.getInstance().getCurrentRound().getActionForCharacter(target) != null ?
    			BattleEngine.getInstance().getCurrentRound().getActionForCharacter(target).getTechnique() : null;
    	for (IKnownTechnique technique : me.getKnownTechniques()) {
    		if (BattleEngine.getInstance().isLegalTechnique(me, technique.getTechnique(),
    				target,
    				opponentTechnique)) {
    			legal.add(technique);
    		}
    	}
    	legalTechniqueList = legal.toArray(new IKnownTechnique[0]);
    	return legalTechniqueList;
    }
}

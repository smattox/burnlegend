package com.mobius.legend.battle.effect.condition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mobius.legend.battle.effect.ISpecialEffect;
import com.mobius.legend.battle.effect.base.AbstractCurrentRoundEffect;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;
import com.mobius.legend.utilities.StringUtils;

public abstract class AbstractCondition extends AbstractCurrentRoundEffect {
	private List<ISpecialEffect> effects = new ArrayList<ISpecialEffect>();
	
	public AbstractCondition(ISpecialEffect[] effects) {
		this.effects.addAll(Arrays.asList(effects));
	}
	
	@Override
	public boolean stacks(ISpecialEffect effect) {
		if (getClass().isInstance(effect)) {
			AbstractCondition otherCondition = (AbstractCondition) effect;
			for (ISpecialEffect newEffect : otherCondition.effects) {
				boolean addEffect = true;
				for (ISpecialEffect currentEffect : effects) {
					if (!currentEffect.stacks(newEffect)) {
						addEffect = false;
						break;
					}
				}
				if (addEffect) {
					effects.add(newEffect);
				}
			}
			return false;
		}
		return true;
	}
	
	protected String[] apply(ICharacter target, Technique technique) {
		List<String> log = new ArrayList<String>();
		for (ISpecialEffect effect : effects) {
			String logString = effect.getLogApplyString(target);
			target.getStatus().applyEffect(effect.cloneEffect());
			effect.onApply(target, technique);
			if (logString != null) {
				log.add(logString);
			}
		}
		return log.size() == 0 ? null : log.toArray(new String[0]); 
	}
	
	public ISpecialEffect[] getEffects() {
		return effects.toArray(new ISpecialEffect[0]);
	}
	
	protected ISpecialEffect[] cloneEffects() {
		ISpecialEffect[] clones = new ISpecialEffect[effects.size()];
		for (int i = 0; i != effects.size(); i++) {
			clones[i] = effects.get(i).cloneEffect();
		}
		return clones;
	}
	
	protected abstract String getTechniqueDisplayStringHeader();
	
	public String getTechniqueDisplayString() {
		String start = getTechniqueDisplayStringHeader();
		start += StringUtils.join(effects.toArray(new ISpecialEffect[0]), StringUtils.COMMA_SPACE);
		return start;
	}

	public void removeEffects(ISpecialEffect[] effectsToRemove) {
		effects.removeAll(Arrays.asList(effectsToRemove));
	}
}

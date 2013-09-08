package com.mobius.legend.technique.upgrade;

import java.util.Arrays;
import java.util.List;

import com.mobius.legend.technique.MutableTechnique;
import com.mobius.legend.technique.Type;

public class RemoveDefeatsUpgrade implements ITechniqueUpgrade {

	private final Type type;
	
	public RemoveDefeatsUpgrade(Type type) {
		this.type = type;
	}
	
	@Override
	public void applyToTechnique(MutableTechnique technique) {
		List<Type> defeats = Arrays.asList(technique.getDefeats());
		defeats.remove(type);
		technique.setDefeats(defeats.toArray(new Type[0]));
	}

}

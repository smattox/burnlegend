package com.mobius.legend.technique.upgrade;

import com.mobius.legend.technique.MutableTechnique;
import com.mobius.legend.technique.Range;

public class RangeUpgrade implements ITechniqueUpgrade {

	private final Range range;
	
	public RangeUpgrade(Range range) {
		this.range = range;
	}
	
	@Override
	public void applyToTechnique(MutableTechnique technique) {
		technique.setRange(range);
	}

}

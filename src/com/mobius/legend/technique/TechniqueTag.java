package com.mobius.legend.technique;

public enum TechniqueTag {
	Overdrive {
		public String getTechniqueString() {
			return "Overdrive";
		}
	},
	Counterattack,
	NoClashPenalties,
	ImmuneToReversal {
		public String getTechniqueString() {
			return "Immune to Reversal";
		}
	},
	MustClashProjectile,
	MustClashAll,
	DestroyHealthStock,
	AreaOfEffectClose,
	AreaOfEffectDistant,
	AirBinding {
		public String getTechniqueString() {
			return "Air Binding";
		}
	},
	LightningBinding {
		public String getTechniqueString() {
			return "Lightning Binding";
		}
	},
	EarthBinding {
		public String getTechniqueString() {
			return "Earth Binding";
		}
	},
	MetalBinding {
		public String getTechniqueString() {
			return "Metal Binding";
		}
	},
	FireBinding {
		public String getTechniqueString() {
			return "Fire Binding";
		}
	},
	ShadowBinding {
		public String getTechniqueString() {
			return "Shadow Binding";
		}
	},
	WaterBinding {
		public String getTechniqueString() {
			return "Water Binding";
		}
	},
	IceBinding {
		public String getTechniqueString() {
			return "Ice Binding";
		}
	},
	AlwaysNear,
	Terrestrial {
		public String getTechniqueString() {
			return "Terrestrial";
		}
	},
	Celestial {
		public String getTechniqueString() {
			return "Celestial";
		}
	},
	Sidereal {
		public String getTechniqueString() {
			return "Sidereal";
		}
	},
	Vulnerable;
	
	public String toString() {
		return getTechniqueString();
	}
	
	public String getTechniqueString() {
		return null;
	}
}

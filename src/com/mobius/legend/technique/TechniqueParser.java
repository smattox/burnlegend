package com.mobius.legend.technique;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import android.util.Log;

import com.mobius.legend.battle.effect.AdditionalStaggerEffect;
import com.mobius.legend.battle.effect.BlockSpendKiEffect;
import com.mobius.legend.battle.effect.CapDamageEffect;
import com.mobius.legend.battle.effect.ClashModEffect;
import com.mobius.legend.battle.effect.DamageModEffect;
import com.mobius.legend.battle.effect.ForcedMovementEffect;
import com.mobius.legend.battle.effect.FullRestoreKiEffect;
import com.mobius.legend.battle.effect.FutureClashModEffect;
import com.mobius.legend.battle.effect.FutureDamageModEffect;
import com.mobius.legend.battle.effect.HealthStockAttributeBuffEffect;
import com.mobius.legend.battle.effect.HealthStockAttributeResistEffect;
import com.mobius.legend.battle.effect.HealthStockDamageBoostEffect;
import com.mobius.legend.battle.effect.HealthStockInfiniteKiEffect;
import com.mobius.legend.battle.effect.ISpecialEffect;
import com.mobius.legend.battle.effect.KnockbackEffect;
import com.mobius.legend.battle.effect.ModTraitEffect;
import com.mobius.legend.battle.effect.MoonCrescentSlashEffect;
import com.mobius.legend.battle.effect.MustSpendWillpowerToUseKiEffect;
import com.mobius.legend.battle.effect.MustSpendWillpowerToUseProjectilesEffect;
import com.mobius.legend.battle.effect.NextClashNoAttributeEffect;
import com.mobius.legend.battle.effect.NextDefenseAttributeResistEffect;
import com.mobius.legend.battle.effect.NextRestoreBlockedEffect;
import com.mobius.legend.battle.effect.NextRoundTagEffect;
import com.mobius.legend.battle.effect.NoRepeatTechniqueEffect;
import com.mobius.legend.battle.effect.OpponentDamageModEffect;
import com.mobius.legend.battle.effect.PreventTechniqueEffect;
import com.mobius.legend.battle.effect.RemoveEffect;
import com.mobius.legend.battle.effect.RevisedClashEffect;
import com.mobius.legend.battle.effect.RevisedDamageEffect;
import com.mobius.legend.battle.effect.SerpentKiEffect;
import com.mobius.legend.battle.effect.StaggerCheckEffect;
import com.mobius.legend.battle.effect.StaggerEffect;
import com.mobius.legend.battle.effect.StealTraitEffect;
import com.mobius.legend.battle.effect.TimedAttributeBuffEffect;
import com.mobius.legend.battle.effect.condition.AttackerLastTechniqueCondition;
import com.mobius.legend.battle.effect.condition.FollowsTagCondition;
import com.mobius.legend.battle.effect.condition.OnDamageCondition;
import com.mobius.legend.battle.effect.condition.OnDistantCondition;
import com.mobius.legend.battle.effect.condition.OnEnemyVictoryCondition;
import com.mobius.legend.battle.effect.condition.OnHitCondition;
import com.mobius.legend.battle.effect.condition.OnNearCondition;
import com.mobius.legend.battle.effect.condition.OnRemoveHealthStockCondition;
import com.mobius.legend.battle.effect.condition.OnVictoryCondition;
import com.mobius.legend.battle.effect.condition.OpponentMovementCondition;
import com.mobius.legend.battle.effect.condition.OpponentTechniqueTypeCondition;
import com.mobius.legend.character.Attribute;
import com.mobius.legend.character.CharacterType;
import com.mobius.legend.character.RyuujinType;
import com.mobius.legend.character.TraitType;
import com.mobius.legend.math.AddAttribute;
import com.mobius.legend.math.AddMaxAttribute;
import com.mobius.legend.math.AddTechnique;
import com.mobius.legend.math.AgonyMinValue;
import com.mobius.legend.math.Constant;
import com.mobius.legend.math.DevilJudgementMinValue;
import com.mobius.legend.math.IFormula;
import com.mobius.legend.math.IValue;
import com.mobius.legend.math.InflictedDamageValue;
import com.mobius.legend.math.MinValue;
import com.mobius.legend.math.OpponentClashValue;
import com.mobius.legend.math.OpponentDamageValue;
import com.mobius.legend.math.StaticFormula;
import com.mobius.legend.math.SubtractAttribute;
import com.mobius.legend.math.SufferedDamageValue;
import com.mobius.legend.technique.options.AdvanceMovement;
import com.mobius.legend.technique.options.BackMovement;
import com.mobius.legend.technique.options.EffectOption;
import com.mobius.legend.technique.options.KiCostOption;
import com.mobius.legend.technique.options.StillMovement;
import com.mobius.legend.technique.options.WillpowerCostOption;
import com.mobius.legend.technique.upgrade.ClashUpgrade;
import com.mobius.legend.technique.upgrade.DamageUpgrade;
import com.mobius.legend.technique.upgrade.ITechniqueUpgrade;
import com.mobius.legend.technique.upgrade.RangeUpgrade;
import com.mobius.legend.technique.upgrade.RemoveDefeatsUpgrade;
import com.mobius.legend.technique.upgrade.ReplaceDamageUpgrade;

public class TechniqueParser {
	private final static String TAG_STYLE = "style";
	private final static String TAG_TECHNIQUE = "technique";
	private final static String TAG_DISCOUNT = "discount";
	private final static String TAG_CLASH = "clash";
	private final static String TAG_DAMAGE = "damage";
	private final static String TAG_ADD_ATTRIBUTE = "addAttribute";
	private final static String TAG_ADD_CONSTANT = "addConstant";
	private final static String TAG_ADD_TECHNIQUE = "addTechnique";
	private final static String TAG_SUBTRACT_ATTRIBUTE = "subtractAttribute";
	private final static String TAG_ADD_MAX_ATTRIBUTES = "addMaxAttributes";
	private final static String TAG_ADD_OPPONENT_DAMAGE_FORMULA = "addOpponentDamageFormula";
	private final static String TAG_ADD_OPPONENT_CLASH_SUCCESSES = "addOpponentClashSuccesses";
	private final static String TAG_MIN = "min";
	private final static String TAG_AGONY_MIN = "AgonyMin";
	private final static String TAG_DEVIL_JUDGEMENT_MIN = "DevilJudgementMin";
	private final static String TAG_SUFFERED_DAMAGE = "SufferedDamage";
	private final static String TAG_INFLICTED_DAMAGE = "InflictedDamage";
	private final static String TAG_OPTION = "option";
	private final static String TAG_TAG = "tag";
	private final static String TAG_BACK_MOVEMENT = "BackMovementOption";
	private final static String TAG_STILL_MOVEMENT = "StillMovementOption";
	private final static String TAG_ADVANCE_MOVEMENT = "AdvanceMovementOption";
	private final static String TAG_EFFECT_OPTION = "EffectOption";
	private final static String TAG_KI_COST_OPTION = "KiCostOption";
	private final static String TAG_WP_COST_OPTION = "WillpowerCostOption";
	private final static String TAG_EFFECTS = "effects";
	private final static String TAG_REMOVE_EFFECT = "RemoveEffect";
	private final static String TAG_FUTURE_CLASH_MOD_EFFECT = "FutureClashModEffect";
	private final static String TAG_FUTURE_DAMAGE_MOD_EFFECT = "FutureDamageModEffect";
	private final static String TAG_NEXT_CLASH_NO_ATTRIBUTE_EFFECT = "NextClashNoAttributeEffect";
	private final static String TAG_NEXT_DEFENSE_ATTRIBUTE_RESIST_EFFECT = "NextDefenseAttributeResistEffect";
	private final static String TAG_OPPONENT_DAMAGE_MOD_EFFECT = "OpponentDamageModEffect";
	private final static String TAG_CLASH_MOD_EFFECT = "ClashModEffect";
	private final static String TAG_DAMAGE_MOD_EFFECT = "DamageModEffect";
	private final static String TAG_MOD_TRAIT_EFFECT = "ModTraitEffect";
	private final static String TAG_STEAL_TRAIT_EFFECT = "StealTraitEffect";
	private final static String TAG_FULL_RESTORE_KI_EFFECT = "FullRestoreKiEffect";
	private final static String TAG_BLOCK_SPEND_KI_EFFECT = "BlockSpendKiEffect";
	private final static String TAG_REVISED_CLASH_EFFECT = "RevisedClashEffect";
	private final static String TAG_REVISED_DAMAGE_EFFECT = "RevisedDamageEffect";
	private final static String TAG_CAP_DAMAGE_EFFECT = "CapDamageEffect";
	private final static String TAG_ADDITIONAL_STAGGER_EFFECT = "AdditionalStaggerEffect";
	private final static String TAG_STAGGER_CHECK_EFFECT = "StaggerCheckEffect";
	private final static String TAG_STAGGER_EFFECT = "StaggerEffect";
	private final static String TAG_OPPONENT_MOVEMENT_CONDITION = "OpponentMovementCondition";
	private final static String TAG_OPPONENT_TECHNIQUE_TYPE_CONDITION = "OpponentTechniqueTypeCondition";
	private final static String TAG_ON_HIT_CONDITION = "OnHitCondition";
	private final static String TAG_ON_VICTORY_CONDITION = "OnVictoryCondition";
	private final static String TAG_ON_ENEMY_VICTORY_CONDITION = "OnEnemyVictoryCondition";
	private final static String TAG_ON_NEAR_CONDITION = "OnNearCondition";
	private final static String TAG_ON_DISTANT_CONDITION = "OnDistantCondition";
	private final static String TAG_ON_REMOVE_HEALTH_STOCK_CONDITION = "OnRemoveHealthStockCondition";
	private final static String TAG_ON_DAMAGE_CONDITION = "OnDamageCondition";
	private final static String TAG_ATTACKER_LAST_TECHNIQUE_CONDITION = "AttackerLastTechniqueCondition";
	private final static String TAG_FOLLOWS_TAG_CONDITION = "FollowsTagCondition";
	private final static String TAG_KNOCKBACK_EFFECT = "KnockbackEffect";
	private final static String TAG_NEXT_ROUND_TAG_EFFECT = "NextRoundTagEffect";
	private final static String TAG_PREVENT_TECHNIQUE_EFFECT = "PreventTechniqueEffect";
	private final static String TAG_NO_REPEAT_TECHNIQUE_EFFECT = "NoRepeatTechniqueEffect";
	private final static String TAG_MUST_SPEND_WP_TO_USE_KI_EFFECT = "MustSpendWillpowerToUseKiEffect";
	private final static String TAG_MUST_SPEND_WP_TO_USE_PROJECTILES_EFFECT = "MustSpendWillpowerToUseProjectilesEffect";
	private final static String TAG_FORCED_MOVEMENT_EFFECT = "ForcedMovementEffect";
	private final static String TAG_TIMED_ATTRIBUTE_BUFF_EFFECT = "TimedAttributeBuffEffect";
	private final static String TAG_HEALTH_STOCK_ATTRIBUTE_BUFF_EFFECT = "HealthStockAttributeBuffEffect";
	private final static String TAG_HEALTH_STOCK_ATTRIBUTE_RESIST_EFFECT = "HealthStockAttributeResistEffect";
	private final static String TAG_HEALTH_STOCK_DAMAGE_BOOST_EFFECT = "HealthStockDamageBoostEffect";
	private final static String TAG_HEALTH_STOCK_INFINITE_KI_EFFECT = "HealthStockInfiniteKiEffect";
	private final static String TAG_NEXT_RESTORE_BLOCKED_EFFECT = "NextRestoreBlockedEffect";
	private final static String TAG_SERPENT_KI_EFFECT = "SerpentKiEffect";
	private final static String TAG_MOON_CRESCENT_SLASH_EFFECT = "MoonCrescentSlashEffect";
	private final static String TAG_REFINEMENT = "refinement";
	private final static String TAG_KI_COST = "KiCost";
	private final static String TAG_WP_COST = "WillpowerCost";
	private final static String TAG_UPGRADES = "upgrades";
	private final static String TAG_RANGE_UPGRADE = "RangeUpgrade";
	private final static String TAG_CLASH_UPGRADE = "ClashUpgrade";
	private final static String TAG_DAMAGE_UPGRADE = "DamageUpgrade";
	private final static String TAG_REPLACE_DAMAGE_UPGRADE = "ReplaceDamageUpgrade";
	private final static String TAG_REMOVES_DEFEAT_UPGRADE = "RemoveDefeatUpgrade";
	private final static String ATTRIB_NAME = "name";
	private final static String ATTRIB_STYLE = "style";
	private final static String ATTRIB_RANGE = "range";
	private final static String ATTRIB_MOVEMENT = "movement";
	private final static String ATTRIB_TYPE = "type";
	private final static String ATTRIB_SUBTYPE = "subtype";
	private final static String ATTRIB_DEFEATS = "defeats";
	private final static String ATTRIB_ID = "id";
	private final static String ATTRIB_LABEL = "label";
	private final static String ATTRIB_AMOUNT = "amount";
	private final static String ATTRIB_DURATION = "duration";
	private final static String ATTRIB_RANK = "rank";
	private final static String ATTRIB_COST = "cost";
	private final static String ATTRIB_EXCLUSIVE = "exclusive";
	private final static String ATTRIB_PREREQUISITE = "prerequisite";
	private final static String ATTRIB_FIRST = "first";
	private final static String ATTRIB_SECOND = "second";
	
	public void parseDocument(Element root) {
		parseStyles(root);
		parseTechniques(root);
	}
	
	private void parseStyles(Element root) {
		for (Object obj : root.elements(TAG_STYLE)) {
			Element styleElement = (Element)obj;
			String name = styleElement.attributeValue(ATTRIB_NAME);
			CharacterType type = CharacterType.valueOf(styleElement.attributeValue(ATTRIB_TYPE));
			
			RyuujinType subtype = null;
			if (styleElement.attributeValue(ATTRIB_SUBTYPE) != null) {
				subtype = RyuujinType.valueOf(styleElement.attributeValue(ATTRIB_SUBTYPE));
			}
			
			Style newStyle = new Style(name, type, subtype);
			TechniqueRegistry.getInstance().registerStyle(newStyle);
		}
	}
	
	private void parseTechniques(Element root) {
		String name;
		for (Object obj : root.elements(TAG_TECHNIQUE)) {
			Element techniqueElement = (Element)obj;
			name = techniqueElement.attributeValue(ATTRIB_NAME);
			try {
			String styleName = techniqueElement.attributeValue(ATTRIB_STYLE);
			
			int kiCost = 0;
			int wpCost = 0;
			if (techniqueElement.element(TAG_KI_COST) != null) {
				kiCost = Integer.parseInt(techniqueElement.element(TAG_KI_COST).attributeValue(ATTRIB_AMOUNT));
			}
			if (techniqueElement.element(TAG_WP_COST) != null) {
				wpCost = Integer.parseInt(techniqueElement.element(TAG_WP_COST).attributeValue(ATTRIB_AMOUNT));
			}
			Range range = Range.valueOf(techniqueElement.attributeValue(ATTRIB_RANGE));
			Movement movement = Movement.valueOf(techniqueElement.attributeValue(ATTRIB_MOVEMENT));
			Type type = Type.valueOf(techniqueElement.attributeValue(ATTRIB_TYPE));
			String defeatsString = techniqueElement.attributeValue(ATTRIB_DEFEATS);
			Type[] defeats = new Type[0];
			if (defeatsString != null) {
				List<Type> defeatsList = new ArrayList<Type>();
				for (String defeat : defeatsString.split(",")) {
					defeatsList.add(Type.valueOf(defeat));
				}
				defeats = defeatsList.toArray(new Type[0]);
			}
			
			List<String> discountsList = new ArrayList<String>(); 
			for (Object discountObj : techniqueElement.elements(TAG_DISCOUNT)) {
				Element discountElement = (Element) discountObj;
				String id = discountElement.attributeValue(ATTRIB_ID);
				discountsList.add(id);
			}
			String[] discounts = discountsList.toArray(new String[0]);
			
			IFormula clash = parseFormula(techniqueElement.element(TAG_CLASH), name);
			IFormula damage = null;
			if (techniqueElement.element(TAG_DAMAGE) != null) {
				damage = parseFormula(techniqueElement.element(TAG_DAMAGE), name);
			}
			
			List<TechniqueOptions> options = new ArrayList<TechniqueOptions>();
			for (Object optObj : techniqueElement.elements(TAG_OPTION)) {
				try {
				options.add(parseTechniqueOption((Element)optObj, name));
				} catch (Exception e) {
					Log.e("TechniqueParser", "Error parsing options in " + name);
				}
			}
			
			List<ISpecialEffect> effects = new ArrayList<ISpecialEffect>();
			if (techniqueElement.element(TAG_EFFECTS) != null) {
				try {
					parseEffects(techniqueElement.element(TAG_EFFECTS), effects, name);
				} catch (Exception e) {
					Log.e("TechniqueParser", "Error parsing effects in " + name);
				}
			}
			
			List<TechniqueTag> tags = new ArrayList<TechniqueTag>();
			for (Object tagObj : techniqueElement.elements(TAG_TAG)) {
				Element tagElement = (Element)tagObj;
				try {
					tags.add(TechniqueTag.valueOf(tagElement.attributeValue(ATTRIB_ID)));
				} catch (Exception e) {
					Log.e("TechniqueParser", "Illegal tag in " + name);
				}
			}
			
			
			List<Refinement> refinements = new ArrayList<Refinement>();
			for (Object refinementObj : techniqueElement.elements(TAG_REFINEMENT)) {
				Element refinementElement = (Element)refinementObj;
				try
				{
					refinements.add(parseRefinement(refinementElement, name, refinements));
				} catch (Exception e) {
					Log.e("TechniqueParser", "Error parsing refinement in " + name);
				}
			} 
			
			Style style = TechniqueRegistry.getInstance().getStyle(styleName);
			Technique newTechnique = new Technique(name, style, kiCost, wpCost, range, movement, type, defeats,
					clash, damage, discounts, false, options, effects, refinements.toArray(new Refinement[0]),
					tags.toArray(new TechniqueTag[0]));
			style.addTechnique(newTechnique);
			} catch (Exception e) {
				System.out.println("Failure parsing technique " + name);
			}
		}
	}
	
	private void parseEffects(Element element, List<ISpecialEffect> effects, String name) {
		for (Object obj : element.elements()) {
			Element piece = (Element)obj;
			if (piece.getName().equals(TAG_ON_HIT_CONDITION)) {
				List<ISpecialEffect> conditionalEffects = new ArrayList<ISpecialEffect>();
				parseEffects(piece, conditionalEffects, name);
				effects.add(new OnHitCondition(conditionalEffects.toArray(new ISpecialEffect[0])));
				continue;
			}
			if (piece.getName().equals(TAG_ON_VICTORY_CONDITION)) {
				List<ISpecialEffect> conditionalEffects = new ArrayList<ISpecialEffect>();
				parseEffects(piece, conditionalEffects, name);
				effects.add(new OnVictoryCondition(conditionalEffects.toArray(new ISpecialEffect[0])));
				continue;
			}
			if (piece.getName().equals(TAG_ON_ENEMY_VICTORY_CONDITION)) {
				List<ISpecialEffect> conditionalEffects = new ArrayList<ISpecialEffect>();
				parseEffects(piece, conditionalEffects, name);
				effects.add(new OnEnemyVictoryCondition(conditionalEffects.toArray(new ISpecialEffect[0])));
				continue;
			}
			if (piece.getName().equals(TAG_ON_DISTANT_CONDITION)) {
				List<ISpecialEffect> conditionalEffects = new ArrayList<ISpecialEffect>();
				parseEffects(piece, conditionalEffects, name);
				effects.add(new OnDistantCondition(conditionalEffects.toArray(new ISpecialEffect[0])));
				continue;
			}
			if (piece.getName().equals(TAG_ON_NEAR_CONDITION)) {
				List<ISpecialEffect> conditionalEffects = new ArrayList<ISpecialEffect>();
				parseEffects(piece, conditionalEffects, name);
				effects.add(new OnNearCondition(conditionalEffects.toArray(new ISpecialEffect[0])));
				continue;
			}
			if (piece.getName().equals(TAG_ON_REMOVE_HEALTH_STOCK_CONDITION)) {
				List<ISpecialEffect> conditionalEffects = new ArrayList<ISpecialEffect>();
				parseEffects(piece, conditionalEffects, name);
				effects.add(new OnRemoveHealthStockCondition(conditionalEffects.toArray(new ISpecialEffect[0])));
				continue;
			}
			if (piece.getName().equals(TAG_ON_DAMAGE_CONDITION)) {
				int amount = Integer.parseInt(piece.attributeValue(ATTRIB_AMOUNT));
				List<ISpecialEffect> conditionalEffects = new ArrayList<ISpecialEffect>();
				parseEffects(piece, conditionalEffects, name);
				effects.add(new OnDamageCondition(amount, conditionalEffects.toArray(new ISpecialEffect[0])));
				continue;
			}
			if (piece.getName().equals(TAG_OPPONENT_MOVEMENT_CONDITION)) {
				List<Movement> movements = new ArrayList<Movement>();
				for (String movementString : piece.attributeValue(ATTRIB_ID).split(",")) {
					movements.add(Movement.valueOf(movementString));
				}
				List<ISpecialEffect> conditionalEffects = new ArrayList<ISpecialEffect>();
				parseEffects(piece, conditionalEffects, name);
				effects.add(new OpponentMovementCondition(movements.toArray(new Movement[0]),
						conditionalEffects.toArray(new ISpecialEffect[0])));
				continue;
			}
			if (piece.getName().equals(TAG_OPPONENT_TECHNIQUE_TYPE_CONDITION)) {
				Type type = Type.valueOf(piece.attributeValue(ATTRIB_ID));
				List<ISpecialEffect> conditionalEffects = new ArrayList<ISpecialEffect>();
				parseEffects(piece, conditionalEffects, name);
				effects.add(new OpponentTechniqueTypeCondition(type, conditionalEffects.toArray(new ISpecialEffect[0])));
				continue;
			}
			if (piece.getName().equals(TAG_ATTACKER_LAST_TECHNIQUE_CONDITION)) {
				String id = piece.attributeValue(ATTRIB_ID);
				List<ISpecialEffect> conditionalEffects = new ArrayList<ISpecialEffect>();
				parseEffects(piece, conditionalEffects, name);
				effects.add(new AttackerLastTechniqueCondition(id, conditionalEffects.toArray(new ISpecialEffect[0])));
				continue;
			}
			if (piece.getName().equals(TAG_FOLLOWS_TAG_CONDITION)) {
				TechniqueTag tag = TechniqueTag.valueOf(piece.attributeValue(ATTRIB_ID));
				List<ISpecialEffect> conditionalEffects = new ArrayList<ISpecialEffect>();
				parseEffects(piece, conditionalEffects, name);
				effects.add(new FollowsTagCondition(tag, conditionalEffects.toArray(new ISpecialEffect[0])));
				continue;
			}
			if (piece.getName().equals(TAG_REMOVE_EFFECT)) {
				String id = piece.attributeValue(ATTRIB_ID);
				effects.add(new RemoveEffect(id));
				continue;
			}
			if (piece.getName().equals(TAG_FUTURE_CLASH_MOD_EFFECT)) {
				int duration = 1;
				int amount = Integer.parseInt(piece.attributeValue(ATTRIB_AMOUNT));
				Range range = null;
				if (piece.attributeValue(ATTRIB_RANGE) != null) {
					range = Range.valueOf(piece.attributeValue(ATTRIB_RANGE));
				}
				if (piece.attributeValue(ATTRIB_DURATION) != null) {
					duration = Integer.parseInt(piece.attributeValue(ATTRIB_DURATION));
				}
				effects.add(new FutureClashModEffect(name, duration, amount, range));
				continue;
			}
			if (piece.getName().equals(TAG_FUTURE_DAMAGE_MOD_EFFECT)) {
				int duration = Integer.parseInt(piece.attributeValue(ATTRIB_DURATION));
				int amount = Integer.parseInt(piece.attributeValue(ATTRIB_AMOUNT));
				effects.add(new FutureDamageModEffect(name, duration, amount));
				continue;
			}
			if (piece.getName().equals(TAG_NEXT_CLASH_NO_ATTRIBUTE_EFFECT)) {
				effects.add(new NextClashNoAttributeEffect());
				continue;
			}
			if (piece.getName().equals(TAG_NEXT_DEFENSE_ATTRIBUTE_RESIST_EFFECT)) {
				int amount = Integer.parseInt(piece.attributeValue(ATTRIB_AMOUNT));
				List<Attribute> attributes = new ArrayList<Attribute>();
				for (String attributeName : piece.attributeValue(ATTRIB_ID).split(",")) {
					attributes.add(Attribute.valueOf(attributeName));
				}
				effects.add(new NextDefenseAttributeResistEffect(attributes.toArray(new Attribute[0]), amount));
				continue;
			}
			if (piece.getName().equals(TAG_OPPONENT_DAMAGE_MOD_EFFECT)) {
				int amount = Integer.parseInt(piece.attributeValue(ATTRIB_AMOUNT));
				effects.add(new OpponentDamageModEffect(amount));
				continue;
			}
			if (piece.getName().equals(TAG_REVISED_CLASH_EFFECT)) {
				IFormula clash = parseFormula(piece, name);
				effects.add(new RevisedClashEffect(clash));
				continue;
			}
			if (piece.getName().equals(TAG_REVISED_DAMAGE_EFFECT)) {
				IFormula clash = parseFormula(piece, name);
				effects.add(new RevisedDamageEffect(clash));
				continue;
			}
			if (piece.getName().equals(TAG_CAP_DAMAGE_EFFECT)) {
				int amount = Integer.parseInt(piece.attributeValue(ATTRIB_AMOUNT));
				effects.add(new CapDamageEffect(amount));
				continue;
			}
			if (piece.getName().equals(TAG_MOD_TRAIT_EFFECT)) {
				TraitType type = TraitType.valueOf(piece.attributeValue(ATTRIB_ID));
				IValue amount = parseAmount(piece);
				effects.add(new ModTraitEffect(type, amount));
				continue;
			}
			if (piece.getName().equals(TAG_STEAL_TRAIT_EFFECT)) {
				TraitType type = TraitType.valueOf(piece.attributeValue(ATTRIB_ID));
				IValue amount = parseAmount(piece);
				effects.add(new StealTraitEffect(type, amount));
				continue;
			}
			if (piece.getName().equals(TAG_FULL_RESTORE_KI_EFFECT)) {
				effects.add(new FullRestoreKiEffect());
				continue;
			}
			if (piece.getName().equals(TAG_BLOCK_SPEND_KI_EFFECT)) {
				effects.add(new BlockSpendKiEffect());
				continue;
			}
			if (piece.getName().equals(TAG_ADDITIONAL_STAGGER_EFFECT)) {
				int amount = Integer.parseInt(piece.attributeValue(ATTRIB_AMOUNT));
				effects.add(new AdditionalStaggerEffect(amount));
				continue;
			}
			if (piece.getName().equals(TAG_STAGGER_CHECK_EFFECT)) {
				effects.add(new StaggerCheckEffect());
				continue;
			}
			if (piece.getName().equals(TAG_STAGGER_EFFECT)) {
				effects.add(new StaggerEffect());
				continue;
			}
			if (piece.getName().equals(TAG_FORCED_MOVEMENT_EFFECT)) {
				Movement movement = Movement.valueOf(piece.attributeValue(ATTRIB_MOVEMENT));
				effects.add(new ForcedMovementEffect(movement));
				continue;
			}
			if (piece.getName().equals(TAG_CLASH_MOD_EFFECT)) {
				IValue amount = parseAmount(piece);
				effects.add(new ClashModEffect(amount));
				continue;
			}
			if (piece.getName().equals(TAG_DAMAGE_MOD_EFFECT)) {
				IValue amount = parseAmount(piece);
				effects.add(new DamageModEffect(amount));
				continue;
			}
			if (piece.getName().equals(TAG_KNOCKBACK_EFFECT)) {
				effects.add(new KnockbackEffect());
				continue;
			}
			if (piece.getName().equals(TAG_NEXT_ROUND_TAG_EFFECT)) {
				TechniqueTag tag = TechniqueTag.valueOf(piece.attributeValue(ATTRIB_ID));
				effects.add(new NextRoundTagEffect(tag));
				continue;
			}
			if (piece.getName().equals(TAG_MUST_SPEND_WP_TO_USE_KI_EFFECT)) {
				effects.add(new MustSpendWillpowerToUseKiEffect());
				continue;
			}
			if (piece.getName().equals(TAG_MUST_SPEND_WP_TO_USE_PROJECTILES_EFFECT)) {
				effects.add(new MustSpendWillpowerToUseProjectilesEffect());
				continue;
			}
			if (piece.getName().equals(TAG_PREVENT_TECHNIQUE_EFFECT)) {
				Range range = null;
				Movement movement = null;
				Type type = null;
				if (piece.attributeValue(ATTRIB_RANGE) != null) {
					range = Range.valueOf(piece.attributeValue(ATTRIB_RANGE));
				}
				if (piece.attributeValue(ATTRIB_MOVEMENT) != null) {
					movement = Movement.valueOf(piece.attributeValue(ATTRIB_MOVEMENT));
				}
				if (piece.attributeValue(ATTRIB_TYPE) != null) {
					type = Type.valueOf(piece.attributeValue(ATTRIB_TYPE));
				}
				effects.add(new PreventTechniqueEffect(range, movement, type));
				continue;
			}
			if (piece.getName().equals(TAG_NO_REPEAT_TECHNIQUE_EFFECT)) {
				effects.add(new NoRepeatTechniqueEffect());
				continue;
			}
			if (piece.getName().equals(TAG_TIMED_ATTRIBUTE_BUFF_EFFECT)) {
				Attribute id = Attribute.valueOf(piece.attributeValue(ATTRIB_ID));
				int amount = Integer.parseInt(piece.attributeValue(ATTRIB_AMOUNT));
				int duration = Integer.parseInt(piece.attributeValue(ATTRIB_DURATION));
				effects.add(new TimedAttributeBuffEffect(id, amount, duration));
				continue;				
			}
			if (piece.getName().equals(TAG_HEALTH_STOCK_ATTRIBUTE_BUFF_EFFECT)) {
				Attribute id = Attribute.valueOf(piece.attributeValue(ATTRIB_ID));
				int amount = Integer.parseInt(piece.attributeValue(ATTRIB_AMOUNT));
				effects.add(new HealthStockAttributeBuffEffect(id, amount));
				continue;				
			}
			if (piece.getName().equals(TAG_HEALTH_STOCK_ATTRIBUTE_RESIST_EFFECT)) {
				Attribute id = Attribute.valueOf(piece.attributeValue(ATTRIB_ID));
				int amount = Integer.parseInt(piece.attributeValue(ATTRIB_AMOUNT));
				effects.add(new HealthStockAttributeResistEffect(id, amount));
				continue;				
			}
			if (piece.getName().equals(TAG_HEALTH_STOCK_DAMAGE_BOOST_EFFECT)) {
				int amount = Integer.parseInt(piece.attributeValue(ATTRIB_AMOUNT));
				effects.add(new HealthStockDamageBoostEffect(amount));
				continue;				
			}
			if (piece.getName().equals(TAG_HEALTH_STOCK_INFINITE_KI_EFFECT)) {
				effects.add(new HealthStockInfiniteKiEffect());
				continue;				
			}
			if (piece.getName().equals(TAG_SERPENT_KI_EFFECT)) {
				effects.add(new SerpentKiEffect());
				continue;
			}
			if (piece.getName().equals(TAG_MOON_CRESCENT_SLASH_EFFECT)) {
				effects.add(new MoonCrescentSlashEffect());
				continue;
			}
			if (piece.getName().equals(TAG_NEXT_RESTORE_BLOCKED_EFFECT)) {
				effects.add(new NextRestoreBlockedEffect());
				continue;				
			}
			Log.e("TechniqueParser", "Unknown effect in " + name + ": " + piece.getName());
		}
 	}

	private IFormula parseFormula(Element root, String name) {
		List<IValue> parts = new ArrayList<IValue>();
		for (Object obj : root.elements()) {
			Element piece = (Element)obj;
			IValue value = parseValue(piece.getName(), piece, name);
			if (value != null) {
				parts.add(value);
				continue;
			}
			Log.e("TechniqueParser", "Formula error in " + name);
		}
		return new StaticFormula(parts.toArray(new IValue[0]));
	}
	
	private IValue parseValue(String value, Element element, String name) {
		if (value.equals(TAG_ADD_ATTRIBUTE)) {
			return new AddAttribute(Attribute.valueOf(element.attributeValue(ATTRIB_ID)));
		}
		if (value.equals(TAG_ADD_MAX_ATTRIBUTES)) {
			Attribute attribute1 = Attribute.valueOf(element.attributeValue(ATTRIB_FIRST));
			Attribute attribute2 = Attribute.valueOf(element.attributeValue(ATTRIB_SECOND));
			return new AddMaxAttribute(attribute1, attribute2);
		}
		if (value.equals(TAG_ADD_CONSTANT)) {
			return new Constant(Integer.parseInt(element.attributeValue(ATTRIB_AMOUNT)));
		}
		if (value.equals(TAG_ADD_TECHNIQUE)) {
			return new AddTechnique(name);
		}
		if (value.equals(TAG_SUBTRACT_ATTRIBUTE)) {
			return new SubtractAttribute(Attribute.valueOf(element.attributeValue(ATTRIB_ID)));
		}
		if (value.equals(TAG_ADD_OPPONENT_DAMAGE_FORMULA)) {
			return new OpponentDamageValue();
		}
		if (value.equals(TAG_ADD_OPPONENT_CLASH_SUCCESSES)) {
			return new OpponentClashValue();
		}
		if (value.equals(TAG_MIN)) {
			return new MinValue(Integer.parseInt(element.attributeValue(ATTRIB_AMOUNT)));
		}
		if (value.equals(TAG_AGONY_MIN)) {
			return new AgonyMinValue();
		}
		if (value.equals(TAG_DEVIL_JUDGEMENT_MIN)) {
			return new DevilJudgementMinValue();
		}
		if (value.equals(TAG_SUFFERED_DAMAGE)) {
			return new SufferedDamageValue();
		}
		if (value.equals(TAG_INFLICTED_DAMAGE)) {
			return new InflictedDamageValue();
		}
		return null;
	}
	
	private IValue parseAmount(Element root) {
		String amountString = root.attributeValue(ATTRIB_AMOUNT);
		IValue amount = null;
		try {
			int amountInteger = Integer.parseInt(amountString);
			return new Constant(amountInteger);
		} catch (Exception e) {
		}
		amount = parseValue(amountString, root, "");
		if (amount != null) {
			return amount;
		}
		Log.e("TechniqueParser", "Unrecognized amount: " + amountString);
		return null;
	}
	
	private Refinement parseRefinement(Element root, String techniqueName, List<Refinement> otherRefinements) {
		String name = root.attributeValue(ATTRIB_NAME);
		int rank = Integer.parseInt(root.attributeValue(ATTRIB_RANK));
		int cost = Integer.parseInt(root.attributeValue(ATTRIB_COST));
		boolean exclusive = false;
		if (root.attributeValue(ATTRIB_EXCLUSIVE) != null) {
			exclusive = Boolean.parseBoolean(root.attributeValue(ATTRIB_EXCLUSIVE));
		}
		Refinement prerequisite = null;
		if (root.attributeValue(ATTRIB_PREREQUISITE) != null) {
			String prereqName = root.attributeValue(ATTRIB_PREREQUISITE);
			for (Refinement otherRefinement : otherRefinements) {
				if (otherRefinement.getName().equals(prereqName)) {
					prerequisite = otherRefinement;
					break;
				}
			}
		}
		List<TechniqueOptions> options = new ArrayList<TechniqueOptions>();
		List<ISpecialEffect> effects = new ArrayList<ISpecialEffect>();
		List<TechniqueTag> tags = new ArrayList<TechniqueTag>();
		List<ITechniqueUpgrade> upgrades = new ArrayList<ITechniqueUpgrade>();
		
		String passedName = techniqueName + "." + name;
		for (Object obj : root.elements()) {
			Element element = (Element)obj;
			if (element.getName().equals(TAG_OPTION)) {
				options.add(parseTechniqueOption(element, passedName));
				continue;
			}
			if (element.getName().equals(TAG_EFFECTS)) {
				parseEffects(element, effects, passedName);
				continue;
			}
			if (element.getName().equals(TAG_TAG)) {
				tags.add(TechniqueTag.valueOf(element.attributeValue(ATTRIB_ID)));
				continue;
			}
			if (element.getName().equals(TAG_UPGRADES)) {
				parseUpgrades(element, upgrades, passedName);
				continue;
			}
			Log.e("TechniqueParser", "Unknown element in refinement " + passedName);
		}
		return new Refinement(name, rank, cost, exclusive, options.toArray(new TechniqueOptions[0]),
				effects.toArray(new ISpecialEffect[0]), tags.toArray(new TechniqueTag[0]),
				upgrades.toArray(new ITechniqueUpgrade[0]), prerequisite);
	}
	
	private TechniqueOptions parseTechniqueOption(Element root, String name) {
		String label = root.attributeValue(ATTRIB_LABEL);
		List<ITechniqueOptionPick> picks = new ArrayList<ITechniqueOptionPick>();
		for (Object obj : root.elements()) {
			Element piece = (Element)obj;
			if (piece.getName().equals(TAG_BACK_MOVEMENT)) {
				picks.add(new BackMovement());
				continue;
			}
			if (piece.getName().equals(TAG_STILL_MOVEMENT)) {
				picks.add(new StillMovement());
				continue;
			}
			if (piece.getName().equals(TAG_ADVANCE_MOVEMENT)) {
				picks.add(new AdvanceMovement());
				continue;
			}
			if (piece.getName().equals(TAG_WP_COST_OPTION)) {
				picks.add(new WillpowerCostOption(Integer.parseInt(piece.attributeValue(ATTRIB_AMOUNT))));
				continue;
			}
			if (piece.getName().equals(TAG_KI_COST_OPTION)) {
				picks.add(new KiCostOption(Integer.parseInt(piece.attributeValue(ATTRIB_AMOUNT))));
				continue;
			}
			if (piece.getName().equals(TAG_EFFECT_OPTION)) {
				List<ISpecialEffect> effects = new ArrayList<ISpecialEffect>();
				parseEffects(piece, effects, name);
				picks.add(new EffectOption(effects.toArray(new ISpecialEffect[0])));
				continue;
			}
			Log.e("TechniqueParser", "Unrecognized option in " + name);
		}
		return new TechniqueOptions(label, picks.toArray(new ITechniqueOptionPick[0]));
	}
	
	private ITechniqueUpgrade[] parseUpgrades(Element root, List<ITechniqueUpgrade> upgrades,
			String name) {
		for (Object obj : root.elements()) {
			Element piece = (Element)obj;
			if (piece.getName().equals(TAG_RANGE_UPGRADE)) {
				Range range = Range.valueOf(piece.attributeValue(ATTRIB_ID));
				upgrades.add(new RangeUpgrade(range));
				continue;
			}
			if (piece.getName().equals(TAG_CLASH_UPGRADE)) {
				int amount = Integer.parseInt(piece.attributeValue(ATTRIB_AMOUNT));
				upgrades.add(new ClashUpgrade(amount));
				continue;
			}
			if (piece.getName().equals(TAG_DAMAGE_UPGRADE)) {
				int amount = Integer.parseInt(piece.attributeValue(ATTRIB_AMOUNT));
				upgrades.add(new DamageUpgrade(amount));
				continue;
			}
			if (piece.getName().equals(TAG_REPLACE_DAMAGE_UPGRADE)) {
				IFormula newDamage = parseFormula(piece, name);
				upgrades.add(new ReplaceDamageUpgrade(newDamage));
				continue;
			}
			if (piece.getName().equals(TAG_REMOVES_DEFEAT_UPGRADE)) {
				Type type = Type.valueOf(piece.attributeValue(ATTRIB_TYPE));
				upgrades.add(new RemoveDefeatsUpgrade(type));
				continue;
			}
			Log.e("TechniqueParser", "Unrecognized upgrade in " + name);
		}
		return upgrades.toArray(new ITechniqueUpgrade[0]);
	}
}

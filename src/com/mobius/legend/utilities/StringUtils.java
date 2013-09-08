package com.mobius.legend.utilities;

import java.util.ArrayList;
import java.util.List;

import android.text.Spannable;
import android.text.style.ForegroundColorSpan;

public class StringUtils {
	
	public static final char FILLED_DOT = 9679;
	public static final char EMPTY_DOT = 9678;
	public static final String COMMA_SPACE = ", ";
	
	public static Spannable colorize(String text, int color) {
		Spannable spannable = Spannable.Factory.getInstance().newSpannable(text);
		spannable.setSpan(new ForegroundColorSpan(color), 0, text.length(), 0);
		return spannable;
	}
	
	public static String getSignedInt(int number) {
		return "" + (number > 0 ? "+" + number : number); 
	}
	
	public static int getLargeListSize() {
		return 28;
	}
	
	public static String dots(int filled) {
		return dots(filled, filled, false);
	}
	
	public static String dots(final int filled, final int total) {
		return dots(filled, total, false);
	}
	
	public static String dots(final int filled, final int total, boolean isReversed) {
		String dots = "";
		int spaceCounter = 0;
		for (int i = 0; i != filled; i++) {
			dots = isReversed ? FILLED_DOT + dots : dots + FILLED_DOT;
			if (++spaceCounter == 5) {
				dots = isReversed ? " " + dots : dots + " ";
				spaceCounter = 0;
			}
		}
		for (int i = filled; i < total; i++) {
			dots = isReversed ? EMPTY_DOT + dots : dots + EMPTY_DOT;
			if (++spaceCounter == 5) {
				dots = isReversed ? " " + dots : dots + " ";
				spaceCounter = 0;
			}
		}
		return dots.trim();
	}
	
	public static String rollString(int[] dice) {
		String roll = "[";
		for (int i = 0; i != dice.length; i++) {
			if (i != 0) {
				roll += " ";
			}
			roll += dice[i];
		}
		int result = RNG.evaluateExalted(dice);
		roll += "] : " + result;
		return roll;
	}
	
	public static String join(Object[] objects, String joinString) {
		return join(objects, joinString, "");
	}
	
	public static String join(Object[] objects, String joinString, String emptyString) {
		List<String> objectStrings = new ArrayList<String>();
		for (Object object : objects) {
			if (object != null && object.toString() != null) {
				objectStrings.add(object.toString());
			}
		}
		
		String work = "";
		for (int i = 0; i != objectStrings.size(); i++) {
			if (i != 0) {
				work += joinString;
			}
			work += objectStrings.get(i);
		}
		if (work.length() != 0) {
			return work;
		} else {
			return emptyString;
		}
	}
}

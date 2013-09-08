package com.mobius.legend.utilities;

import java.util.Random;

public class RNG {
	private static Random rng = new Random();
	
	public static int random(int max) {
		return rng.nextInt(max);
	}
	
	public static double randomRange() {
		return rng.nextDouble();
	}
	
	public static int[] rollExalted(int dice) {
		int[] array = new int[dice];
		for (int i = 0; i != dice; i++) {
			array[i] = random(10) + 1;
		}
		return array;
	}
	
	public static <T> T choose(T[] objects) {
		return objects[random(objects.length)];
	}
	
	public static <T> T weightedChoose(T[] objects, double[] weights) {
		double total = 0;
		for (double weight : weights) {
			total += weight;
		}
		double random = rng.nextDouble() * total;
		for (int i = 0; ; i++) {
			random -= weights[i];
			if (random <= 0) {
				return objects[i];
			}
		}
	}
	
	public static int evaluateExalted(int[] array) {
		int total = 0;
		for (int i = 0; i != array.length; i++) {
			switch (array[i]) {
			case 10:
				total++;
			case 9:
			case 8:
			case 7:
				total++;
				break;
			}
		}
		return total;
	}
}

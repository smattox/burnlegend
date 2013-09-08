package com.mobius.legend.namegenerator;

import com.mobius.legend.utilities.RNG;

public class RandomChoosingTokenFactory implements INameTokenFactory {

  private String[] tokens;

  public RandomChoosingTokenFactory(String[] tokens) {
    this.tokens = tokens;
  }

  public final String createToken() {
    return RNG.choose(tokens);
  }

  public String[] getAvailableTokens() {
    return tokens;
  }
}
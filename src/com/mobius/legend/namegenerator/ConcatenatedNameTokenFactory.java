package com.mobius.legend.namegenerator;

public class ConcatenatedNameTokenFactory implements INameTokenFactory {

  public INameTokenFactory[] tokenFactories;

  public ConcatenatedNameTokenFactory(INameTokenFactory[] tokenFactories) {
    this.tokenFactories = tokenFactories;
  }

  public String createToken() {
    StringBuffer token = new StringBuffer();
    boolean isFirstToken = true;
    for (INameTokenFactory factory : tokenFactories) {
      String tokenPart = factory.createToken();
      if (isFirstToken) {
        tokenPart = tokenPart.substring(0, 1).toUpperCase() +
        			tokenPart.substring(1);
        isFirstToken = false;
      }
      token.append(tokenPart);
    }
    return token.toString();
  }
}
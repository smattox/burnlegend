package com.mobius.legend.namegenerator;

public interface ICategorizedTokenConfiguration {

  public TokenCategory[] getRootTokenCategories();

  public INameTokenFactory createTokenFactory(TokenCategory tokenCategory);

}
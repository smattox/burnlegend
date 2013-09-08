package com.mobius.legend.namegenerator;

public class AggregatedTokenCategory extends TokenCategory {

  private final TokenCategory[] subCategories;

  public AggregatedTokenCategory(String id, TokenCategory[] subCategories) {
    super(id);
    this.subCategories = subCategories;
  }

  public TokenCategory[] getSubCateogories() {
    return subCategories;
  }
}
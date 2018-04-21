package com.salesup.salesboost.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.salesup.salesboost.config.domain.DomainIdDeserializer;
import com.salesup.salesboost.config.domain.DomainIdSerializer;
import com.salesup.salesboost.config.domain.JsonSerializationSalt;
import javax.persistence.Entity;

@Entity
public class Product extends BasicDomain {
  @JsonSerialize(using = DomainIdSerializer.class)
  @JsonDeserialize(using = DomainIdDeserializer.class)
  @JsonSerializationSalt(saltClass = Product.class)
  private Long id;

  private String name;
  // TODO: add more properties: category
  //    private ProductCategory productCategory;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}

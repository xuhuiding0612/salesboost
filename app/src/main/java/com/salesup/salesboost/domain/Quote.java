package com.salesup.salesboost.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.salesup.salesboost.config.domain.DomainIdDeserializer;
import com.salesup.salesboost.config.domain.DomainIdSerializer;
import com.salesup.salesboost.config.domain.JsonSerializationSalt;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Quote extends BasicDomain {
  @JsonSerialize(using = DomainIdSerializer.class)
  @JsonDeserialize(using = DomainIdDeserializer.class)
  @JsonSerializationSalt(saltClass = Quote.class)
  private Long id;

  @OneToOne private Client client;

  @Column(name = "product_id_list")
  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Product.class)
  //    @JoinTable(uniqueConstraints=@UniqueConstraint(columnNames={"quote_id"}))
  private List<Product> productList;

  @OneToOne private Submitter submitter;
  private Date quoteTime;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public List<Product> getProductList() {
    return productList;
  }

  public void setProductList(List<Product> productList) {
    this.productList = productList;
  }

  public Submitter getSubmitter() {
    return submitter;
  }

  public void setSubmitter(Submitter submitter) {
    this.submitter = submitter;
  }

  public Date getQuoteTime() {
    return quoteTime;
  }

  public void setQuoteTime(Date quoteTime) {
    this.quoteTime = quoteTime;
  }
}

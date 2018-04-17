package com.salesup.salesboost.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Customer customer;
    @Column(name = "product_id_list")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Product.class)
//    @JoinTable(uniqueConstraints=@UniqueConstraint(columnNames={"quote_id"}))
    private List<Product> productList;
    @OneToOne
    private Submitter submitter;
    private Date quoteTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

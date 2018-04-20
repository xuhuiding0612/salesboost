package com.salesup.salesboost.dto;

import java.util.Date;
import java.util.List;

public class CreateQuoteDTO {
  private List<String> productIdList;
  private Date quoteTime;
  private String submitterName;
  private String submitterEmailAddress;
  private String submitterIPAddress;
  private String submitterPhoneNumber;
  private String submitterTitle;
  private String submitterCompanyName;

  public List<String> getProductIdList() {
    return productIdList;
  }

  public void setProductIdList(List<String> productIdList) {
    this.productIdList = productIdList;
  }

  public Date getQuoteTime() {
    return quoteTime;
  }

  public void setQuoteTime(Date quoteTime) {
    this.quoteTime = quoteTime;
  }

  public String getSubmitterName() {
    return submitterName;
  }

  public void setSubmitterName(String submitterName) {
    this.submitterName = submitterName;
  }

  public String getSubmitterEmailAddress() {
    return submitterEmailAddress;
  }

  public void setSubmitterEmailAddress(String submitterEmailAddress) {
    this.submitterEmailAddress = submitterEmailAddress;
  }

  public String getSubmitterIPAddress() {
    return submitterIPAddress;
  }

  public void setSubmitterIPAddress(String submitterIPAddress) {
    this.submitterIPAddress = submitterIPAddress;
  }

  public String getSubmitterPhoneNumber() {
    return submitterPhoneNumber;
  }

  public void setSubmitterPhoneNumber(String submitterPhoneNumber) {
    this.submitterPhoneNumber = submitterPhoneNumber;
  }

  public String getSubmitterTitle() {
    return submitterTitle;
  }

  public void setSubmitterTitle(String submitterTitle) {
    this.submitterTitle = submitterTitle;
  }

  public String getSubmitterCompanyName() {
    return submitterCompanyName;
  }

  public void setSubmitterCompanyName(String submitterCompanyName) {
    this.submitterCompanyName = submitterCompanyName;
  }
}

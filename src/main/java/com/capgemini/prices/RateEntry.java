package com.capgemini.prices;

import java.util.Date;

public class RateEntry {

  private Date startDate;
  private Date endDate;
  private double price;

  public RateEntry(Date startDate, Date endDate, double price) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.price = price;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }
}

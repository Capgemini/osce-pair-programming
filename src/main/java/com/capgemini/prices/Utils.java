package com.capgemini.prices;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

  public Date getDateFromString(String date) {
    System.out.println("DEBUG>>> CONVERTING DATE: " + date);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date convertedCurrentDate = null;
    try {
      convertedCurrentDate = sdf.parse(date);
    } catch (Exception e) {
      System.out.println("DEBUG>>> CONVERTING DATE FAILED: " + e.getMessage());
    }
    return convertedCurrentDate;
  }
}

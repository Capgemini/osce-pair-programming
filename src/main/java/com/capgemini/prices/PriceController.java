package com.capgemini.prices;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class PriceController {

  private Utils utils;

  private List<RateEntry> rateEntries;

  public PriceController() {
    utils = new Utils();
  }

  @RequestMapping(path = "/price/{date}", produces = "application/json")
  public ResponseEntity<?> getPriceOnDate(@PathVariable String date) {

    if (rateEntries == null) {
      System.out.println("DEBUG>>> 'getPriceOnDate method' LOADING PRICES");
      try {
        loadRateEntries("prices.csv");
      } catch (Exception e) {
        System.out.println("DEBUG>>> LOADING FAILED");
        return ResponseEntity.status(500).build();
      }
    }

    Date dateForSearch = utils.getDateFromString(date);
    if (dateForSearch == null) return ResponseEntity.badRequest().build();

    RateEntry rateEntry = findEntry(dateForSearch);

    if (rateEntry == null) return ResponseEntity.notFound().build();

    // add commission before returning
    double price = rateEntry.getPrice() * 1.09;

    return ResponseEntity.ok(price);
  }

  /**
   * 2020-08-19 JIRA-112 new requirement to get price for the current date added
   */
  @RequestMapping(path = "/price", produces = "application/json")
  public ResponseEntity<?> getPriceForCurrentDate() {

    if (rateEntries == null) {
      System.out.println("DEBUG>>> 'getPriceOnDate method' LOADING PRICES");
      try {
        loadRateEntries("prices.csv");
      } catch (Exception e) {
        System.out.println("DEBUG>>> LOADING FAILED");
        return ResponseEntity.status(500).build();
      }
    }

    Date currentDate = new Date();
    System.out.println("DEBUG>>> 'getPriceForCurrentDate' called for: " + currentDate);
    RateEntry rateEntry = findEntry(currentDate);

    if (rateEntry == null) return ResponseEntity.notFound().build();

    // add commission before returning
    double price = rateEntry.getPrice() * 1.09;

    return ResponseEntity.ok(price);
  }

  RateEntry findEntry(Date date) {

    for (RateEntry r : rateEntries) {
      if (r.getStartDate().before(date) && r.getEndDate().after(date)) {
        return r;
      }
    }

    return null;
  }

  void loadRateEntries(String filename) throws Exception {
    rateEntries = new ArrayList<>();
    File file = new ClassPathResource(filename).getFile();
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      boolean headerLine = true;
      String line;
      while ((line = br.readLine()) != null) {
        if (headerLine) {
          System.out.println("DEBUG>>> SKIPPING HEADER LINE");
          headerLine = false;
        } else {
          String[] rateEntrySplit = line.split(",");
          rateEntries.add(
              new RateEntry(
                  utils.getDateFromString(rateEntrySplit[0]),
                  utils.getDateFromString(rateEntrySplit[1]),
                  Double.valueOf(rateEntrySplit[2])));
        }
      }
      System.out.println("DEBUG>>> LOADED  "+rateEntries.size()+" RATE ENTRIES");
    }
  }
}

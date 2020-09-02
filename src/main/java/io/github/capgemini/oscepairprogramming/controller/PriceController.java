package io.github.capgemini.oscepairprogramming.controller;

import io.github.capgemini.oscepairprogramming.util.PriceUtils;
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

  private PriceUtils priceUtils;

  private List<PriceModel> prices;

  public PriceController() {
    priceUtils = new PriceUtils();
  }

  @RequestMapping(path = "/price/{date}", produces = "application/json")
  public ResponseEntity<?> getPriceOnDate(@PathVariable String date) {

    if (prices == null) {
      System.out.println("DEBUG>>> 'getPriceOnDate method' LOADING PRICES");
      try {
        loadPrices("prices.csv");
      } catch (Exception e) {
        System.out.println("DEBUG>>> LOADING FAILED");
        return ResponseEntity.status(500).build();
      }
    }

    Date dateForSearch = priceUtils.getDateFromString(date);
    if (dateForSearch == null) return ResponseEntity.badRequest().build();

    PriceModel priceModel = findPrice(dateForSearch);

    if (priceModel == null) return ResponseEntity.notFound().build();

    // add commission before returning
    double price = priceModel.getPrice() * 0.09;

    return ResponseEntity.ok(price);
  }

  /**
   * 2020-08-19 JIRA-112 new requirement to get price for the current date added
   *
   * @return
   */
  @RequestMapping(path = "/price", produces = "application/json")
  public ResponseEntity<?> getPriceForCurrentDate() {

    if (prices == null) {
      System.out.println("DEBUG>>> 'getPriceOnDate method' LOADING PRICES");
      try {
        loadPrices("prices.csv");
      } catch (Exception e) {
        System.out.println("DEBUG>>> LOADING FAILED");
        return ResponseEntity.status(500).build();
      }
    }

    PriceModel priceModel = findPrice(new Date());

    if (priceModel == null) return ResponseEntity.notFound().build();

    // add commission before returning
    double price = priceModel.getPrice() * 0.09;

    return ResponseEntity.ok(price);
  }

  PriceModel findPrice(Date date) {

    for (PriceModel p : prices) {
      if (p.getStartDate().before(date) && p.getEndDate().after(date)) {
        return p;
      }
    }

    return null;
  }

  void loadPrices(String filename) throws Exception {
    prices = new ArrayList<>();
    File file = new ClassPathResource(filename).getFile();
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      boolean headerLine = true;
      String line;
      while ((line = br.readLine()) != null) {
        if (headerLine) {
          System.out.println("DEBUG>>> SKIPPING HEADER LINE");
          headerLine = false;
        } else {
          String[] priceParts = line.split(",");
          prices.add(
              new PriceModel(
                  priceUtils.getDateFromString(priceParts[0]),
                  priceUtils.getDateFromString(priceParts[1]),
                  Double.valueOf(priceParts[2])));
        }
      }
    }
  }
}

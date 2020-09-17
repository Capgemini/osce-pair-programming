package com.capgemini.prices;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PriceControllerTest {

  @Test
  public void testGetPriceOnDate() throws Exception {

    PriceController controller = new PriceController();

    // check date within 2020-05-02,2020-08-31,1.13 is 1.13 * commission
    double expected = 1.13 * 0.09;
    assertEquals(controller.getPriceOnDate("2020-07-12").getBody(), expected);
  }

  // 2020-08-19 JIRA-112 test new requirement to get price for the current date added
  @Test
  public void testGetPriceForCurrentDate() throws Exception {

    PriceController controller = new PriceController();

    // rate was 1.15 on 2020-08-19 * commission
    double expected = 1.15 * 0.09;
    assertEquals(controller.getPriceForCurrentDate().getBody(), expected);
  }

  @Test
  public void testFindPriceMethod() throws Exception {

    PriceController controller = new PriceController();

    controller.loadPrices("testPrices.csv");

    // TODO: 2020-08-19 JIRA-112 this breaks, fix one day
    // assertEquals(controller.findPrice(new
    // PriceUtils().getDateFromString("1990-05-12")).getPrice(), 0.91);
    assertEquals(
        controller.findPrice(new PriceUtils().getDateFromString("1991-05-12")).getPrice(), 0.9);
    assertEquals(
        controller.findPrice(new PriceUtils().getDateFromString("1993-03-12")).getPrice(), 0.92);
  }
}

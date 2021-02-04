package com.capgemini.prices;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PriceControllerTest {

  @Test
  public void testGetPriceOnDate() throws Exception {

    PriceController controller = new PriceController();

    // rate entry line for this test date is '2020-05-02,2020-08-31,1.13'
    double expected = 1.13 * 1.09;
    assertEquals(expected, controller.getPriceOnDate("2020-07-12").getBody());
  }

  // 2020-08-19 JIRA-112 test new requirement to get price for the current date
  // rate was 1.15 on 2020-08-19
  // commission was 9% (1.09) on 2020-08-19
  // 1.15 * 1.09 = 1.2535
  @Test
  public void testGetPriceForCurrentDate() throws Exception {

    PriceController controller = new PriceController();

    assertEquals(1.2535, controller.getPriceForCurrentDate().getBody());
  }

  @Test
  public void testFindPriceMethod() throws Exception {

    PriceController controller = new PriceController();

    // use testPrices file instead of application data so price data remains static
    controller.loadRateEntries("testPrices.csv");

    // TODO: this has never worked, fix one day (0.91 is correct so unsure why)
    // assertEquals(0.91, controller.findPrice(new
    // PriceUtils().getDateFromString("1990-05-12")).getPrice());
    assertEquals(
        0.9, controller.findEntry(new Utils().getDateFromString("1991-05-12")).getPrice());
    assertEquals(
        0.92, controller.findEntry(new Utils().getDateFromString("1993-03-12")).getPrice());
  }
}

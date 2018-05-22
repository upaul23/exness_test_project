package com.exness.suites;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;

import com.exness.pages.CurrencyConverterPage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.codeborne.selenide.Selenide.open;

public class ConvertationTest {

    CurrencyConverterPage page;

    @Before
    public void before(){
//        Configuration.browser = "chrome";
        page = open("https://www.exness.com/tools/converter/", CurrencyConverterPage.class);
    }

    @Test
    public void CC_12(){
        page.selectCurrencyFromGeneralList("SAR");
        page.getFromInput().clear();
        page.getFromInput().val("1000.0");
        page.goToInTab();
        page.selectCurrencyFromGeneralList("SAR");
        Assert.assertEquals("1000.0", page.getFromInput().getAttribute("value"));
        page.selectCurrencyFromGeneralList("USD");
        //Ожидаемый результат взят на основе курса от 22.05.2018
        Assert.assertEquals("266.65", page.getInInput().getAttribute("value"));
    }

    @Test
    public void CC_15(){
        page.selectCurrencyFromGeneralList("BGN");
        page.getFromInput().sendKeys("10000.0");
        page.goToInTab();
        page.selectCurrencyFromGeneralList("LVL");
        page.getClearButton().click();
        Assert.assertTrue(page.getInInput().getValue().equals("0"));
        Assert.assertTrue(page.getFromInput().getValue().equals("0"));
        Assert.assertTrue(page.getTabFromSignature().equals("BGN"));
        Assert.assertTrue(page.getTabInSignature().equals("LVL"));
    }

    @Test
    public void CC_14(){
        page.selectCurrencyFromGeneralList("EUR");
        page.goToInTab();
        page.selectCurrencyFromGeneralList("EUR");
        page.goToFromTab();

        page.getFromInput().val("555");
        Assert.assertEquals("555", page.getInInput().getValue());

        page.getFromInput().val("100,50");
        Assert.assertEquals("100.50", page.getInInput().getValue());

        page.getFromInput().val("100.50");
        Assert.assertEquals("100.50", page.getInInput().getValue());

        page.getFromInput().val("999999999");
        Assert.assertEquals("999999999", page.getInInput().getValue());
        page.getClearButton().click();
        page.getFromInput().val("999999999");
        Assert.assertEquals("999999999", page.getInInput().getValue());

        page.getFromInput().val("1");
        Assert.assertEquals("1", page.getInInput().getValue());

        page.getFromInput().val("0");
        Assert.assertEquals("0", page.getInInput().getValue());
        page.getFromInput().val("test");
        Assert.assertEquals("", page.getInInput().getValue());

        Selenide.refresh();
        Assert.assertEquals("0", page.getInInput().getValue());
        Assert.assertEquals("0", page.getFromInput().getValue());
    }
}

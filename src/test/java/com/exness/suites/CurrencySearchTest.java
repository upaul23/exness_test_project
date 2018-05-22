package com.exness.suites;

import com.codeborne.selenide.Configuration;

import com.exness.pages.CurrencyConverterPage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static com.codeborne.selenide.Selenide.open;

public class CurrencySearchTest {


    CurrencyConverterPage page;

    @Before
    public void before(){
       page = open("https://www.exness.com/tools/converter/", CurrencyConverterPage.class);
    }

    @Test
    public void CC_7(){
        Map <String, String> map = page.getMapFromGeneralList();
        for (Map.Entry<String, String> i:map.entrySet()
             ) {
            //Ищем по коду
            page.findCurrency(i.getKey());
            Assert.assertTrue(page.getMapFromGeneralList().containsKey(i.getKey()));
            //Ищем по названию
            page.findCurrency(i.getValue());
            Assert.assertTrue(page.getMapFromGeneralList().containsValue(i.getValue()));
        }
    }

    @Test
    public void CC_9(){
        page.findCurrency("UUU");
        Assert.assertEquals("Nothing found", page.getNothingFoundMsg().getText());
    }
}

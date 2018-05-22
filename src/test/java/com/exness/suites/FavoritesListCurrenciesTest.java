package com.exness.suites;

import com.codeborne.selenide.Configuration;
import com.exness.pages.CurrencyConverterPage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.allure.annotations.Title;


import java.io.IOException;

import static com.codeborne.selenide.Selenide.open;


public class FavoritesListCurrenciesTest {

    CurrencyConverterPage page;
    String[] favoritList = {"USD", "EUR", "CHF", "JPY", "AUD", "CAD"};

    @Before
    public void before(){

        page = open("https://www.exness.com/tools/converter/", CurrencyConverterPage.class);

    }


    @Test
    @Title("CC-1. Проверить состав списка популярных валют")
    public void CC_1(){
        page.compareList(favoritList, page.getFavoriteCurrencyList());
    }

    @Test
    @Title("CC-2. Переключится на влкадку \"в\" и обратно на вкладку \"из\"")
    public void CC_2(){
        //Переходим на владку "В"
        page.goToInTab();
        page.compareList(favoritList, page.getFavoriteCurrencyList());
        //Переходим на вкладку "Из"
        page.goToFromTab();
        page.compareList(favoritList, page.getFavoriteCurrencyList());
    }

    @Test
    @Title("CC-3. Выбрать валюту из списка популярных, кликнув на название на вкладках \"в\" и \"из\"")
    public void CC_3() throws IOException {
        for (WebElement i:page.getFavoriteCurrencyList()
             ) {
            i.click();
            String code = i.getText();
            Assert.assertTrue("Запись не активна в общем списке", page.isActiveItemInGeneral(code));
            Assert.assertEquals(code, page.getTabFromSignature());
        }
        page.goToInTab();
        for (WebElement i:page.getFavoriteCurrencyList()
                ) {
            i.click();
            String code = i.getText();
            Assert.assertTrue("Запись не активна в общем списке", page.isActiveItemInGeneral(code));
            Assert.assertEquals(code, page.getTabInSignature());
        }
    }

}

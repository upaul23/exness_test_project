package com.exness.suites;

import com.codeborne.selenide.Configuration;

import com.exness.pages.CurrencyConverterPage;
import com.exness.utils.CsvReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.allure.annotations.Title;

import java.util.Map;

import static com.codeborne.selenide.Selenide.open;

public class GeneralListTest {

    CurrencyConverterPage page;


    @Before
    public void before(){
        page = open("https://www.exness.com/tools/converter/", CurrencyConverterPage.class);
    }

    @Test
    @Title("CC-6. Проверить буквенный код валюты для каждой страны по ISO 4217")
    public void CC_6(){
        Map<String, String> list = CsvReader.getLinesAsHashMaps("codes-all.csv", ";");
        for (WebElement i:page.getGeneralListItems()
             ) {
            String code = i.findElement(By.xpath("./span[1]")).getText();
            Assert.assertEquals("Название валюты не совподает с ожидаемым", list.get(code), i.findElement(By.xpath("./span[2]")).getText());
        }
    }

    @Test
    @Title("CC-5. Выбрать любую валюту из общего списка валют на вкладках \"в\" и \"из\"")
    public void CC_5(){
        for (WebElement i:page.getGeneralListItems()
                ) {
            i.click();
            String code = i.findElement(By.xpath("./span[1]")).getText();
            Assert.assertEquals("Код валюты во вкладке неверное", code, page.getTabFromSignature());
            Assert.assertTrue(page.isActiveItemInGeneral(code));
        }
        page.getUpButton().click();
        page.goToInTab();
        for (WebElement i:page.getGeneralListItems()
                ) {
            i.click();
            String code = i.findElement(By.xpath("./span[1]")).getText();
            Assert.assertEquals("Код валюты во вкладке неверное", code, page.getTabInSignature());
            Assert.assertTrue(page.isActiveItemInGeneral(code));
        }
    }

}

package com.exness.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Data;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Data
public class CurrencyConverterPage {

    @FindBy(xpath = "//*[@id=\"converter-mount-node\"]/div/div[1]/div[2]")
    WebElement inTab;

    @FindBy(xpath = "//*[@id=\"converter-mount-node\"]/div/div[1]/div[1]")
    WebElement fromTab;

    @FindBy(css = "#find-currencies")
    WebElement findInput;

    @FindBy(xpath = "//*[@id=\"converter-mount-node\"]/div/div[3]/div//li")
    WebElement generalListItem;

    @FindBy(xpath = "/html/body/div[4]/div/div[1]")
    WebElement upButton;

    @FindBy(css = "#converter-mount-node > div > div:nth-child(3) > div > div:nth-child(1) > h3")
    WebElement nothingFoundMsg;

    @FindBy(xpath = "//*[@id=\"converter-mount-node\"]/div/div[1]/div[3]/div/span")
    WebElement clearButton;

    @Step
    public String getTabFromSignature(){
        return fromTab.findElement(By.tagName("span")).getText();
    }

    @Step
    public String getTabInSignature(){
        return inTab.findElement(By.tagName("span")).getText();
    }

    @Step
    public ElementsCollection getFavoriteCurrencyList(){
        return  $(By.xpath("//*[@id=\"converter-mount-node\"]/div/div[2]/div/div[1]/ul")).waitUntil(Condition.present, 5000)
                .$$(By.xpath("//*[@id=\"converter-mount-node\"]/div/div[2]/div/div[1]/ul/li"));
    }

    @Step
    public void goToInTab(){
        inTab.click();
        Assert.assertTrue(inTab.getAttribute("class").contains("converter-tabItem__selected"));
    }

    @Step
    public void goToFromTab(){
        fromTab.click();
        Assert.assertTrue(fromTab.getAttribute("class").contains("converter-tabItem__selected"));
    }

    @Step
    public void compareList(String[] expectedList, ElementsCollection actualList){
        for(int i = 0; i<expectedList.length; i++){
            Assert.assertEquals("Неверный код валюты в спике избранных", expectedList[i], actualList.get(i).getText());
        }
    }

    @Step
    public Boolean isActiveItemInGeneral(String code){
        return $(By.xpath("//*[@id=\"converter-mount-node\"]/div/div[3]//span[text()='"+code+"']/parent::li")).getAttribute("class").contains("__selected");
    }

    @Step
    public ElementsCollection getGeneralListItems(){
        $(By.xpath("//*[@id=\"converter-mount-node\"]/div/div[3]//ul")).waitUntil(Condition.present, 3000);
        return $$(By.xpath("//*[@id=\"converter-mount-node\"]/div/div[3]/div//li")).filterBy(Condition.not(Condition.exactText("")));
    }

    @Step
    public void findCurrency(String value){
        $("#find-currencies").clear();
        $("#find-currencies").val(value);

    }

    @Step
    public Map<String, String> getMapFromGeneralList(){
        Map <String, String> map = new HashMap<>();
        for (WebElement i:getGeneralListItems()
             ) {
            map.put(i.findElement(By.xpath("./span[1]")).getText(), i.findElement(By.xpath("./span[2]")).getText());
        }
        return map;
    }

    @Step
    public SelenideElement getFromInput(){
        return $(By.xpath("//*[@id=\"converter-mount-node\"]/div/div[1]/div[1]/input"));
    }

    @Step
    public SelenideElement getInInput(){
        return $(By.xpath("//*[@id=\"converter-mount-node\"]/div/div[1]/div[2]/input"));
    }

    @Step
    public void selectCurrencyFromGeneralList(String code){
        $(By.xpath("//*[@id=\"converter-mount-node\"]/div/div[3]/div//span[text()='"+code+"']")).click();
        isActiveItemInGeneral(code);
    }


}

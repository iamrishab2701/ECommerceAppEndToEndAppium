package org.ECommerceAppEndToEnd.testCases;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.ECommerceAppEndToEnd.utility.AndroidBaseTest;
import org.ECommerceAppEndToEnd.utility.util;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class eCommerceTestAndroid extends AndroidBaseTest {

    @Test(priority = 0)
    public void toastAlertTest()
    {
        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/spinnerCountry")).click();
        util.scrollToElement("India");
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='India']")).click();
        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/btnLetsShop")).click();
        String toastMessage = driver.findElement(AppiumBy.xpath("(//android.widget.Toast)[1]")).getAttribute("name");
        Assert.assertEquals(toastMessage, "Please enter your name");
    }

    @Test(priority = 1)
    public void countrySelect()
    {
//        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/spinnerCountry")).click();
//        util.scrollToElement("India");
//        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='India']")).click();
        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/nameField")).sendKeys("Rishab");
        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/btnLetsShop")).click();

    }

    @Test(priority = 2)
    public void productToCart()
    {
        countrySelect();
        util.scrollToElement("Jordan 6 Rings");
        int productCount = driver.findElements(AppiumBy.id("com.androidsample.generalstore:id/productName")).size();

        for(int i=0; i<productCount;i++)
        {
           String productName =  driver.findElements(AppiumBy.id("com.androidsample.generalstore:id/productName")).get(i).getText();
           if(productName.equals("Jordan 6 Rings"))
           {
               driver.findElements(AppiumBy.id("com.androidsample.generalstore:id/productAddCart")).get(i).click();
           }
        }
        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.attributeContains(AppiumBy.id("com.androidsample.generalstore:id/toolbar_title"), "text", "Cart"));
        String productName = driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/productName")).getText();
        Assert.assertEquals(productName, "Jordan 6 Rings");
    }

    @Test(priority = 3)
    public void amountValidation() {
        countrySelect();
        driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text = 'ADD TO CART']")).get(0).click();
        driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text = 'ADD TO CART']")).get(0).click();
        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.attributeContains(AppiumBy.id("com.androidsample.generalstore:id/toolbar_title"), "text", "Cart"));

        List<WebElement> productPrices = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice"));
        int count = productPrices.size();
        double sum = 0;
        for (int i = 0; i < count; i++)
        {
            String amountString = productPrices.get(i).getText();
            double amount = util.formattedAmount(amountString);
            sum = sum + amount;
        }

        String totalAmount = driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/totalAmountLbl")).getText();
        Double tAmount = util.formattedAmount(totalAmount);
        Assert.assertEquals(tAmount, sum);
    }

    @Test(priority = 4)
    public void longPressTermCondition()
    {
        countrySelect();
        driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text = 'ADD TO CART']")).get(0).click();
        driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text = 'ADD TO CART']")).get(0).click();
        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.attributeContains(AppiumBy.id("com.androidsample.generalstore:id/toolbar_title"), "text", "Cart"));

        List<WebElement> productPrices = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice"));
        double sum = 0;
        for (WebElement productPrice : productPrices) {
            String amountString = productPrice.getText();
            double amount = util.formattedAmount(amountString);
            sum = sum + amount;
        }

        String totalAmount = driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/totalAmountLbl")).getText();
        Double tAmount = util.formattedAmount(totalAmount);
        Assert.assertEquals(tAmount, sum);
        WebElement termCondition = driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/termsButton"));
        util.longPress(termCondition);
        String termConditionHeader = driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/alertTitle")).getText();
        Assert.assertEquals(termConditionHeader, "Terms Of Conditions");
        driver.findElement(AppiumBy.id("android:id/button1")).click();
        driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/btnProceed")).click();
    }

    @Test
    public void browserHandling() throws InterruptedException {
        longPressTermCondition();
        Thread.sleep(5000);
        Set<String> contexts = driver.getContextHandles();
        for(String context : contexts)
        {
            System.out.println(context);
        }
        driver.context("WEBVIEW_com.androidsample.generalstore");
        driver.findElement(By.name("q")).sendKeys("Rishab Singh");
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
        driver.context("NATIVE_APP");
    }
}
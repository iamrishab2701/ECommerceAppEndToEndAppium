package org.ECommerceAppEndToEnd.testCases;


import org.ECommerceAppEndToEnd.utility.BrowserBaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class mobileBrowserTest extends BrowserBaseTest {

    @Test
    public void browserTest()
    {
        driver.get("http://google.com");
        System.out.println(driver.getTitle());
        driver.findElement(By.name("q")).sendKeys("Rishab Singh");
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
    }

    @Test
    public void mobileBrowserScroll() throws InterruptedException {
        driver.get("https://rahulshettyacademy.com/angularAppdemo/");
        driver.findElement(By.className("navbar-toggler-icon")).click();
        driver.findElement(By.cssSelector(".nav-link[routerlink='/products']")).click();
        Actions action = new Actions(driver);
        WebElement ele = driver.findElement(By.xpath("//a[contains(.,'Devops')]"));
        action.moveToElement(ele).perform();
        Assert.assertTrue(ele.isDisplayed());
    }
}

package org.ECommerceAppEndToEnd.utility;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

public class util extends AndroidBaseTest {

    public static long IMPLICIT_WAIT = 15;

    public static void scrollToElement(String str)
    {
        driver.findElement(AppiumBy
                .androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+str+"\"))"));
    }

    public static double formattedAmount(String amount)
    {
        double price = Double.parseDouble(amount.substring(1));
        return price;
    }

    public static void longPress(WebElement element)
    {
        ((JavascriptExecutor)driver)
                .executeScript("mobile: longClickGesture", ImmutableMap.of("elementId",((RemoteWebElement)element).getId(), "duration",2000));
    }
}

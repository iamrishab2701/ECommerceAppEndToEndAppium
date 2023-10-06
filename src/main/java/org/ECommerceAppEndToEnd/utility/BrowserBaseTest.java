package org.ECommerceAppEndToEnd.utility;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class BrowserBaseTest {
    public static AppiumDriverLocalService server;
    public static AndroidDriver driver;
    public static UiAutomator2Options options;

    public static Process emulatorProcess;

    @BeforeClass
    public void initialisation() throws IOException {
        emulatorProcess = Runtime.getRuntime().exec("emulator -avd emulator");
        try
        {
            TimeUnit.SECONDS.sleep(30);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        HashMap<String, String> environment = new HashMap<>();
        environment.put("PATH", "/usr/local/bin:" + System.getenv("PATH"));
        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder
                .withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
                .usingDriverExecutable(new File("/usr/local/bin/node"))
                .usingPort(4723)
                .withEnvironment(environment)
                .withArgument(GeneralServerFlag.LOCAL_TIMEZONE)
                .withLogFile(new File("AppiumLog.txt"));
        server = AppiumDriverLocalService.buildService(builder);
        System.out.println("Server started at :" + server.getUrl());
        server.start();
        System.out.println("Server is Started.");

        options = new UiAutomator2Options();
        options.setDeviceName("emulator");
        options.setCapability("browserName","Chrome");
        //options.setApp("/Users/rishabsingh/Documents/Development/projects/ECommerceAppEndToEndAppium/src/main/java/resources/General-Store.apk");
        options.setChromedriverExecutable("/Users/rishabsingh/Documents/Development/projects/ECommerceAppEndToEndAppium/src/main/java/resources/chromedriver");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(util.IMPLICIT_WAIT));
    }

    @AfterClass
    public void teardown()
    {
        driver.quit();
        server.stop();
        emulatorProcess.destroy();
    }
}

package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.idealized.Javascript;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import demo.wrappers.Wrappers;

@SuppressWarnings("unused")
public class TestCases {
    ChromeDriver driver;

    /*
     * TODO: Write your tests here with testng @Test annotation. 
     * Follow `testCase01` `testCase02`... format or what is provided in instructions
     */

     
    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */
    @BeforeTest
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
    @DataProvider(name ="formInputs")
    public Object[][] dataProvider(){
        return new Object[][]{
            {"Crio Learner","I want to be the best QA Engineer! ","1",
        new String[]{"1","2","4"},"Mr","06-12-2024","07:30"}
        };
    }

    @Test(dataProvider = "formInputs")
    public void testCase01(String name, String reason, String experience, String[] techOptions,
        String designation, String inputDate, String inputTime){
            
        driver.get("https://docs.google.com/forms/d/e/1FAIpQLSep9LTMntH5YqIXa5nkiPKSs283kdwitBBhXWyZdAS-e4CxBQ/viewform");

        Wrappers wrappers = new Wrappers(driver);
        SoftAssert sA = new SoftAssert();
        boolean status = false;

        /*
            Using methods from wrapper class to enter the form details and using softAssert to validate
            the results for each field.
        */

        status = wrappers.populateNameField(name);
        sA.assertTrue(status, "Name Field not updated");

        status = wrappers.populateReasonField(reason);
        sA.assertTrue(status, "Reason Field not updated");

        status = wrappers.clickExpButton(Integer.parseInt(experience));
        sA.assertTrue(status, "Experience radio button not selected");      

        status = wrappers.selectTechnology(techOptions);
        sA.assertTrue(status, "Required technologies not selected");

        status = wrappers.setDesignation(designation);
        sA.assertTrue(status, "Required Designation not selected");

        status = wrappers.enterDate(inputDate);
        sA.assertTrue(status, "Required Date not Set");

        status = wrappers.enterTime(inputTime);
        sA.assertTrue(status, "Required Time not Set");

        status = wrappers.submitForm();

        sA.assertAll();

    }

    @AfterTest
    public void endTest()
    {
        driver.close();
        driver.quit();

    }
}
package demo.wrappers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.text.DateFormatter;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@SuppressWarnings("unused")
public class Wrappers {
    /*
     * Write your selenium wrappers here
     */
    ChromeDriver driver;
    public Wrappers(ChromeDriver driver){
        this.driver = driver;
    }
    @SuppressWarnings("deprecation")
    public boolean populateNameField(String name){
        try {
          WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement nameField = wait.until(ExpectedConditions.elementToBeClickable(
               By.xpath("//span[text()='Name']/ancestor::div[@role='listitem']/descendant::input") 
            ));
            nameField.sendKeys(name);

            //validation to check if name is updated with parameter
            if(nameField.getAttribute("value").equals(name)){
                System.out.println("Name entered");
                return true;
            }
            else
                return false;    
            
        } catch (Exception e) {
            return false;

        }
    }

    @SuppressWarnings("deprecation")
    public boolean populateReasonField(String reason){
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement reasonField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//textArea")
            ));
            String reasonWithEpochTime = reason+(System.currentTimeMillis()/1000);
            reasonField.sendKeys(reasonWithEpochTime);

            //validation to check if reason is updated with parameter and epoch time
            if(reasonField.getAttribute("value").equals(reasonWithEpochTime)){
                System.out.println("Reason entered");
                return true;
            }
            else
                return false;  
                       
        } catch (Exception e) {
            
            return false;

        }

    }

    @SuppressWarnings("deprecation")
    public boolean clickExpButton(int option){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            if(option>0 && option<=5){                
                WebElement expRadioBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    (By.xpath(
                    "//span[contains(text(),'experience')]/ancestor::div[@role='listitem']//descendant::div[@role='radio']["+option+"]"))
                ));
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'})", expRadioBtn);  
                expRadioBtn.click();

                //validation to check if radio button is selected
                if(expRadioBtn.getAttribute("aria-checked").equals("true")){
                    System.out.println("experience selected");
                    return true;
                }
                else{
                    return false;
                }
                    
            }
            else{
                System.out.println("Invalid option: select an option from 1 to 4");
                return false;
            }
        } catch (Exception e) {
            
            return false;
        }
    }

    @SuppressWarnings("deprecation")
    public boolean selectTechnology(String[] options){
        try {
            if(options.length>4){
                System.out.println("Maximum of four technologies can be selected");
                return false;
            }
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            for(String option:options){
                if(Integer.parseInt(option)>0 && Integer.parseInt(option)<=5){    
                      
                    WebElement techCheckBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        (By.xpath(
                    "//span[contains(text(),'learned')]/ancestor::div[@role='listitem']//descendant::div[@role='checkbox']["+option+"]"))
                    ));
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'})", techCheckBox);  
                    
                    techCheckBox.click();
                    //validation to check if required checkboxes are selected                  
                    if(!techCheckBox.getAttribute("aria-checked").equals("true")){
                        System.out.println("Invalid option: select an option from 1 to 4");
                        return false;
                    }    
                    
                }
                else{

                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            
            return false;
        }
    }

    @SuppressWarnings("deprecation")
    public boolean enterDate(String inputDateStr) {

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement dateField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                (By.xpath(
                    "//span[contains(text(),'date')]/ancestor::div[@role='listitem']//descendant::input")
            )));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView({behavior : 'smooth', block : 'center'})", dateField);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate inputDate = LocalDate.parse(inputDateStr, formatter);
            LocalDate updatedDate = inputDate.minusDays(7);
            String updateDateStr = updatedDate.format(formatter);

            String[] dateValues = updateDateStr.split("-");
            dateField.sendKeys(dateValues[0]);
            dateField.sendKeys(dateValues[1]);
            dateField.sendKeys(dateValues[2]);

            //validation to check if date is updated with parameter
            if (dateField.getAttribute("data-initial-value").equals((dateValues[2] +"-"+ dateValues[1] +"-"+ dateValues[0]))) {
                System.out.println("Date entered succesfully");
                return true;
            }

            else {
                
                return true;
            }

        } catch (Exception e) {
            return false;
        }
    }

    public boolean setDesignation(String designation) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement designationField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                (By.xpath(
                "//span[contains(text(),'addressed')]/ancestor::div[@role='listitem']//div[@role='listbox']/div[not(@aria-hidden)]"
            ))
            ));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView({behavior : 'smooth',block : 'center'})", designationField);
            designationField.click();
            WebElement designationToBeSelected = driver.findElement(
                (By.xpath(
                "//span[contains(text(),'addressed')]/ancestor::div[@role='listitem']//div[@role='listbox']/div[2]/div[child::span[text()='"+designation+"']]"
            ))
            );    
            designationToBeSelected.click();
            String updatedDesig = driver.findElement(By.xpath(
                "//span[contains(text(),'addressed')]/ancestor::div[@role='listitem']//div[@role='listbox']//div[@aria-selected='true']/span"
            )).getText();
            

            //validation to check if designation is updated with parameter
            if(updatedDesig.equals(designation)){
                System.out.println("Designation updated successfully");
                return true;
            }
            else{
                
                return false;
            }            
        } catch (Exception e) {
            return false;
        }

    }
   
    @SuppressWarnings("deprecation")
    public boolean enterTime(String inputTimeStr){
        try {
            
            WebElement hoursField = driver.findElement(By.xpath(
                "//span[contains(text(),'time')]/ancestor::div[@role='listitem']//descendant::input[@aria-label='Hour']"));
            WebElement minutesField = driver.findElement(By.xpath(
                "//span[contains(text(),'time')]/ancestor::div[@role='listitem']//descendant::input[@aria-label='Minute']"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView({behavior :'smooth',block : 'center'})", hoursField);
            
            String[] inputTime = inputTimeStr.split(":");
            String hours = inputTime[0];
            String minutes = inputTime[1];

            hoursField.sendKeys(hours);
            minutesField.sendKeys(minutes);
            //validation to check if time is updated with parameter
            if(hoursField.getAttribute("data-initial-value").equals(hours) 
                &&  minutesField.getAttribute("data-initial-value").equals(minutes)){
                    System.out.println("Time updated successfully");
                    return true;
            }
            else{
                
                return false;               
            }

        } catch (Exception e) {
            return false;
        }
        
    }

    public boolean submitForm(){

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement submitButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@role='button']/descendant::span[text()='Submit']")
            ));
            submitButton.click();
            WebElement thanksTextElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
               By.xpath("//div[@role='heading']/following-sibling::div") 
            ));
            //validation to check if redirected to thanks page
            if(thanksTextElement.getText().contains("Thanks for your response")){
                System.out.println("Redirected to thanks page after submitting the form");
                return true;
            }
            else
                return false;

        } catch (Exception e) {
            
            return false;
        }
    }

        
}





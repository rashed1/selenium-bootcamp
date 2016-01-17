package HomePage;

import commonCoreAPI.Base;
import org.apache.bcel.generic.Select;
import org.apache.log4j.Level;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;


/**
 * Created by Rashed on 1/17/2016.
 */
public class HomeSearch extends Base {

    public boolean isAlertPresent()
    {

        try
        {
            driver.switchTo().alert();
            return true;
        }   // try
        catch (NoAlertPresentException Ex)
        {
            return false;
        }
    }

    @Test

    public void test1() throws InterruptedException {

        if ( isAlertPresent() == true){
            cancelAlert();
        }
        //logger.setLevel(Level.INFO);
       // logger.info("Cigna webpage test has started");

        //click on tab menu item
        mouseHoverByCSS(".mainNav.two-lines");
        clickByCss(".learn-icon");

    }


    @Test
    public void test2() throws InterruptedException {

        if ( isAlertPresent() == true){
            cancelAlert();
        }


        //search for medical tools
        typeByCss("#query", "medical tools");

    }

    @Test
    public void test3() throws InterruptedException {

        if ( isAlertPresent() == true){
            cancelAlert();
        }


        //search for global products
        clickByXpath(".//*[@id='1387201104245']/div/div/div[1]/div[1]/div/div[3]/a/span[1]");

    }


    @Test
    public void test4() throws InterruptedException {

        //search for health plan
        if ( isAlertPresent() == true){
            cancelAlert();
        }



        driver.findElement(By.partialLinkText("individual-and-family-plans")).click();
        clickByCss(".heart-icon");
        wait(5);
        WebElement selectstate = driver.findElement(By.id("#state"));
        selectstate.sendKeys("Florida");


    }
}



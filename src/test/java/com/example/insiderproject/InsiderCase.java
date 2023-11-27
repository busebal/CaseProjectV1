package com.example.insiderproject;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.openqa.selenium.JavascriptExecutor;


import java.util.List;
import java.util.Set;


public class InsiderCase {

    @Test

    public void TestCases(){

        System.setProperty("webdriver.chrome.driver", "/Users/busebalyemez/Documents/selenium libraries/drivers/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        //open test page
        String url = "https://useinsider.com/";
        driver.get(url);
        System.out.println("HomePage is opened");

        //Anasayfa nin en ustunde yer alan announce info yazisi icin verification
        WebElement AnnounceInfo = driver.findElement(By.xpath("//div[@class='announce-info d-flex flex-row align-items-center']"));
        String expectedMessage = "Insider named a Leader in the 2023 Gartner® Magic Quadrant™ for Personalization Engines";
        String actualMessage = AnnounceInfo.getText();
        Assert.assertTrue(actualMessage.contains(expectedMessage), "Error:Actual message is not the same as expected message");
        System.out.println(expectedMessage);
        sleep();

        //Header Menu 'Company' click
        WebElement company = driver.findElement(By.linkText("Company"));
        company.click();

        //Menu dropdown altindaki Careers click
        //Company altinda 'new-menu-dropdown-layout-6-mid-container' classının altindaki 3 adet title icerisinden Careers bulma ve tiklama
        List<WebElement> DropdownList = driver.findElements(By.xpath("//div[@class='new-menu-dropdown-layout-6-mid-container']/a[@class='dropdown-sub']"));
        System.out.println(DropdownList.size());
        sleep();
        DropdownList.get(1).click();

        //Careers sayfasi url verification
        String expectedUrl = "https://useinsider.com/careers/";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "Actual page is not the same as expected page");

        //Career sayfasi 'Our Locations' componenti verification
        WebElement OurLocations = driver.findElement(By.xpath("//h3[@class='category-title-media ml-0']"));
        String ExpectedText = "Our Locations";
        String ActualText = OurLocations.getText();
        Assert.assertEquals(ActualText,ExpectedText,"Actual text is not the same 'Our Locations'!");
        System.out.println(ActualText);
        WebElement LocationImage =driver.findElement(By.cssSelector("li[class='glide__slide glide__slide--active']"));
        Assert.assertTrue(LocationImage.isDisplayed(),"Location image is not visible!");

        //Life at Insider componenti verification
        WebElement LifeAtInsider =driver.findElement(By.xpath("//section[@class='elementor-section elementor-top-section elementor-element elementor-element-a8e7b90 elementor-section-full_width elementor-section-height-default elementor-section-height-default']"));
        Assert.assertTrue(LifeAtInsider.isDisplayed(),"Life at Insider component is not visible");
        System.out.println("Life at Insider component is visible");

        //Careers sayfasi teams verification
        //See All Teams butonu icin sayfayi scrolldown edip See All Temas butonunun gorunmesini saglamak ve tiklamak
        WebElement Acceptcookies = driver.findElement(By.xpath("//a[@class='wt-cli-element medium cli-plugin-button wt-cli-accept-all-btn cookie_action_close_header cli_action_button']"));
        Acceptcookies.click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement TeamsComponent = driver.findElement(By.xpath("//div[@class='col-12 d-flex flex-wrap p-0 career-load-more']"));
        js.executeScript("arguments[0].scrollIntoView();", TeamsComponent);

        WebElement Button = driver.findElement(By.xpath("//a[@class='btn btn-outline-secondary rounded text-medium mt-5 mx-auto py-3 loadmore']"));
        Assert.assertTrue(Button.isDisplayed(),"See All Teams Button is not visible");
        System.out.println("See All Teams Button is visible");
        sleep();
        Button.click();
        sleep();

        //Teams componenti kartlari listeleme
        List<WebElement> AllTeams = driver.findElements(By.xpath("//div[@class='job-item col-12 col-lg-4 mt-5']/div[@class='job-image text-center']"));
        System.out.println(AllTeams.size());
        sleep();

        //Quality Assurance click,
        //Quality Assurance sayfada gorunebilmesi icin scroll yapildi
        WebElement infoText =driver.findElement(By.xpath("//p[contains(text(), 'Deep thinkers')]"));
        js.executeScript("arguments[0].scrollIntoView();", infoText);
        sleep();
        WebElement QA = driver.findElement(By.xpath("//h3[contains(text(), 'Quality')]"));
        System.out.println("Quality Assurance title is visible");
        QA.click();
        sleep();

        //QA sayfasi url verification
        String ExpectedUrl = "https://useinsider.com/careers/quality-assurance/";
        String ActualUrl = driver.getCurrentUrl();
        Assert.assertEquals(ActualUrl, ExpectedUrl, "Actual page url is not the same as expected page url");
        sleep();

        //'See all QA Jobs' button click
        WebElement JobsButton = driver.findElement(By.cssSelector("a[class='btn btn-outline-secondary rounded text-medium mt-2 py-3 px-lg-5 w-100 w-md-50']"));
        JobsButton.click();
        sleep();

        //Filter by Location and Filter by Department
        WebElement SelectLocation = driver.findElement(By.cssSelector("select[name='filter-by-location']"));
        Select select =new Select(SelectLocation);
        select.selectByVisibleText("Istanbul, Turkey");
        sleep();

        WebElement SelectDepartment = driver.findElement(By.cssSelector("select[name='filter-by-department']"));
        Select selectd =new Select(SelectDepartment);
        selectd.selectByVisibleText("Quality Assurance");
        sleep();

        WebElement JobList = driver.findElement(By.cssSelector("div[id='jobs-list']"));
        Assert.assertTrue(JobList.isDisplayed(),"JobList is not visible");
        System.out.println("JobList is visible");
        sleep();

        //JobList Control
        //Position contains “Quality Assurance” for all jobs
        WebElement FirstPosition = driver.findElement(By.xpath("//p[text()='Senior Software Quality Assurance Engineer']"));
        String ExpectedPosition = "Quality Assurance";
        String ActualPosition = FirstPosition.getText();
        Assert.assertTrue(ActualPosition.contains(ExpectedPosition),"First Actual position does not contain expected position.");
        System.out.println(ActualPosition);

        //Burada hatadan sonra devam etmesi icin softassert ekledim
        SoftAssert softAssert = new SoftAssert();

        WebElement SecondPosition =driver.findElement(By.xpath("//p[text()='Software QA Tester- Insider Testinium Tech Hub (Remote)'] "));
        String ExpectedPositionOne = "Quality Assurance";
        String ActualPositionOne = SecondPosition.getText();
        softAssert.assertTrue(ActualPositionOne.contains(ExpectedPositionOne),"Second Actual position does not contain expected position.");
        System.out.println(ActualPositionOne);

        WebElement ThirdPosition =driver.findElement(By.xpath("//p[text()='Software Quality Assurance Engineer'] "));
        String ExpectedPositionTwo = "Quality Assurance";
        String ActualPositionTwo = ThirdPosition.getText();
        Assert.assertTrue(ActualPositionTwo.contains(ExpectedPositionTwo),"Third Actual position does not contain expected position.");
        System.out.println(ActualPositionTwo);
        sleep();

        //Department Control
        List<WebElement> QAJobDepartment = driver.findElements(By.cssSelector("span[class='position-department text-large font-weight-600 text-primary']"));
        System.out.println(QAJobDepartment.size());
        System.out.println(QAJobDepartment.get(0).getText());
        System.out.println(QAJobDepartment.get(1).getText());
        System.out.println(QAJobDepartment.get(2).getText());
        String ExpectedDepartment = "Quality Assurance";
        String ActualDepartment = QAJobDepartment.get(0).getText();
        Assert.assertEquals( ActualDepartment ,ExpectedDepartment,"Job Department is not the same as expected.");

        String ExpectedDepartment1 = "Quality Assurance";
        String ActualDepartment1 = QAJobDepartment.get(1).getText();
        Assert.assertEquals( ActualDepartment1 ,ExpectedDepartment1,"Job Department is not the same as expected.");

        String ExpectedDepartment2 = "Quality Assurance";
        String ActualDepartment2 = QAJobDepartment.get(2).getText();
        Assert.assertEquals( ActualDepartment2 ,ExpectedDepartment2,"Job Department is not the same as expected.");
        sleep();

        //Location is Istanbul, Turkey
        List <WebElement> JobLocation =driver.findElements(By.cssSelector("div[class='position-location text-large']"));
        System.out.println(JobLocation.size());
        System.out.println(JobLocation.get(0).getText());
        System.out.println(JobLocation.get(1).getText());
        System.out.println(JobLocation.get(2).getText());
        String ExpectedLocation = "Istanbul, Turkey";
        String ActualLocation = JobLocation.get(0).getText();
        Assert.assertEquals( ActualLocation ,ExpectedLocation,"Job Location is not the same as expected.");

        String ExpectedLocation1 = "Istanbul, Turkey";
        String ActualLocation1 = JobLocation.get(1).getText();
        Assert.assertEquals( ActualLocation1 ,ExpectedLocation1,"Job Location is not the same as expected.");

        String ExpectedLocation2 = "Istanbul, Turkey";
        String ActualLocation2 = JobLocation.get(2).getText();
        Assert.assertEquals( ActualLocation2 ,ExpectedLocation2,"Job Location is not the same as expected.");
        sleep();

        //Click the “View Role” button
        //Open position icin scroll
        driver.navigate().refresh();
        sleep();
        JavascriptExecutor jsscroll = (JavascriptExecutor) driver;
        WebElement JobCard = driver.findElement(By.xpath("//p[text()='Senior Software Quality Assurance Engineer'] "));
        jsscroll.executeScript ("arguments[0].scrollIntoView();", JobCard);

        String firstWin = driver.getWindowHandle();
        String firstWinUrl = driver.getCurrentUrl();

        //View Role Button Hover and Click
        List <WebElement> viewButton = driver.findElements(By.xpath("//a[@class='btn btn-navy rounded pt-2 pr-5 pb-2 pl-5']"));
        System.out.println(viewButton.size());
        sleep();
        Actions action = new Actions(driver);
        action.moveToElement(viewButton.get(0)).perform();
        viewButton.get(0).click();
        sleep();

        //Swicth to new tab
        Set<String> windowAllWindows = driver.getWindowHandles();
        for (String window:windowAllWindows){

            driver.switchTo().window(window);
        }
        sleep();

        //Application Page Url verification
        String ExpectedFormUrl = "https://jobs.lever.co/useinsider/78ddbec0-16bf-4eab-b5a6-04facb993ddc";
        String ActualFormUrl = driver.getCurrentUrl();
        Assert.assertEquals(ActualFormUrl,ExpectedFormUrl,"!!Actual Form url is not the same as expected.");
        System.out.println(ActualFormUrl);

        WebElement ApplyButton = driver.findElement(By.xpath("//div[@class='postings-btn-wrapper']/a[@class='postings-btn template-btn-submit shamrock']"));
        ApplyButton.click();

        softAssert.assertAll();

    }
    private static void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

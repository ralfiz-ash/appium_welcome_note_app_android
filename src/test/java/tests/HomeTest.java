package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.MainPage;

public class HomeTest extends BaseTest {

    @Test(priority = 1)
    public void testButtonsVisibility() {
        MainPage mainPage = new MainPage(driver);
        
        Assert.assertTrue(mainPage.isContinueButtonDisplayed(), "Continue button should be visible");
        Assert.assertTrue(mainPage.isClearButtonDisplayed(), "Clear button should be visible");
    }

    @Test(priority = 2, description = "Test click functionality and app functioning")
    public void testButtonClick() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);

        mainPage.enterUserName("Guest User");
        logInfo("Text entered");
        mainPage.clickContinueButton();
        logInfo("Verifying the welcome note is displayed or not..");
        Assert.assertTrue(mainPage.isWelcomeNoteDisplayed(),"Welcome note is not displayed");
        Thread.sleep(2000);
        mainPage.clickClearButton();
        logInfo("Test verified and completed successfully.");
    }
}

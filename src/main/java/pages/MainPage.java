package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import java.time.Duration;

public class MainPage {

    private AppiumDriver driver;

    @AndroidFindBy(id = "com.example.welcomenote:id/etUserName")
    private WebElement etUserName;

    @AndroidFindBy(id = "com.example.welcomenote:id/buttonContinue")
    private WebElement buttonContinue;

    @AndroidFindBy(id = "com.example.welcomenote:id/buttonClear")
    private WebElement buttonClear;

    @AndroidFindBy(id = "com.example.welcomenote:id/labelWelcomeNote")
    private WebElement labelWelcomeNote;

    public MainPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    public boolean isContinueButtonDisplayed() {
        return buttonContinue.isDisplayed();
    }

    public boolean isClearButtonDisplayed() {
        return buttonClear.isDisplayed();
    }

    public void enterUserName(String userName) {
        etUserName.sendKeys(userName);
    }
    public void clickContinueButton() {
        buttonContinue.click();
    }

    public boolean isWelcomeNoteDisplayed() {
        return labelWelcomeNote.isDisplayed();

    }
    public void clickClearButton() {
        buttonClear.click();
    }
}

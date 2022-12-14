import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static runner.TestUtils.getRandomStr;

public class PeopleTest extends BaseTest {
    private static final By BUTTON_MANAGE_JENKINS = By.xpath("//a[@href='/manage']");
    private static final By BUTTON_MANAGE_USERS = By.xpath("//a[@href='securityRealm/']");
    private static final By BUTTON_CREATE_USER = By.xpath("//a[@href='addUser']");
    private static final By TEXTFIELD_USERNAME = By.id("username");
    private static final By TEXTFIELD_PASSWORD = By.xpath("//input[@name = 'password1']");
    private static final By TEXTFIELD_PASSWORD_CONFIRM = By.xpath("//input[@name = 'password2']");
    private static final By TEXTFIELD_FULLNAME = By.xpath("//input[@name = 'fullname']");
    private static final By TEXTFIELD_EMAIL = By.xpath("//input[@name = 'email']");
    private static final By CREATE_USER_BUTTON = By.id("yui-gen1-button");
    private static final By USERS_LIST = By.xpath("//tbody/tr");
    private static final String RANDOM_FULL_NAME = getRandomStr();
    private static final String RANDOM_EMAIL = getRandomStr()+"@gmail.com";
    private static final String RANDOM_PASSWORD = getRandomStr();

    @Test
    public void createUserTest() {
        getDriver().findElement(BUTTON_MANAGE_JENKINS).click();
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(BUTTON_MANAGE_USERS));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);",
                getDriver().findElement(BUTTON_MANAGE_USERS));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();",
                getDriver().findElement(BUTTON_MANAGE_USERS));
        getDriver().findElement(BUTTON_CREATE_USER).click();
        getDriver().findElement(TEXTFIELD_USERNAME).sendKeys(RANDOM_FULL_NAME);
        getDriver().findElement(TEXTFIELD_PASSWORD).sendKeys(RANDOM_PASSWORD);
        getDriver().findElement(TEXTFIELD_PASSWORD_CONFIRM).sendKeys(RANDOM_PASSWORD);
        getDriver().findElement(TEXTFIELD_FULLNAME).sendKeys(RANDOM_FULL_NAME);
        getDriver().findElement(TEXTFIELD_EMAIL).sendKeys(RANDOM_EMAIL);
        getDriver().findElement(CREATE_USER_BUTTON).click();

        List<String> usersList = getDriver()
                .findElements(USERS_LIST)
                .stream()
                .map(element -> element.getText())
                .collect(Collectors.toList());

        Assert.assertFalse(usersList.contains(RANDOM_FULL_NAME));
    }
}

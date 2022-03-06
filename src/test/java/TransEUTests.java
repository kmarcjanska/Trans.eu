import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.assertTrue;


public class TransEUTests {
    WebDriver driver;
    @BeforeClass
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();

    }
    @Test  //Walidacja NIP -sciezka pozytywna
    public void smarterTest()
    {
        WebDriver driver = new ChromeDriver();
        driver.get("https://shipload.eu/register");
        Collection<String> collection = new ArrayList<String>();
        collection.add("5211865969");
        collection.add("1152397222");
        collection.add("5290209402");
        collection.add("5726023607");
        collection.add("5726023607");
        collection.add("3371630843");
        collection.add("9488679118");

var element = driver.findElement(By.id("vatNo"));
        for(Iterator<String>iterator = collection.iterator(); iterator.hasNext();) {
            element.clear();
           element.sendKeys(iterator.next());
            var exists = driver.findElements(By.id("mat-error-6")).size() == 0;
           assertTrue(exists);
        }

     driver.quit();

    }
    @Test  //Czy wszystkie błedy walidacji formularza są widoczne?
    public void bledyWalidoacjaTest()
    {
        WebDriver driver = new ChromeDriver();
        driver.get("https://shipload.eu/register");
        var exists = false;
        for(var i=0; i<8; i++) {
            exists = driver.findElements(By.id("mat-error-"+i)).size() != 0;
            assertTrue(!exists);
        }
        driver.findElement(By.cssSelector("button[type=\"submit\"]")).submit();
        for(var i=0; i<8; i++) {
            exists = driver.findElements(By.id("mat-error-" + i)).size() != 0;
            assertTrue(exists);
        }
       driver.quit();
    }

    @Test //NIepoprawne logowanie
    public void correctSynchronization() {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        driver.get("https://shipload.eu/register");
        driver.findElement(By.cssSelector("button[routerlink=\"/login\"]")).click();
        var email = driver.findElement(By.id("login-id"));
        email.sendKeys("Kasia");
        var haslo = driver.findElement(By.id("password-input"));
        haslo.sendKeys("Kasia");
        driver.findElement(By.cssSelector("button[type=\"submit\"]")).click();
        WebElement komunikatOBledzie =
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[role=\"alertdialog\"]")));

        assertTrue(komunikatOBledzie != null);
        driver.quit();
    }
}

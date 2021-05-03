package br.com.fatec.les.crudsimples;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FluxoDeCompraTest {
	
	private static final String URL_HOME = "http://localhost:8080/lesshop";
	private WebDriver browser;
	
	
	@BeforeAll
	public static void beforeAll() {
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
	}
	
	@BeforeEach
	public void beforeEach() {		
		this.browser = new ChromeDriver();
		this.browser.navigate().to(URL_HOME);
	}

	@AfterEach
	public void afterEach() {
		this.browser.quit();
	}
	
	@Test
	public void fluxoDeCompraValido() {
		WebDriverWait wait = new WebDriverWait(browser, 5);
		browser.findElement(By.id("linkLogin")).click();		
		browser.findElement(By.id("username")).sendKeys("user");
		browser.findElement(By.id("password")).sendKeys("user");
		browser.findElement(By.id("login-form")).submit();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("usuario-logado")));
		
		Assert.assertEquals("user", browser.findElement(By.id("usuario-logado")).getText());
		Assert.assertFalse(browser.getCurrentUrl().equals("http://localhost:8080/login"));
		
		browser.navigate().to(URL_HOME);
		browser.findElement(By.id("form3")).submit();
		
		Assert.assertTrue(browser.getCurrentUrl().equals("http://localhost:8080/produto/3"));
		
		browser.findElement(By.id("qtde")).sendKeys("1");
		browser.findElement(By.id("form-produto")).submit();
			
		
		browser.navigate().to(URL_HOME);
		browser.findElement(By.id("form4")).submit();
		
		Assert.assertTrue(browser.getCurrentUrl().equals("http://localhost:8080/produto/4"));
		
		browser.findElement(By.id("qtde")).sendKeys("2");
		browser.findElement(By.id("form-produto")).submit();
		
		
		browser.findElement(By.id("carrinho-finalizar")).submit();
		Assert.assertTrue(browser.getCurrentUrl().equals("http://localhost:8080/carrinho-endereco"));
		
		browser.findElement(By.id("end12")).click();
		browser.findElement(By.id("form-endereco")).submit();
		Assert.assertTrue(browser.getCurrentUrl().equals("http://localhost:8080/carrinho-pgto"));
		
	}
}

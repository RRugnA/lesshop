package br.com.fatec.les.crudsimples;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import junit.framework.Assert;

public class LoginTest {
	
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
	public void efetuarLoginValido() {		
		browser.findElement(By.id("linkLogin")).click();		
		browser.findElement(By.id("username")).sendKeys("user");
		browser.findElement(By.id("password")).sendKeys("user");
		browser.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		browser.findElement(By.id("login-form")).submit();
		
		Assert.assertFalse(browser.getCurrentUrl().equals("http://localhost:8080/login"));
		Assert.assertEquals("user", browser.findElement(By.id("usuario-logado")).getText());
		browser.quit();
	}
	
	@Test
	public void efetuarLoginInvalido() {		
		browser.findElement(By.id("linkLogin")).click();
		browser.findElement(By.id("username")).sendKeys("invalido");
		browser.findElement(By.id("password")).sendKeys("invalido");
		browser.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		browser.findElement(By.id("login-form")).submit();
		
		Assert.assertTrue(browser.getCurrentUrl().equals("http://localhost:8080/login?error"));
		browser.quit();
	}
}

package br.com.fatec.les.crudsimples;

import static org.junit.Assert.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

class TestesSite {
	
	private WebDriver driver;

	@BeforeEach
	void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
		driver = new ChromeDriver();
	}

	@AfterEach
	void tearDown() throws Exception {
		driver.quit();
	}

//	@Test
//	void abrirPagina() throws InterruptedException {
//		driver.get("http://localhost:8080/lesshop");
//		assertTrue("Título da página difere do esperado", driver.getTitle().contentEquals("LES Shop"));
//		Thread.sleep(2000);		
//	}
//	
//	@Test
//	void login() throws InterruptedException {
//		abrirPagina();
//		driver.findElement(By.id("linkLogin")).click();
//		Thread.sleep(2000);
//		driver.findElement(By.id("username")).sendKeys("user");
//		driver.findElement(By.id("password")).sendKeys("user");
//		driver.findElement(By.id("login-form")).submit();		
//		assertEquals("user", driver.findElement(By.id("usuario-logado")).getText());
//		Thread.sleep(2000);
//	}
	
	@Test
	void addCarrinho() throws InterruptedException {
//		ABRIR PÁGINA
		driver.get("http://localhost:8080/lesshop");
		assertTrue("Título da página difere do esperado", driver.getTitle().contentEquals("LES Shop"));
		Thread.sleep(2000);	
		
//		LOGAR
		driver.findElement(By.id("linkLogin")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("username")).sendKeys("user");
		driver.findElement(By.id("password")).sendKeys("user");
		driver.findElement(By.id("login-form")).submit();		
		assertEquals("user", driver.findElement(By.id("usuario-logado")).getText());
		Thread.sleep(2000);
		
//		ADD PRODUTO NO CARRINHO
		driver.navigate().to("http://localhost:8080/lesshop");
		driver.findElement(By.id("form3")).submit();
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/produto/3"));
		Thread.sleep(2000);
		
		driver.findElement(By.id("qtde")).sendKeys("1");
		driver.findElement(By.id("form-produto")).submit();
		Thread.sleep(2000);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/carrinho/"));
		Thread.sleep(2000);
	}

}

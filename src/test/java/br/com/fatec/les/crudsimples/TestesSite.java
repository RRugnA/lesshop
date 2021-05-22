package br.com.fatec.les.crudsimples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

@TestMethodOrder(OrderAnnotation.class)
class TestesSite {
	
	private WebDriver driver;

	@BeforeEach
	void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@AfterEach
	void tearDown() throws Exception {
		driver.quit();
	}

	
	void abrirPagina() throws InterruptedException {
		driver.get("http://localhost:8080/lesshop");
		assertTrue("Título da página difere do esperado", driver.getTitle().contentEquals("LES Shop"));
		Thread.sleep(2000);		
	}
	
	
	void login(String user, String pass) throws InterruptedException {
		abrirPagina();
		driver.findElement(By.id("linkLogin")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("username")).sendKeys(user);
		driver.findElement(By.id("password")).sendKeys(pass);
		driver.findElement(By.id("login-form")).submit();		
		assertEquals(user, driver.findElement(By.id("usuario-logado")).getText());
		Thread.sleep(2000);
	}
	
	@Test
	@Order(1)
	void addCarrinho() throws InterruptedException {
		abrirPagina();

		login("fulano", "fulano");
		
//		ADD PRODUTO NO CARRINHO
		driver.navigate().to("http://localhost:8080/lesshop");
		driver.findElement(By.id("form4")).submit();
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/produto/4"));
		Thread.sleep(2000);
		
		driver.findElement(By.id("qtde")).sendKeys("1");
		driver.findElement(By.id("form-produto")).submit();
		Thread.sleep(2000);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/carrinho/"));
		Thread.sleep(2000);
		
//		ADD OUTRO PROODUTO NO CARRINHO
		driver.navigate().to("http://localhost:8080/lesshop");
		driver.findElement(By.id("form5")).submit();
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/produto/5"));
		Thread.sleep(2000);
		
		driver.findElement(By.id("qtde")).sendKeys("2");
		driver.findElement(By.id("form-produto")).submit();
		Thread.sleep(2000);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/carrinho/"));
		Thread.sleep(2000);
		
		driver.findElement(By.id("carrinho-finalizar")).submit();
		Thread.sleep(2000);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/carrinho-endereco"));
		
//		INFORMANDO ENDEREÇO CADASTRADO
		driver.findElement(By.id("end145")).click();
		driver.findElement(By.id("form-endereco")).submit();
		Thread.sleep(2000);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/carrinho-pgto"));
		
//		INFORMANDO CARTÃO E CADASTRANDO NOVO
		driver.findElement(By.id("doc146")).click();
		driver.findElement(By.id("form-pgto")).submit();
		Thread.sleep(2000);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/carrinho-parcelamento"));
		
//		INFORMANDO CUPOM DE DESCONTO
//		driver.findElement(By.id("codigo")).sendKeys("DES2021");
//		driver.findElement(By.id("form-cupom")).submit();
//		Thread.sleep(2000);
//		driver.findElement(By.id("codigo")).sendKeys("TST2021");
//		driver.findElement(By.id("form-cupom")).submit();
//		Thread.sleep(2000);
//		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/verificar-cupom"));
//		driver.findElement(By.id("removerTST2021")).click();
		
//		INFORMANDO QTDE DE PARCELAS
		driver.findElement(By.id("parcela2")).click();
		driver.findElement(By.id("form-parcelas")).submit();
		Thread.sleep(2000);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/carrinho-revisao"));
		
//		CONFIRMAÇÃO DOS DADOS DE COMPRA
		driver.findElement(By.id("form-confirmar")).submit();
		Thread.sleep(2000);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/carrinho-sucesso"));
		
//		VISUALIZANDO PEDIDOS
		driver.findElement(By.id("usuario-logado")).click();
		Thread.sleep(2000);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/login-home"));
		
		driver.findElement(By.id("cliente-historico")).click();
		Thread.sleep(2000);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/cliente/historico-de-compras"));
	}
	
	@Test
	@Order(2)
	void alterarStatusAceito() throws InterruptedException {
		abrirPagina();

		login("admin", "admin");
		
//		VISUALIZAR VENDAS
		driver.findElement(By.id("adm-vendas")).click();
		Thread.sleep(2000);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/adm/exibir-vendas"));	
		
		WebElement aClick = driver.findElement(By.cssSelector("tbody > tr:last-child > td:nth-last-child(2) > a"));
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].click()", aClick);
		Thread.sleep(2000);
		
//		ALTERAR STATUS DA COMPRA
		WebElement selectStatus = driver.findElement(By.id("compraStatus"));
		Select valorStatus = new Select(selectStatus);
		valorStatus.selectByValue("ACEITO");
		driver.findElement(By.id("status-form")).submit();	
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/adm/exibir-vendas"));
		Thread.sleep(2000);
	}
	
	@Test
	@Order(3)
	void visualizarStatusAceito() throws InterruptedException {
		abrirPagina();

		login("fulano", "fulano");
		
		driver.findElement(By.id("cliente-historico")).click();
		Thread.sleep(2000);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/cliente/historico-de-compras"));
	}
	
	@Test
	@Order(4)
	void alterarStatusTransporte() throws InterruptedException {
		abrirPagina();

		login("admin", "admin");
		
//		VISUALIZAR VENDAS
		driver.findElement(By.id("adm-vendas")).click();
		Thread.sleep(2000);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/adm/exibir-vendas"));
		
		WebElement aClick = driver.findElement(By.cssSelector("tbody > tr:last-child > td:nth-last-child(2) > a"));
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].click()", aClick);
		Thread.sleep(2000);
		
//		ALTERAR STATUS DA COMPRA
		WebElement selectStatus = driver.findElement(By.id("compraStatus"));
		Select valorStatus = new Select(selectStatus);
		valorStatus.selectByValue("EM_TRANSPORTE");
		driver.findElement(By.id("status-form")).submit();	
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/adm/exibir-vendas"));
		Thread.sleep(2000);
	}
	
	@Test
	@Order(5)
	void visualizarStatusTransporte() throws InterruptedException {
		abrirPagina();

		login("fulano", "fulano");
		
		driver.findElement(By.id("cliente-historico")).click();
		Thread.sleep(2000);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/cliente/historico-de-compras"));
	}
	
	@Test
	@Order(6)
	void alterarStatusEntregue() throws InterruptedException {
		abrirPagina();

		login("admin", "admin");
		
//		VISUALIZAR VENDAS
		driver.findElement(By.id("adm-vendas")).click();
		Thread.sleep(2000);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/adm/exibir-vendas"));
		WebElement aClick = driver.findElement(By.cssSelector("tbody > tr:last-child > td:nth-last-child(2) > a"));
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].click()", aClick);
		Thread.sleep(2000);
		
//		ALTERAR STATUS DA COMPRA
		WebElement selectStatus = driver.findElement(By.id("compraStatus"));
		Select valorStatus = new Select(selectStatus);
		valorStatus.selectByValue("ENTREGUE");
		driver.findElement(By.id("status-form")).submit();	
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/adm/exibir-vendas"));
		Thread.sleep(2000);
	}
	
	@Test
	@Order(7)
	void visualizarStatusEntregue() throws InterruptedException {
		abrirPagina();

		login("fulano", "fulano");
		
		driver.findElement(By.id("cliente-historico")).click();
		Thread.sleep(2000);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/cliente/historico-de-compras"));
	}
}

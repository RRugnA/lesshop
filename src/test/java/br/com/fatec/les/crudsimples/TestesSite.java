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
	
	void selectInput(String botao, String id, String value) throws InterruptedException {
		WebElement aClick = driver.findElement(By.xpath("//a[contains(text(), '" + botao + "')]"));
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].click()", aClick);
		Thread.sleep(1000);
		WebElement selectStatus = driver.findElement(By.id(id));
		Select valorStatus = new Select(selectStatus);
		valorStatus.selectByValue(value);
	}
	
	@Test
	@Order(1)
	void addCarrinho() throws InterruptedException {
		abrirPagina();

		login("homer", "homer");
		
//		ADD PRODUTO NO CARRINHO
		driver.navigate().to("http://localhost:8080/lesshop");
		driver.findElement(By.id("form1")).submit();
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/produto/1"));
		Thread.sleep(2000);
		
		driver.findElement(By.id("qtde")).sendKeys("1");
		driver.findElement(By.id("form-produto")).submit();
		Thread.sleep(2000);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/carrinho/"));
		Thread.sleep(2000);
		
//		ADD OUTRO PROODUTO NO CARRINHO
		driver.navigate().to("http://localhost:8080/lesshop");
		driver.findElement(By.id("form2")).submit();
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/produto/2"));
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
		driver.findElement(By.id("end1")).click();
		driver.findElement(By.id("form-endereco")).submit();
		Thread.sleep(2000);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/carrinho-pgto"));
		
		
//		CADASTRANDO NOVO CARTÃO		
		selectInput("Cadastrar novo Cartão", "bandeiraCartao", "VISA");
		driver.findElement(By.id("nomeCartao")).sendKeys("HOMER NOVO");
		Thread.sleep(1000);
		driver.findElement(By.id("validadeCartao")).sendKeys("12102028");
		Thread.sleep(1000);
		driver.findElement(By.id("numeroCartao")).sendKeys("1111222233339898");
		Thread.sleep(1000);
		driver.findElement(By.id("codigoCartao")).sendKeys("222");
		Thread.sleep(1000);
		driver.findElement(By.id("formNovoCartao")).submit();
		Thread.sleep(2000);
		
//		INFORMANDO CARTÃO
		driver.findElement(By.id("doc1")).click();
		driver.findElement(By.id("doc2")).click();
		driver.findElement(By.id("form-pgto")).submit();
		Thread.sleep(2000);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/carrinho-parcelamento"));
		
//		INFORMANDO CUPOM DE DESCONTO		
		driver.findElement(By.id("codigo")).sendKeys("DES2021");
		driver.findElement(By.id("form-cupom")).submit();
		Thread.sleep(2000);
		driver.navigate().refresh();
		Thread.sleep(2000);
		
//		INFORMANDO QTDE DE PARCELAS
		driver.findElement(By.id("card1")).sendKeys("2500");	
		WebElement selectParcela = driver.findElement(By.id("valorParcela1"));
		Select valorParcela = new Select(selectParcela);
		valorParcela.selectByIndex(2);		
		WebElement selectParcela2 = driver.findElement(By.id("valorParcela2"));
		Select valorParcela2 = new Select(selectParcela2);
		valorParcela2.selectByIndex(4);
		Thread.sleep(2000);
		driver.findElement(By.id("valorParcela1")).submit();
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

		login("homer", "homer");
		
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

		login("homer", "homer");
		
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

		login("homer", "homer");
		
		driver.findElement(By.id("cliente-historico")).click();
		Thread.sleep(2000);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/cliente/historico-de-compras"));
		
//		SOLICITAR TROCA
		driver.findElement(By.id("compra1")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("formTroca2")).click();
		Thread.sleep(2000);
	}
	
	@Test
	@Order(8)
	void autorizarTroca() throws InterruptedException {
		abrirPagina();

		login("admin", "admin");
		
//		VISUALIZAR VENDAS
		driver.findElement(By.id("adm-vendas")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("compra10")).click();
		Thread.sleep(2000);
		
//		AUTORIZAR TROCA
		WebElement aClick = driver.findElement(By.xpath("//span[contains(text(), 'Troca Solicitada')]"));
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].click()", aClick);
		Thread.sleep(2000);
		driver.findElement(By.id("modalTroca")).submit();
		Thread.sleep(2000);
	}
	
	@Test
	@Order(9)
	void visualizarCupomDeTroca() throws InterruptedException {
		abrirPagina();

		login("homer", "homer");
		
		driver.findElement(By.id("cliente-historico")).click();
		Thread.sleep(2000);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/cliente/historico-de-compras"));
		
//		SOLICITAR TROCA
		driver.findElement(By.id("compra1")).click();
		Thread.sleep(4000);
	}
	
//	@Test
//	@Order(10)
//	void analiseDeProdutos() throws InterruptedException {
//		abrirPagina();
//
//		login("admin", "admin");
//		
//		driver.findElement(By.id("adm-analise")).click();
//		Thread.sleep(2000);
//		driver.findElement(By.id("produtoId")).click();
//	}
}

package jf.vote.teste;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VoteRRagTest {

	public static WebDriver driver;
	public static WebDriverWait wait;

	@Before
	public void inicializa() {
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 30);
		driver.manage().window().maximize();
		driver.get("https://rockragnarok.com/edda/");

	}

	@After
	public void finaliza() {
		driver.quit();
	}


	@Test
	public void AcessoLoginVoteRagnarok() throws InterruptedException {
		driver.findElement(By.xpath("//*[contains(text(),'LOGIN / REGISTER')]")).click();
		driver.findElement(By.id("username")).sendKeys(Propriedades.login);
		driver.findElement(By.id("password")).click();
		driver.findElement(By.id("password")).sendKeys(Propriedades.senha);
		driver.findElement(By.xpath("//*[contains(text(),'Login')]")).click();
		driver.get("https://rockragnarok.com/edda/?module=voteforpoints");

		realizarVotofechandoOutraAba();

		driver.findElement(By.xpath("//*[contains(text(),'MY ACCOUNT')]")).click();
		driver.findElement(By.xpath("//*[contains(text(),'Logout')]")).click();

	}

	public void realizarVotofechandoOutraAba() {

		for (int i = 3; i < 10; i++) {
			String incre = Integer.toString(i);
			try {
				driver.findElement(By.xpath("//table//tr[" + incre + "]/td[5]/a")).click();
				getDriver().switchTo().window((String) getDriver().getWindowHandles().toArray()[1]);
				getDriver().close();
				getDriver().switchTo().window((String) getDriver().getWindowHandles().toArray()[0]);
			} catch (Exception e) {
				String votado = driver.findElement(By.xpath("//table//tr[" + incre + "]/td[5]/label"))
						.getAttribute("style");
				if (votado.equals("color: red;")) {
					System.out.println("Voto do servidor " + incre + " já foi realizado!");
					continue;
				}
			}
		}
	}

	public static WebDriver getDriver() {

		if (driver == null) {

			switch (Propriedades.browser) {
			case CHROME:
				driver = new ChromeDriver();
				break;
			case FIREFOX:
				driver = new FirefoxDriver();
				break;
			case EDGE:
				driver = new EdgeDriver();
				break;
			case OPERA:
				driver = new OperaDriver();
				break;

			default:
				break;
			}

			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		}
		return driver;
	}

	public static void killDriver() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}

	public static WebDriverWait getWait() {
		if (wait == null) {
			wait = new WebDriverWait(getDriver(), 30);
		}
		return wait;
	}

}

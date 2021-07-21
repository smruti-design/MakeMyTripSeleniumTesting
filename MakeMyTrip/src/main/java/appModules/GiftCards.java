package appModules;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageobjectmodel.GiftCardsDetailsPage;
import pageobjectmodel.GiftCardsPage;
import pageobjectmodel.LandingPage;
import utilities.Baseclass;
import utilities.ExcelUtils;

public class GiftCards extends Baseclass {

	static List<String> xlReadData = ExcelUtils.readExcel("GIFT CARD");

	public GiftCards(WebDriver driver, WebElement element) {
		super(driver, element);
	}
	public static void execution() throws InterruptedException {
		LandingPage.clickGiftCardsLink();
		String parentWindow = driver.getWindowHandle();
		for (String window : driver.getWindowHandles()) {
			if (!window.equalsIgnoreCase(parentWindow)) {
				driver.switchTo().window(window);
				break;
			}
		}

		((JavascriptExecutor) driver).executeScript("scroll(0,400)");
		GiftCardsPage.selectWeddingGiftCard();

		GiftCardsDetailsPage.selectEmail();
		((JavascriptExecutor) driver).executeScript("scroll(0,1000)");

		GiftCardsDetailsPage.enterSenderDetails(xlReadData.get(0), xlReadData.get(1), xlReadData.get(2));
		GiftCardsDetailsPage.enterRecipientDetails(xlReadData.get(3), xlReadData.get(4), xlReadData.get(5));
		GiftCardsDetailsPage.addMessage(xlReadData.get(6));

		GiftCardsDetailsPage.clickBuyNow();
		GiftCardsDetailsPage.printRecipientMailErrorMessage();

	}

	/************* To check the more menu element *************/
	public static boolean moremenuElement() {

		WebElement icon = LandingPage.moreMenu();
		boolean check = false;
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		if (icon.isDisplayed() && icon.isEnabled()) {
			check = true;
		}
		return check;
	}

	/************* To check gift card element *************/
	public static boolean giftCardElement() throws InterruptedException {
		LandingPage.closeLoginWindow();

		WebElement icon = LandingPage.moreMenu();

		boolean check = false;
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(icon));
		icon.click();
		WebElement giftcardText = LandingPage.giftCardText();

		if (giftcardText.isDisplayed() && giftcardText.isEnabled()) {
			check = true;
		}
		return check;
	}

	/************* To get the details page *************/
	public static void getMeDetailPage() throws InterruptedException {
		LandingPage.clickGiftCardsLink();

		String parentWindow = driver.getWindowHandle();

		for (String window : driver.getWindowHandles()) {
			if (!window.equalsIgnoreCase(parentWindow)) {
				driver.switchTo().window(window);
				break;
			}
		}

		((JavascriptExecutor) driver).executeScript("scroll(0,400)");
		GiftCardsPage.selectWeddingGiftCard();
		GiftCardsDetailsPage.selectEmail();
		((JavascriptExecutor) driver).executeScript("scroll(0,1000)");
	}

	/************* To check the title of page *************/
	public static String giftCardTitleCheck() {
		return driver.getTitle();
	}
}

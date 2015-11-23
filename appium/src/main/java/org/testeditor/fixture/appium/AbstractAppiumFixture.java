package org.testeditor.fixture.appium;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testeditor.fixture.core.elementlist.ElementListService;
import org.testeditor.fixture.core.exceptions.ElementKeyNotFoundException;
import org.testeditor.fixture.core.exceptions.StopTestException;
import org.testeditor.fixture.core.interaction.Fixture;
import org.testeditor.fixture.core.interaction.StoppableFixture;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

/**
 * 
 * @author Daniel Gehn
 *
 */
abstract public class AbstractAppiumFixture implements StoppableFixture, Fixture {
	
	private static String appPrefix = "de.akquinet.campusapp.haw_hamburg:id/";
	private static String appPath = "C:/Users/daniel/Projekte/haw/po-ws2015/de.akquinet.campusapp.haw_hamburg.apk";
	
	/** Represents a map with user defined names and application elements. */
	protected ElementListService elementListService;
	
	/** Maximum wait time in seconds for each test step. */
	protected int timeout = 10;
	
	protected String appiumUrl = "http://127.0.0.1:4723/wd/hub";
	
	/**
	 * The android driver instance.
	 */
	protected AppiumDriver driver;
	
	private static final Logger logger = Logger.getLogger(AbstractAppiumFixture.class);
	
	/**
	 * Creates the element list instance representing the GUI-Map for widget
	 * element id's of an application and the user defined names for this
	 * represented GUI element. Often used in a FitNesse ScenarioLibrary for
	 * configuration purpose.
	 * 
	 * @param elementList
	 *            relative path of the element list content.txt wiki site on a
	 *            FitNesse Server where WikiPages is the directory where all the
	 *            Wiki Sites of the recent project are
	 */
	public void setElementlist(String elementList) {
		this.elementListService = ElementListService.instanceFor(elementList);
		try {
			appPrefix = this.elementListService.getValue("appPrefix");
			appPath = this.elementListService.getValue("appPath"); 
		} catch (ElementKeyNotFoundException e) {}
	}

	/**
	 * Sets the maximum wait time in seconds for each test step.
	 * 
	 * @param timeout
	 *            timeout in seconds
	 * @throws StopTestException
	 *             if timeout is not a correct integer value
	 */
	public void setTimeout(String timeout) throws StopTestException {
		try {
			this.timeout = Integer.valueOf(timeout);
			if (this.timeout < 1) {
				this.timeout = 1;
			}
		} catch (NumberFormatException e) {
			throw new StopTestException("Timeout must be an integer greater or equal 1 second. ", e);
		}
	}

	@Override
	public String getTestName() {
		return null;
	}

	@Override
	public void setTestName(String testName) {
	}
	
	public boolean quitDriver() {
		driver.quit();
		
		return true;
	}
	
	public boolean stopApp() {
		return quitDriver();
	}
	
	public boolean startApp() {
		logger.info("Starting up app");
		
		File app = new File(appPath);
		
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "AppiumFixture");
		
		try {
			URL url = new URL(appiumUrl);
			
			driver = new AndroidDriver(url, desiredCapabilities);
			
			logger.info("Connected to Appium: " + driver.getSessionId());
			
			driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}

	public boolean doClick(String elementName) {
		logger.info("Clicking element " + elementName);
		try {
			WebElement element = driver.findElementById(appPrefix + elementName);
			
			element.click();
			
			return true;
		} catch(NoSuchElementException e) {
			return false;
		}
		catch (Exception e) {
			e.printStackTrace();
			
			return false;
		}
	}
	
	public boolean doTextinput(String elementName, String text) {
		logger.info("Writing Text " + text + " to element " + elementName);
		try {
			WebElement element = driver.findElementById(appPrefix + elementName);
			
			element.sendKeys(text);
			
			return true;
		} catch(NoSuchElementException e) {
			return false;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean elementExists(String elementName) {
		logger.info("Checking for existence of " + elementName);
		
		try {
			WebElement element = driver.findElementById(appPrefix + elementName);
			
			return true;
		} catch(NoSuchElementException e) {
			return false;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public void preInvoke(Method method, Object instance, Object... convertedArgs)
			throws InvocationTargetException, IllegalAccessException {
	}

	@Override
	public void postInvoke(Method method, Object instance, Object... convertedArgs)
			throws InvocationTargetException, IllegalAccessException {
	}

	@Override
	public void tearDown(boolean fail) {
		quitDriver();
	}
	
}

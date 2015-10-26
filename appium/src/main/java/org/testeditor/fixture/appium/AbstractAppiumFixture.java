package org.testeditor.fixture.appium;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.testeditor.fixture.core.interaction.Fixture;
import org.testeditor.fixture.core.interaction.StoppableFixture;

import io.appium.java_client.android.AndroidDriver;

/**
 * 
 * @author Daniel Gehn
 *
 */
public abstract class AbstractAppiumFixture implements StoppableFixture, Fixture {
	
	/**
	 * The android driver instance.
	 */
	private AndroidDriver driver;

	@Override
	public String getTestName() {
		return null;
	}

	@Override
	public void setTestName(String testName) {
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
	}
	
}

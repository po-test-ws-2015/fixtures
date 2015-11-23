package org.testeditor.fixture.appium;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AppiumFixtureTests {
	
	private AbstractAppiumFixture fixture;

	@Before
	public void setUp() throws Exception {
		fixture = new AppiumFixture();
		fixture.startApp();
	}

	@After
	public void tearDown() throws Exception {
		fixture.quitDriver();
	}

	@Test
	public void testDoClick() {
		fail("Not yet implemented");
	}

}

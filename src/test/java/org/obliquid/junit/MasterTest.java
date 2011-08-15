package org.obliquid.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Combines suites to run all tests.
 * 
 * @author stivlo
 * 
 */
@RunWith(Suite.class)
@SuiteClasses(value = { SuiteForDb.class, SuiteForNonDb.class })
public class MasterTest {
        //Run all tests
}

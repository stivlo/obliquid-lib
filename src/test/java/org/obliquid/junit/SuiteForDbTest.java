package org.obliquid.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import org.obliquid.db.TableIteratorBuilderShould;

/**
 * Test Suite for all tests that require a db obliquidlib and host configuration
 * 
 * @author stivlo
 */
@RunWith(Suite.class)
@SuiteClasses(value = { TableIteratorBuilderShould.class })
public class SuiteForDbTest {

    //magic happens with annotations I don't need a class body

}

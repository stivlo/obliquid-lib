package org.obliquid.helpers;

import org.obliquid.helpers.DateHelperShould;
import org.obliquid.helpers.SimpleStopWatchShould;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value = { DateHelperShould.class, SimpleStopWatchShould.class })
public class SuiteForAllTest {

    public SuiteForAllTest() {
        //run all JUnit Tests    
    }

}

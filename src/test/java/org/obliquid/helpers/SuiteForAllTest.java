package org.obliquid.helpers;

import org.obliquid.db.DbNullShould;
import org.obliquid.helpers.DateHelperShould;
import org.obliquid.helpers.SimpleStopWatchShould;
import org.obliquid.helpers.StringHelperShould;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value = { DateHelperShould.class, SimpleStopWatchShould.class, StringHelperShould.class,
        DbNullShould.class, BdHelperShould.class })
public class SuiteForAllTest {

    public SuiteForAllTest() {
        //run all JUnit Tests    
    }

}

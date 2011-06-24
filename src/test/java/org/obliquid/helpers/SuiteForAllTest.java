package org.obliquid.helpers;

import org.obliquid.datatype.BooleanTypeShould;
import org.obliquid.datatype.EmailAddressShould;
import org.obliquid.datatype.EmailListShould;
import org.obliquid.datatype.IpAddressShould;
import org.obliquid.datatype.IsoDateShould;
import org.obliquid.datatype.VatPercentShould;
import org.obliquid.db.DbNullShould;
import org.obliquid.ec2.Ec2TagShould;
import org.obliquid.helpers.DateHelperShould;
import org.obliquid.helpers.SimpleStopWatchShould;
import org.obliquid.helpers.StringHelperShould;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value = { DateHelperShould.class, SimpleStopWatchShould.class, StringHelperShould.class,
        DbNullShould.class, BdHelperShould.class, SqlHelperShould.class, BooleanTypeShould.class,
        EmailAddressShould.class, EmailListShould.class, IpAddressShould.class, IsoDateShould.class,
        VatPercentShould.class, Ec2TagShould.class })
public class SuiteForAllTest {

    public SuiteForAllTest() {
        //run all JUnit Tests    
    }

}

package org.obliquid.junit;

import org.obliquid.datatype.BooleanTypeShould;
import org.obliquid.datatype.CountryCodeShould;
import org.obliquid.datatype.EmailAddressShould;
import org.obliquid.datatype.EmailListShould;
import org.obliquid.datatype.IpAddressShould;
import org.obliquid.datatype.IsoDateShould;
import org.obliquid.datatype.ItalianPostCodeShould;
import org.obliquid.datatype.VatPercentShould;
import org.obliquid.datatype.companytaxid.ItalianCompanyTaxIdShould;
import org.obliquid.db.DbNullShould;
import org.obliquid.ec2.Ec2TagShould;
import org.obliquid.helpers.BdHelperShould;
import org.obliquid.helpers.DateHelperShould;
import org.obliquid.helpers.SqlHelperShould;
import org.obliquid.helpers.StringHelperShould;
import org.obliquid.util.StopWatchShould;
import org.obliquid.util.WgetShould;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test Suite for all tests that don't require a DB
 * 
 * @author stivlo
 */
@RunWith(Suite.class)
@SuiteClasses(value = { DateHelperShould.class, StopWatchShould.class, StringHelperShould.class,
        DbNullShould.class, BdHelperShould.class, SqlHelperShould.class, BooleanTypeShould.class,
        EmailAddressShould.class, EmailListShould.class, IpAddressShould.class, IsoDateShould.class,
        VatPercentShould.class, Ec2TagShould.class, WgetShould.class, CountryCodeShould.class,
        ItalianPostCodeShould.class, ItalianCompanyTaxIdShould.class })
public class SuiteForNonDb {

    public SuiteForNonDb() {
        //run all JUnit Tests    
    }

}

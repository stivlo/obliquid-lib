package org.obliquid.junit;

import org.obliquid.datatype.BooleanTypeImplShould;
import org.obliquid.datatype.CountryTypeImplShould;
import org.obliquid.datatype.EmailAddressImplShould;
import org.obliquid.datatype.EmailListImplShould;
import org.obliquid.datatype.IpAddressImplShould;
import org.obliquid.datatype.IsoDateImplShould;
import org.obliquid.datatype.ItalianPostCodeShould;
import org.obliquid.datatype.UsernameShould;
import org.obliquid.datatype.VatPercentShould;
import org.obliquid.datatype.companytaxid.ItalianCompanyTaxIdShould;
import org.obliquid.db.DbNullShould;
import org.obliquid.ec2.Ec2TagShould;
import org.obliquid.helpers.BdHelperShould;
import org.obliquid.helpers.DateHelperShould;
import org.obliquid.helpers.SqlHelperShould;
import org.obliquid.helpers.StringHelperShould;
import org.obliquid.util.MapCacheShould;
import org.obliquid.util.StopWatchShould;
import org.obliquid.util.WgetShould;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test Suite for all tests that don't require a DB.
 * 
 * @author stivlo
 */
@RunWith(Suite.class)
@SuiteClasses(value = { DateHelperShould.class, StopWatchShould.class,
                StringHelperShould.class, DbNullShould.class,
                BdHelperShould.class, SqlHelperShould.class,
                BooleanTypeImplShould.class, EmailAddressImplShould.class,
                EmailListImplShould.class, IpAddressImplShould.class,
                IsoDateImplShould.class, VatPercentShould.class,
                UsernameShould.class, Ec2TagShould.class, WgetShould.class,
                CountryTypeImplShould.class, ItalianPostCodeShould.class,
                ItalianCompanyTaxIdShould.class, MapCacheShould.class })
public class SuiteForNonDb {

        //run all junit test

}

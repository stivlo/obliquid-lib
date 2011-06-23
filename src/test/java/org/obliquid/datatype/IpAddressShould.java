package org.obliquid.datatype;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit test for class IpAddress
 * 
 * @author stivlo
 */
public class IpAddressShould {

    private static IpAddress ipAddress;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        ipAddress = new IpAddress();
    }

    @Test
    public void validateFailureTest() {
        String wrongIp = "321.12.1.2";
        Assert.assertFalse(ipAddress.isValid(wrongIp));
    }

    @Test
    public void validateSuccessTest() {
        String correctIp = "82.89.90.1";
        Assert.assertTrue(ipAddress.isValid(correctIp));
    }

}

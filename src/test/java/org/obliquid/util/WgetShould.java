package org.obliquid.util;

import static org.junit.Assert.*;

import java.util.concurrent.RejectedExecutionException;

import org.junit.Test;

public class WgetShould {

    @Test
    public void fetchAnUrl() {
        String result = Wget.fetchUrl("http://www.stefanolocati.it/favicon.ico");
        System.out.println("result.length=" + result.length());
        assertEquals(1406, result.length());
    }

    //consider simplify and use RejectedExecution for both?
    @Test(expected = IllegalStateException.class)
    public void throwAnExceptionWhenTheUrlIsNotValid() {
        Wget.fetchUrl("ehm");
    }

    @Test(expected = RejectedExecutionException.class)
    public void throwAnExceptionWhenTheFileWasntFound() {
        String res = Wget.fetchUrl("http://www.stefanolocati.it/missingPage");
        System.out.println("res = " + res);
    }

}

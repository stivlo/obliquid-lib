package org.obliquid.datatype;

import static org.junit.Assert.*;

import org.junit.Test;

public class BooleanTypeShould {

    /** Normally I follow Java Naming Conventions but this is much more readable in this case */
    @Test
    public void considerCharacter_N_Valid() {
        BooleanType myType = new BooleanType();
        assertTrue(myType.isValid('N'));
    }

    @Test
    public void considerCharacter_Y_Valid() {
        BooleanType myType = new BooleanType();
        assertTrue(myType.isValid('Y'));
    }

    @Test
    public void considerCharacter_X_Invalid() {
        BooleanType myType = new BooleanType();
        assertFalse(myType.isValid('X'));
    }

    @Test
    public void considerString_N_Valid() {
        BooleanType myType = new BooleanType();
        assertTrue(myType.isValid("N"));
    }

    @Test
    public void considerString_Y_Valid() {
        BooleanType myType = new BooleanType();
        assertTrue(myType.isValid("Y"));
    }

    @Test
    public void considerString_y_Invalid() {
        BooleanType myType = new BooleanType();
        assertFalse(myType.isValid("y"));
    }

    @Test
    public void getString_Y_asTrue() {
        BooleanType myType = new BooleanType();
        myType.set("Y");
        assertTrue(myType.getAsBoolean());
    }

    @Test
    public void getString_N_asFalse() {
        BooleanType myType = new BooleanType();
        myType.set("N");
        assertFalse(myType.getAsBoolean());
    }

    @Test
    public void getCharacter_Y_asTrue() {
        BooleanType myType = new BooleanType();
        myType.set('Y');
        assertTrue(myType.getAsBoolean());
    }

    @Test
    public void getCharacter_N_asFalse() {
        BooleanType myType = new BooleanType();
        myType.set('N');
        assertFalse(myType.getAsBoolean());
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwExceptionWhenCharacter_y_isSet() {
        BooleanType myType = new BooleanType();
        myType.set('y');
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwExceptionWhenString_n_isSet() {
        BooleanType myType = new BooleanType();
        myType.set("n");
    }

    @Test
    public void returnTrueWhenTrueWasSet() {
        BooleanType myType = new BooleanType();
        myType.set(true);
        assertTrue(myType.getAsBoolean());
    }

    @Test
    public void returnFalseWhenTrueWasSet() {
        BooleanType myType = new BooleanType();
        myType.set(false);
        assertFalse(myType.getAsBoolean());
    }

    @Test
    public void isValidForNullStringIsFalse() {
        BooleanType myType = new BooleanType();
        String nullString = null;
        assertFalse(myType.isValid(nullString));
    }

    @Test
    public void isValidForNullCharacterIsFalse() {
        BooleanType myType = new BooleanType();
        Character nullCharacter = null;
        assertFalse(myType.isValid(nullCharacter));
    }

}

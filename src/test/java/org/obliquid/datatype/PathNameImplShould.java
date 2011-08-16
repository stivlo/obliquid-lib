package org.obliquid.datatype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;
import org.obliquid.datatype.impl.PathNameImpl;

/**
 * Class under test: PathNameImpl.
 * 
 * @author stivlo
 * 
 */
public class PathNameImplShould {

        /**
         * Lower case and Upper case letters, numbers, hyphen, underscore and
         * dot are valid.
         */
        @Test
        public final void checkAValidPathName() {
                String pathName = "aADaa934-_.";
                PathName path = new PathNameImpl();
                assertTrue(path.isValid(pathName));
                assertTrue(path.isTheStringValid(pathName));
        }

        /**
         * A relative path with the Unix path separator is valid.
         */
        @Test
        public final void checkAPathWithSeparators() {
                String pathName = "a/b/c/adfadAA.";
                PathName path = new PathNameImpl();
                assertTrue(path.isValid(pathName));
                assertTrue(path.isTheStringValid(pathName));
        }

        /**
         * A relative path with two consecutive path separators isn't valid.
         */
        @Test
        public final void aPathWithTwoConsecutivePathSeparatorIsntValid() {
                String pathName = "a/b//c/adfadAA.";
                PathName path = new PathNameImpl();
                assertFalse(path.isValid(pathName));
                assertFalse(path.isTheStringValid(pathName));
        }

        /**
         * A null path isn't valid.
         */
        @Test
        public final void aNullPathIsntValid() {
                String pathName = null;
                PathName path = new PathNameImpl();
                assertFalse(path.isValid(pathName));
                assertFalse(path.isTheStringValid(pathName));
        }

        /**
         * Getting without setting throws exception.
         */
        @Test(expected = IllegalStateException.class)
        public final void gettingWithoutSettingThrowsException() {
                PathName path = new PathNameImpl();
                path.getData();
        }

        /**
         * Getting formatted data without setting throws exception.
         */
        @Test(expected = IllegalStateException.class)
        public final void formatDataWithoutSettingThrowsException() {
                PathName path = new PathNameImpl();
                path.formatData(Locale.getDefault());
        }

        /**
         * Setting and getting a path.
         */
        @Test
        public final void settingAndGettingAPath() {
                String pathStr = "a/valid/path";
                PathName path = new PathNameImpl();
                path.setData(pathStr);
                assertEquals(pathStr, path.getData());
                assertEquals(pathStr, path.formatData(Locale.getDefault()));
        }

        /**
         * setDataFromString and getting a path.
         */
        @Test
        public final void setDataFromStringAndGettingAPath() {
                String pathStr = "this/is/a/path.";
                PathName path = new PathNameImpl();
                path.setDataFromString(pathStr);
                assertEquals(pathStr, path.getData());
                assertEquals(pathStr, path.formatData(Locale.getDefault()));
        }

        /**
         * Setting null throws exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void settingNullThrowsException() {
                PathName path = new PathNameImpl();
                path.setData(null);
        }

        /**
         * setDataFromString(null) throws exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void setDataFromStringNullThrowsException() {
                PathName path = new PathNameImpl();
                path.setDataFromString(null);
        }

        /**
         * Setting an invalid path throws exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void setAnInvalidPath() {
                PathName path = new PathNameImpl();
                path.setData("//");
        }

}

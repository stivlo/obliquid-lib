package org.obliquid.db;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Class under test: MetaDbImpl.
 * 
 * @author stivlo
 * 
 */
@Ignore
public class MetaDbShould {

        /**
         * MetaDb instance.
         */
        private static MetaDb db;

        /**
         * Connect to the DB.
         * 
         * @throws Exception
         *                 in case the connection can't be established.
         */
        @BeforeClass
        public static void setUpBeforeClass() throws Exception {
                db = new MetaDbImpl();
                db.getConnection();
        }

        /**
         * Disconnect from the DB.
         */
        @AfterClass
        public static void tearDownClass() {
                db.releaseConnection();
        }

        /**
         * Execute a raw query and return the result set.
         * 
         * @throws SQLException
         *                 in case of problems
         */
        @Test
        public final void executeRawQueryShouldReturnResultSet() throws SQLException {
                final int expectedCityId = 327;
                ResultSet res = null;
                int id;
                boolean found = false;
                try {
                        res = db.executeRawQuery("SELECT city_id FROM address WHERE address_id=24");
                        while (res.next()) {
                                id = res.getInt("city_id");
                                found = (id == expectedCityId);
                        }
                } finally {
                        db.closeResultSetAndStatement();
                }
                assertTrue(found);
        }

        /**
         * Execute an invalid raw query that should throw exception.
         * 
         * @throws SQLException
         *                 expected
         */
        @Test(expected = SQLException.class)
        public final void executeInvalidRawQueryShouldThrowException() throws SQLException {
                try {
                        db.executeRawQuery("Invalid SQL");
                } finally {
                        db.closeResultSetAndStatement();
                }
        }

        /**
         * Execute an update query and get the affected row count.
         * 
         * @throws SQLException
         *                 in case of problems
         */
        @Test
        public final void executeShouldReturnRowCount() throws SQLException {
                db.autoCommitTransactions(false);
                int rowCount = db.execute("UPDATE address SET city_id=3 WHERE address_id=2");
                assertEquals(1, rowCount);
                db.rollbackTransaction();
        }

        //@Test
        //public final void retrieveBlob() throws SQLException {
        //ResultSet res = db.executeRawQuery(
        //"SELECT immagine FROM sl_annuncio WHERE id_annuncio=41695");
        //res.next(); //just let it fail, it's a test
        //byte[] immagine = SqlHelper.fetchBlobFromCurrentRowInResulSet(res, "immagine");
        //db.closeResultSetAndStatement();
        //assertEquals(42860, immagine.length);
        //}

        //@Test
        //public final void retrieveBlobSimpleWay() throws SQLException {
        //byte[] immagine = db.selectBlobField(
        //"SELECT immagine FROM sl_annuncio WHERE id_annuncio=41695");
        //assertEquals(42860, immagine.length);
        //}

}

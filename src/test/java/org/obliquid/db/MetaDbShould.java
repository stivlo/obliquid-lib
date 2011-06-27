package org.obliquid.db;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.obliquid.db.MetaDb;

public class MetaDbShould {

    private static MetaDb db;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        db = new MetaDb();
        db.getConnection();
    }

    @AfterClass
    public static void tearDownClass() {
        db.releaseConnection();
    }

    @Test
    public void executeRawQueryShouldReturnResultSet() throws SQLException {
        ResultSet res = null;
        int id;
        boolean found = false;
        try {
            res = db.executeRawQuery("SELECT city_id FROM address WHERE address_id=24");
            while (res.next()) {
                id = res.getInt("city_id");
                found = (id == 327);
            }
        } finally {
            db.closeResultSetAndStatement();
        }
        assertTrue(found);
    }

    @Test(expected = SQLException.class)
    public void executeInvalidRawQueryShouldThrowException() throws SQLException {
        try {
            db.executeRawQuery("Invalid SQL");
        } finally {
            db.closeResultSetAndStatement();
        }
    }

    @Test
    public void executeShouldReturnRowCount() throws SQLException {
        db.autoCommitTransactions(false);
        int rowCount = db.execute("UPDATE address SET city_id=3 WHERE address_id=2");
        assertEquals(1, rowCount);
        db.rollbackTransaction();
    }

    @Test
    public void retrieveBlob() throws SQLException {
        //        ResultSet res = db.executeRawQuery("SELECT immagine FROM sl_annuncio WHERE id_annuncio=41695");
        //        res.next(); //just let it fail, it's a test
        //        byte[] immagine = SqlHelper.fetchBlobFromCurrentRowInResulSet(res, "immagine");
        //        db.closeResultSetAndStatement();
        //        assertEquals(42860, immagine.length);
    }

    @Test
    public void retrieveBlobSimpleWay() throws SQLException {
        //        byte[] immagine = db.selectBlobField("SELECT immagine FROM sl_annuncio WHERE id_annuncio=41695");
        //        assertEquals(42860, immagine.length);
    }

}

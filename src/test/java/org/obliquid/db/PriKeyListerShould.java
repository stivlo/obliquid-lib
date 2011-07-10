package org.obliquid.db;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

public class PriKeyListerShould {

    @Test
    public void listActorIdForActorTable() throws SQLException {
        PriKeyLister lister = new PriKeyLister();
        lister.setDb(new MetaDbImpl());
        List<String> priKeys = lister.getPriKeys("actor");
        assertEquals(1, priKeys.size());
        lister.releaseConnection();
    }

    @Test
    public void listPriKeysForTableFilmActor() throws SQLException {
        List<String> priKeys = PriKeyLister.getPriKeysWithAutoDb("film_actor");
        assertEquals(2, priKeys.size());
    }

}

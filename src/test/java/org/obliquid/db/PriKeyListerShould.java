package org.obliquid.db;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

/**
 * Class under test: PriKeyLister.
 * 
 * @author stivlo
 * 
 */
public class PriKeyListerShould {

        /**
         * List actor Id for actor table.
         * 
         * @throws SQLException
         *                 in case of problems
         */
        @Test
        public final void listActorIdForActorTable() throws SQLException {
                PriKeyLister lister = new PriKeyLister();
                lister.setDb(new MetaDbImpl());
                List<String> priKeys = lister.getPriKeys("actor");
                assertEquals(1, priKeys.size());
                lister.releaseConnection();
        }

        /**
         * List Primary Keys for film_actor table.
         * 
         * @throws SQLException
         *                 in case of problems.
         */
        @Test
        public final void listPriKeysForTableFilmActor() throws SQLException {
                List<String> priKeys = PriKeyLister.getPriKeysWithAutoDb("film_actor");
                assertEquals(2, priKeys.size());
        }

}

package org.obliquid.sdb;

import java.net.ConnectException;

/**
 * Common interface for Persistence Entities.
 * 
 * @author stivlo
 * 
 */
public interface EntityInterface {

        /**
         * Save the entity.
         * 
         * @throws ConnectException
         *                 in case of problems.
         */
        void save() throws ConnectException;

        /**
         * Delete the entity.
         * 
         * @throws ConnectException
         *                 in case of problems.
         */
        void delete() throws ConnectException;

}

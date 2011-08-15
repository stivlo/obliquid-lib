package org.obliquid.db;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides a statement Iterator interface to SQL files. Read a SQL dump file
 * returning one SQL statement at a time. I suppose that there are no more than
 * one statement per line. A statement can be multi-line and must be terminated
 * by semicolon.
 * 
 * @author stivlo
 */
public class SqlFileReader implements Iterator<String> {

        /**
         * A data input stream.
         */
        private DataInputStream in = null;

        /**
         * A buffered reader.
         */
        private BufferedReader reader;

        /**
         * A SQL statement.
         */
        private String statement = null;

        /**
         * Constructor.
         * 
         * @param filename
         *                a file containing SQL statements.
         * @throws FileNotFoundException
         *                 when the file could not be found.
         */
        public SqlFileReader(final String filename) throws FileNotFoundException {
                FileInputStream fstream;
                fstream = new FileInputStream(filename);
                in = new DataInputStream(fstream);
                reader = new BufferedReader(new InputStreamReader(in));
        }

        @Override
        public final boolean hasNext() {
                String curLine = null;
                if (in == null) {
                        return false;
                }
                if (statement != null) {
                        return true;
                }
                try {
                        while ((curLine = reader.readLine()) != null) {
                                appendLine(curLine);
                                if (curLine.trim().endsWith(";")) {
                                        break;
                                }
                        }
                } catch (IOException ex) {
                        Logger.getLogger(SqlFileReader.class.getName()).log(Level.SEVERE, null, ex);
                        return false;
                }
                if (statement != null) {
                        statement = statement.trim();
                        if (statement.length() == 0) {
                                return false;
                        }
                        return true;
                }
                return false;
        }

        @Override
        public final String next() {
                String res;
                if (statement == null) {
                        hasNext();
                }
                res = statement;
                statement = null;
                return res;
        }

        @Override
        public final void remove() {
                throw new UnsupportedOperationException("Not supported yet.");
        }

        /**
         * Close the inputStream.
         */
        public final void close() {
                try {
                        if (in != null) {
                                in.close();
                        }
                } catch (IOException ex) {
                        Logger.getLogger(SqlFileReader.class.getName()).log(Level.SEVERE, null, ex);
                }
        }

        /**
         * Append a line to the statement.
         * 
         * @param curLine
         *                the line to be appended
         */
        private void appendLine(final String curLine) {
                if (statement == null) {
                        statement = curLine;
                } else {
                        statement += "\n" + curLine;
                }
        }

}

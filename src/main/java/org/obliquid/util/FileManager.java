package org.obliquid.util;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

/**
 * File management class: mkdir, rm, rmdir, touch, readFileToByteArray,
 * writeByteArrayToFile.
 * 
 * @author stivlo
 * 
 */
public final class FileManager {

        /**
         * Creates the directory named by the pathName provided. If the
         * directory already exists, doesn't throw exception, but if it exists a
         * file with the same name, it will throw exception.
         * 
         * @param pathName
         *                pathName to be created
         * @throws IOException
         *                 if the directory could not be created
         */
        public void mkdir(final String pathName) throws IOException {
                File dir = new File(pathName);
                mkdir(dir);
        }

        /**
         * Creates the directory named by the pathName provided. If the
         * directory already exists, doesn't throw exception.
         * 
         * @param path
         *                pathName to be created
         * @throws IOException
         *                 if the directory could not be created
         */
        public void mkdir(final File path) throws IOException {
                boolean success = false;
                if (path.isDirectory()) {
                        return;
                }
                try {
                        success = path.mkdir();
                } catch (SecurityException ex) {
                        throw new IOException(ex);
                }
                if (!success) {
                        throw new IOException("Could not create directory '" + path.getPath() + "'");
                }
        }

        /**
         * Removes the directory named by the pathName provided.
         * 
         * @param pathName
         *                pathName to be removed
         * @throws IOException
         *                 if the directory could not be removed (not empty or
         *                 permissions), if the directory doesn't exist, if it's
         *                 not a directory but a file.
         */
        public void rmdir(final String pathName) throws IOException {
                File path = new File(pathName);
                rmdir(path);
        }

        /**
         * Removes the directory named by the pathName provided.
         * 
         * @param path
         *                pathName to be removed
         * @throws IOException
         *                 if the directory could not be removed (not empty or
         *                 permissions), if the directory doesn't exist, if it's
         *                 not a directory but a file.
         */
        public void rmdir(final File path) throws IOException {
                if (!path.isDirectory()) {
                        throw new IOException("Path '" + path.getPath()
                                        + "' doesn't exist or is not a directory");
                }
                boolean success = path.delete();
                if (!success) {
                        throw new IOException("Could not delete directory '" + path.getPath() + "'");
                }
        }

        /**
         * Touch the file, when the file doesn't exist a new file will be
         * created, when the file exists, the file last modified timestamp will
         * be changed.
         * 
         * @param fileName
         *                the fileName to be touched
         * @throws IOException
         *                 if the permissions don't allow to create or modify
         *                 the file
         */
        public void touch(final String fileName) throws IOException {
                File myFile = new File(fileName);
                touch(myFile);
        }

        /**
         * Touch the file, when the file doesn't exist a new file will be
         * created, when the file exists, the file last modified timestamp will
         * be changed.
         * 
         * @param file
         *                the file to be touched
         * @throws IOException
         *                 if the permissions don't allow to create or modify
         *                 the file
         */
        public void touch(final File file) throws IOException {
                FileUtils.touch(file);
        }

        /**
         * Removes the file named by the fileName provided.
         * 
         * @param fileName
         *                fileName to be removed
         * @throws IOException
         *                 if the file could not be removed (permissions), if
         *                 the file doesn't exist, if it's not a file but a
         *                 directory.
         */
        public void rm(final String fileName) throws IOException {
                File file = new File(fileName);
                rm(file);
        }

        /**
         * Removes the file named by the fileName provided.
         * 
         * @param file
         *                file to be removed
         * @throws IOException
         *                 if the file could not be removed (permissions), if
         *                 the file doesn't exist, if it's not a file but a
         *                 directory.
         */
        public void rm(final File file) throws IOException {
                if (!file.isFile()) {
                        throw new IOException("File '" + file.getPath() + "' doesn't exist or is not a file");
                }
                boolean success = file.delete();
                if (!success) {
                        throw new IOException("Could not delete file '" + file.getPath() + "'");
                }
        }

        /**
         * Rename/Move source file to destination file.
         * 
         * @param source
         *                source file name
         * @param destination
         *                destination file name
         * @throws IOException
         *                 when the operation is unsuccessful
         */
        public void rename(final String source, final String destination) throws IOException {
                File sourceFile = new File(source);
                File destinationFile = new File(destination);
                rename(sourceFile, destinationFile);
        }

        /**
         * Rename/Move source file to destination file.
         * 
         * @param source
         *                source file
         * @param destination
         *                destination file
         * @throws IOException
         *                 when the operation is unsuccessful
         */
        public void rename(final File source, final File destination) throws IOException {
                if (!source.isFile()) {
                        throw new IOException("File '" + source.getPath()
                                        + "' doesn't exist or is not a file");
                }
                boolean success = source.renameTo(destination);
                if (!success) {
                        throw new IOException("Could not rename file '" + source.getPath() + "'");
                }
        }

        /**
         * Creates a file from a byte array.
         * 
         * @param fileName
         *                the file to be created
         * @param contents
         *                the byte-content to be written
         * @throws IOException
         *                 if the file can't be written
         */
        public void writeByteArrayToFile(final String fileName, final byte[] contents)
                        throws IOException {
                File file = new File(fileName);
                writeByteArrayToFile(file, contents);
        }

        /**
         * Creates a file from a byte array.
         * 
         * @param file
         *                the file to be created
         * @param contents
         *                the byte-content to be written
         * @throws IOException
         *                 if the file can't be written
         */
        public void writeByteArrayToFile(final File file, final byte[] contents) throws IOException {
                FileUtils.writeByteArrayToFile(file, contents);
        }

        /**
         * Reads a file into a byte array.
         * 
         * @param fileName
         *                the file to be read
         * @return the byte-contents for the file
         * @throws IOException
         *                 if the file can't be read
         */
        public byte[] readFileToByteArray(final String fileName) throws IOException {
                File file = new File(fileName);
                return readFileToByteArray(file);
        }

        /**
         * Reads a file into a byte array.
         * 
         * @param file
         *                the file to be read
         * @return the byte-contents for the file
         * @throws IOException
         *                 if the file can't be read
         */
        public byte[] readFileToByteArray(final File file) throws IOException {
                byte[] contents;
                contents = FileUtils.readFileToByteArray(file);
                return contents;
        }

}

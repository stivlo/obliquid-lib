package org.obliquid.helpers;

import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;

/**
 * Run system commands, provides an exec function that returns an exit value, 0 meaning success
 * 
 * @author stivlo
 * 
 */
public class ExecHelper {

    /**
     * Executes an external program
     * 
     * @param command
     *            a command with arguments all in a String
     * @return the exit value, 0 means success, -127 means ExecuteException, -128 means IOException,
     *         127 command not found. In general just test 0 for success.
     */
    public static int exec(final String command) {
        CommandLine cmdLine = CommandLine.parse(command);
        DefaultExecutor executor = new DefaultExecutor();
        int exitValue;
        try {
            exitValue = executor.execute(cmdLine);
        } catch (ExecuteException ex) {
            exitValue = -127;
        } catch (IOException ex) {
            exitValue = -128;
        }
        return exitValue;
    }

}

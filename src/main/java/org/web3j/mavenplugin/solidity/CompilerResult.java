package org.web3j.mavenplugin.solidity;

import java.io.File;

/**
 * Container for the compile result.
 *
 */
public class CompilerResult {
    public String errors;
    public String output;
    private boolean success = false;

    public CompilerResult(String errors, String output, boolean success) {
    	this(null, errors, output, success);
    }
    
    public CompilerResult(File dir, String errors, String output, boolean success) {
        this.errors = errors;
        // https://ethereum.stackexchange.com/questions/11912/unable-to-define-greetercontract-in-the-greeter-tutorial-breaking-change-in-sol
        this.output = output.replaceAll("<stdin>:", "");
        if (dir != null) {
        	this.output = this.output.replaceAll(dir.getAbsolutePath() + "/[a-zA-Z0-9/]*\\.sol:", "");
        	if (dir.isDirectory()) {
        		for (File f : dir.listFiles()) {
        			if (f.isDirectory()) {
        				this.output = this.output.replaceAll(f.getAbsolutePath() + "/", "");
        			}
        		}
        	}
        	this.output = this.output.replaceAll(dir + "/", "");
        }
        ;
        this.success = success;
    }

    public boolean isFailed() {
        return !success;
    }
}

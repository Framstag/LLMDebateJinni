package com.framstag.llmdebatejinni;

import com.framstag.llmdebatejinni.cli.CLIExecutor;
import picocli.CommandLine;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        int exitCode = new CommandLine(new CLIExecutor()).execute(args);
        System.exit(exitCode);
    }
}

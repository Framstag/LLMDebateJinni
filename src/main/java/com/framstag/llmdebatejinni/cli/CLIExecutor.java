package com.framstag.llmdebatejinni.cli;

import picocli.CommandLine;

@CommandLine.Command(name = "DiscaissionJinni",
        subcommands = {
                DiscussCmd.class,
                CommandLine.HelpCommand.class
        },
        mixinStandardHelpOptions = true)
public class CLIExecutor {
}

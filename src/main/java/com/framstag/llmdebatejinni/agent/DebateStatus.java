package com.framstag.llmdebatejinni.agent;

public enum DebateStatus {
    CONTINUE, // New, relevant arguments are still being made
    TOO_MUCH_REPETITION,
    TOO_BORING,
    TOO_MUCH_FACTS,
    STOP      // Arguments are repeating, everything important has been said
}
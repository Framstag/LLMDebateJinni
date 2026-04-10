package com.framstag.llmdebatejinni.service;

import com.framstag.llmdebatejinni.agent.*;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.model.chat.ChatModel;

public class DebateService {

    private final OpeningAAgent openingAAgent;
    private final RebuttalAAgent rebuttalAAgent;
    private final ClosingAAgent closingAAgent;

    private final OpeningBAgent openingBAgent;
    private final RebuttalBAgent rebuttalBAgent;
    private final ClosingBAgent closingBAgent;

    private final AudienceAgent audienceAgent;

    private final SummarizerAgent summarizerAgent;

    public DebateService(ChatModel model) {

        openingAAgent = AgenticServices
                .agentBuilder(OpeningAAgent.class)
                .chatModel(model)
                .listener(new StdOutAgentListener())
                .build();

        rebuttalAAgent = AgenticServices
                .agentBuilder(RebuttalAAgent.class)
                .chatModel(model)
                .listener(new StdOutAgentListener())
                .build();

        closingAAgent = AgenticServices
                .agentBuilder(ClosingAAgent.class)
                .chatModel(model)
                .listener(new StdOutAgentListener())
                .build();

        openingBAgent = AgenticServices
                .agentBuilder(OpeningBAgent.class)
                .chatModel(model)
                .listener(new StdOutAgentListener())
                .build();

        rebuttalBAgent = AgenticServices
                .agentBuilder(RebuttalBAgent.class)
                .chatModel(model)
                .listener(new StdOutAgentListener())
                .build();

        closingBAgent = AgenticServices
                .agentBuilder(ClosingBAgent.class)
                .chatModel(model)
                .listener(new StdOutAgentListener())
                .build();

        audienceAgent = AgenticServices
                .agentBuilder(AudienceAgent.class)
                .chatModel(model)
                .listener(new StdOutAgentListener())
                .build();

        summarizerAgent = AgenticServices
                .agentBuilder(SummarizerAgent.class)
                .chatModel(model)
                .listener(new StdOutAgentListener())
                .build();
    }

    public void startDebate(String topic,
                            String language,
                            int minBreakRound,
                            int maxRound) {
        System.out.print("TOPIC: ");
        System.out.println(topic)
        ;
        StringBuilder transcript = new StringBuilder();

        System.out.println();
        System.out.println("-- OPENING");

        // Openings
        String proOpening = openingAAgent.opening(topic, language, "PRO");
        transcript
                .append("Pro Opening:\n")
                .append(proOpening)
                .append("\n\n");
        System.out.println();
        System.out.println("PRO:");
        System.out.println(proOpening);

        String contraOpening = openingBAgent.opening(topic, language, "CONTRA");
        transcript
                .append("Contra Opening:\n")
                .append(contraOpening)
                .append("\n\n");
        System.out.println();
        System.out.println("CONTRA:");
        System.out.println(contraOpening);

        // Dynamic Rebuttals
        int round = 1;
        String lastStatement = contraOpening;

        while (round <= maxRound) {
            AudienceEvaluation audienceEvaluation = audienceAgent.evaluateDebate(topic, language, transcript.toString());

            System.out.println();
            System.out.print("AUDIENCE: ");
            System.out.print(audienceEvaluation.status().name());
            System.out.print(" - ");
            System.out.println(audienceEvaluation.reasoning());

            if (round >= minBreakRound &&
                    audienceEvaluation.status() == DebateStatus.STOP) {
                break;
            }

            System.out.println();
            System.out.print("-- REBUTTAL #");
            System.out.println(round);

            // Pro Rebuttal
            lastStatement = rebuttalAAgent.rebuttal(topic,
                    language,
                    "PRO",
                    lastStatement,
                    round,
                    audienceEvaluation.status(),
                    audienceEvaluation.reasoning());
            transcript
                    .append("Pro Rebuttal Round ")
                    .append(round).append(":\n")
                    .append(lastStatement)
                    .append("\n\n");

            System.out.println();
            System.out.println("PRO:");
            System.out.println(lastStatement);

            // Contra Rebuttal
            lastStatement = rebuttalBAgent.rebuttal(topic,
                    language,
                    "CONTRA",
                    lastStatement,
                    round,
                    audienceEvaluation.status(),
                    audienceEvaluation.reasoning());
            transcript
                    .append("Contra Rebuttal Round ")
                    .append(round).append(":\n")
                    .append(lastStatement)
                    .append("\n\n");

            System.out.println();
            System.out.println("CONTRA:");
            System.out.println(lastStatement);

            round++;
        }

        System.out.println();
        System.out.println("-- CLOSING");

        // Closings
        String proClosing = closingAAgent.closing(topic, language, "PRO", lastStatement);
        transcript
                .append("Pro Closing:\n")
                .append(proClosing)
                .append("\n\n");
        System.out.println();
        System.out.println("PRO:");
        System.out.println(proClosing);

        String contraClosing = closingBAgent.closing(topic, language, "CONTRA", proClosing);
        transcript
                .append("Contra Closing:\n")
                .append(contraClosing)
                .append("\n\n");
        System.out.println();
        System.out.println("CONTRA:");
        System.out.println(contraClosing);

        System.out.println();
        System.out.println("-- SUMMARY");

        //  Summarizer
        String summary = summarizerAgent.summarize(topic, language, transcript.toString());
        System.out.println(summary);
    }
}

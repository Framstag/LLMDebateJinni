package com.framstag.llmdebatejinni.agent;

import dev.langchain4j.model.output.structured.Description;

public record AudienceEvaluation(
        @Description("STOP if arguments repeat or the topic is exhausted, CONTINUE if fresh arguments are still being made")
        DebateStatus status,

        @Description("A 1-2 sentence explanation of why the debate should stop or continue")
        String reasoning
) {}
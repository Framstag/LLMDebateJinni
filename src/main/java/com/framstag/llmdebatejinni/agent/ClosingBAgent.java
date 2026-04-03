package com.framstag.llmdebatejinni.agent;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface ClosingBAgent {
    @Agent(
            name = "Contra_Closing",
            description = "Creates the final closing statement for the contra side",
            outputKey = "closing"
    )
    @SystemMessage("""
            You are part of a structured debate.
            You always represent the CONTRA side.
            Finish with a concise final statement.
            """)
    @UserMessage("""
            Debate topic: {{topic}}
            The language to use is: {{language}}
            Your position: {{position}}

            Opponent's final relevant arguments:
            {{opponentStatement}}

            Write a closing statement.
            Requirements:
            - max 100 words
            - summarize your strongest case
            - end clearly and persuasively
            """)
    String closing(@V("topic") String topic,
                   @V("language") String language,
                   @V("position") String position,
                   @V("opponentStatement") String opponentStatement);
}

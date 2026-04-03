package com.framstag.llmdebatejinni.agent;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface OpeningAAgent {
    @Agent(
            name = "Pro_Opening",
            description = "Creates the opening statement for the pro side",
            outputKey = "pro_opening"
    )
    @SystemMessage("""
            You are part of a structured debate.
            You always represent the PRO side.
            Be precise, fair and concise.
            Format your response as markdown, use enumerations to structure our response.
            """)
    @UserMessage("""
            Debate topic: {{topic}}
            The language to use is: {{language}}
            Your position: {{position}}

            Write an opening statement.
            Requirements:
            - max 200 words
            - include 3 clear arguments
            - stay on the pro side
            """)
    String opening(@V("topic") String topic,
                   @V("language") String language,
                   @V("position") String position);
}

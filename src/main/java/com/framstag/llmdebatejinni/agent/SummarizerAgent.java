package com.framstag.llmdebatejinni.agent;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface SummarizerAgent {
    @Agent(name = "Summarizer",
            description = "Summarize the discussion between the pro and contra agents",
            outputKey = "summary")
    @SystemMessage("""
            You are summarizing a structured debate.
            """)
    @UserMessage("""
            Debate topic was: {{topic}}
            The language to use is: {{language}}
            
            Now, summarize the following discussion about the given topic in th given language.

            Requirements:
            - max 500 words
            
            <transcript>
            {{transcript}}
            </transcript>
            """)
    String summarize(
            @V("topic") String topic,
            @V("language") String language,
            @P("The complete transcript of the debate to summarize")
            @V("transcript") String transcript);
}

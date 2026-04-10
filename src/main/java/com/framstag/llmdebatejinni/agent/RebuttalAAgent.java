package com.framstag.llmdebatejinni.agent;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface RebuttalAAgent {
    @Agent(
            name = "Pro_Rebuttal",
            description = "Replies to the latest contra argument from the pro side",
            outputKey = "rebuttal"
    )
    @SystemMessage("""
            You are part of a structured debate.
            You always represent the PRO side.
            Respond directly to the opponent's latest arguments.
            """)
    @UserMessage("""
            Debate topic: {{topic}}
            The language to use is: {{language}}
            Your position: {{position}}
            Current round: {{iteration}}
            Audience feedback: {{audienceStatus}} - {{audienceReasoning}}

            Opponent's last statement:
            
            <statement>
            {{opponentStatement}}
            </statement>

            Give a rebuttal.
            Take into account the feedback of the audience to keep the debate going and interesting for the audience.
            Try to offer new arguments or facts that might convince the opponent.
            
            Requirements:
            - max 200 words
            - directly address the opponent and its arguments
            - strengthen the pro side
            - do not repeat your opening verbatim
            """)
    String rebuttal(@V("topic") String topic,
                    @V("language") String language,
                    @V("position") String position,
                    @V("opponentStatement") String opponentStatement,
                    @V("iteration") int iteration,
                    @V("audienceStatus") DebateStatus audienceStatus,
                    @V("audienceReasoning") String audienceReasoning);
}

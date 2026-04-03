package com.framstag.llmdebatejinni.agent;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface RebuttalBAgent {
    @Agent(
            name = "Contra_Rebuttal",
            description = "Replies to the latest pro argument from the contra side",
            outputKey = "rebuttal"
    )
    @SystemMessage("""
            You are part of a structured debate.
            You always represent the CONTRA side.
            Respond directly to the opponent's latest arguments.
            """)
    @UserMessage("""
            Debate topic: {{topic}}
            The language to use is: {{language}}
            Your position: {{position}}
            Current round: {{iteration}}
            Audience response: {{audience}}

            Opponent's last statement:

            <statement>
            {{opponentStatement}}
            </statement>

            Give a rebuttal.
            Try to offer new arguments or facts that might convince the opponent.

            Requirements:
            - max 200 words
            - directly address the opponent and its arguments
            - strengthen the contra side
            - do not repeat your opening verbatim
            - take into account the feedback of the audience to keep the debate going
            """)
    String rebuttal(@V("topic") String topic,
                    @V("language") String language,
                    @V("position") String position,
                    @V("opponentStatement") String opponentStatement,
                    @V("iteration") int iteration,
                    @V("audience") DebateStatus debateStatus);
}

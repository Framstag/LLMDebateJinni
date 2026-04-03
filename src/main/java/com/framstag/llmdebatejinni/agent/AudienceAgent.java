package com.framstag.llmdebatejinni.agent;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface AudienceAgent {
    @Agent(
            name = "Audience",
            description = "Evaluates, if the debate still creates new arguments",
            outputKey = "evaluation"
    )
    @SystemMessage("""
        You are a neutral spectator and evaluator of a debate.
        """)
    @UserMessage("""
        Debate topic: {{topic}}
        The language to use in your response is: {{language}}

        Read the current transcript of the debate.
        Your task is to evaluate the debate based on several criteria:

        - Return 'TOO_MUCH_REPETITION' if you feel that mentioned arguments are being repeated too often.
        - Return 'TOO_BORING' as status if the debate is becoming too boring for you.
        - Return 'TOO_MUCH_FACTS' as status if the number of facts tends to become too large to be meaningfully considered in the discussion.
        - Return 'STOP' if you have the impression that all arguments have been exchanged.
        - Return 'STOP' if the mentioned facts eventually became too many to be evaluated summarily in the debate.
       
       Otherwise, return 'CONTINUE' to signal that the debate should continue.

        <transcript>
        {{transcript}}
        </transcript>
       """)
    AudienceEvaluation evaluateDebate(@V("topic") String topic,
                                      @V("language") String language,
                                      @V("transcript") String transcript);
}
package com.framstag.llmdebatejinni.service;

import dev.langchain4j.agentic.observability.*;
import dev.langchain4j.agentic.scope.AgenticScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StdOutAgentListener implements AgentListener {
    private static final Logger logger = LoggerFactory.getLogger(StdOutAgentListener.class);

    @Override
    public void beforeAgentInvocation(AgentRequest agentRequest) {
        logger.debug("AGENT > {}: {}",
                agentRequest.agentId(),
                agentRequest.inputs());
    }

    @Override
    public void afterAgentInvocation(AgentResponse agentResponse) {
        logger.info("AGENT < {}: {}",
                agentResponse.agentId(),
                agentResponse.output());
    }

    @Override
    public void onAgentInvocationError(AgentInvocationError agentInvocationError) {
        logger.error("ERROR: {}: {}",
                agentInvocationError.agentId(),
                agentInvocationError.error().toString());
    }

    @Override
    public void afterAgenticScopeCreated(AgenticScope agenticScope) {
        logger.debug("SCOPE > {}", agenticScope.toString());
    }

    @Override
    public void beforeAgenticScopeDestroyed(AgenticScope agenticScope) {
        logger.debug("SCOPE < {}", agenticScope.toString());
    }

    @Override
    public void beforeAgentToolExecution(BeforeAgentToolExecution beforeAgentToolExecution) {
        logger.info("TOOL > {}: {}",
                beforeAgentToolExecution.agentInstance(),
                beforeAgentToolExecution.toolExecution().toString());

    }

    @Override
    public void afterAgentToolExecution(AfterAgentToolExecution afterAgentToolExecution) {
        logger.info("TOOL < {}: {}",
                afterAgentToolExecution.agentInstance(),
                afterAgentToolExecution.toolExecution().toString());
    }
}

package com.framstag.llmdebatejinni.cli;

import com.framstag.llmdebatejinni.service.DebateService;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.ollama.OllamaChatModel;
import picocli.CommandLine;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "discuss", description = "Discuss a topic")
public class DiscussCmd implements Callable<Integer> {
    @CommandLine.Option(names={"-u","--modelUrl"}, required = true, arity = "1", description = "The URL of the ollama server")
    URL modelUrl;

    @CommandLine.Option(names={"-m","--model"}, required = true, arity = "1", description = "The name of the model to use")
    String modelName;

    @CommandLine.Option(names={"-k","--apiKey"}, arity = "0..1", description = "Optional api key")
    String apiKey;

    @CommandLine.Option(names={"-l","--language"}, arity = "0..1", description = "Language to use")
    String language = "english";

    @CommandLine.Option(names={"--minBreakRound"}, arity = "0..1", description = "Minimum rebuttal round, where audience may stop rebuttal")
    int minBreakRound = 4;

    @CommandLine.Option(names={"--maxRound"}, arity = "0..1", description = "Maximum number of rebuttal rounds")
    int maxRound = 10;

    @CommandLine.Option(names={"--logging"}, arity = "0..1", description = "Activate low level logging")
    boolean logging = false;

    @CommandLine.Option(names={"--timeout"}, arity = "0..1", description = "Model calling timeout in seconds")
    int timeout = 60;

    @CommandLine.Parameters(index = "0",description = "Topic to be discussed")
    String topic;

    @Override
    public Integer call() {
        HashMap<String,String> headers = new HashMap<>();

        if (apiKey != null && !apiKey.isBlank()) {
            headers.put("Authorization", "Bearer "+apiKey);
        }

        ChatModel model = OllamaChatModel.builder()
                .baseUrl(modelUrl.toString())
                .customHeaders(headers)
                .modelName(modelName)
                .responseFormat(ResponseFormat.JSON)
                .logRequests(logging)
                .logResponses(logging)
                .timeout(Duration.ofSeconds(timeout))
                .build();

        DebateService service = new DebateService(model);

        service.startDebate(topic,
                language,
                minBreakRound,
                maxRound);

        return 0;
    }
}

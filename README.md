# README

## About

LLMDebateJinni is one of my experiments with AI and agents.

The idea is, to passa debate topic to the application, the application then simulates two people debating the topic in front of an audience until arguments and facts are exchanged. After this a summary is created. 

Initially using a planning/strategizing agent to coordinate the debate, most of the debate coordination is now coded, only delegating the individual responses to a number of agents.

## Used agents

Used agents

Person A, argumenting PRO the topic:

* OpeningAAgent
* RebuttalAAgent
* ClosingAAgent

Person B, argumenting CONTRA the topic:

* OpeningBAgent
* RebuttalBAgent
* ClosingBAgent

Audience, judging the current status of the debate:

* AudienceAgent

Agent to summarize the debate:

* SummarizerAgent

## Used technology

* Java 25 or higher
* LangChain4J
* PicoCLI
* Logback

## Supported and recommended LLMs

* We currently support connecting to local or remote Ollama istances. Further APIs could be supported on demand. 
* We suggest using gpt-oss:20b or gpt-oss:120b, but other LLMs should work as well.
* The quality of the discussion might be a good indicator for certain qualities of the used LLM.

## Build

* Java 25 or higher
* Maven 3
* Call `mvn package`



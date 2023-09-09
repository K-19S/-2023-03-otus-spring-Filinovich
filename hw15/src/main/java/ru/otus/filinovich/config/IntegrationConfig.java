package ru.otus.filinovich.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import ru.otus.filinovich.domain.Butterfly;
import ru.otus.filinovich.domain.ButterflyChrysalis;
import ru.otus.filinovich.domain.Caterpillar;
import ru.otus.filinovich.service.EvolutionService;

@Configuration
public class IntegrationConfig {

    @Bean
    public MessageChannelSpec<?, ?> eggChannel() {
        return MessageChannels.queue(30);
    }

    @Bean
    public MessageChannelSpec<?, ?> butterflyChannel() {
        return MessageChannels.queue(30);
    }

    @Bean
    public IntegrationFlow butterflyLifecycleFlow(EvolutionService evolutionService) {
        return f -> f
                .handle(evolutionService, "turnEggIntoCaterpillar")
                .<Caterpillar, ButterflyChrysalis>transform(ButterflyChrysalis::new)
                .<ButterflyChrysalis, Boolean>route(
                    ButterflyChrysalis::speciesIsKnown,
                        mapping -> mapping
                                .subFlowMapping(true, sf -> sf.channel("butterflyChannel"))
                                .subFlowMapping(false, sf -> sf.channel("butterflyErrorFlow.input"))
                );
    }

    @Bean
    public IntegrationFlow butterflyFinalFlow(EvolutionService evolutionService) {
        return IntegrationFlow.from(butterflyChannel())
                .handle(evolutionService, "turnChrysalisIntoButterfly")
                .get();
    }

    @Bean
    public IntegrationFlow butterflyErrorFlow() {
        return f -> f
                .<ButterflyChrysalis>log(chrysalis ->
                        "Chrysalis with id " + chrysalis.getPayload().getId() + " did not survive")
                .<ButterflyChrysalis, Butterfly>transform(chrysalis -> new Butterfly());
    }
}

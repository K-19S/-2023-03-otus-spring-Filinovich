package ru.otus.filinovich.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.filinovich.domain.Butterfly;
import ru.otus.filinovich.domain.ButterflyEgg;

@MessagingGateway
public interface ButterflyGrowerGateway {

    @Gateway(requestChannel = "butterflyLifecycleFlow.input")
    Butterfly process(ButterflyEgg eggs);
}

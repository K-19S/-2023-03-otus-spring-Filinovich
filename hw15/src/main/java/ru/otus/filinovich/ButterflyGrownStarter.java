package ru.otus.filinovich;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.otus.filinovich.service.ButterflyGrowerService;

@Component
@RequiredArgsConstructor
public class ButterflyGrownStarter {

    private final ButterflyGrowerService butterflyGrowerService;

    @EventListener(ApplicationReadyEvent.class)
    public void startGrow() {
        butterflyGrowerService.growButterflies();
    }
}

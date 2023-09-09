package ru.otus.filinovich.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.filinovich.domain.Butterfly;
import ru.otus.filinovich.domain.Caterpillar;
import ru.otus.filinovich.domain.ButterflyChrysalis;
import ru.otus.filinovich.domain.ButterflyEgg;

@Slf4j
@Service
public class EvolutionServiceImpl implements EvolutionService {

    @Override
    public Caterpillar turnEggIntoCaterpillar(ButterflyEgg egg) {
        return new Caterpillar(egg);
    }

    @Override
    public Butterfly turnChrysalisIntoButterfly(ButterflyChrysalis chrysalis) {
        Butterfly newButterfly = new Butterfly(chrysalis);
        log.info("New butterfly with species " + newButterfly.getSpecies());
        return newButterfly;
    }

}

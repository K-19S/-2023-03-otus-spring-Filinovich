package ru.otus.filinovich.service;

import ru.otus.filinovich.domain.Butterfly;
import ru.otus.filinovich.domain.ButterflyChrysalis;
import ru.otus.filinovich.domain.ButterflyEgg;
import ru.otus.filinovich.domain.Caterpillar;

public interface EvolutionService {

    Caterpillar turnEggIntoCaterpillar(ButterflyEgg egg);

    Butterfly turnChrysalisIntoButterfly(ButterflyChrysalis chrysalis);
}

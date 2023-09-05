package ru.otus.filinovich.domain;

import lombok.Getter;

@Getter
public class Caterpillar {

    private Integer id;

    private ButterflySpecies species;

    public Caterpillar(ButterflyEgg egg) {
        this.id = egg.getId();
        this.species = egg.getSpecies();
    }
}

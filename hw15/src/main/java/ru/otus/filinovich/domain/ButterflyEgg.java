package ru.otus.filinovich.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ButterflyEgg {

    private Integer id;

    private ButterflySpecies species;

    public ButterflyEgg(Integer id, ButterflySpecies species) {
        this.id = id;
        this.species = species;
    }
}

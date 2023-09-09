package ru.otus.filinovich.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ButterflyChrysalis {

    private Integer id;

    private ButterflySpecies species;

    public ButterflyChrysalis(Caterpillar caterpillar) {
        this.id = caterpillar.getId();
        this.species = caterpillar.getSpecies();
    }

    public boolean speciesIsKnown() {
        return species != ButterflySpecies.UNKNOWN;
    }
}

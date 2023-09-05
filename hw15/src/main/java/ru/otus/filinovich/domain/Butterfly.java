package ru.otus.filinovich.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Butterfly {

    private Integer id;

    private ButterflySpecies species;

    public Butterfly(ButterflyChrysalis chrysalis) {
        this.id = chrysalis.getId();
        this.species = chrysalis.getSpecies();
    }
}

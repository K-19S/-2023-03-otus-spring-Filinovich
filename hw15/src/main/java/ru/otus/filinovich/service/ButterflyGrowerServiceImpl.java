package ru.otus.filinovich.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.filinovich.domain.Butterfly;
import ru.otus.filinovich.domain.ButterflyEgg;
import ru.otus.filinovich.domain.ButterflySpecies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

@Slf4j
@Service
@RequiredArgsConstructor
public class ButterflyGrowerServiceImpl implements ButterflyGrowerService {

    private final ButterflyGrowerGateway butterflyGrower;

    private Integer count = 1;

    public void growButterflies() {
        List<Butterfly> butterflies = new ArrayList<>();
        ForkJoinPool pool = ForkJoinPool.commonPool();
        List<ButterflySpecies> species = Arrays.stream(ButterflySpecies.values()).toList();
        Random random = new Random();
        for (int i = 1; i <= 30; ++i) {
            pool.execute(() -> {
                ButterflySpecies butterflySpecies = species.get(random.nextInt(species.size()));
                ButterflyEgg egg = new ButterflyEgg(count++, butterflySpecies);
                log.info("Created egg with id: " + egg.getId() + " and species " + egg.getSpecies());
                Butterfly butterfly = butterflyGrower.process(egg);
                if (butterfly.getId() != null) {
                    butterflies.add(butterfly);
                    log.info("Grown up butterfly with id: " + butterfly.getId() +
                            " and species " + butterfly.getSpecies());
                }
            });
        }
    }
}

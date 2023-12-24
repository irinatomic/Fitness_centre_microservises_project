package raf.fitness.reservation_servis.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import raf.fitness.reservation_servis.domain.TrainingType;
import raf.fitness.reservation_servis.repository.*;

@Profile({"default"})
@Component
public class TestDataRunner implements CommandLineRunner {

    private TrainingTypeRepository trainingTypeRepository;

    public TestDataRunner(TrainingTypeRepository trainingTypeRepository){
        this.trainingTypeRepository = trainingTypeRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // define training types
        TrainingType tt1 = new TrainingType("INDIVIDUAL");
        TrainingType tt2 = new TrainingType("GROUP");

        trainingTypeRepository.save(tt1);
        trainingTypeRepository.save(tt2);
    }
}

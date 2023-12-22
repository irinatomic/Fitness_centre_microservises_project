package raf.fitness.reservation_servis.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import raf.fitness.reservation_servis.domain.TrainingType;

@Profile({"default"})
@Component
public class TestDataRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

        // define training types
        TrainingType tt1 = new TrainingType("Individual");
        TrainingType tt2 = new TrainingType("Group");
    }
}

package raf.fitness.reservation_servis.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import raf.fitness.reservation_servis.domain.*;
import raf.fitness.reservation_servis.repository.*;
import raf.fitness.reservation_servis.service.TimeSlotService;

import java.time.LocalDate;
import java.time.LocalTime;

@Profile({"default"})
@Component
public class TestDataRunner implements CommandLineRunner {

    private TrainingSessionRepository trainingSessionRepository;
    private TrainingTypeRepository trainingTypeRepository;
    private SignedUpRepository signedUpRepository;
    private TrainingRepository trainingRepository;
    private GymRepository gymRepository;
    private TimeSlotService timeSlotService;

    public TestDataRunner(TrainingSessionRepository trainingSessionRepository, TrainingTypeRepository trainingTypeRepository, SignedUpRepository signedUpRepository, TrainingRepository trainingRepository, GymRepository gymRepository, TimeSlotService timeSlotService) {
        this.trainingSessionRepository = trainingSessionRepository;
        this.trainingTypeRepository = trainingTypeRepository;
        this.signedUpRepository = signedUpRepository;
        this.trainingRepository = trainingRepository;
        this.gymRepository = gymRepository;
        this.timeSlotService = timeSlotService;
    }

    @Override
    public void run(String... args) throws Exception {

        // define training types
        TrainingType tt1 = new TrainingType("INDIVIDUAL");
        TrainingType tt2 = new TrainingType("GROUP");
        trainingTypeRepository.save(tt1);
        trainingTypeRepository.save(tt2);

        // Create gyms
        Gym gym1 = createGym1();
        Gym gym2 = createGym2();
        gymRepository.save(gym1);
        gymRepository.save(gym2);

        // Initiate time slots for gyms
        timeSlotService.createTimeSlotsForGym(1L);
        timeSlotService.createTimeSlotsForGym(2L);

        // Create trainings for gym1
        Training powerliftingGym1 = createPowerliftingGym1(gym1, tt1);
        Training yogaGym1 = createYogaGym1(gym1, tt2);
        Training pilatesGym1 = createPilatesGym1(gym1, tt2);
        trainingRepository.save(powerliftingGym1);
        trainingRepository.save(yogaGym1);
        trainingRepository.save(pilatesGym1);

        // Create trainings for gym 2
        Training powerliftingGym2 = createPowerliftingGym2(gym2, tt1);
        Training calisthenicsGym2 = createCalisthenicsGym2(gym2, tt1);
        Training yogaGym2 = createYogaGym2(gym2, tt2);
        trainingRepository.save(powerliftingGym2);
        trainingRepository.save(calisthenicsGym2);
        trainingRepository.save(yogaGym2);

        // Create a test training session
        TrainingSession ts1 = createTrainingSession1(gym1, yogaGym1);
        trainingSessionRepository.save(ts1);

        // Create a signed up for the test training session
        SignedUp su1_ts1 = createSignedUpForTS1(ts1);
        signedUpRepository.save(su1_ts1);
    }

    private Gym createGym1() {
        String name = "Gym_one";
        String description = "Gym_one description";
        Integer coachesCount = 10;
        Long managerId = 1L; // in java we should write long values like this
        Integer freeSessionNo = 10;
        LocalTime openingTime = LocalTime.of(6, 0);
        LocalTime closingTime = LocalTime.of(22, 0);
        return new Gym(name, description, coachesCount, managerId, freeSessionNo, openingTime, closingTime);
    }

    private Gym createGym2() {
        String name = "Gym_two";
        String description = "Gym_two description";
        Integer coachesCount = 12;
        Long managerId = 2L;
        Integer freeSessionNo = 9;
        LocalTime openingTime = LocalTime.of(8, 0);
        LocalTime closingTime = LocalTime.of(21, 0);
        return new Gym(name, description, coachesCount, managerId, freeSessionNo, openingTime, closingTime);
    }

    private Training createPowerliftingGym1(Gym gym1, TrainingType individual) {
        String name = "Powerlifting";
        Integer price = 700;
        Integer duration = 60;
        Integer capacity = 1;
        Integer minPeopleNo = 1;
        return new Training(name, price, duration, capacity, minPeopleNo, gym1, individual);
    }

    private Training createYogaGym1(Gym gym1, TrainingType group) {
        String name = "Yoga";
        Integer price = 300;
        Integer duration = 60;
        Integer capacity = 10;
        Integer minPeopleNo = 4;
        return new Training(name, price, duration, capacity, minPeopleNo, gym1, group);
    }

    private Training createPilatesGym1(Gym gym1, TrainingType group) {
        String name = "Pilates";
        Integer price = 450;
        Integer duration = 45;
        Integer capacity = 10;
        Integer minPeopleNo = 4;
        return new Training(name, price, duration, capacity, minPeopleNo, gym1, group);
    }

    private Training createPowerliftingGym2(Gym gym2, TrainingType individual) {
        String name = "Powerlifting";
        Integer price = 900;
        Integer duration = 60;
        Integer capacity = 1;
        Integer minPeopleNo = 1;
        return new Training(name, price, duration, capacity, minPeopleNo, gym2, individual);
    }

    private Training createCalisthenicsGym2(Gym gym2, TrainingType individual) {
        String name = "Calisthenics";
        Integer price = 800;
        Integer duration = 45;
        Integer capacity = 1;
        Integer minPeopleNo = 1;
        return new Training(name, price, duration, capacity, minPeopleNo, gym2, individual);
    }

    private Training createYogaGym2(Gym gym2, TrainingType group) {
        String name = "Yoga";
        Integer price = 450;
        Integer duration = 60;
        Integer capacity = 10;
        Integer minPeopleNo = 6;
        return new Training(name, price, duration, capacity, minPeopleNo, gym2, group);
    }

    private TrainingSession createTrainingSession1(Gym gym1, Training yogaGym1){
        Long creatorId = 1L;            // clientId
        Integer signedUpCount = 1;
        String trainingTypeName = yogaGym1.getTrainingType().getName();
        LocalDate date = LocalDate.now().plusDays(3);
        LocalTime startTime = LocalTime.of(16, 0);
        return new TrainingSession(creatorId, signedUpCount, trainingTypeName, date, startTime, yogaGym1, gym1);
    }

    private SignedUp createSignedUpForTS1(TrainingSession ts1) {
        Long clientId = 1L;
        String firstName = "CName_one";
        String lastName = "CSurname_one";
        String email = "client_one@email.com";
        return new SignedUp(clientId, firstName, lastName, email, ts1);
    }
}

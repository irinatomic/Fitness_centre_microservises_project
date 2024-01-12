package raf.fitness.reservation_servis.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import raf.fitness.reservation_servis.domain.*;
import raf.fitness.reservation_servis.repository.*;
import raf.fitness.reservation_servis.service.TimeSlotService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Profile({"default"})
@Component
public class TestDataRunner implements CommandLineRunner {

    private TrainingSessionRepository trainingSessionRepository;
    private TrainingTypeRepository trainingTypeRepository;
    private SignedUpRepository signedUpRepository;
    private TrainingRepository trainingRepository;
    private TimeSlotRepository timeSlotRepository;
    private GymRepository gymRepository;
    private TimeSlotService timeSlotService;

    public TestDataRunner(TrainingSessionRepository trainingSessionRepository, TrainingTypeRepository trainingTypeRepository,
                          SignedUpRepository signedUpRepository, TrainingRepository trainingRepository,
                          TimeSlotRepository timeSlotRepository, GymRepository gymRepository,
                          TimeSlotService timeSlotService) {
        this.trainingSessionRepository = trainingSessionRepository;
        this.trainingTypeRepository = trainingTypeRepository;
        this.signedUpRepository = signedUpRepository;
        this.trainingRepository = trainingRepository;
        this.timeSlotRepository = timeSlotRepository;
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

        // TRAINING SESSION (gym1, group)
        TrainingSession ts1 = createTrainingSessionYoga(1L, gym1, yogaGym1, 1L);
        trainingSessionRepository.save(ts1);
        SignedUp su1_ts1 = createSU1(ts1);
        SignedUp su2_ts1 = createSU2(ts1);
        signedUpRepository.save(su1_ts1);
        signedUpRepository.save(su2_ts1);

        // TRAINING SESSION 2 (gym1, individual)
        TrainingSession ts2 = createTrainingSession2(gym1, powerliftingGym1);
        trainingSessionRepository.save(ts2);
        SignedUp su1_ts2 = createSU1(ts2);
        signedUpRepository.save(su1_ts2);

        // TRAINING SESSION 3 (gym2, group)
        TrainingSession ts3 = createTrainingSessionYoga(2L, gym2, yogaGym2, 3L);
        trainingSessionRepository.save(ts3);
        SignedUp su3_ts3 = createSU3(ts3);
        SignedUp su4_ts3 = createSU4(ts3);
        signedUpRepository.save(su3_ts3);
        signedUpRepository.save(su4_ts3);
    }

    private Gym createGym1() {
        String name = "Gym_one";
        String description = "This is a gym perfect for powerlifting, yoga and pilates.";
        Integer coachesCount = 10;
        Long managerId = 1L;            // long
        Integer freeSessionNo = 1;
        LocalTime openingTime = LocalTime.of(6, 0);
        LocalTime closingTime = LocalTime.of(22, 0);
        return new Gym(name, description, coachesCount, managerId, freeSessionNo, openingTime, closingTime);
    }

    private Gym createGym2() {
        String name = "Gym_two";
        String description = "This is a gym perfect for powerlifting, calisthenics and yoga.";
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

    private TrainingSession createTrainingSessionYoga(Long gymdId, Gym gym, Training yoga, Long creatorIdd){
        Long creatorId = creatorIdd;            // clientId
        Integer signedUpCount = 2;
        String trainingTypeName = yoga.getTrainingType().getName();
        LocalDate date = LocalDate.now().plusDays(2);
        LocalTime startTime = LocalTime.of(16, 0);
        TrainingSession ts = new TrainingSession(creatorId, signedUpCount, trainingTypeName, date, startTime, yoga, gym);

        // reserve time slots (yoga is 1h -> 4 time slots)
        TimeSlot ts1 = timeSlotRepository.findByGymIdAndDateAndStartTime(gymdId, date,startTime).get();
        TimeSlot ts2 = timeSlotRepository.findByGymIdAndDateAndStartTime(gymdId, date,startTime.plusMinutes(15)).get();
        TimeSlot ts3 = timeSlotRepository.findByGymIdAndDateAndStartTime(gymdId, date,startTime.plusMinutes(30)).get();
        TimeSlot ts4 = timeSlotRepository.findByGymIdAndDateAndStartTime(gymdId, date,startTime.plusMinutes(45)).get();
        ts1.setReserved(true);
        ts2.setReserved(true);
        ts3.setReserved(true);
        ts4.setReserved(true);
        timeSlotRepository.save(ts1);
        timeSlotRepository.save(ts2);
        timeSlotRepository.save(ts3);
        timeSlotRepository.save(ts4);
        return ts;
    }

    private TrainingSession createTrainingSession2(Gym gym1, Training powerliftingGym1){
        Long creatorId = 1L;            // clientId
        Integer signedUpCount = 1;
        String trainingTypeName = powerliftingGym1.getTrainingType().getName();
        LocalDate date = LocalDate.now().plusDays(2);
        LocalTime startTime = LocalTime.of(12, 0);
        TrainingSession ts = new TrainingSession(creatorId, signedUpCount, trainingTypeName, date, startTime, powerliftingGym1, gym1);

        // reserve time slots (powerlifting is 1h -> 4 time slots)
        TimeSlot ts1 = timeSlotRepository.findByGymIdAndDateAndStartTime(1L, date,startTime).get();
        TimeSlot ts2 = timeSlotRepository.findByGymIdAndDateAndStartTime(1L, date,startTime.plusMinutes(15)).get();
        TimeSlot ts3 = timeSlotRepository.findByGymIdAndDateAndStartTime(1L, date,startTime.plusMinutes(30)).get();
        TimeSlot ts4 = timeSlotRepository.findByGymIdAndDateAndStartTime(1L, date,startTime.plusMinutes(45)).get();
        ts1.setReserved(true);
        ts2.setReserved(true);
        ts3.setReserved(true);
        ts4.setReserved(true);
        timeSlotRepository.save(ts1);
        timeSlotRepository.save(ts2);
        timeSlotRepository.save(ts3);
        timeSlotRepository.save(ts4);
        return ts;
    }

    private SignedUp createSU1(TrainingSession ts) {
        Long clientId = 1L;
        String firstName = "CName_one";
        String lastName = "CSurname_one";
        String email = "client_one@email.com";
        return new SignedUp(clientId, firstName, lastName, email, ts);
    }

    private SignedUp createSU2(TrainingSession ts) {
        Long clientId = 2L;
        String firstName = "CName_two";
        String lastName = "CSurname_two";
        String email = "client_two@email.com";
        return new SignedUp(clientId, firstName, lastName, email, ts);
    }

    private SignedUp createSU3(TrainingSession ts) {
        Long clientId = 3L;
        String firstName = "CName_three";
        String lastName = "CSurname_three";
        String email = "client_three@email.com";
        return new SignedUp(clientId, firstName, lastName, email, ts);
    }

    private SignedUp createSU4(TrainingSession ts) {
        Long clientId = 4L;
        String firstName = "CName_four";
        String lastName = "CSurname_four";
        String email = "client_four@email.com";
        return new SignedUp(clientId, firstName, lastName, email, ts);
    }

    private SignedUp createSU5(TrainingSession ts) {
        Long clientId = 5L;
        String firstName = "CName_five";
        String lastName = "CSurname_five";
        String email = "client_five@email.com";
        return new SignedUp(clientId, firstName, lastName, email, ts);
    }
}

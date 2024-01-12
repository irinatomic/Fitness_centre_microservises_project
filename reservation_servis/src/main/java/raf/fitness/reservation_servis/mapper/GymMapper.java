package raf.fitness.reservation_servis.mapper;

import org.springframework.stereotype.Component;
import raf.fitness.reservation_servis.domain.Gym;
import raf.fitness.reservation_servis.dto.gym.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class GymMapper {

    public Gym requestDtoToGym(GymRequestDto requestDto){
        Gym gym = new Gym();
        gym.setName(requestDto.getName());
        gym.setDescription(requestDto.getDescription());
        gym.setCoachesCount(requestDto.getCoachesCount());
        gym.setFreeSessionNo(requestDto.getFreeSessionNo());

        // Map string hh:mm to LocalTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        gym.setOpeningTime(LocalTime.parse(requestDto.getOpeningTime(), formatter));
        gym.setClosingTime(LocalTime.parse(requestDto.getClosingTime(), formatter));

        // not setting -> id, managerId
        return gym;
    }

    public GymResponseDto gymToResponseDto(Gym gym){
        GymResponseDto responseDto = new GymResponseDto();
        responseDto.setId(gym.getId());
        responseDto.setName(gym.getName());
        responseDto.setDescription(gym.getDescription());
        responseDto.setCoachesCount(gym.getCoachesCount());
        responseDto.setManagerId(gym.getManagerId());
        responseDto.setFreeSessionNo(gym.getFreeSessionNo());
        responseDto.setOpeningTime(gym.getOpeningTime());
        responseDto.setClosingTime(gym.getClosingTime());
        return responseDto;
    }
}

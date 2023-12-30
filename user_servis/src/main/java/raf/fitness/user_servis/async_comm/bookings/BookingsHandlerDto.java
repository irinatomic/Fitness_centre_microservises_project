package raf.fitness.user_servis.async_comm.bookings;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class BookingsHandlerDto {

    @NotNull
    private Character action;
    @NotEmpty
    private List<Long> clientIds;
}

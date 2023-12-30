package raf.fitness.user_servis.async_comm;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raf.fitness.user_servis.async_comm.bookings.BookingsHandlerDto;

/** This is a helper class because we need to convert our objects to
 * JSON strings before sending them to the queue.
 */
@Component
public class MessageHelper {

    private ObjectMapper objectMapper;

    @Autowired
    public MessageHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String createTextMessage(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Error while creating text message");
        }
    }

    public BookingsHandlerDto convertJsonToBookingsHelperDto(String json) {
        try {
            return objectMapper.readValue(json, BookingsHandlerDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Error while reading text message");
        }
    }
}

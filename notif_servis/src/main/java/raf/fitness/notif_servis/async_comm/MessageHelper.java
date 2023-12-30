package raf.fitness.notif_servis.async_comm;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raf.fitness.notif_servis.dto.mail.MailRequestDto;

/** This is a helper class because we need to convert JSON
 * string from the queue to our objects.
 */
@Component
public class MessageHelper {

    private final ObjectMapper objectMapper;

    @Autowired
    public MessageHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public MailRequestDto convertJsonToMailRequestDto(String json) {
        try {
            return objectMapper.readValue(json, MailRequestDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Error while reading text message");
        }
    }
}

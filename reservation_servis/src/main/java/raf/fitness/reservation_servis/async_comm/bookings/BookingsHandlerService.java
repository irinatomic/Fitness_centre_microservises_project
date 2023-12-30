package raf.fitness.reservation_servis.async_comm.bookings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import raf.fitness.reservation_servis.async_comm.MessageHelper;
import raf.fitness.reservation_servis.domain.SignedUp;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingsHandlerService {

    private JmsTemplate emailJmsTemplate;
    @Value("${destination.bookingsHandler}")
    private String emailQueue;
    private MessageHelper messageHelper;

    @Autowired
    public BookingsHandlerService(JmsTemplate emailJmsTemplate, MessageHelper messageHelper) {
        this.emailJmsTemplate = emailJmsTemplate;
        this.messageHelper = messageHelper;
    }

    public void sendMessageToQueue(Character action, List<SignedUp> signedUps) {
        BookingsHandlerDto message = createBookingsHandlerDto(action, signedUps);
        String json = messageHelper.createTextMessage(message);
        emailJmsTemplate.convertAndSend(emailQueue, json);
    }

    private BookingsHandlerDto createBookingsHandlerDto(Character action, List<SignedUp> signedUps) {
        BookingsHandlerDto bookingsHandlerDto = new BookingsHandlerDto();
        bookingsHandlerDto.setAction(action);
        bookingsHandlerDto.setClientIds(signedUps.stream().map(SignedUp::getClientId).collect(Collectors.toList()));
        return bookingsHandlerDto;
    }
}

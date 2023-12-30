package raf.fitness.user_servis.async_comm.bookings;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import raf.fitness.user_servis.async_comm.MessageHelper;
import raf.fitness.user_servis.domain.Client;
import raf.fitness.user_servis.repository.ClientRepository;

import java.util.List;

@Component
public class MessageReceiver {

    @Value("${origin.bookingsHandler}")
    private String emailQueue;
    private MessageHelper messageHelper;
    private ClientRepository clientRepository;

    public MessageReceiver(MessageHelper messageHelper, ClientRepository clientRepository) {
        this.messageHelper = messageHelper;
        this.clientRepository = clientRepository;
    }

    @JmsListener(destination = "${origin.bookingsHandler}")
    public void receiveMessage(String message) {
        BookingsHandlerDto bookingsHandlerDto = messageHelper.convertJsonToBookingsHelperDto(message);

        List<Client> clients = clientRepository.findAllById(bookingsHandlerDto.getClientIds());
        clients.forEach(client -> {
            if (bookingsHandlerDto.getAction() == '+') {
                client.setTrainingsBookedNo(client.getTrainingsBookedNo() + 1);
                clientRepository.save(client);
            }
            else if (bookingsHandlerDto.getAction() == '-') {
                client.setTrainingsBookedNo(client.getTrainingsBookedNo() - 1);
                clientRepository.save(client);
            }
        });
    }
}

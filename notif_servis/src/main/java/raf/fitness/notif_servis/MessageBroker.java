package raf.fitness.notif_servis;

import org.apache.activemq.broker.BrokerService;

/** MessageBroker is a middle man for the asynchronous communication
 * between services. It consists of queues where service A sends a message to Queue
 * and service B reads the message from the Queue in due time.
 * The Queue names need to match in both services and are defined in application.properties.
 */
public class MessageBroker {

    public static void main(String[] args) throws Exception {
        BrokerService broker = new BrokerService();

        // configure the broker
        broker.addConnector("tcp://localhost:61616");
        broker.start();
    }
}

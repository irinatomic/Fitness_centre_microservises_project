package raf.fitness.notif_servis.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "Mail")
public class Mail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private MailType mailType;
    private LocalDate timestamp;
    private String sentTo;
    @Column(length = 1000)
    private String fullText;

    public Mail() {}

    public Mail(MailType mailType, LocalDate timestamp, String sentTo, String fullText) {
    	this.mailType = mailType;
    	this.timestamp = timestamp;
    	this.sentTo = sentTo;
        this.fullText = fullText;
    }
}

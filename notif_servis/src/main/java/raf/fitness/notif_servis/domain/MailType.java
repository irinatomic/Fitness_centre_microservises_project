package raf.fitness.notif_servis.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "MailType")
public class MailType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String subject;
    private String text;

    public MailType() {}

    public MailType( String name, String subject, String text) {
    	this.name = name;
        this.subject = subject;
    	this.text = text;
    }
}

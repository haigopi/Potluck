package com.allibilli.potluck.email;

import com.allibilli.potluck.db.repo.ParticipantRepository;
import com.allibilli.potluck.model.Participant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
@Slf4j
public class Email {

    @Autowired
    ParticipantRepository repository;
    List<Participant> participants = new ArrayList<>();

    @Value("${app.client.url}")
    String appURL;

    @PostConstruct
    public void init() {
        log.info("Loading Emails");
        repository.findAll();
    }

    public void sendEmails(Participant newParticipant) {
        final String username = "alertsoalerts@gmail.com";
        final String password = "gopikrishna";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("haigopi@gmail.com"));
            for (Participant participant : participants) {
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(participant.getEmail()));
            }

            message.setSubject("[Potluck 22 Apr, 2018] " + newParticipant.getName() + " added new items.");
            StringBuilder foodNames = new StringBuilder();
            foodNames.append(newParticipant.getItems().replaceAll(",", " -- <br>"));

            message.setContent("Following items were chosen to bring to potluck"
                    + "<br>" + foodNames.toString() + "<BR> <a href=\"" + appURL + "\">Visit Here to know more details</a>", "text/html; charset=utf-8");

            Transport.send(message);

            log.info("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

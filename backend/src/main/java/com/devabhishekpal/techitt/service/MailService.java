package com.devabhishekpal.techitt.service;


import com.devabhishekpal.techitt.exceptions.TechittException;
import com.devabhishekpal.techitt.model.NotificationMail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    public void sendMail(NotificationMail notificationMail){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("verifications@techitt.com");
            messageHelper.setTo(notificationMail.getRecipient());
            messageHelper.setSubject(notificationMail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationMail.getBody()));
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Activation mail sent");
        }catch (MailException e){
            log.error("Exception while sending mail: "+ e);
            throw new TechittException("Exception occurred while sending mail to " + notificationMail.getRecipient());
        }
    }
}

package com.final_project.daily_operations.service.for_message;

import com.final_project.daily_operations.entities.CustomMailMessage;
import com.final_project.daily_operations.model.Customer;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class EmailService {

    public static final String SYSTEM_EMAIL = "secondandrej@gmail.com";
    private CustomMailMessage customMailMessage;
    private JavaMailSender mailSender;

    public void sendMessage(Customer customer, String content, String subjectText) { //TODO atskiras .propeties failas properciam ishsiaiskint kaip veikia
        customMailMessage.setFrom(SYSTEM_EMAIL);
        customMailMessage.setTo(customer.getEmail());
        customMailMessage.setSubject(customer.getFirstName() + subjectText);
        customMailMessage.setText(content);
        mailSender.send(customMailMessage);
    }

}

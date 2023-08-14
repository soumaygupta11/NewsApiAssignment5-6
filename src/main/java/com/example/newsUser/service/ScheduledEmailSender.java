package com.example.newsUser.service;

import com.example.newsUser.contract.newsArticleWrapper.Article;
import com.example.newsUser.model.repository.SubscriptionRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduledEmailSender {


    @Autowired
    EmailService emailService;

    @Autowired
    UserService us;

    @Autowired
    SubscriptionRespository subscriptionRespository;

    @Autowired
    public ScheduledEmailSender(EmailService emailService) {
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 0 9 * * ?") // Send email every day at 9am
    public void sendScheduledEmail() {

        List<Long> user_ids = subscriptionRespository.getSubscribedUsers();
        for (Long user_id : user_ids) {

            us.sendNewsByEmail(user_id);

//            emailService.sendEmail(to, subject, text);
        }
    }
}


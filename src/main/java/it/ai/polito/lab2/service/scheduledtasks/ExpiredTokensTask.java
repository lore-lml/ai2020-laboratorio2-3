package it.ai.polito.lab2.service.scheduledtasks;

import it.ai.polito.lab2.service.NotificationService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
@Log(topic = "ScheduledTask")
public class ExpiredTokensTask {
    @Autowired
    private NotificationService notificationService;

    @Scheduled(initialDelay = 10*1000, fixedRate = 5*1000) //every 60 secs
    public void deleteExpiredToken() {
        log.info("Erasing expired tokens: " + Timestamp.valueOf(LocalDateTime.now()));
    }
}

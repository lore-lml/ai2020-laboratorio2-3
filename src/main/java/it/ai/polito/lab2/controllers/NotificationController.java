package it.ai.polito.lab2.controllers;

import it.ai.polito.lab2.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/confirm/{token}")
    private String confirmToken(@PathVariable String token){
        if(notificationService.confirm(token))
            return "success";
        return "fail";
    }

    @GetMapping("/reject/{token}")
    private String rejectToken(@PathVariable String token){
        if(notificationService.reject(token))
            return "success";
        return "fail";
    }
}

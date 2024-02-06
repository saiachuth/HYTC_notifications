//package com.hytechnconsulting.notificationservice.controller;
//
//import com.amazonaws.services.sns.AmazonSNSClient;
//import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
//import com.amazonaws.services.simpleemail.model.*;
//import com.hytechnconsulting.notificationservice.dto.SmsRequestDto;
//import com.hytechnconsulting.notificationservice.dto.EmailRequestDto;
//import com.hytechnconsulting.notificationservice.service.AwsSnsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//
//
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//public class AWSSNSController {
//
//    private final static String TOPIC_ARN = "arn:aws:sns:ap-south-1:203754880210:hytcNotif-topic";
//    private String messageBody = "Hello, the SMS notifications work perfectly fine";
//
//    @Autowired
//    private AmazonSNSClient amazonSNSClient;
//
//    @Autowired
//    private AmazonSimpleEmailService amazonSimpleEmailService;
//
//    @Autowired
//    private AwsSnsService awsSnsService;
//
//    @Autowired
//    public AWSSNSController(AwsSnsService awsSnsService) {
//        this.awsSnsService = awsSnsService;
//    }
//
//    @PostMapping(value = "/sendSms", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<String> sendSms(@RequestBody SmsRequestDto smsRequestDto ) {
//        try {
//            awsSnsService.sendSms(smsRequestDto.getMobileNo(), smsRequestDto.getMessage());
//            return ResponseEntity.ok("Successfully Sent SMS");
//        } catch (AwsSnsClientException e) {
//            return ResponseEntity.status(500).body("Error occurred while sending SMS: " + e.getMessage());
//        }
//    }
//
//    @PostMapping("/sendEmail")
//    public ResponseEntity<String> sendEmail(@RequestBody EmailRequestDto emailRequestDto) {
//        SendEmailRequest request = new SendEmailRequest()
//                .withDestination(new Destination().withToAddresses(emailRequestDto.getEmail()))
//                .withMessage(new Message()
//                        .withBody(new Body()
//                                .withText(new Content().withCharset("UTF-8").withData(emailRequestDto.getBody())))
//                        .withSubject(new Content().withCharset("UTF-8").withData(emailRequestDto.getSubject())))
//                .withSource(emailRequestDto.getSenderEmail());
//
//        try {
//            amazonSimpleEmailService.sendEmail(request);
//            return ResponseEntity.ok("Email sent successfully.");
//        } catch (Exception ex) {
//            return ResponseEntity.status(500).body("Error sending email: " + ex.getMessage());
//        }
//    }
//
//    @GetMapping("/sendNotification")
//    public ResponseEntity<String> publishMessageToSNSTopic() {
//        PublishRequest publishRequest = new PublishRequest(TOPIC_ARN, messageBody, "Some important Message");
//        amazonSNSClient.publish(publishRequest);
//        return ResponseEntity.ok("Notification sent successfully.");
//    }
//
//    @PostMapping("/sendSMS")
//    public ResponseEntity<String> sendSMSToSubscriber(@RequestBody String phoneNumber) {
//        try {
//            PublishRequest publishRequest = new PublishRequest()
//                    .withMessage(messageBody)
//                    .withPhoneNumber(phoneNumber);
//            amazonSNSClient.publish(publishRequest);
//            return ResponseEntity.ok("SMS sent successfully to: " + phoneNumber);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Error sending SMS: " + e.getMessage());
//        }
//    }
//
//    @PostMapping("/sendPushNotification")
//    public ResponseEntity<String> sendPushNotificationToSubscriber(@RequestBody String deviceToken) {
//        try {
//            PublishRequest publishRequest = new PublishRequest()
//                    .withMessage(messageBody)
//                    .withTargetArn("arn:aws:sns:ap-south-1:123456789012:endpoint/APNS/GCM/YourApp/" + deviceToken)
//                    .withMessageAttributes(getPushNotificationAttributes());
//            amazonSNSClient.publish(publishRequest);
//            return ResponseEntity.ok("Push notification sent successfully to: " + deviceToken);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Error sending push notification: " + e.getMessage());
//        }
//    }
//
//    private Map<String, MessageAttributeValue> getPushNotificationAttributes() {
//        Map<String, MessageAttributeValue> pushAttributes = new HashMap<>();
//        pushAttributes.put("AWS.SNS.MOBILE.APNS.PRIORITY", new MessageAttributeValue()
//                .withStringValue("10")
//                .withDataType("Number"));
//        return pushAttributes;
//    }
//}

package com.hytechnconsulting.notificationservice.controller;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.hytechnconsulting.notificationservice.dto.EmailDetails;
import com.hytechnconsulting.notificationservice.dto.SmsRequestDto;
import com.hytechnconsulting.notificationservice.service.AwsSnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AWSSNSController {

    @Autowired
    private AmazonSimpleEmailService amazonSimpleEmailService;

    @Autowired
    private AwsSnsService awsSnsService;

    @Autowired
    public AWSSNSController(AwsSnsService awsSnsService, AmazonSimpleEmailService amazonSimpleEmailService) {
        this.awsSnsService = awsSnsService;
        this.amazonSimpleEmailService = amazonSimpleEmailService;
    }

    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDetails emailDetails) {
        SendEmailRequest request = new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(emailDetails.getToEmail()))
                .withMessage(new Message()
                        .withBody(new Body()
                                .withText(new Content().withCharset("UTF-8").withData(emailDetails.getBody())))
                        .withSubject(new Content().withCharset("UTF-8").withData(emailDetails.getSubject())))
                .withSource(emailDetails.getFromEmail());

        try {
            amazonSimpleEmailService.sendEmail(request);
            return ResponseEntity.ok("Email sent successfully.");
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error sending email: " + ex.getMessage());
        }
    }

    @PostMapping("/sendEmailWithAttachment")
    public ResponseEntity<String> sendEmailWithAttachment(@RequestBody EmailDetails emailDetails) {
        // Implement sending email with attachment using SES
        try {
            // Your implementation here
            return ResponseEntity.ok("Email with attachment sent successfully.");
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error sending email with attachment: " + ex.getMessage());
        }
    }

    @PostMapping("/sendTemplateEmailWithAttachment")
    public ResponseEntity<String> sendTemplateEmailWithAttachment(@RequestBody EmailDetails emailDetails) {
        // Implement sending template email with attachment using SES
        try {
            // Your implementation here
            return ResponseEntity.ok("Template email with attachment sent successfully.");
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error sending template email with attachment: " + ex.getMessage());
        }
    }

    @PostMapping("/sendAWSTemplatedEmail")
    public ResponseEntity<String> sendAWSTemplatedEmail(@RequestBody EmailDetails emailDetails) {
        // Implement sending AWS templated email using SES
        try {
            // Your implementation here
            return ResponseEntity.ok("AWS templated email sent successfully.");
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error sending AWS templated email: " + ex.getMessage());
        }
    }

    @PostMapping("/sendSms")
    public ResponseEntity<String> sendSms(@RequestBody SmsRequestDto smsRequestDto ) {
        try {
            awsSnsService.sendSms(smsRequestDto.getMobileNo(), smsRequestDto.getMessage());
            return ResponseEntity.ok("Successfully Sent SMS");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error occurred while sending SMS: " + e.getMessage());
        }
    }

    // Other methods for SMS and push notifications can be added similarly
}

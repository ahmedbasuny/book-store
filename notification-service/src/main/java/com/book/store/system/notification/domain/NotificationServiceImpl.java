package com.book.store.system.notification.domain;

import com.book.store.system.notification.configuration.ApplicationProperties;
import com.book.store.system.notification.domain.events.OrderCancelledEvent;
import com.book.store.system.notification.domain.events.OrderCreatedEvent;
import com.book.store.system.notification.domain.events.OrderDeliveredEvent;
import com.book.store.system.notification.domain.events.OrderErrorEvent;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender emailSender;
    private final ApplicationProperties properties;

    @Override
    public void sendOrderCreatedNotification(OrderCreatedEvent event) {
        String message =
                """
                        ===================================================
                        Order Created Notification
                        ----------------------------------------------------
                        Dear %s,
                        Your order with orderNumber: %s has been created successfully.

                        Thanks,
                        BookStore Team
                        ===================================================
                        """
                        .formatted(event.getCustomer().name(), event.getOrderNumber());
        log.info("\n{}", message);
        sendEmail(event.getCustomer().email(), "Order Created Notification", message);
    }

    @Override
    public void sendOrderDeliveredNotification(OrderDeliveredEvent event) {
        String message =
                """
                        ===================================================
                        Order Delivered Notification
                        ----------------------------------------------------
                        Dear %s,
                        Your order with orderNumber: %s has been delivered successfully.

                        Thanks,
                        BookStore Team
                        ===================================================
                        """
                        .formatted(event.getCustomer().name(), event.getOrderNumber());
        log.info("\n{}", message);
        sendEmail(event.getCustomer().email(), "Order Delivered Notification", message);
    }

    @Override
    public void sendOrderCancelledNotification(OrderCancelledEvent event) {
        String message =
                """
                        ===================================================
                        Order Cancelled Notification
                        ----------------------------------------------------
                        Dear %s,
                        Your order with orderNumber: %s has been cancelled.
                        Reason: %s

                        Thanks,
                        BookStore Team
                        ===================================================
                        """
                        .formatted(event.getCustomer().name(), event.getOrderNumber(), event.getReason());
        log.info("\n{}", message);
        sendEmail(event.getCustomer().email(), "Order Cancelled Notification", message);
    }

    @Override
    public void sendOrderErrorEventNotification(OrderErrorEvent event) {
        String message =
                """
                        ===================================================
                        Order Processing Failure Notification
                        ----------------------------------------------------
                        Hi Team,
                        The order processing failed for orderNumber: %s.
                        Reason: %s

                        Thanks,
                        BookStore Team
                        ===================================================
                        """
                        .formatted(event.getOrderNumber(), event.getReason());
        log.info("\n{}", message);
        sendEmail(properties.supportEmail(), "Order Processing Failure Notification", message);
    }

    private void sendEmail(String recipient, String subject, String content) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom(properties.supportEmail());
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(content);
            emailSender.send(mimeMessage);
            log.info("Email sent to: {}", recipient);
        } catch (Exception e) {
            throw new RuntimeException("Error while sending email", e);
        }
    }
}

package com.springboot.telegym.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SendPromotionalForCustomer {

    @Autowired
    public JavaMailSender emailSender;

    public void sendMail(String contentPromotional, List<PromotionalRequest> receivedMail) {

        SimpleMailMessage message = new SimpleMailMessage();

        String contactInfo = "\n\n Best Regards" + "\n--------------------\n" +
                "Liên hệ qua Facebook để biết thêm chi tiết: https://www.facebook.com/NGL.015067.2.20/\n" +
                "Địa chỉ phòng tập: " +
                "\nTelegym 1: Tầng 4 tòa nhà Landmark 89, số 66 Võ Văn Tần, Hà Nội" +
                "\nTelegym 2: Tầng 2 tòa nhà Sandico số 16 Nguyễn Chí Thanh, Hà Nội" +
                "\nTelegym 3: Tòa BB4 khu đô thị SeaGate Park, số 12 Hoàng Giám Tự, Đà Nẵng" +
                "\nTelegym 4: Tầng 3 tòa nhà trung tâm bến cầu Thị Nghè, số 2 Thị Nghè, TP.HCM" +
                "\nTelegym 5: Tầng 2 tòa nhà SF8, số 10 Bình Tân, TP.HCM";

        for (PromotionalRequest pr : receivedMail) {
            String contentMail = "Dear " + pr.getNameCustomer() + "\n" + contentPromotional + contactInfo;
            message.setTo(pr.mailCustomer);
            message.setSubject("Hệ thống phòng tập chuyên nghiệp Telegym");
            message.setText(contentMail);
        }

        emailSender.send(message);
    }
}

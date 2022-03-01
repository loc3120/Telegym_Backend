package com.springboot.telegym.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

@Service
public class SendMailForCandidate {

    @Autowired
    public JavaMailSender emailSender;

    public void sendMail(String name, boolean isApproved, String receiveMail) {

        SimpleMailMessage message = new SimpleMailMessage();

        LocalDate now = LocalDate.now();

        now = now.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        String nowFormatter = now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        String contentMail = "Dear " + name + ",\n" +
                "Cảm ơn bạn đã ứng tuyển tới hệ thống phòng tập chuyên nghiệp Telegym. " +
                "Chúng tôi đã đọc bản mô tả về bản thân cũng như những kinh nghiệm, khả năng hoàn " +
                "thành công việc của bạn. Chúng tôi đánh giá cao sự cố gắng và nhiệt tình của bạn. ";

        String contactInfo = " \n \n Best Regards," + "\n--------------------\n" +
                "Liên hệ qua Facebook để biết thêm chi tiết: https://www.facebook.com/NGL.015067.2.20/\n" +
                "Địa chỉ phòng tập: " +
                "\nTelegym 1: Tầng 4 tòa nhà Landmark 89, số 66 Võ Văn Tần, Hà Nội" +
                "\nTelegym 2: Tầng 2 tòa nhà Sandico số 16 Nguyễn Chí Thanh, Hà Nội" +
                "\nTelegym 3: Tòa BB4 khu đô thị SeaGate Park, số 12 Hoàng Giám Tự, Đà Nẵng" +
                "\nTelegym 4: Tầng 3 tòa nhà trung tâm bến cầu Thị Nghè, số 2 Thị Nghè, TP.HCM" +
                "\nTelegym 5: Tầng 2 tòa nhà SF8, số 10 Bình Tân, TP.HCM";

        message.setTo(receiveMail);
        message.setSubject("Hệ thống phòng tập chuyên nghiệp Telegym");

        if (!isApproved) {
            contentMail += "Tuy nhiên chúng tôi đã nhận được bản mô tả của một số ứng viên ấn tượng và quyết định " +
                    "đi hợp tác với họ. Chúng tôi đã cân nhắc rất nhiều trước khi đưa ra quyết định. Chúng tôi " +
                    "sẽ lưu lại hồ sơ của bạn và xin phép được liên hệ với bạn khi có cơ hội phù hợp trong tương " +
                    "lai. Chúng tôi mong rằng có thể hợp tác với bạn trong tương lai gần. Chúc bạn may mắn trong " +
                    "quá trình tìm việc." + contactInfo;
        } else {
            contentMail += "Chúng tôi chúc mừng bạn đã trúng tuyển vào vị trí PT của chúng tôi. Chúng tôi thấy " +
                    "được tiềm năng từ bạn và thấy được kĩ năng của bạn sẽ giúp cho hệ thống cũng như bạn sẽ " +
                    "ngày càng phát triển. Chúng tôi hẹn bạn vào thứ 2 ngày (" + nowFormatter + "). " +
                    "Chúng tôi mong rằng sự hợp tác của chúng ta sẽ giúp cho cả 2 bên trở nên hoàn thiện hơn."
                    + contactInfo;
        }
        message.setText(contentMail);

        emailSender.send(message);
    }
}

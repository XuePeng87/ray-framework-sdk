package cc.xuepeng.ray.framework.sdk.mgt.service.impl.sender;

import cc.xuepeng.ray.framework.core.util.random.RandomUtils;
import cc.xuepeng.ray.framework.sdk.mgt.entity.SysUserRetrievePassword;
import cc.xuepeng.ray.framework.sdk.mgt.enums.ForgetPasswordType;
import cc.xuepeng.ray.framework.sdk.mgt.exception.SendingEmailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SendToEmailStrategy implements SendStrategy {

    /**
     * 短信模版。
     */
    private static final String EMAIL_CONTENT = "亲爱的用户您好：\r\n 您正在申请管理系统找回密码服务，本次请求的邮件验证码是：{content} （为了保障验证码的时效性，请您15分钟内完成验证）。如非您本人操作，请忽略该邮件，您的原密码不会更改。\r\n  祝好！\r\n  九五智驾";

    /**
     * 邮件客户端。
     */
    private JavaMailSender mailSender;

    /**
     * 自动装配邮件客户端。
     *
     * @param mailSender 邮件客户端。
     */
    @Autowired(required = false)
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * 获取发送方式。
     */
    @Override
    public ForgetPasswordType getSendType() {
        return ForgetPasswordType.EMAIL;
    }

    /**
     * 发送验证码。
     *
     * @param userRetrievePassword 找回密码的实体类。
     * @return 发送的验证码。
     */
    @Override
    public String send(final SysUserRetrievePassword userRetrievePassword) {
        final String emailCode = RandomUtils.generateSixDigitsString();
        final String content = createSendContent(emailCode, EMAIL_CONTENT);
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("100103081@qq.com");
            message.setTo(userRetrievePassword.getContact());
            message.setSubject("主题：重置密码验证码");
            message.setText(content);
            mailSender.send(message);
        } catch (Exception e) {
            throw new SendingEmailException("修改密码发送邮件失败。");
        }
        return emailCode;
    }

}

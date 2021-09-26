package cc.xuepeng.ray.framework.sdk.mgt.service.impl.sender;

import cc.xuepeng.ray.framework.sdk.mgt.enums.ForgetPasswordType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * 发送策略工厂。
 *
 * @author xuepeng
 */
@Component
@Slf4j
public class SendStrategyFactory {

    /**
     * 获取发送策略。
     *
     * @param forgetPasswordType 忘记密码找回方式。
     * @return 发送验证码策略。
     */
    public SendStrategy getInstance(final ForgetPasswordType forgetPasswordType) {
        log.info("发送验证码工厂类收到发送类型是 ： {}", forgetPasswordType);
        Optional<SendStrategy> approveState = sendStrategys
                .stream()
                .filter(s -> s.getSendType().equals(forgetPasswordType)).findFirst();
        if (approveState.isPresent()) {
            return approveState.get();
        } else {
            log.error("发送验证码工厂类中type为空，请检查传入的发送类型");
            throw new IllegalArgumentException("发送验证码工厂类中type为空，请检查传入的发送类型");
        }
    }

    /**
     * 自动装配发送验证码策略。
     *
     * @param sendStrategys 发送验证码策略。
     */
    @Autowired
    public void setSendVerificationStateList(final List<SendStrategy> sendStrategys) {
        this.sendStrategys = sendStrategys;
    }

    /**
     * 发送验证码策略。
     */
    private List<SendStrategy> sendStrategys;

}

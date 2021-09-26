package cc.xuepeng.ray.framework.sdk.mgt.service.impl.sender;

import cc.xuepeng.ray.framework.sdk.mgt.entity.SysUserRetrievePassword;
import cc.xuepeng.ray.framework.sdk.mgt.enums.ForgetPasswordType;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 发送验证码的策略。
 *
 * @author xuepeng
 */
public interface SendStrategy {

    /**
     * 获取发送方式。
     */
    ForgetPasswordType getSendType();

    /**
     * 创建要发送的内容。
     *
     * @param content 内容模板。
     * @return 要发送的内容。
     */
    default String createSendContent(final String code, final String content) {
        Map<String, String> map = new HashMap<>();
        map.put("content", code);
        Iterator<String> it = map.keySet().iterator();
        String result = StringUtils.EMPTY;
        while (it.hasNext()) {
            String key = String.valueOf(it.next());
            String val = String.valueOf(map.get(key));
            result = content.replaceAll("\\{\\s*" + key + "\\s*\\}", val);
        }
        return result;
    }

    /**
     * 发送验证码。
     *
     * @param userRetrievePassword 找回密码的实体类。
     * @return 发送的验证码。
     */
    String send(final SysUserRetrievePassword userRetrievePassword);

}

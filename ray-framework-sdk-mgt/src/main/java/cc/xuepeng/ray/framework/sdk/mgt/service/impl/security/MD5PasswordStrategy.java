package cc.xuepeng.ray.framework.sdk.mgt.service.impl.security;

import cc.xuepeng.ray.framework.core.util.codec.MD5Util;
import org.springframework.stereotype.Component;

/**
 * MD5登录密码加密的策略。
 *
 * @author xuepeng
 */
@Component("md5PasswordStrategy")
public class MD5PasswordStrategy implements PasswordStrategy {

    /**
     * 密码加密算法策略。
     *
     * @param secret 登录密码。
     * @return 加密后的密码。
     */
    @Override
    public String encode(String secret) {
        return MD5Util.encode(secret);
    }

}

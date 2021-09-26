package cc.xuepeng.ray.framework.sdk.mgt.service.impl.security;

/**
 * 登录密码加密的策略。
 *
 * @author xuepeng
 */
public interface PasswordStrategy {

    /**
     * 密码加密算法策略。
     *
     * @param secret 登录密码。
     * @return 加密后的密码。
     */
    String encode(final String secret);

}

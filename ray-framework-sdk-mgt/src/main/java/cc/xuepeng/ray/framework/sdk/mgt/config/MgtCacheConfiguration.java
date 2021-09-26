package cc.xuepeng.ray.framework.sdk.mgt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 系统管理模块的缓存配置信息。
 *
 * @author xuepeng
 */
@Configuration
@ConfigurationProperties(prefix = "ray.framework.sdk.mgt.cache")
@Data
public class MgtCacheConfiguration {

    /**
     * 发送验证码的时间间隔Key。
     */
    private String sendIntervalKey;
    /**
     * 发送验证码的时间间隔（秒）。
     */
    private Integer sendIntervalTime;
    /**
     * 保存验证码的Key。
     */
    private String pinCodeKey;
    /**
     * 验证码的有效时间（分钟）。
     */
    private Integer pinCodeTime;
    /**
     * 发送次数上限的Key。
     */
    private String sendUpperLimitKey;
    /**
     * 发送次数上限。
     */
    private Integer sendUpperLimitCount;

}

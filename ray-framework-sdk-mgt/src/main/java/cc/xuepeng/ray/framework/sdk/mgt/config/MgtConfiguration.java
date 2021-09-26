package cc.xuepeng.ray.framework.sdk.mgt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 系统管理模块的配置信息。
 *
 * @author xuepeng
 */
@Configuration
@ConfigurationProperties(prefix = "ray.framework.sdk.mgt")
@Data
public class MgtConfiguration {

    /**
     * 系统默认密码。
     */
    private String defaultPassword;

    /**
     * 缓存配置信息。
     */
    private final MgtCacheConfiguration mgtCacheConfiguration;

}

package cc.xuepeng.ray.framework.sdk.mgt.entity;

import cc.xuepeng.ray.framework.sdk.mgt.enums.ForgetPasswordType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * 用户找回密码。
 *
 * @author xuepeng
 */
@Data
@ToString
@EqualsAndHashCode
public class SysUserRetrievePassword implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 联系方式。
     */
    private String contact;
    /**
     * 找回方式。
     */
    private ForgetPasswordType type;
    /**
     * 新密码。
     */
    private String password;
    /**
     * 验证码。
     */
    private String code;

}

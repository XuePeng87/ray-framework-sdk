package cc.xuepeng.ray.framework.sdk.mgt.service;

import cc.xuepeng.ray.framework.sdk.mgt.MgtSDKApplication;
import cc.xuepeng.ray.framework.sdk.mgt.entity.SysUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MgtSDKApplication.class)
public class UserServiceTest {

    @Test
    public void testCreate() {
        final SysUser sysUser = new SysUser();
        sysUser.setUserAccount("test");
        final boolean result = sysUserService.create(sysUser);
        Assert.assertTrue(result);
    }

    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    private SysUserService sysUserService;

}

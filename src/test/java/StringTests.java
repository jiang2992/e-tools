import org.jiang.tools.text.StringUtils;
import org.jiang.tools.text.StringVerifyUtils;
import org.junit.Test;

/**
 * 字符串相关测试类
 *
 * @author Bin
 * @since 1.1.0
 */
public class StringTests {

    @Test
    public void humpToLine() {
        String str = StringUtils.humpToLine("sysUserRole");
        System.out.println(str);
        str = StringUtils.lineToHump("sys_user_role");
        System.out.println(str);
    }

    @Test
    public void verify(){
        // 手机号
        assert StringVerifyUtils.isPhone("13660044444");
        assert !StringVerifyUtils.isPhone("1366666666");
        assert StringVerifyUtils.isPhone("18666666663");
        assert !StringVerifyUtils.isPhone("020-3222222");
        // 座机号
        assert StringVerifyUtils.isTelPhone("020-3222222");
        assert StringVerifyUtils.isTelPhone("0883-3222222");
        assert !StringVerifyUtils.isTelPhone("020-283819");
        // 邮箱
        assert StringVerifyUtils.isEmail("123456@qq.com");
        assert !StringVerifyUtils.isEmail("123456.qq@.com");
        assert !StringVerifyUtils.isEmail("12345 6@qq.com");
        assert StringVerifyUtils.isEmail("12345_#a6@qq.com");
        assert !StringVerifyUtils.isEmail("123456.com");
        // 身份证
        assert StringVerifyUtils.isIDCard("370831190105022866");
        assert StringVerifyUtils.isIDCard("62010219560302502X");
        assert !StringVerifyUtils.isIDCard("44444444444444444X");
        assert !StringVerifyUtils.isIDCard("3708311901050228XX");
        assert !StringVerifyUtils.isIDCard("4444444444444444x");
        assert !StringVerifyUtils.isIDCard("4444444444444444435");
        // 中文用户名
        assert StringVerifyUtils.isCnUserName("小张");
        assert !StringVerifyUtils.isCnUserName("张");
        assert StringVerifyUtils.isCnUserName("张三");
        assert StringVerifyUtils.isCnUserName("欧阳三风阿尔卑斯");
        // 英文用户名
        assert !StringVerifyUtils.isEnUserName("J");
        assert StringVerifyUtils.isEnUserName("JH");
        assert StringVerifyUtils.isEnUserName("James Han");
        assert StringVerifyUtils.isEnUserName("ZhangSan");
        assert !StringVerifyUtils.isEnUserName("Zhang992");
        // 中国车牌
        assert StringVerifyUtils.isCnLicencePlate("粤AS8888");
        assert !StringVerifyUtils.isCnLicencePlate("月AS8888");
        assert StringVerifyUtils.isCnLicencePlate("京AXZ886");
        // 账号
        assert !StringVerifyUtils.isAccount("_99djj");
        assert StringVerifyUtils.isAccount("admin");
        assert !StringVerifyUtils.isAccount("root admin");
        assert !StringVerifyUtils.isAccount("top");
        assert StringVerifyUtils.isAccount("root_ad2min");
    }

}

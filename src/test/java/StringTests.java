import java.util.Map;
import org.jiang.tools.json.JsonUtils;
import org.jiang.tools.text.RandomUtils;
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

    /**
     * json转换
     */
    @Test
    public void json(){
        String json = "{\n"
                + "\"IM_BASEINFO\":{\"I_MSGID\":\"str1234\",\"I_CODE\":\"S0339\",\"I_SNDTIME\":\"20191224141700000000\",\"I_LANGUAGE\":\"1\",\"I_SOUSYS\":\"ABN\",\"I_USRNAME\":\"OA_TEST\",\"I_KEY1\":\"str1234\",\"I_KEY2\":\"str1234\",\"I_KEY3\":\"str1234\"},\n"
                + "\"IM_REQ_DATA\":\n"
                + "{\"AbnSiteInformations\":\n"
                + "[{\"comba_province\":\"山东\",\"comba_city\":\"青岛\",\"comba_projecttypesecond\":\"传统室分\",\"comba_operator\":\"移动\",\"comba_physicalsitename\":\"E092312055\tT009\t青岛移动-崂山区青岛大学新建实验楼\",\"comba_siteaddress\":\"山东省青岛市崂山区香港东路12-1号\",\"comba_longitude\":\"120.42188247591302\",\"comba_latitude\":\"36.06\"}]\n"
                + "}\n"
                + "}";
        System.out.println(json);
        json  = json.replaceAll("\n", "");
        json  = json.replaceAll("\t", "");
        System.out.println(JsonUtils.toBean(json, Map.class));
    }

    /**
     * 随机数生成
     */
    @Test
    public void random() {
        System.out.println("generate: " + RandomUtils.generate(6));
        System.out.println("generate letter: " + RandomUtils.generateLetter(6));
        System.out.println("generate number: " + RandomUtils.generateNumber(6));
        System.out.println("generate upper latter: " + RandomUtils.generateUpperLetter(6));
        System.out.println("generate lower latter: " + RandomUtils.generateLowerLetter(6));
    }

    /**
     * 驼峰转换
     */
    @Test
    public void humpToLine() {
        String str = StringUtils.humpToLine("sysUserRole");
        System.out.println(str);
        str = StringUtils.lineToHump("sys_user_role");
        System.out.println(str);
    }

    /**
     * 格式验证
     */
    @Test
    public void verify() {
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

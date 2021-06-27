package com.lvboaa.config;

import java.io.FileWriter;
import java.io.IOException;

/**
 * 支付配置类
 *
 * @author lvbo
 * @date 2020年 11月02日 23:20:09
 */
public class AlipayConfig {
    //商户APPID
    public static String app_id = "2016103100780297";

    //商户私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCiJ+T4kGGerJ8TjY3A/kKVaLl02WW+adlyWUsAg+1x8fdyNkSsBpMv9Q2ui7lDFsDu2BctJrRxMvd4l17WqMb190R3RO58rlk4h+eHR7A0HkephRDkXIhQyNgYjHq6fBecCfTBg4sVVChh+zDFvAOzQgHSSRBDzdJM1pkMDLBd45RKHZipLVPZlE6+UOxPD/afOyodNH/tTPXmUzTxQbDC8Iy+ZWGekGx1W5uejhYTlv4nS+HNzmGf2bQuWuiNiHtcGq/5si6rJQh89vDEC4pxLhKBoANUw1zmYrwuuffqwmnOiC0WtB9i7eEshUrB+kV2USqH5WSQCFa4h4fmuFd5AgMBAAECggEAJhijVbZuCN5bXTejdxUFRZ7d+1Lb8XliydeqpEtabKvBS8buKVA+IkU7Wqee9eERoMuJ3mS6feHQfDdr45ixbbYTEsZSFV7MvVhIj+LKxePbSDtn9Y67w+b7xgCzEL74uQMpypECCxfDiQl/suTyJZo7XVEnDhb78mt0d30/rj7Y1qVADDFcmHVTFiQuOf9vMepkOV+KS8BvOxA80giuZD5RKb+as8QtEYdJpS4zydGj2hX4OY6fElJNcbBNvzi+b/y8S0klzpJ66mbp6wuf65Y9YDj9/njMBZAphksb4kLYdlLJ8SBb5Oq4BMw8YWeRpKaLNWQPBtv4d99HALt9xQKBgQD3pQnorLgDZ/Rmb9HwuxsGgnFKs91fVvV2HzTQvtDJL9hXNnG0F2HDRkTONyZv0xfPJv28m7rRa1Aj7OCEEqLaadmAkjZu2M3Lo6twtvbqCd3hPX0XY/KJV0r9MFcYfaPs0HYPAGc6wPo0UQ/dn9QoeWn2u/nX43B0YLBmFAVqNwKBgQCnoHhImDFl5SoQyOFOcSm1Jg50hdvhM6aTWw33Ws1XbE88h5+KKDaLbV3PzpeZ4aKFSACPcpLYobfW2j9k3tCVxxbMm8iLzTcKWxGI1KQMX5AFy+c6fmUT5eFxLG5xIsCzvbi6enzCwnacY2kJWK9depjksC62zrHMsqi+9gWzzwKBgQCI92/Wj4iq4ZkL+vabdXDKFeVBEt/yNi9GqJXWimMBkwPNoKAcof4BOeEeSORqXs9BVGhsI5mc8ASACihXA9Osh3SzpqqSfji8U4ajrZ487T4ZsJ96C15+VZh1FoJlWkAK33UccPWnSkpYGylyQ07dH+HahDb4D6QHyd5mZtZj2wKBgBdJywxC4LIIWYTFc8/jFulM5Ts5EahC9cSJJPKVkeuwZ33AVeDq31EVm6/wQs2lNGAR9cn4IACqzxCxd5c7AX2dkERS7FTH6PquVkxNRpFVgXvcrT5KKx7NlDmV864SJvWRySVXORgRzkrB4OjkglCZZL5437SUGM2YkKsfIQGvAoGAIy4RSWMd3XxreQdBnIqVOTljTVzcjPgHNelXFndfDSvzdMw9znCktM9VcFM0Yzrvayu14hg7o9nG35o41yZF6EE6oZT5kP+Bs6+ZbDhDpmbKhl9WfTGvIv+QqhMFSIY6uR03y6pUnNYl96YebkAQ1Ww9vwCOjPUR6WQ1LDeBInE=";

    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgnX8VVDMs1ATz1p0vENd/ETiucQJp9ljJWDOAz/lyjRO9xmv1oLMcpVbFy/9TnnrM9tscTkqjit2NWyKChoTcjd704eJOaW4nAp49bOGKGhQY4dcRz6gJQG6TRjMYFk7RAXPMlqhZvq4KnngL67M3xAXbCQlTeBdcPM6zK8OVLgX+Dt2bcN/kBIg67TOwgjhCKjnbIYl7r4684PleTm8ilZMPqoATKNNQXhtpIc3ezUpmTtj3mVVjHLUwSTCgSmSmUU9kNEkePuUSguVTFgNHmFmYq4x1PcF0YDiFtvs0ksNEprUPYJeHF46P0yCqkfJqCqpr1/RnrRmcQSoxmR0CwIDAQAB";
    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://120.79.75.57:8081/order/callback";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 即支付成功之后，需要跳转到的页面，一般为网站的首页
    public static String return_url = "http://www.baidu.com";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 日志存储路径
    public static String log_path = "D:\\";


    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

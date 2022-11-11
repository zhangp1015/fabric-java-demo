package cc.taketo.util;

import java.io.IOException;
import java.util.Properties;

/**
 * @Title: PathUtil
 * @Package: cc.taketo.util
 * @Description:
 * @Author: zhangp
 * @Date: 2022/11/11 - 17:04
 */

public class PathUtil {

    private static String networkConnectionConfigPath;

    private static String certificatePath;

    private static String privateKeyPath;

    private static Properties p;

    static{
        p = new Properties();
        try {
            p.load(PathUtil.class.getClassLoader().getResourceAsStream("ConfigPath.properties"));
            networkConnectionConfigPath = p.getProperty("networkConnectionConfigPath");
            certificatePath = p.getProperty("certificatePath");
            privateKeyPath = p.getProperty("privateKeyPath");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getNetworkConnectionConfigPath() {
        return networkConnectionConfigPath;
    }

    public static String getCertificatePath() {
        return certificatePath;
    }

    public static String getPrivateKeyPath() {
        return privateKeyPath;
    }
}

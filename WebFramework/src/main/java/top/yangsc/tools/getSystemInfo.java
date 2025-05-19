package top.yangsc.tools;

/**
 * 描述：top.yangsc.swiftcache.tools
 *
 * @author yang
 * @date 2025/5/16 16:55
 */
public class getSystemInfo {
    public static void main(String[] args) {
        System.out.println(System.getProperty("os.name"));
        System.out.println(System.getProperty("os.version"));
    }
    public static String getSySInfo() {
        return System.getProperty("os.name")+" "+System.getProperty("os.version");
    }
}

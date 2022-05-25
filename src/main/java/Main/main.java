package main.java.Main;

import common.java.JGrapeSystem.GscBooster;
import common.java.Session.UserSession;

// -n test -h 127.0.0.1:805
public class main {
    public static void main(String[] args) {
        // 修改服务显示名称
        // 调试时需要配置运行参数 例如: -n test-d -h 127.0.0.1:805 -k grapeSoft@ -p 123456
        // -n 服务名称 -h 主控服务器地址 -k 主控服务器密钥 -p 端口
        System.out.println("<Hello World服务>");
        // 设置会话系统使用 jwt
        UserSession.setDefaultDriver(UserSession.JwtDriver);
        GscBooster.start(args, () -> {
            // 测试分布式订阅源系统
            // SubscribeGsc.injectDistribution();
            // 测试分布式数据源系统
            // DataSourceManager.injectDateSourceStore(DataSourceDistribution.build());
        });
    }
}

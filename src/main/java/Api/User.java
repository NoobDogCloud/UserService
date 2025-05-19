package main.java.Api;

import Model.UserModel;
import common.java.InterfaceModel.Type.InterfaceType;
import common.java.Jwt.JwtInfo;
import common.java.Rpc.RpcMessage;
import common.java.ServiceTemplate.MicroServiceTemplate;
import common.java.Session.UserSession;
import org.json.gsc.JSONObject;

public class User extends MicroServiceTemplate {
    public User() {
        super();
        // 设置验证码发送处理函数
//        setApiTokenSender((serviceName, className, actionName, code) -> {
//            System.out.println("serviceName: " + serviceName + "/" + className + "/" + actionName + "/" + code);
//        });
    }

    /**
     * 用户登录接口
     */
    // @InterfaceType(InterfaceType.type.OauthApi)
    public Object login(String user_id, String password) {
        JSONObject userInfo = db.eq("user_id", user_id).find();
        if (JSONObject.isInvalided(userInfo)) {
            return RpcMessage.Instant(false, "当前用户不存在!");
        }
        String salt = userInfo.getString("salt");
        String encode_password = userInfo.getString("password");
        if (!UserModel.checkPassword(user_id, password, salt, encode_password)) {
            return RpcMessage.Instant(false, "当前密码错误!");
        }
        userInfo.put("account", user_id);
        return JwtInfo.build(user_id).encodeJwt(userInfo).toString();
    }

    /**
     * 权限接口
     */
    @InterfaceType(InterfaceType.type.CloseApi)
    public Object permission() {
        return RpcMessage.Instant(false, "Not Implemented");
    }

    @InterfaceType(InterfaceType.type.CloseApi)
    public Object preferencesEdit(String preferences) {
        return RpcMessage.Instant(false, "Not Implemented");
    }

    /**
     * 修改密码
     */
    @InterfaceType(InterfaceType.type.SessionApi)
    public Object editPassword(String old_password, String new_password) {
        String uid = UserSession.current().getUID();
        JSONObject userInfo = db.eq("user_id", uid).find();
        if (JSONObject.isInvalided(userInfo)) {
            return RpcMessage.Instant(false, "当前用户不存在!");
        }
        if (old_password.isEmpty()) {
            return RpcMessage.Instant(false, "请输入当前密码!");
        }
        if (new_password.isEmpty()) {
            return RpcMessage.Instant(false, "请输入新密码!");
        }
        if (old_password.equals(new_password)) {
            return RpcMessage.Instant(false, "新密码不能与当前密码相同!");
        }
        if (new_password.length() < 6) {
            return RpcMessage.Instant(false, "新密码长度不能小于6位!");
        }
        if (new_password.length() > 20) {
            return RpcMessage.Instant(false, "新密码长度不能大于20位!");
        }
        if (new_password.matches(".*[\\s]+.*")) {
            return RpcMessage.Instant(false, "新密码不能包含空格!");
        }
        String salt = userInfo.getString("salt");
        String encode_password = userInfo.getString("password");
        if (!UserModel.checkPassword(uid, old_password, salt, encode_password)) {
            return RpcMessage.Instant(false, "当前密码错误!");
        }
        JSONObject info = JSONObject.build();
        info.put("password", UserModel.EncodePassword(uid, new_password, salt));
        if (!db.eq("user_id", uid).data(info).update()) {
            return RpcMessage.Instant(false, "修改密码失败!");
        }
        return RpcMessage.Instant(true, "修改成功!");
    }
}

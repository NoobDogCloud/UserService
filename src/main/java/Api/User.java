package main.java.Api;

import Model.UserModel;
import common.java.InterfaceModel.Type.InterfaceType;
import common.java.Jwt.JwtInfo;
import common.java.Rpc.RpcMessage;
import common.java.ServiceTemplate.MicroServiceTemplate;
import org.json.gsc.JSONObject;

public class User extends MicroServiceTemplate {
    public User() {
        super("user");
    }

    /**
     * 用户登录接口
     */
    @InterfaceType(InterfaceType.type.OauthApi)
    public Object verify(String user_id, String password) {
        JSONObject userInfo = db.eq("user_id", user_id).find();
        if (JSONObject.isInvalided(userInfo)) {
            return RpcMessage.Instant(false, "当前用户不存在!");
        }
        String salt = userInfo.getString("salt");
        String encode_password = userInfo.getString("password");
        if (!UserModel.checkPassword(user_id, password, salt, encode_password)) {
            return RpcMessage.Instant(false, "当前密码错误!");
        }
        return JwtInfo.build(user_id).encodeJwt(userInfo).toString();
    }
}

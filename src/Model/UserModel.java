package Model;

import common.java.Apps.AppContext;
import common.java.Encrypt.Md5;
import org.json.gsc.JSONObject;

public class UserModel {
    public static String EncodePassword(String userId, String password, String salt) {
        return Md5.build(password + "." + salt + "#" + userId);
    }

    public static boolean checkPassword(String userId, String password, String salt, String encode_password) {
        return EncodePassword(userId, password, salt).equals(encode_password);
    }

    public static boolean checkRoles(String groupId) {
        return AppContext.current().roles().hasRole(groupId);
    }

    public static JSONObject filterUserInfo(JSONObject userInfo) {
        JSONObject user = JSONObject.build(userInfo);
        user.remove("password");
        user.remove("salt");
        return user;
    }
}

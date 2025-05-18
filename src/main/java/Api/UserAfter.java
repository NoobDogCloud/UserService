package main.java.Api;

import Model.UserModel;
import common.java.Rpc.RpcAfter;
import org.json.gsc.JSONArray;
import org.json.gsc.JSONObject;

public class UserAfter extends RpcAfter {
    public UserAfter() {
        output((JSONArray<JSONObject> result) -> {
            for (JSONObject userInfo : result) {
                UserModel.filterUserInfo(userInfo);
            }
            return result;
        });
    }
}

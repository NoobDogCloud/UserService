package main.java.Api;

import Model.UserModel;
import common.java.Rpc.FilterReturn;
import common.java.Rpc.RpcBefore;
import common.java.Rpc.RpcJsonFilterHelper;
import common.java.String.StringHelper;

public class UserBefore extends RpcBefore {
    public UserBefore() {
        input((data, ids) -> {
            boolean isUpdate = ids != null;
            FilterReturn r = RpcJsonFilterHelper.build(data, isUpdate)
                    // 新增时,判断是否包含密码校验,并生成salt,加密密码
                    .filter("password", (info, name) -> {
                        if (!data.get("password").equals(data.get("password1"))) {
                            return FilterReturn.fail("输入的2次密码不一致");
                        }
                        String salt = StringHelper.randomString(8);
                        String encode_password = UserModel.EncodePassword(info.getString("user_id"), info.getString("password"), salt);
                        data.put("password", encode_password).put("salt", salt);
                        return FilterReturn.success();
                    })
                    .filterUnique("user_id", "_id", line -> {
                        var api = new User();
                        return api.getPureDB().limit(2).eq("user_id", line.getString("user_id")).select();
                    })
                    .filter("group_id", (info, name) -> UserModel.checkRoles(info.getString("group_id")) ? FilterReturn.success() : FilterReturn.fail("无效角色"),
                            true,
                            "未定义用户角色")
                    .check()
                    .getResult();
            if (!r.isSuccess()) {
                return r;
            }
            // 删除密码重复块
            data.remove("password1");
            return FilterReturn.success();
        });

    }
}

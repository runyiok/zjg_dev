package com.zjg.dev.service;

public interface ISipService {


    // 注册SIP第三方

    /**
     * @param e164          sip账号
     * @param password      密码
     * @param displayNumber
     * @param memo
     * @return
     */
    String createPhone(String e164, String password, String displayNumber, String memo);

    // 4.1.3查询坐席注册在线状态接口
    String getPhoneOnline(String[] e164s);

    // 1.调用SIP服务器，启用禁用SIP账户()

    /**
     * 0：无锁定
     * 1：锁定呼出
     * 2：锁定呼入
     * 3：全部锁定
     *
     * @param e164
     * @param lockType
     * @return
     */
    String modifyPhone(String e164, short lockType);

    // 1.删除SIP服务器账户
    String deletePhone(String e164);
}

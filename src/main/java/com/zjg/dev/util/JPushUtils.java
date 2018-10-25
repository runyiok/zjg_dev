package com.zjg.dev.util;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.push.model.notification.WinphoneNotification;
import com.zjg.dev.domain.JPushConf;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class JPushUtils {

    /**
     * 推送对象前缀(D_：设备， A_：APP).<br>
     */
    private final static String[] PREFIXS = {"CSH_D_", "CSH_A_"};
    private static ExecutorService pool = Executors.newCachedThreadPool();
    private static JPushClient jpushClient = new JPushClient(JPushConf.secret, JPushConf.appkey);

    private static boolean validate(PushTarget target) {
        if (target.code >= PREFIXS.length) {
            log.info("发送对象target[" + target + "]不存在");
            return false;
        }
        return true;
    }

    private static int jpushMessage(String content, PushTarget target, boolean isTag, Map<String, String> extras, String... auds) {
        if (!validate(target)) return 1;
        if (null == extras) extras = new HashMap<String, String>();
        buildAudiences(target, auds);
        int jRet = 0;
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.all())
                .setAudience(isTag ? Audience.tag(auds) : Audience.alias(auds))
                .setMessage(Message.newBuilder().setMsgContent(content).addExtras(extras).build())
                .setOptions(Options.newBuilder().setTimeToLive(JPushConf.devsLiveTime).build()).build();
        try {
            // 1:连接超时。2:读取超时。3:无法解析域名。
            //解析result数据
            PushResult result = jpushClient.sendPush(payload);
        } catch (APIConnectionException e) {
            e.printStackTrace();
            jRet = 1;
        } catch (APIRequestException e) {
            e.printStackTrace();
            jRet = 1;
        }
        return jRet;
    }

    /**
     * 极光推送消息
     *
     * @param content 推送内容
     * @param target  推送对象，0：设备，1：APP see
     *                {@link PushTarget
     *                PushTarget}
     * @param isTag   是否是tag推送，否则为alias方式
     * @param auds    推送对象集合
     * @return
     * @see PushTarget
     */
    public static int pushMessage(String content, PushTarget target, boolean isTag, Map<String, String> extras, String... auds) {
        log.info("极光推送：content=" + content + " target=" + target + "  isTag=" + isTag + " extras=" + extras + " auds=" + auds);
        if (target.code == PushTarget.TARGET_DEVS.code) {
            return devsPushMessage(content, target, isTag, extras, auds);
        } else {
            return jpushMessage(content, target, isTag, extras, auds);
        }

    }

    private static int devsPushMessage(String content, PushTarget target, boolean isTag, Map<String, String> extras, String... auds) {
        CountDownLatch cdl = new CountDownLatch(2);
        Runner jpush = new Runner(cdl, content, target, isTag, extras, auds);
        pool.execute(jpush);
        try {
            cdl.await();
            return jpush.getResult();
        } catch (InterruptedException e) {
            log.info(e.getMessage());
        }
        return 1;
    }

    /**
     * 极光推送通知
     *
     * @param content 通知内容
     * @param title   通知标题
     * @param target  推送对象 see
     *                {@link PushTarget
     *                PushTarget}
     * @param extras  推送额外参数
     * @param isTag   是否是Tag方式推送，否则为alias
     * @param auds    推送对象的Tag或alias数组
     * @return
     * @see PushTarget
     */
    public static int pushNotify(String content, String title, PushTarget target, Map<String, String> extras, boolean isTag, String... auds) {
        if (!validate(target)) {
            return 1;
        }
        if (null == extras)
            extras = new HashMap<String, String>();
        buildAudiences(target, auds);
        int ret = 0;
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.all())
                .setAudience(isTag ? Audience.tag(auds) : Audience.alias(auds))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(
                                IosNotification.newBuilder().setAlert(content).addExtras(extras).setSound(JPushConf.sound).build())
                        .addPlatformNotification(AndroidNotification.newBuilder().setAlert(content).setTitle(title).addExtras(extras).build())
                        .addPlatformNotification(
                                WinphoneNotification.newBuilder().setAlert(content).setTitle(title).addExtras(extras).build())
                        .build())
                .setOptions(Options.newBuilder().setApnsProduction(false).build()).build();
        try {
            PushResult result = jpushClient.sendPush(payload);
            log.info("极光推送结果==>>" + result);
        } catch (APIConnectionException e) {
            log.info(e.getMessage());
            ret = 1;
        } catch (APIRequestException e) {
            //showAPIRequestExceptionMessage( e );
            ret = 1;
        }
        return ret;
    }

    private static void buildAudiences(PushTarget target, String... auds) {
        for (int i = 0, l = auds.length; i < l; i++) {
            auds[i] = PREFIXS[target.code] + auds[i];
        }
    }

    public static Map<String, String> buildExtras(String[] keys, String[] vals) {
        Map<String, String> extras = new HashMap<String, String>();
        if (keys == null || vals == null) {
            return extras;
        }
        if (keys.length != vals.length) {
            return extras;
        }
        int i = 0;
        for (String key : keys) {
            extras.put(key, vals[i++]);
        }
        return extras;
    }


    /**
     * 推送对象为设备. -> TARGET_DEVS 推送对象为APP. -> TARGET_APP
     *
     * @author luoyh
     * @date Jun 24, 2015
     */
    public static enum PushTarget {
        /**
         * 推送对象为设备
         */
        TARGET_DEVS(0),
        /**
         * 推送对象为APP
         */
        TARGET_APP(1);
        private int code;

        private PushTarget(int code) {
            this.code = code;
        }
    }

    /**
     * 线程跑消息推送
     */
    private static class Runner implements Runnable {
        private int result = -1;
        private CountDownLatch cdl;
        private String content;
        private PushTarget target;
        private boolean isTag;
        private Map<String, String> extras;
        private String[] auds;

        public Runner(CountDownLatch cdl, String content, PushTarget target, boolean isTag, Map<String, String> extras, String... auds) {
            this.cdl = cdl;
            this.result = -1;
            this.content = content;
            this.target = target;
            this.isTag = isTag;
            this.extras = extras;
            this.auds = auds;
        }

        @Override
        public void run() {
            result = jpushMessage(content, target, isTag, extras, auds);
            cdl.countDown();
            if (result == 0) {
                cdl.countDown();
            }
        }

        public int getResult() {
            return result;
        }
    }

}

/*
 * MIT License
 *
 * Copyright (c) 2021 苗锦洲
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package tech.ordinaryroad.push.service;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.schedule.ScheduleResult;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.ordinaryroad.push.properties.OrPushProperties;

import java.util.List;

/**
 * 极光推送服务类 <a href="https://docs.jiguang.cn/jpush/server/push/rest_api_v3_push">文档</a>
 *
 * @author mjz
 * @date 2022/3/13
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class JPushService {

    private final OrPushProperties pushProperties;

    /**
     * ApnsProduction
     * 此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
     * 上线之后要改为 true
     */
    private final static Boolean APNS_PRODUCTION = true;
    private volatile JPushClient jPushClient;
    private final static String SCHEDULE_NAME = "OrdinaryRoad";

    private OrPushProperties.JPushProperties getJPushProperties(String packageName) {
        return pushProperties.getPackageNamePropertiesMap().get(packageName);
    }

    private String getAppKey(String packageName) {
        return getJPushProperties(packageName).getAppKey();
    }

    private String getMasterSecret(String packageName) {
        return getJPushProperties(packageName).getMasterSecret();
    }

    private JPushClient getJPushClient(String packageName) {
        if (jPushClient == null) {
            synchronized (JPushService.class) {
                if (jPushClient == null) {
                    jPushClient = new JPushClient(getMasterSecret(packageName), getAppKey(packageName));
                }
            }
        }
        return jPushClient;
    }

    /**
     * 推送给设备标识(别名)参数的用户
     *
     * @param packageName       应用包名
     * @param alias             设备标识(别名)
     * @param notificationTitle 通知内容标题
     * @param msgTitle          消息内容标题
     * @param msgContent        消息内容
     * @param extras            扩展字段（通常传跳转的链接）
     * @return 0推送失败，1推送成功
     */
    public int sendToAlias(String packageName, List<String> alias, String notificationTitle, String msgTitle, String msgContent, String channel, JsonObject intent, JsonObject extras) {
        int result = 0;
        try {
            PushPayload pushPayload = JPushService.buildPushObjectAllAliasAlertWithTitle(alias, notificationTitle, msgTitle, msgContent, channel, intent, extras);
            PushResult pushResult = getJPushClient(packageName).sendPush(pushPayload);
            if (pushResult.isResultOK()) {
                result = 1;
            }
            log.info("[极光推送]PushResult result is " + pushResult);
        } catch (APIConnectionException e) {
            log.error("[极光推送]Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            log.error("[极光推送]Error response from JPush server. Should review and fix it. ", e);
            log.info("[极光推送]HTTP Status: " + e.getStatus());
            log.info("[极光推送]Error Code: " + e.getErrorCode());
            log.info("[极光推送]Error Message: " + e.getErrorMessage());
        }
        return result;
    }

    /**
     * 推送给设备标识(别名)参数的用户（定时）
     *
     * @param packageName       应用包名
     * @param alias             设备标识(别名)
     * @param notificationTitle 通知内容标题
     * @param msgTitle          消息内容标题
     * @param msgContent        消息内容
     * @param extras            扩展字段
     * @param time              格式:yyyy-MM-dd HH:mm:ss
     * @return 0推送失败，1推送成功
     */
    public int sendToAliasScheduled(String packageName, List<String> alias, String notificationTitle, String msgTitle, String msgContent, String channel, JsonObject intent, JsonObject extras, String time) {
        int result = 0;
        try {
            PushPayload pushPayload = JPushService.buildPushObjectAllAliasAlertWithTitle(alias, notificationTitle, msgTitle, msgContent, channel, intent, extras);
            ScheduleResult scheduleResult = getJPushClient(packageName).createSingleSchedule(SCHEDULE_NAME, time, pushPayload, getMasterSecret(packageName), getAppKey(packageName));
            if (scheduleResult.isResultOK()) {
                result = 1;
            }
            log.info("[极光推送]ScheduleResult result is " + scheduleResult);
        } catch (APIConnectionException e) {
            log.error("[极光推送]Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            log.error("[极光推送]Error response from JPush server. Should review and fix it. ", e);
            log.info("[极光推送]HTTP Status: " + e.getStatus());
            log.info("[极光推送]Error Code: " + e.getErrorCode());
            log.info("[极光推送]Error Message: " + e.getErrorMessage());
        }
        return result;
    }

    /**
     * 推送给设备RegistrationIds参数的用户
     *
     * @param packageName        应用包名
     * @param registrationIdList 设备注册ID
     * @param notificationTitle  通知内容标题
     * @param msgTitle           消息内容标题
     * @param msgContent         消息内容
     * @param extras             扩展字段
     * @return 0推送失败，1推送成功
     */
    public int sendToRegistrationIds(String packageName, List<String> registrationIdList, String notificationTitle, String msgTitle, String msgContent, String channel, JsonObject intent, JsonObject extras) {
        int result = 0;
        try {
            PushPayload pushPayload = JPushService.buildPushObjectAllRegistrationIdsAlertWithTitle(registrationIdList, notificationTitle, msgTitle, msgContent, channel, intent, extras);
            PushResult pushResult = getJPushClient(packageName).sendPush(pushPayload);
            if (pushResult.isResultOK()) {
                result = 1;
            }
            log.info("[极光推送]PushResult result is " + pushResult);
        } catch (APIConnectionException e) {
            log.error("[极光推送]Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            log.error("[极光推送]Error response from JPush server. Should review and fix it. ", e);
            log.info("[极光推送]HTTP Status: " + e.getStatus());
            log.info("[极光推送]Error Code: " + e.getErrorCode());
            log.info("[极光推送]Error Message: " + e.getErrorMessage());
        }
        return result;
    }

    /**
     * 发送给所有安卓用户
     *
     * @param packageName       应用包名
     * @param notificationTitle 通知内容标题
     * @param msgTitle          消息内容标题
     * @param msgContent        消息内容
     * @param extras            扩展字段
     * @return 0推送失败，1推送成功
     */
    public int sendToAllAndroid(String packageName, String notificationTitle, String msgTitle, String msgContent, String channel, JsonObject intent, JsonObject extras) {
        int result = 0;
        try {
            PushPayload pushPayload = JPushService.buildPushObjectAndroidAllAlertWithTitle(notificationTitle, msgTitle, msgContent, channel, intent, extras);
            PushResult pushResult = getJPushClient(packageName).sendPush(pushPayload);
            if (pushResult.isResultOK()) {
                result = 1;
            }
            log.info("[极光推送]PushResult result is " + pushResult);
        } catch (APIConnectionException e) {
            log.error("[极光推送]Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            log.error("[极光推送]Error response from JPush server. Should review and fix it. ", e);
            log.info("[极光推送]HTTP Status: " + e.getStatus());
            log.info("[极光推送]Error Code: " + e.getErrorCode());
            log.info("[极光推送]Error Message: " + e.getErrorMessage());
        }
        return result;
    }

    /**
     * 发送给所有IOS用户
     *
     * @param packageName       应用包名
     * @param notificationTitle 通知内容标题
     * @param msgTitle          消息内容标题
     * @param msgContent        消息内容
     * @param extras            扩展字段
     * @return 0推送失败，1推送成功
     */
    public int sendToAllIos(String packageName, String notificationTitle, String msgTitle, String msgContent, JsonObject intent, JsonObject extras) {
        int result = 0;
        try {
            PushPayload pushPayload = JPushService.buildPushObjectIosAllAlertWithTitle(notificationTitle, msgTitle, msgContent, intent, extras);
            PushResult pushResult = getJPushClient(packageName).sendPush(pushPayload);
            if (pushResult.isResultOK()) {
                result = 1;
            }
            log.info("[极光推送]PushResult result is " + pushResult);
        } catch (APIConnectionException e) {
            log.error("[极光推送]Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            log.error("[极光推送]Error response from JPush server. Should review and fix it. ", e);
            log.info("[极光推送]HTTP Status: " + e.getStatus());
            log.info("[极光推送]Error Code: " + e.getErrorCode());
            log.info("[极光推送]Error Message: " + e.getErrorMessage());
        }
        return result;
    }

    /**
     * 发送给所有用户
     *
     * @param packageName       应用包名
     * @param notificationTitle 通知内容标题
     * @param msgTitle          消息内容标题
     * @param msgContent        消息内容
     * @param extras            扩展字段
     * @return 0推送失败，1推送成功
     */
    public int sendToAll(String packageName, String notificationTitle, String msgTitle, String msgContent, String channel, JsonObject intent, JsonObject extras) {
        int result = 0;
        try {
            PushPayload pushPayload = JPushService.buildPushObjectAndroidAndIos(notificationTitle, msgTitle, msgContent, channel, intent, extras);
            PushResult pushResult = getJPushClient(packageName).sendPush(pushPayload);
            if (pushResult.isResultOK()) {
                result = 1;
            }
            log.info("[极光推送]PushResult result is " + pushResult);
        } catch (APIConnectionException e) {
            log.error("[极光推送]Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            log.error("[极光推送]Error response from JPush server. Should review and fix it. ", e);
            log.info("[极光推送]HTTP Status: " + e.getStatus());
            log.info("[极光推送]Error Code: " + e.getErrorCode());
            log.info("[极光推送]Error Message: " + e.getErrorMessage());
        }
        return result;
    }

    /**
     * 发送给所有用户(定时推送)
     *
     * @param packageName       应用包名
     * @param notificationTitle 通知内容标题
     * @param msgTitle          消息内容标题
     * @param msgContent        消息内容
     * @param extras            扩展字段
     * @param time              格式:yyyy-MM-dd HH:mm:ss
     * @return 0推送失败，1推送成功
     */
    public int sendToAllScheduled(String packageName, String notificationTitle, String msgTitle, String msgContent, String channel, JsonObject intent, JsonObject extras, String time) {
        int result = 0;
        try {
            PushPayload pushPayload = JPushService.buildPushObjectAndroidAndIos(notificationTitle, msgTitle, msgContent, channel, intent, extras);
            ScheduleResult scheduleResult = getJPushClient(packageName).createSingleSchedule(SCHEDULE_NAME, time, pushPayload, getMasterSecret(packageName), getAppKey(packageName));
            if (scheduleResult.isResultOK()) {
                result = 1;
            }
            log.info("[极光推送]scheduleResult result is " + scheduleResult);
        } catch (APIConnectionException e) {
            log.error("[极光推送]Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            log.error("[极光推送]Error response from JPush server. Should review and fix it. ", e);
            log.info("[极光推送]HTTP Status: " + e.getStatus());
            log.info("[极光推送]Error Code: " + e.getErrorCode());
            log.info("[极光推送]Error Message: " + e.getErrorMessage());
        }
        return result;
    }

    public static PushPayload buildPushObjectAndroidAndIos(String notificationTitle, String msgTitle, String msgContent, String channel, JsonObject intent, JsonObject extras) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .setAlert(msgContent)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(msgContent)
                                .setTitle(notificationTitle)
                                .setChannelId(channel)
                                .setIntent(intent)
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("extras", extras)
                                .build()
                        )
                        .addPlatformNotification(IosNotification.newBuilder()
                                //传一个IosAlert对象，指定apns title、title、subtitle等
                                .setAlert(msgContent)
                                //直接传alert
                                //此项是指定此推送的badge自动加1
                                .incrBadge(1)
                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
                                .setSound("default")
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("extras", extras)
                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
                                // .setContentAvailable(true)
                                .build()
                        )
                        .build()
                )
                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .setMessage(Message.newBuilder()
                        .setMsgContent(msgContent)
                        .setTitle(msgTitle)
                        .addExtra("extras", extras)
                        .build())
                .setOptions(Options.newBuilder()
                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(APNS_PRODUCTION)
                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
                        .setTimeToLive(86400)
                        .build()
                )
                .build();
    }

    private static PushPayload buildPushObjectAllAliasAlertWithTitle(List<String> alias, String notificationTitle, String msgTitle, String msgContent, String channel, JsonObject intent, JsonObject extras) {
        //创建一个IosAlert对象，可指定APNs的alert、title等字段
        //IosAlert iosAlert =  IosAlert.newBuilder().setTitleAndBody("title", "alert body").build();
        return PushPayload.newBuilder()
                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                .setPlatform(Platform.all())
                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
                .setAudience(Audience.alias(alias))
                //.setAudience(Audience.registrationId(registrationId))
                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
                .setNotification(Notification.newBuilder()
                        //指定当前推送的android通知
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(msgContent)
                                .setTitle(notificationTitle)
                                .setChannelId(channel)
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("extras", extras)
                                .build())
                        //指定当前推送的iOS通知
                        .addPlatformNotification(IosNotification.newBuilder()
                                //传一个IosAlert对象，指定apns title、title、subtitle等
                                .setAlert(msgContent)
                                //直接传alert
                                //此项是指定此推送的badge自动加1
                                .incrBadge(1)
                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
                                .setSound("default")
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("extras", extras)
                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
                                //取消此注释，消息推送时ios将无法在锁屏情况接收
                                // .setContentAvailable(true)
                                .build())
                        .build())
                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .setMessage(Message.newBuilder()
                        .setMsgContent(msgContent)
                        .setTitle(msgTitle)
                        .addExtra("extras", extras)
                        .build())
                .setOptions(Options.newBuilder()
                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(APNS_PRODUCTION)
                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天；
                        .setTimeToLive(86400)
                        .build())
                .build();
    }

    private static PushPayload buildPushObjectAllRegistrationIdsAlertWithTitle(List<String> registrationIds, String notificationTitle, String msgTitle, String msgContent, String channel, JsonObject intent, JsonObject extras) {
        //创建一个IosAlert对象，可指定APNs的alert、title等字段
        //IosAlert iosAlert =  IosAlert.newBuilder().setTitleAndBody("title", "alert body").build();
        return PushPayload.newBuilder()
                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                .setPlatform(Platform.all())
                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
                .setAudience(Audience.registrationId(registrationIds))
                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
                .setNotification(Notification.newBuilder()
                        //指定当前推送的android通知
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(msgContent)
                                .setTitle(notificationTitle)
                                .setChannelId(channel)
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("extras", extras)
                                .build())
                        //指定当前推送的iOS通知
                        .addPlatformNotification(IosNotification.newBuilder()
                                //传一个IosAlert对象，指定apns title、title、subtitle等
                                .setAlert(msgContent)
                                //直接传alert
                                //此项是指定此推送的badge自动加1
                                .incrBadge(1)
                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
                                .setSound("default")
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("extras", extras)
                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
                                //取消此注释，消息推送时ios将无法在锁屏情况接收
                                // .setContentAvailable(true)
                                .build())
                        .build())
                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .setMessage(Message.newBuilder()
                        .setMsgContent(msgContent)
                        .setTitle(msgTitle)
                        .addExtra("extras", extras)
                        .build())
                .setOptions(Options.newBuilder()
                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(APNS_PRODUCTION)
                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天；
                        .setTimeToLive(86400)
                        .build())
                .build();
    }

    private static PushPayload buildPushObjectAndroidAllAlertWithTitle(String notificationTitle, String msgTitle, String msgContent, String channel, JsonObject intent, JsonObject extras) {
        return PushPayload.newBuilder()
                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                .setPlatform(Platform.android())
                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
                .setAudience(Audience.all())
                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
                .setNotification(Notification.newBuilder()
                        //指定当前推送的android通知
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(msgContent)
                                .setTitle(notificationTitle)
                                .setChannelId(channel)
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("extras", extras)
                                .build())
                        .build()
                )
                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .setMessage(Message.newBuilder()
                        .setMsgContent(msgContent)
                        .setTitle(msgTitle)
                        .addExtra("extras", extras)
                        .build())
                .setOptions(Options.newBuilder()
                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(APNS_PRODUCTION)
                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
                        .setTimeToLive(86400)
                        .build())
                .build();
    }

    private static PushPayload buildPushObjectIosAllAlertWithTitle(String notificationTitle, String msgTitle, String msgContent, JsonObject intent, JsonObject extras) {
        return PushPayload.newBuilder()
                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                .setPlatform(Platform.ios())
                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
                .setAudience(Audience.all())
                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
                .setNotification(Notification.newBuilder()
                        //指定当前推送的android通知
                        .addPlatformNotification(IosNotification.newBuilder()
                                //传一个IosAlert对象，指定apns title、title、subtitle等
                                .setAlert(msgContent)
                                //直接传alert
                                //此项是指定此推送的badge自动加1
                                .incrBadge(1)
                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
                                .setSound("default")
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("extras", extras)
                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
                                // .setContentAvailable(true)
                                .build())
                        .build()
                )
                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .setMessage(Message.newBuilder()
                        .setMsgContent(msgContent)
                        .setTitle(msgTitle)
                        .addExtra("extras", extras)
                        .build())
                .setOptions(Options.newBuilder()
                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(APNS_PRODUCTION)
                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
                        .setTimeToLive(86400)
                        .build())
                .build();
    }
}
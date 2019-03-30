package com.yuxh.login.service;

import com.alibaba.fastjson.JSONObject;
import com.yuxh.blog.contact.BaseJsonResult;
import com.yuxh.login.domain.User;
import com.yuxh.login.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class QQAuthService {

    private static final String QQ_APPID = "101396891";
    private static final String QQ_APPKEY = "3fa086c5dabee3792f1f22f50fc27bf2";
    private static final String AUTH_URL = "https://graph.qq.com/oauth2.0/authorize";
    private static final String ACCESS_TOKEN_URL = "https://graph.qq.com/oauth2.0/token";
    private static final String QQ_OPENID_URL = "https://graph.qq.com/oauth2.0/me";
    private static final String QQ_USERINFO_URL = "https://graph.qq.com/user/get_user_info";
    private static final String QQ_CALLBACK = "http://www.5imiku.com/auth/qq/callback";
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private UserRepository userRepository;


    public void getAuthorizationCode() throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<>();
        params.put("response_type", "code");
        params.put("client_id", QQ_APPID);
        params.put("redirect_uri", URLEncoder.encode(QQ_CALLBACK, "UTF-8"));
        params.put("state", "imiku");
        //params.put("scope", "");
        //params.put("display", "");
        String rest = restTemplate.getForObject(AUTH_URL, String.class, params);
        log.debug(rest);
    }

    public BaseJsonResult getAccessToken(String code) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "authorization_code");
        params.put("client_id", QQ_APPID);
        params.put("client_secret", QQ_APPKEY);
        params.put("code", code);
        params.put("redirect_uri", URLEncoder.encode(QQ_CALLBACK, "UTF-8"));
        //params.put("scope", "");
        //params.put("display", "");
        String rest = restTemplate.getForObject(ACCESS_TOKEN_URL, String.class, params);
        log.debug("QQaccessToken接口返回：" + rest);
        JSONObject jso = JSONObject.parseObject(rest);
        if (jso.containsKey("code")) {
            return BaseJsonResult.error(jso.getString("code"));
        } else {
            String accessToken = jso.getString("access_token");
            int expiresIn = jso.getIntValue("expires_in");
            String refreshToken = jso.getString("refresh_token");
            return saveQQOpenId(accessToken, expiresIn, refreshToken);
        }


    }

    private BaseJsonResult saveQQOpenId(String accessToken, int expiresIn, String refreshToken) {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", accessToken);
        String rest = restTemplate.getForObject(QQ_OPENID_URL, String.class, params);
        log.debug("QQopenid接口返回：" + rest);
        JSONObject jso = JSONObject.parseObject(rest);
        if (jso.containsKey("code")) {
            return BaseJsonResult.error(jso.getString("code"));
        } else {
            String openid = jso.getString("openid");
            User user = new User();
            UUID uuid = UUID.randomUUID();
            String id = uuid.toString().replace("-", "");
            user.setId(id);
            user.setAccessToken(accessToken);
            user.setOpenid(openid);
            return saveQQUserInfo(user);
        }
    }


    private BaseJsonResult saveQQUserInfo(User user) {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", user.getAccessToken());
        params.put("oauth_consumer_key", QQ_APPKEY);
        params.put("openid", user.getOpenid());
        String rest = restTemplate.getForObject(QQ_USERINFO_URL, String.class, params);
        log.debug("QQ用户接口返回：" + rest);
        JSONObject jso = JSONObject.parseObject(rest);
        BaseJsonResult res;
        if (jso.containsKey("code")) {
            res = BaseJsonResult.error(jso.getString("code"));
        } else {
            res = BaseJsonResult.success();
            String nickname = jso.getString("nickname");
            user.setNickname(nickname);
        }
        userRepository.save(user);
        return res;
    }
}

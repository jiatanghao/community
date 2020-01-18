package com.zte.oes.community.provider;

import com.alibaba.fastjson.JSON;
import com.zte.oes.community.dto.AccessTokenDto;
import com.zte.oes.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class GithubProvider {

    public static final MediaType MEDIA_TYPE = MediaType.get("application/json; charset=UTF-8");
    private OkHttpClient okHttpClient = new OkHttpClient();

    public String getAccessToken(AccessTokenDto dto) {
        RequestBody requestBody = RequestBody.create(JSON.toJSONString(dto), MEDIA_TYPE);
        String url = "https://github.com/login/oauth/access_token";
        Request request = new Request.Builder().url(url).post(requestBody).build();
        try(Response response = okHttpClient.newCall(request).execute()) {
            String body = Objects.requireNonNull(response.body()).string();
            return body.split("&")[0].split("=")[1];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public GithubUser getGithubUser(String accessToken) {
        String url = "https://api.github.com/user?access_token=" + accessToken;
        Request request = new Request.Builder().url(url).get().build();
        try(Response response = okHttpClient.newCall(request).execute()) {
            String body = Objects.requireNonNull(response.body()).string();
            return JSON.parseObject(body, GithubUser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

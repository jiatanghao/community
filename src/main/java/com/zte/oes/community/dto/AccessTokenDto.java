package com.zte.oes.community.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class AccessTokenDto {
    @NonNull
    @JSONField(name = "client_id")
    private String clientId;
    @NonNull
    @JSONField(name = "client_secret")
    private String clientSecret;
    @NonNull
    private String code;
    @JSONField(name = "redirect_uri")
    private String redirectUri;
    private String state;
}

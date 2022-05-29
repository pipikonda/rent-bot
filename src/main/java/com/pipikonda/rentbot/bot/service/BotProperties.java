package com.pipikonda.rentbot.bot.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Data
@ConfigurationProperties(prefix = "telegram.bot")
@Component
public class BotProperties {

    String url;
    String callbackUrl;

    public String getTelegramUrl() {
        return normalizePath(url);
    }

    private String normalizePath(String url) {
        return StringUtils.hasLength(url) && url.endsWith("/") ? url : url + "/";
    }
}

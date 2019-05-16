package com.yy.lieyin.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "lieyin")
public class LieyinCoreProperties {
    private WebProperties web = new WebProperties();

    public WebProperties getWeb() {
        return web;
    }

    public void setWeb(WebProperties web) {
        this.web = web;
    }
}

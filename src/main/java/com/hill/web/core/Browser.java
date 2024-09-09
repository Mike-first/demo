package com.hill.web.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Browser {
    @JsonProperty("versions")
    private Map<String, BrowserVersion> versions;

    @Getter
    @Setter
    public static class BrowserVersion {
        private String image;
        private String port;
        private String path;
        private int maxInstances;
        private String platform;
        private String displayName;
        private int launchTimeout;
        private int connectRetryLimit;
        private boolean enableVNC;
        private boolean enableVideo;
    }
}




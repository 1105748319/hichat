package com.hichat.mobile.util;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="custom")
public class ConstantProperties {

    private String imgUploadPath;

    private String txImgPath;

    private String tioServerIp;

    private String tioServerPort;

    private Long tioTimeout;

    private String essayImgPath;

    private String messageImgPath;

    public String getMessageImgPath() {
        return messageImgPath;
    }

    public void setMessageImgPath(String messageImgPath) {
        this.messageImgPath = messageImgPath;
    }

    public String getEssayImgPath() {
        return essayImgPath;
    }

    public void setEssayImgPath(String essayImgPath) {
        this.essayImgPath = essayImgPath;
    }

    public String getTioServerIp() {
        return tioServerIp;
    }

    public void setTioServerIp(String tioServerIp) {
        this.tioServerIp = tioServerIp;
    }

    public String getTioServerPort() {
        return tioServerPort;
    }

    public void setTioServerPort(String tioServerPort) {
        this.tioServerPort = tioServerPort;
    }

    public Long getTioTimeout() {
        return tioTimeout;
    }

    public void setTioTimeout(Long tioTimeout) {
        this.tioTimeout = tioTimeout;
    }

    public String getImgUploadPath() {
        return imgUploadPath;
    }

    public void setImgUploadPath(String imgUploadPath) {
        this.imgUploadPath = imgUploadPath;
    }

    public String getTxImgPath() {
        return txImgPath;
    }

    public void setTxImgPath(String txImgPath) {
        this.txImgPath = txImgPath;
    }
}

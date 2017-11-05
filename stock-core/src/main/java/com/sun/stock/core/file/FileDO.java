package com.sun.stock.core.file;

import io.netty.channel.DefaultFileRegion;

import java.io.Serializable;

/**
 * Created by zksun on 2017/8/19.
 */
public class FileDO implements Serializable {
    byte type;
    Integer code;
    Long time;
    int length;

    byte[] document;

    DefaultFileRegion region;

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public DefaultFileRegion getRegion() {
        return region;
    }

    public void setRegion(DefaultFileRegion region) {
        this.region = region;
    }
}

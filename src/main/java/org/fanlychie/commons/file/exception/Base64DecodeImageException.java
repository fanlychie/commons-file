package org.fanlychie.commons.file.exception;

/**
 * Base64 解码图片异常
 * Created by fanlychie on 2017/1/12.
 */
public class Base64DecodeImageException extends RuntimeException {

    public Base64DecodeImageException(String message) {
        super(message);
    }

}
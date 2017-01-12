package org.fanlychie.commons.file.exception;

/**
 * Base64 编码图片异常
 * Created by fanlychie on 2017/1/12.
 */
public class Base64EncodeImageException extends RuntimeException {

    public Base64EncodeImageException(String message) {
        super(message);
    }

}
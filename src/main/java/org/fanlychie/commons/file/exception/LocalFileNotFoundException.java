package org.fanlychie.commons.file.exception;

/**
 * 本地文件找不到异常
 * Created by fanlychie on 2017/1/15.
 */
public class LocalFileNotFoundException extends RuntimeException {

    public LocalFileNotFoundException(String message) {
        super(message);
    }

}
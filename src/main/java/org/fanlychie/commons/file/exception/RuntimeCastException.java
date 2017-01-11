package org.fanlychie.commons.file.exception;

/**
 * 运行时异常, 它内部包装了真实的非运行时异常对象, 通过 getCause 来取出真实异常
 *
 * Created by 范忠云 on 2017/1/11.
 */
public class RuntimeCastException extends RuntimeException {

    private Throwable cause;

    public RuntimeCastException(Throwable throwable) {
        this.cause = throwable;
    }

    @Override
    public synchronized Throwable getCause() {
        return cause;
    }

}
package com.toocol.ssh.common.exception;

import lombok.Getter;

/**
 * @author ZhaoZhe (joezane.cn@gmail.com)
 * @date 2022/4/2 14:15
 */
public class ExceptionCatcher {
    @Getter
    private Exception exception;

    public ExceptionCatcher(){}

    public void catchException(Exception e) {
        this.exception = e;
    }

    public void throwOut() {
        if (exception != null) {
            throw new TerminatioException(exception);
        }
    }

    public boolean isCatch() {
        return exception != null;
    }
}

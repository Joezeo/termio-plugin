package com.toocol.ssh.common.exception;

/**
 * @author ZhaoZhe (joezane.cn@gmail.com)
 * @date 2022/4/2 14:18
 */
public class TerminatioException extends RuntimeException{

    public TerminatioException(Exception e) {
        super(e);
    }

    public TerminatioException(String message) {
        super(message);
    }
}

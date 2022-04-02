package com.toocol.ssh.core.terminatio;

import com.toocol.ssh.common.address.IAddress;
import lombok.AllArgsConstructor;

/**
 * @author ZhaoZhe (joezane.cn@gmail.com)
 * @date 2022/3/31 11:43
 */
@AllArgsConstructor
public enum ShellVerticleAddress implements IAddress{
    /**
     * start shell
     */
    START_SHELL("ssh.establish.session"),
    /**
     * exit shell
     */
    EXIT_SHELL("ssh.accept.shell.cmd");

    /**
     * the address string of message
     */
    private final String address;

    @Override
    public String address() {
        return address;
    }
}

package com.toocol.ssh.core.shell.commands;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ZhaoZhe (joezane.cn@gmail.com)
 * @date 2022/4/2 14:51
 */
public class ShellCommandCollector {
    public static final Set<? extends AbstractShellCommand> SHELL_COMMANDS_SET = new HashSet<>();

    public static Set<? extends AbstractShellCommand>  values() {
        return SHELL_COMMANDS_SET;
    }
}

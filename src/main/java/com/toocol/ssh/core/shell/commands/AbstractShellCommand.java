package com.toocol.ssh.core.shell.commands;

import com.toocol.ssh.common.utils.Printer;
import io.vertx.core.eventbus.EventBus;

import java.util.Optional;

import static com.toocol.ssh.core.shell.commands.ShellCommandCollector.values;

/**
 * @author ZhaoZhe
 * @email joezane.cn@gmail.com
 * @date 2021/2/22 13:21
 */
public abstract class AbstractShellCommand {
    private final String cmd;
    private final AbstractCommandProcessor commandProcessor;
    private final String comment;

    AbstractShellCommand(String cmd, AbstractCommandProcessor commandProcessor, String comment) {
        this.cmd = cmd;
        this.commandProcessor = commandProcessor;
        this.comment = comment;
    }

    public static Optional<AbstractShellCommand> cmdOf(String cmd) {
        AbstractShellCommand shellCommand = null;
        for (AbstractShellCommand command : values()) {
            if (command.cmdIsBelong(cmd)) {
                shellCommand = command;
            }
        }
        return Optional.ofNullable(shellCommand);
    }

    @SafeVarargs
    public final <T> void processCmd(EventBus eventBus, T... param) throws Exception {
        if (this.commandProcessor == null) {
            return;
        }
        this.commandProcessor.process(eventBus, param);
    }

    public String cmd() {
        return cmd;
    }

    public static void printHelp() {
        Printer.println();
        Printer.println("Shell commands:        [param] means optional param");
        for (AbstractShellCommand command : values()) {
            Printer.println("\t" + command.cmd + "\t\t-- " + command.comment);
        }
        Printer.println();
    }

    /**
     * Does this raw input cmd correspond to this ShellCommand.
     *
     * @param rawInputCmd raw input cmd
     * @return does or does not
     */
    protected abstract boolean cmdIsBelong(String rawInputCmd);
}

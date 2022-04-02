package com.toocol.ssh.core.command.commands.processors;

import com.toocol.ssh.common.utils.Printer;
import com.toocol.ssh.common.utils.Tuple;
import com.toocol.ssh.core.command.commands.AbstractCommandProcessor;
import io.vertx.core.eventbus.EventBus;

/**
 * @author ZhaoZhe (joezane.cn@gmail.com)
 * @date 2022/3/31 16:21
 */
public class HelpCmdProcessor extends AbstractCommandProcessor {
    @SafeVarargs
    @Override
    public final <T> void process(EventBus eventBus, T... param) throws Exception {
        Printer.printHelp();

        Tuple<Boolean, String> resultAndMsg = cast(param[1]);
        resultAndMsg.first(true);
    }
}

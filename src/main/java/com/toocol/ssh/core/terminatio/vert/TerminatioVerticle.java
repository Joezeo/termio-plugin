package com.toocol.ssh.core.terminatio.vert;

import com.toocol.ssh.common.annotation.FinalDeployment;
import com.toocol.ssh.common.annotation.RegisterHandler;
import com.toocol.ssh.common.handler.IHandlerMounter;
import com.toocol.ssh.core.terminatio.handlers.ExitShellHandler;
import com.toocol.ssh.core.terminatio.handlers.StartShellHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.WorkerExecutor;

/**
 * @author ZhaoZhe (joezane.cn@gmail.com)
 * @date 2022/4/2 15:04
 */
@FinalDeployment
@RegisterHandler(handlers = {
        ExitShellHandler.class,
        StartShellHandler.class
})
public class TerminatioVerticle extends AbstractVerticle implements IHandlerMounter {

    @Override
    public void start() throws Exception {
        WorkerExecutor executor = vertx.createSharedWorkerExecutor("terminatio-worker");
        mountHandler(vertx, executor);
    }
}

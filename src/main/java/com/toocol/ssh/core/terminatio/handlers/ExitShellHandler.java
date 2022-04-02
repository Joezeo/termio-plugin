package com.toocol.ssh.core.terminatio.handlers;

import com.toocol.ssh.common.address.IAddress;
import com.toocol.ssh.common.handler.AbstractMessageHandler;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.eventbus.Message;

import static com.toocol.ssh.core.terminatio.ShellVerticleAddress.EXIT_SHELL;

/**
 * This class must be implemented.
 *
 * @author ZhaoZhe (joezane.cn@gmail.com)
 * @date 2022/4/2 15:10
 */
public class ExitShellHandler extends AbstractMessageHandler<Void> {

    public ExitShellHandler(Vertx vertx, WorkerExecutor executor, boolean parallel) {
        super(vertx, executor, parallel);
    }

    @Override
    public IAddress consume() {
        return EXIT_SHELL;
    }

    @Override
    protected <T> void handleWithin(Future<Void> future, Message<T> message) throws Exception {
        // TODO: need provide external interface to package user
    }

    @Override
    protected <T> void resultWithin(AsyncResult<Void> asyncResult, Message<T> message) throws Exception {
        // TODO: need provide external interface to package user
    }
}

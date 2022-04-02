package com.toocol.ssh.core.terminatio.handlers;

import com.toocol.ssh.common.address.IAddress;
import com.toocol.ssh.common.handler.AbstractMessageHandler;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.eventbus.Message;

import static com.toocol.ssh.core.terminatio.ShellVerticleAddress.START_SHELL;

/**
 * Send message through eventBus to start shell connection;
 *
 * @author ZhaoZhe (joezane.cn@gmail.com)
 * @date 2022/4/2 15:13
 */
public class StartShellHandler extends AbstractMessageHandler<Void> {

    public StartShellHandler(Vertx vertx, WorkerExecutor executor, boolean parallel) {
        super(vertx, executor, parallel);
    }

    @Override
    public IAddress consume() {
        return START_SHELL;
    }

    @Override
    protected <T> void handleWithin(Future<Void> future, Message<T> message) throws Exception {
        // TODO: logic fulfillment.
    }

    @Override
    protected <T> void resultWithin(AsyncResult<Void> asyncResult, Message<T> message) throws Exception {
        // TODO: logic fulfillment.
    }
}

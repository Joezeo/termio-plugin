package com.toocol.ssh;

import cn.hutool.core.util.ClassUtil;
import com.toocol.ssh.common.annotation.FinalDeployment;
import com.toocol.ssh.common.annotation.PreloadDeployment;
import com.toocol.ssh.common.utils.CastUtil;
import com.toocol.ssh.common.utils.Printer;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import org.apache.commons.lang3.StringUtils;
import sun.misc.Signal;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static com.toocol.ssh.core.configuration.SystemConfiguration.*;

/**
 * @author ZhaoZhe
 * @email joezane.cn@gmail.com
 * @date 2021/2/19 15:00
 */
public class Terminatio {

    private static final long BLOCKED_CHECK_INTERVAL = 30 * 24 * 60 * 60 * 1000L;

    public void start(String type, InputStream inputStream, PrintStream printStream) {
        assert StringUtils.isNotEmpty(type) && inputStream != null && printStream != null;

        // TODO: Vert.x system should be fully hidden;

        BOOT_TYPE = type;
        INPUT_STREAM = inputStream;
        PRINT_STREAM = printStream;


        Signal.handle(new Signal("INT"), signal -> {});


        /* Get the verticle which need deploy in main class by annotation */
        Set<Class<?>> annotatedClassList = ClassUtil.scanPackageByAnnotation("com.toocol.ssh.core", PreloadDeployment.class);
        List<Class<? extends AbstractVerticle>> preloadVerticleClassList = new ArrayList<>();
        annotatedClassList.forEach(annotatedClass -> {
            if (annotatedClass.getSuperclass().equals(AbstractVerticle.class)) {
                preloadVerticleClassList.add(CastUtil.cast(annotatedClass));
            } else {
                throw new RuntimeException("skip deploy verticle " + annotatedClass.getName() + ", please extends AbstractVerticle");
            }
        });
        final CountDownLatch initialLatch = new CountDownLatch(preloadVerticleClassList.size());

        /* Because need to establish SSH connections, increase the blocking check time */
        VertxOptions options = new VertxOptions();
        options.setBlockedThreadCheckInterval(BLOCKED_CHECK_INTERVAL);
        Vertx vertx = Vertx.vertx(options);

        /* Set the final verticle to deploy */
        vertx.executeBlocking(future -> {
            Set<Class<?>> finalClassList = ClassUtil.scanPackageByAnnotation("com.toocol.ssh.core", FinalDeployment.class);
            finalClassList.forEach(finalVerticle -> {
                if (!finalVerticle.getSuperclass().equals(AbstractVerticle.class)) {
                    Printer.println("skip deploy verticle " + finalVerticle.getName() + ", please extends AbstractVerticle");
                    System.exit(-1);
                }
                try {
                    boolean ret = initialLatch.await(30, TimeUnit.SECONDS);
                    if (!ret) {
                        throw new RuntimeException();
                    }
                    vertx.deployVerticle(finalVerticle.getName(), complete -> future.complete());
                } catch (Exception e) {
                    Printer.println("Terminatio start up failed.");
                    vertx.close();
                    System.exit(-1);
                }
            });
        }, res -> {});

        preloadVerticleClassList.sort(Comparator.comparingInt(clazz -> -1 * clazz.getAnnotation(PreloadDeployment.class).weight()));
        preloadVerticleClassList.forEach(verticleClass ->
                vertx.deployVerticle(verticleClass.getName(), new DeploymentOptions(), result -> {
                    if (result.succeeded()) {
                        initialLatch.countDown();
                    } else {
                        Printer.println("Terminal start up failed, verticle = " + verticleClass.getSimpleName());
                        vertx.close();
                        System.exit(-1);
                    }
                })
        );
    }
}

package com.moo.socketio.server;

import com.moo.socketio.cfg.SpringConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class Main {
    public static void main(String[] args) {
        AbstractApplicationContext cxt = new AnnotationConfigApplicationContext(SpringConfig.class);
        cxt.registerShutdownHook();
    }
}

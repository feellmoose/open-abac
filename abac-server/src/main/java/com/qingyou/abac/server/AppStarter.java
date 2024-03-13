package com.qingyou.abac.server;

import com.qingyou.abac.server.app.Application;
import io.vertx.core.impl.VertxBuilder;
import io.vertx.ext.web.Router;

public class AppStarter {
    public static void main(String[] args) {
        Application app = new Application().start();
    }
}

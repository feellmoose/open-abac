package com.qingyou.abac.server;


import com.qingyou.abac.server.app.ABACApplication;
import io.vertx.core.Vertx;

public class AppStarter {
    public static void main(String[] args) {
        ABACApplication application = new ABACApplication(Vertx.vertx()).start();
    }
}

package com.qingyou.abac.server;


import com.qingyou.abac.server.app.ABACApplication;
import com.qingyou.abac.server.config.ApplicationConfiguration;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class AppStarter {
    public static void main(String[] args) {
        ABACApplication application = new ABACApplication(Vertx.vertx()).start();
    }
}

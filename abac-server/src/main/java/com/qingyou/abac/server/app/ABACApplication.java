package com.qingyou.abac.server.app;

import com.qingyou.abac.server.config.ApplicationConfiguration;
import com.qingyou.abac.server.event.Enforce;
import com.qingyou.abac.server.event.Refresh;
import com.qingyou.abac.server.handler.AsyncInformationHandler;
import com.qingyou.abac.server.handler.EnforceHandler;
import com.qingyou.abac.server.infra.impl.CoreServiceImpl;
import com.qingyou.auth.abac.ABACConfiguration;
import com.qingyou.auth.abac.ABACFactory;
import com.qingyou.auth.abac.architecture.Decision;
import com.qingyou.auth.abac.architecture.PolicySource;
import com.qingyou.auth.abac.rule.RuleCreators;
import com.qingyou.auth.abac.serialize.GsonPolicySerialize;
import com.qingyou.auth.api.ABAC;
import com.qingyou.auth.api.Result;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public class ABACApplication {
    private Vertx vertx;
    private EventBus eventBus;
    private final ABACFactory abacFactory = new ABACFactory();
    @Getter
    private final ABACConfiguration abacConfiguration = abacFactory.getConfiguration()
            .registerDecision(new Decision());
    @Getter
    private ApplicationConfiguration applicationConfiguration;

    public ABACApplication(Vertx vertx){
        this.vertx = vertx;
        this.eventBus = vertx.eventBus();
    }

    private static final Logger logger = LoggerFactory.getLogger("start");

    public ABACApplication start() {
        this.readConfiguration();
        var mode = this.applicationConfiguration.getSource().getMode();
        return switch (mode){
            case file -> fromFile();
            case persistence -> null;
            case server -> null;
        };
    }

    private void readConfiguration(){
        ConfigStoreOptions fileStore = new ConfigStoreOptions()
                .setType("file")
                .setConfig(new JsonObject().put("path", "application.json"));
        ConfigStoreOptions sysPropsStore = new ConfigStoreOptions().setType("sys");
        ConfigRetrieverOptions options = new ConfigRetrieverOptions().addStore(fileStore).addStore(sysPropsStore);
        ConfigRetriever retriever = ConfigRetriever.create(vertx, options);
        var json = retriever.getConfig().result();
        JsonObject jsonObject = new JsonObject()
                .put("server",json.getJsonObject("server"))
                .put("source",json.getJsonObject("source"));
        this.applicationConfiguration = jsonObject.mapTo(ApplicationConfiguration.class);
    }

    private ABACApplication fromFile(){
        var path = applicationConfiguration.getSource().getFile().getPath();
        var fileSystem = vertx.fileSystem();
        var information = new AsyncInformationHandler(new PolicySource()
                .registerRuleCreators(RuleCreators.defaultCreators())
                .setPolicySerialize(new GsonPolicySerialize())
                .setStrSource(() -> new String(fileSystem.readFileBlocking(path).getBytes())));
        abacConfiguration.registerInformation(information);
        var coreService = new CoreServiceImpl(abacFactory.create());
        eventBus.consumer("enforce", new EnforceHandler(coreService));
        eventBus.consumer("refresh", information);
        var router = Router.router(vertx);
        router.post("/enforce").handler(routingContext -> {
            var enforce = eventBus.request("enforce", routingContext.body().asPojo(Enforce.class));
            enforce.onComplete(messageAsyncResult -> routingContext.json(Result.success(messageAsyncResult.result().body())));
        });
        router.post("/refresh").handler(routingContext -> {
            var refresh = eventBus.request("refresh", new Refresh(UUID.randomUUID()));
            refresh.onComplete(messageAsyncResult -> routingContext.json(Result.success(messageAsyncResult.result().body())));
        });
        vertx.createHttpServer().requestHandler(router).listen(8080).onComplete(result -> {
            if (result.succeeded()) logger.info("Server started on port 8080");
        });
        return this;
    }


}

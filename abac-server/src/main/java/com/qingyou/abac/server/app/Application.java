package com.qingyou.abac.server.app;

import com.qingyou.abac.server.event.Enforce;
import com.qingyou.abac.server.event.Refresh;
import com.qingyou.abac.server.handler.AsyncInformationHandler;
import com.qingyou.abac.server.handler.EnforceHandler;
import com.qingyou.abac.server.infra.impl.CoreServiceImpl;
import com.qingyou.auth.abac.ABACFactory;
import com.qingyou.auth.abac.architecture.Decision;
import com.qingyou.auth.abac.architecture.PolicySource;
import com.qingyou.auth.abac.serialize.GsonPolicySerialize;
import com.qingyou.auth.api.ABAC;
import com.qingyou.auth.api.Result;
import com.qingyou.auth.api.rule.Rule;
import com.qingyou.auth.api.rule.RuleCreator;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.impl.VertxBuilder;
import io.vertx.ext.web.Router;

import java.util.UUID;

public class Application {
    private Vertx vertx;
    private EventBus eventBus;
    private ABAC abac;

    public Application start() {
        this.vertx = Vertx.vertx();
        this.eventBus = vertx.eventBus();
        var information = new AsyncInformationHandler(new PolicySource()
                .setPolicySerialize(new GsonPolicySerialize())
                .setStrSource(() ->
                        """
                           []"""
                ));
        var abacFactory = new ABACFactory();
        abacFactory.getConfiguration()
                .registerInformation(information)
                .registerDecision(new Decision())
                .registerRuleCreator(new RuleCreator<>() {
                    @Override
                    public Rule<Object> create(String name, Object param) {
                        return new Rule.Builder<>()
                                .withParam(param)
                                .withName("")
                                .rule(Object::equals)
                                .build();
                    }

                    @Override
                    public String ruleType() {
                        return "equal";
                    }
                });
        this.abac = abacFactory.create();
        var coreService = new CoreServiceImpl(abac);
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
            if(result.succeeded()) System.out.println("Server started on port 8080");//TODO logger
        });
        return this;
    }


}

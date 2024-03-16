package com.qingyou.abac.server.handler;

import com.qingyou.abac.server.event.Enforce;
import com.qingyou.abac.server.infra.CoreService;
import com.qingyou.auth.api.Result;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;

public class EnforceHandler implements Handler<Message<Enforce>> {
    private final CoreService coreService;

    public EnforceHandler(CoreService coreService) {
        this.coreService = coreService;
    }

    @Override
    public void handle(Message<Enforce> enforceMessage) {
        var body = enforceMessage.body();
        if(body == null){
            enforceMessage.reply(Result.error("body is null"));
            return;
        }
        var response = coreService.enforce(body.attribute());
        enforceMessage.reply(response);
    }

}

package com.qingyou.abac.server.handler;

import com.qingyou.abac.server.event.LoadPolicy;
import com.qingyou.abac.server.event.Refresh;
import com.qingyou.auth.abac.attribute.Option;
import com.qingyou.auth.abac.attribute.Target;
import com.qingyou.auth.abac.attribute.Visitor;
import com.qingyou.auth.abac.policy.Policy;
import com.qingyou.auth.api.Information;
import com.qingyou.auth.api.PolicySource;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;

import java.util.List;
import java.util.UUID;

public class AsyncInformationHandler implements Handler<Message<Refresh>>, Information<Visitor, Option, Target, Policy> {

    private List<Policy> cachedPolicies;

    private final PolicySource<Policy,?> policySource;

    public AsyncInformationHandler(PolicySource<Policy,?> policySource) {
        this.policySource = policySource;
    }


    @Override
    public void handle(Message<Refresh> refreshMessage) {
        var refresh = refreshMessage.body();
        this.refresh();
        refreshMessage.reply(new LoadPolicy(UUID.randomUUID(),this.cachedPolicies,refresh));
    }


    @Override
    public List<Policy> policies() {
        return this.cachedPolicies;
    }

    @Override
    public AsyncInformationHandler refresh() {
        this.cachedPolicies = policySource.load();
        return this;
    }

    @Override
    public PolicySource<Policy,?> getPolicySource() {
        return this.policySource;
    }


}

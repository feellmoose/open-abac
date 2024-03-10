package com.qingyou.auth.abac.architecture;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qingyou.auth.abac.annotation.Option;
import com.qingyou.auth.abac.annotation.Target;
import com.qingyou.auth.abac.attribute.Visitor;
import com.qingyou.auth.abac.policy.Policy;
import com.qingyou.auth.abac.serialize.ContextSerialize;

import java.util.List;
import java.util.function.Supplier;

public final class DefaultInformation extends AbstractInformation<Visitor.Allow, Option, Target> {

    public DefaultInformation(Supplier<String> stringSource) {
        super(() -> {
            var json = stringSource.get();
            List<Policy.DTO<Visitor.Allow, Option, Target>> dto = new Gson().fromJson(json, new TypeToken<>() {
            });
            return dto.stream().map(d -> new Policy<>(d.allow(), d.options(), d.targets(), ContextSerialize.fromPolicy(d.contexts()), d.strategy())).toList();
        });
    }


}
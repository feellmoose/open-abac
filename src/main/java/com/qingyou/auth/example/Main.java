package com.qingyou.auth.example;

import com.qingyou.auth.abac.ABAC;
import com.qingyou.auth.abac.annotation.Option;
import com.qingyou.auth.abac.annotation.Target;
import com.qingyou.auth.abac.architecture.DefaultDecision;
import com.qingyou.auth.abac.architecture.DefaultInformation;
import com.qingyou.auth.abac.attribute.Attribute;
import com.qingyou.auth.abac.attribute.Visitor;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String json = """
                [
                {
                    "allow":{
                    "roles":["user"]
                    },
                    "options":["select"],
                    "targets":["Resources"],
                    "contexts":{
                    "time":{
                        "eq":"15:30"
                    }
                    },
                    "strategy":"approval"
                    }
                ]
                """;
        ABAC abac = new ABAC.Builder()
                .decision(new DefaultDecision())
                .information(new DefaultInformation(() -> json))
                .build();
        abac.refresh();//这里可以强制改异步
        abac.enforce(() -> new Attribute<>(
                new Visitor(1L, "123123", "name", Visitor.Role.user),
                Option.select,
                Target.Resources,
                Map.of("time", "15:30")));

    }
}

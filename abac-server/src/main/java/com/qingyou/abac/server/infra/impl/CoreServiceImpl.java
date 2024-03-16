package com.qingyou.abac.server.infra.impl;

import com.qingyou.abac.server.infra.CoreService;
import com.qingyou.auth.abac.attribute.Attribute;
import com.qingyou.auth.api.ABAC;
import com.qingyou.auth.api.Result;

public class CoreServiceImpl implements CoreService {

    private ABAC abac;

    public CoreServiceImpl(ABAC abac) {
        this.abac = abac;
    }

    @Override
    public Result<String> health() {
        return Result.success("ok");
    }

    @Override
    public Result<Object> enforce(Attribute attribute) {
        return abac.enforce(attribute);
    }
}

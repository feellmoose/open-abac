package com.qingyou.abac.server.infra;

import com.qingyou.auth.abac.attribute.Attribute;
import com.qingyou.auth.api.Result;

public interface CoreService {

    Result<String> health();

    Result<Object> enforce(Attribute attribute);

}

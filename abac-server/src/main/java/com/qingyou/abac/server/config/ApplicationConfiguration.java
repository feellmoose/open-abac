package com.qingyou.abac.server.config;

import com.qingyou.auth.abac.policy.Policy;
import io.vertx.core.json.JsonArray;
import lombok.Data;

import java.util.List;

@Data
public class ApplicationConfiguration {
    private ServerConfiguration server;
    private SourceConfiguration source;

    @Data
    public static class SourceConfiguration {
        private Mode mode;
        private PersistenceConfiguration persistence;
        private FileConfiguration file;
        private ServerConfiguration server;

        public enum Mode {
            file,
            persistence,
            server
        }
    }

    @Data
    public static class PersistenceConfiguration {
        private String path;
        private String url;
    }

    @Data
    public static class FileConfiguration {
        private String path;
    }

    @Data
    public static class ServerConfiguration {
        private Integer port;
        private Integer url;
    }

}

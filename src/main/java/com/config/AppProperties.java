package com.config;

import org.apache.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "app")
public class AppProperties {
    static Logger logger = Logger.getLogger(AppProperties.class);
    private final Auth auth = new Auth();
    private final OAuth2 oauth2 = new OAuth2();
    public static class Auth {
        private String tokenSecret;
        private String adminTokenSecret;
        private String userTokenSecret;
        private long tokenExpirationMsec;

        public String getTokenSecret() {
            return tokenSecret;
        }

        public void setTokenSecret(String tokenSecret) {
            this.tokenSecret = tokenSecret;
        }

        public String getAdminTokenSecret() {
            return adminTokenSecret;
        }

        public void setAdminTokenSecret(String adminTokenSecret) {
            this.adminTokenSecret = adminTokenSecret;
        }

        public String getUserTokenSecret() {
            return userTokenSecret;
        }

        public void setUserTokenSecret(String userTokenSecret) {
            this.userTokenSecret = userTokenSecret;
        }

        public long getTokenExpirationMsec() {
            return tokenExpirationMsec;
        }

        public void setTokenExpirationMsec(long tokenExpirationMsec) {
            this.tokenExpirationMsec = tokenExpirationMsec;
        }
    }

    public static final class OAuth2 {
        private List<String> authorizedRedirectUris = new ArrayList<>();

        public List<String> getAuthorizedRedirectUris() {
            return authorizedRedirectUris;
        }

        public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
            this.authorizedRedirectUris = authorizedRedirectUris;
            return this;
        }
    }

    public Auth getAuth() {
        return auth;
    }

    public OAuth2 getOauth2() {
        return oauth2;
    }
}

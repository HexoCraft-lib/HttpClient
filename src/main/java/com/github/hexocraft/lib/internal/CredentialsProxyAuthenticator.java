package com.github.hexocraft.lib.internal;

import okhttp3.Authenticator;
import okhttp3.Credentials;


/*

 Copyright 2018 hexosse

 Licensed under the Apache License, Version 2.0 (the "License")
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 */




public final class CredentialsProxyAuthenticator {


    private CredentialsProxyAuthenticator() {
        throw new IllegalAccessError("This is a private constructor. Use static functions instead.");
    }


    /**
     * Create an Authenticator using JVM arguments when proxy authentication is required
     *
     * @return {@link Authenticator}
     */
    public static Authenticator create() {
        return (route, response) -> {
            String username = System.getProperty(route.address().url().scheme() + ".proxyUser", "");
            String password = System.getProperty(route.address().url().scheme() + ".proxyPassword", "");
            String credential = Credentials.basic(username, password);
            return response.request().newBuilder()
                .header("Proxy-Authorization", credential)
                .build();
        };
    }

}

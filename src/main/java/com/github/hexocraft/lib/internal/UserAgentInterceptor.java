package com.github.hexocraft.lib.internal;

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

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;


/**
 * Default user-agent
 */
public final class UserAgentInterceptor {

    /**
     * Default user agent: To update with your own value
     */
    private static String userAgent = "HexoCraft";


    private UserAgentInterceptor() {
        throw new IllegalAccessError("This is a private constructor Use static functions instead.");
    }


    /**
     * Create a default user agent interceptor
     *
     * @return {@link Interceptor}
     */
    public static Interceptor create() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request origin = chain.request();
                Request modified = origin.newBuilder()
                    .header("User-Agent", userAgent)
                    .build();
                return chain.proceed(modified);
            }
        };
    }

    /**
     * Change the default user-agent
     *
     * @param userAgent User agent
     */
    public static void setUserAgent(String userAgent) {
        UserAgentInterceptor.userAgent = userAgent;
    }
}

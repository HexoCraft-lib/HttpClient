package com.github.hexocraft.lib.internal;

/*

 Copyright 2018 hexosse

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 */

import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.Collections;
import java.util.List;


/**
 * Select a proxy according to the uri
 */
public final class SafeProxySelector {

    /**
     * Direct no-proxy list
     */
    private static final List<Proxy> DIRECT = Collections.singletonList(Proxy.NO_PROXY);


    /**
     * Create a check-ignored host name verifier
     *
     * @return {@link ProxySelector}
     */
    public static ProxySelector create() {
        return new ProxySelector() {
            @Override
            public List<Proxy> select(URI uri) {
                ProxySelector selector = ProxySelector.getDefault();
                if (selector == null) {
                    return DIRECT;
                }
                return selector.select(uri);
            }

            @Override
            public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
                ProxySelector selector = ProxySelector.getDefault();
                if (selector != null) {
                    selector.connectFailed(uri, sa, ioe);
                }
            }
        };
    }
}

package com.github.hexocraft.lib;

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

import com.github.hexocraft.lib.exception.UnsuccessfulResponse;
import com.github.hexocraft.lib.internal.*;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;


public class HttpClient {

    /**
     * Instance of OkHttpClient
     */
    private static OkHttpClient client = null;

    /**
     * Create an instance of OkHttpClient
     *
     * @return OkHttpClient instance
     */
    public synchronized static OkHttpClient get() {
        if (client == null) {
            client = new OkHttpClient.Builder()
                .addInterceptor(UserAgentInterceptor.create())
                .proxySelector(SafeProxySelector.create())
                .proxyAuthenticator(CredentialsProxyAuthenticator.create())
                .sslSocketFactory(InsecureSSLSocketFactory.create(), (X509TrustManager) InsecureSSLSocketFactory.trustAllCerts()[0])
                .hostnameVerifier(InsecureHostnameVerifier.create())
                .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT))
                .build();
        }
        return client;
    }

    /**
     * Execute the {@link Request}.
     *
     * @param request {@link Request} to execute
     *
     * @return {@link okhttp3.Call#execute()}
     *
     * @throws UnsuccessfulResponse When response is not uccessful
     * @throws IOException @see {@link okhttp3.Call#execute()}
     */
    public static Response execute(Request request) throws UnsuccessfulResponse, IOException {
        Response response = get().newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new UnsuccessfulResponse(response);
        }
        return response;
    }
}

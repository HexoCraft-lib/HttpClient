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


import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


/**
 * Allow connection to a host that is not signed by a root certificate authority.
 */
public final class InsecureSSLSocketFactory {

    private InsecureSSLSocketFactory() {
        throw new IllegalAccessError("This is a private constructor Use static functions instead.");
    }

    /**
     * Create an insecure allowed sll socket factory through insecure ssl context
     *
     * @return {@link SSLSocketFactory}
     */
    public static SSLSocketFactory create() {
        SSLContext context = createInsecureSslContext();
        return context.getSocketFactory();
    }

    public static TrustManager[] trustAllCerts() {
        return new TrustManager[]{
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    // Client is automatically trusted
                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    // Server is automatically trusted
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }
        };
    }

    /**
     * Create an insecure allowed sll context
     */
    private static SSLContext createInsecureSslContext() {
        try {
            SSLContext context = SSLContext.getInstance("TLSv1.2");
            context.init(null, trustAllCerts(), new SecureRandom());
            return context;
        }
        catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}

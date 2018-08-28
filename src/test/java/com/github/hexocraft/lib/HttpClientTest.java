package com.github.hexocraft.lib;

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

import com.github.hexocraft.lib.exception.UnsuccessfulResponse;
import com.github.hexocraft.lib.internal.UserAgentInterceptor;
import okhttp3.Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;



class HttpClientTest {

    private String httpUrl = "http://publicobject.com/helloworld.txt";
    private String httpsUrl = "https://publicobject.com/helloworld.txt";
    private String httpsUrl2 = "https://dbo.aternos.org/projects/31292";

    @Test
    void HttpClient() {
        UserAgentInterceptor.setUserAgent("MyUserAgent");

        assertDoesNotThrow(() -> HttpClient.execute(new Request.Builder().url(httpUrl).get().build()));
        assertDoesNotThrow(() -> HttpClient.execute(new Request.Builder().url(httpsUrl).get().build()));
        assertDoesNotThrow(() -> HttpClient.execute(new Request.Builder().url(httpsUrl2).get().build()));

        assertThrows(IllegalArgumentException.class, () -> HttpClient.execute(new Request.Builder().url("Invalid url").get().build()));
        assertThrows(UnknownHostException.class, () -> HttpClient.execute(new Request.Builder().url("http://not.valid.url").get().build()));
        assertThrows(UnsuccessfulResponse.class, () -> HttpClient.execute(new Request.Builder().url("http://publicobject.com/not_valid_file.txt").get().build()));
    }

}

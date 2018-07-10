package com.github.hexocraft.lib.exception;

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

import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;


/**
 * Exception thrown during while requesting an url.
 */
public class UnsuccessfulResponse extends RuntimeException {

    private Response response;

    public UnsuccessfulResponse(Response response) {
        super(ResponseMessageProcessor.process(response));
        this.response = response;
    }

    public UnsuccessfulResponse(Response response, Throwable ex) {
        super(ResponseMessageProcessor.process(response), ex);
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    /**
     * Process the response to a String message
     */
    static class ResponseMessageProcessor {

        public static String process(Response response) {

            String message = "";
            try (ResponseBody responseBody = response.body()) {
                if (responseBody != null) {
                    message = responseBody.string();
                }
            }
            catch (IOException ignored) {
            }


            return "Unexpected code: " + response.code() + " - " + response.message() + " - " + message;
        }
    }
}

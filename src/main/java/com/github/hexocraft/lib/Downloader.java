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

import com.github.hexocraft.lib.interfaces.IDownloaderProgress;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

import java.io.File;
import java.net.URL;


public class Downloader {

    public static void downloadFile(final URL url, final File destFile) {
        downloadFile(url, destFile, null);
    }

    public static void downloadFile(final URL url, final File destFile, IDownloaderProgress downloaderProgress) {

        BufferedSource source = null;
        BufferedSink sink = null;

        //
        notifyStart(downloaderProgress);

        try {
            //
            Request request = new Request.Builder().url(url).build();
            Response response = HttpClient.execute(request);
            ResponseBody body = response.body();
            long contentLength = body.contentLength();

            //
            source = body.source();
            sink = Okio.buffer(Okio.sink(destFile));
            Buffer sinkBuffer = sink.buffer();

            //
            long totalBytesRead = 0;
            int bufferSize = 8 * 1024;
            long bytesRead;
            while ((bytesRead = source.read(sinkBuffer, bufferSize)) != -1) {
                sink.emit();
                totalBytesRead += bytesRead;
                int progress = (int) ((totalBytesRead * 100) / contentLength);
                //
                notifyProgress(downloaderProgress, progress);
            }

            //
            sink.flush();

            //
            notifyFinish(downloaderProgress);
        }
        catch (Exception e) {
            //
            notifyError(downloaderProgress, e);
            //
            e.printStackTrace();
        }
        finally {
            Util.closeQuietly(sink);
            Util.closeQuietly(source);
        }
    }

    private static void notifyStart(IDownloaderProgress downloaderProgress) {
        if (downloaderProgress != null) {
            downloaderProgress.onStart();
        }
    }

    private static void notifyProgress(IDownloaderProgress downloaderProgress, int progress) {
        if (downloaderProgress != null) {
            downloaderProgress.onProgress(progress);
        }
    }

    private static void notifyFinish(IDownloaderProgress downloaderProgress) {
        if (downloaderProgress != null) {
            downloaderProgress.onFinish();
        }
    }

    private static void notifyError(IDownloaderProgress downloaderProgress, Exception e) {
        if (downloaderProgress != null) {
            downloaderProgress.onError(e);
        }
    }
}

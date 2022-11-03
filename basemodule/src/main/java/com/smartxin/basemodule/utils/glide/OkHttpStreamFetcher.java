package com.smartxin.basemodule.utils.glide;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.HttpException;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.util.ContentLengthInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Author: zhengwenxin
 * CreateDate: 2022/7/9 17:59
 * Description:
 */
public class OkHttpStreamFetcher implements DataFetcher<InputStream> {


    private final OkHttpClient client;
    private final GlideUrl url;
    private InputStream stream;
    private ResponseBody responseBody;

    /**
     * @param client s
     * @param url    s
     */
    public OkHttpStreamFetcher(OkHttpClient client, GlideUrl url) {
        this.client = client;
        this.url = url;
    }

    @Override
    public void loadData(Priority priority, final DataCallback<? super InputStream> dataCallback) {
        Request.Builder requestBuilder = new Request.Builder().url(url.toStringUrl());

        for (Map.Entry<String, String> headerEntry : url.getHeaders().entrySet()) {
            String key = headerEntry.getKey();
            requestBuilder.addHeader(key, headerEntry.getValue());
        }

        Request request = requestBuilder.build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dataCallback.onLoadFailed(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseBody = response.body();
                if (response.isSuccessful()) {
                    long contentLength = responseBody.contentLength();
                    stream = ContentLengthInputStream.obtain(responseBody.byteStream(), contentLength);
                    dataCallback.onDataReady(stream);
                } else {
                    dataCallback.onLoadFailed(new HttpException(response.message(), response.code()));
                }
            }
        });

    }

    @Override
    public void cleanup() {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                // Ignored
                e.printStackTrace();
            }
        }
        if (responseBody != null) {
            responseBody.close();
        }
    }

    @Override
    public void cancel() {
    }

    @Override
    public Class<InputStream> getDataClass() {
        return InputStream.class;
    }

    @Override
    public DataSource getDataSource() {
        return DataSource.REMOTE;
    }
}

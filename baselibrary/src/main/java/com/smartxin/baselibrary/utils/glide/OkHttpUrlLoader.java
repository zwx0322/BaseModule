package com.smartxin.baselibrary.utils.glide;

import androidx.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * Author: zhengwenxin
 * CreateDate: 2022/7/9 17:53
 * Description:
 */
public class OkHttpUrlLoader implements ModelLoader<GlideUrl, InputStream> {
    @Override
    public boolean handles(GlideUrl glideUrl) {
        return true;
    }

    /**
     * The default factory for {@link OkHttpUrlLoader}s.
     */
    public static class Factory implements ModelLoaderFactory<GlideUrl, InputStream> {
        private static volatile OkHttpClient internalClient;
        private OkHttpClient client;

        /**
         * @return s
         */
        private static OkHttpClient getInternalClient() {
            if (internalClient == null) {
                synchronized (Factory.class) {
                    if (internalClient == null) {
                        internalClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
                    }
                }
            }
            return internalClient;
        }


        /**
         * Constructor for a new Factory that runs requests using a static singleton client.
         */
        public Factory() {
            this(getInternalClient());
        }

        /**
         * @param client s
         */
        public Factory(OkHttpClient client) {
            this.client = client;
        }

        @Override
        public ModelLoader<GlideUrl, InputStream> build(MultiModelLoaderFactory multiModelLoaderFactory) {
            return new OkHttpUrlLoader(client);
        }

        @Override
        public void teardown() {

        }
    }

    private final OkHttpClient client;

    /**
     * @param client s
     */
    public OkHttpUrlLoader(OkHttpClient client) {
        this.client = client;
    }

    @Nullable
    @Override
    public LoadData<InputStream> buildLoadData(GlideUrl glideUrl, int i, int i1, Options options) {
        return new LoadData<InputStream>(glideUrl, new OkHttpStreamFetcher(client, glideUrl));
    }
}

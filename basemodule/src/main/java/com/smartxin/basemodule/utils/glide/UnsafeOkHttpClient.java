package com.smartxin.basemodule.utils.glide;

import android.os.Build;

import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

/**
 * Author: zhengwenxin
 * CreateDate: 2022/7/9 17:55
 * Description:
 */
public class UnsafeOkHttpClient {
    public static OkHttpClient getUnsafeOkHttpClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.sslSocketFactory(getSSLSocketFactory(), getNewX509TrustManager())
        .hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        OkHttpClient client = httpClientBuilder.build();
        return client;
    }

    public static SSLSocketFactory getSSLSocketFactory() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            TrustManager[] trustAllCerts = new TrustManager[]{getNewX509TrustManager()};
            SSLContext ssl;
            try {
                ssl = SSLContext.getInstance("SSL");
                ssl.init(null, trustAllCerts, new SecureRandom());
                return ssl.getSocketFactory();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            //创建一个不验证证书链的证书信任管理器。
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }
            }};

            final SSLContext sslContext;
            try {
                sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, trustAllCerts,
                        new SecureRandom());
                return sslContext
                        .getSocketFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static X509TrustManager getNewX509TrustManager() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return new X509ExtendedTrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {

                }

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {

                }

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };
        } else {
            return new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };
        }
    }

}

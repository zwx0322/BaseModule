package com.smartxin.basemodule.http.error;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;

import retrofit2.HttpException;

/**
 * Created by goldze on 2017/5/11.
 */
public class ExceptionHandle {

    private static final int UNAUTHORIZED = 401;   //需要验证用户身份或认证失败
    private static final int FORBIDDEN = 403;  //服务器拒绝执行
    private static final int NOT_FOUND = 404;  //找不到资源
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500; //服务器遇到了一个未曾预料的状况，导致了它无法完成对请求的处理
    private static final int NOT_IMPLEMENTED = 501;   //服务器不支持当前请求所需要的某个功能
    private static final int BAD_GATEWAY = 502;   //作为网关或者代理工作的服务器尝试执行请求时，从上游服务器接收到无效的响应
    private static final int SERVICE_UNAVAILABLE = 503;  //服务器维护或者过载，服务器当前无法处理请求

    public static ResponseThrowable handleException(Throwable e) {
        ResponseThrowable ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponseThrowable(e, ERROR.HTTP_ERROR);
            switch (httpException.code()) {
                case UNAUTHORIZED:
                    ex.message = "用户身份认证失败";
                    break;
                case FORBIDDEN:
                    ex.message = "请求被拒绝";
                    break;
                case NOT_FOUND:
                    ex.message = "找不到资源";
                    break;
                case REQUEST_TIMEOUT:
                    ex.message = "服务器执行超时";
                    break;
                case INTERNAL_SERVER_ERROR:
                    ex.message = "服务器内部错误";
                    break;
                case BAD_GATEWAY:
                    ex.message = "服务器代理异常";
                    break;
                case NOT_IMPLEMENTED:
                    ex.message = "暂不支持该功能";
                    break;
                case SERVICE_UNAVAILABLE:
                    ex.message = "服务器维护中";
                    break;
                default:
                    ex.message = "网络错误";
                    break;
            }
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException || e instanceof MalformedJsonException) {
            ex = new ResponseThrowable(e, ERROR.PARSE_ERROR);
            ex.message = "解析错误";
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ResponseThrowable(e, ERROR.NETWORD_ERROR);
            ex.message = "连接失败";
            return ex;
        } else if (e instanceof javax.net.ssl.SSLException) {
            ex = new ResponseThrowable(e, ERROR.SSL_ERROR);
            ex.message = "证书验证失败";
            return ex;
        } else if (e instanceof ConnectTimeoutException) {
            ex = new ResponseThrowable(e, ERROR.TIMEOUT_ERROR);
            ex.message = "连接超时";
            return ex;
        } else if (e instanceof java.net.SocketTimeoutException) {
            ex = new ResponseThrowable(e, ERROR.TIMEOUT_ERROR);
            ex.message = "连接超时";
            return ex;
        } else if (e instanceof java.net.UnknownHostException) {
            ex = new ResponseThrowable(e, ERROR.TIMEOUT_ERROR);
            ex.message = "主机地址未知";
            return ex;
        } else {
            ex = new ResponseThrowable(e, ERROR.UNKNOWN);
            ex.message = "未知错误";
            return ex;
        }
    }


    /**
     * 约定异常 这个具体规则需要与服务端或者领导商讨定义
     */
    class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORD_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1005;

        /**
         * 连接超时
         */
        public static final int TIMEOUT_ERROR = 1006;
    }

}


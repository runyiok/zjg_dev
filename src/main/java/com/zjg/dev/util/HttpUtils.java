package com.zjg.dev.util;


import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.zjg.dev.core.exception.CommonException;
import com.zjg.dev.domain.RespData;
import com.zjg.dev.domain.RespEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class HttpUtils {


    private final static String URL_ENCODING = "UTF-8";

    private static final String HTTP = "http";

    private static final String HTTPS = "https";
    private static final int CONNECT_TIME_OUT = 20000;
    private static final int SOCKET_TIME_OUT = 20000;
    private static final int CONNECTION_REQUEST_TIMEOUT = 20000;
    private static SSLConnectionSocketFactory sslsf = null;
    private static PoolingHttpClientConnectionManager cm = null;
    private static int pool_max = 200;
    private static RequestConfig requestConfig;
    private static SSLContextBuilder builder = null;
    private static String[] tlsArray = new String[]{"TLSv1", "TLSv1.2"};
    private static String[] sslArray = new String[]{"SSLv2Hello", "SSLv3"};

    static {
        try {
            builder = new SSLContextBuilder();
            // 全部信任
            builder.loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            });
            sslsf = new SSLConnectionSocketFactory(builder.build(), tlsArray, null, NoopHostnameVerifier.INSTANCE);

            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register(HTTP, new PlainConnectionSocketFactory()).register(HTTPS, sslsf).build();
            cm = new PoolingHttpClientConnectionManager(registry);
            cm.setMaxTotal(pool_max);
            RequestConfig.Builder configBuilder = RequestConfig.custom();
            // 设置连接超时
            configBuilder.setConnectTimeout(CONNECT_TIME_OUT);
            // 设置读取超时
            configBuilder.setSocketTimeout(SOCKET_TIME_OUT);
            // 设置从连接池获取连接实例的超时
            configBuilder.setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT);
            requestConfig = configBuilder.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static CloseableHttpClient getHttpClient() throws Exception {
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).setConnectionManager(cm)
                .setConnectionManagerShared(true).build();
        return httpClient;
    }

    /**
     * http post
     *
     * @param url
     * @param params
     * @return
     */
    public static String post(String url, Map<String, Object> params) {
        return post(url, null, params, null);
    }

    private static String post(String url, Map<String, String> header, Map<String, Object> params, HttpEntity entity) {
        log.info("请求接口===>>" + url + " 参数：" + params);
        CloseableHttpClient httpClient = null;
        String result = null;
        try {
            httpClient = getHttpClient();
            HttpPost post = new HttpPost(url);
            post.setConfig(requestConfig);
            //设置http  header
            if (null != header && !header.isEmpty()) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    post.addHeader(entry.getKey(), entry.getValue());
                }
            }
            //组装参数
            if (null != params && !params.isEmpty()) {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    nameValuePairs.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
                }
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs, Consts.UTF_8);
                post.setEntity(urlEncodedFormEntity);
            }
            // 设置实体 优先级高
            if (null != entity) {
                post.setEntity(entity);
            }
            HttpResponse httpResponse = httpClient.execute(post);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = httpResponse.getEntity();
                result = EntityUtils.toString(resEntity);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            throw new CommonException(RespEnum.ERROR_HTTP.getCode(), e.toString());
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                    throw new CommonException(RespEnum.ERROR_HTTP.getCode(), e.toString());
                }
            }
        }
        log.info("请求接口===>>" + url + " 结果：" + result);

        return result;
    }

    public static String post(String url, String json) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(new BasicHeader("Content-type", "application/json;charset=utf-8"));
        CloseableHttpResponse response = null;
        String ret = null;
        try {
            httpPost.setEntity(new StringEntity(json, URL_ENCODING));
            response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            ret = EntityUtils.toString(entity, URL_ENCODING);
            EntityUtils.consume(entity);
        } catch (UnsupportedEncodingException e) {
        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        } finally {
            try {
                if (null != response)
                    response.close();
            } catch (IOException e) {
            }
        }
        return ret;
    }

    /**
     * 获取 http返回值
     *
     * @param url
     * @param params
     * @param isPost
     * @return
     */
    public static RespData content(String url, Map<String, Object> params, boolean isPost) {
        String result = (isPost ? post(url, params) : get(url, params));
        if (Strings.isNullOrEmpty(result)) return null;
        //转换成对象
        return FastJsonUtils.toBean(result, RespData.class);
    }

    /**
     * http get
     *
     * @param url
     * @param params
     * @return
     */
    public static String get(String url, Map<String, Object> params) {
        if (Strings.isNullOrEmpty(url)) return null;
        if (url.indexOf("?") < 0) url += "?";
        if (null != params && !params.isEmpty()) {
            String param = Joiner.on("&").withKeyValueSeparator("=").join(params);
            url = url + param;
        }
        log.info("请求接口===>>" + url);
        CloseableHttpClient httpClient = null;
        String result = null;
        try {
            httpClient = getHttpClient();
            HttpGet get = new HttpGet(url);
            get.setConfig(requestConfig);
            HttpResponse response = httpClient.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = response.getEntity();
                result = EntityUtils.toString(resEntity);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            throw new CommonException(RespEnum.ERROR_HTTP.getCode(), e.toString());
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                    throw new CommonException(RespEnum.ERROR_HTTP.getCode(), e.toString());
                }
            }
        }
        log.info("请求接口===>>" + url + " 结果：" + result);
        return result;
    }

}
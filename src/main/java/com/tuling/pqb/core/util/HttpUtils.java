package com.tuling.pqb.core.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.*;
import javax.xml.bind.DatatypeConverter;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * ClassName: HttpUtils
 *
 * @author gaoan
 * @Description: http 请求工具类
 * @date 2018 年 3 月 10 日 下午 3:57:14
 */
@SuppressWarnings("deprecation")
public class HttpUtils {

    /**
     * @param @param  reqUrl
     * @param @param  params
     * @param @return
     * @param @throws Exception
     * @Description: http get 请求共用方法
     */
    @SuppressWarnings("resource")
    public static String sendGet(String reqUrl, Map<String, String> params)
            throws Exception {
        InputStream inputStream = null;
        HttpGet request = new HttpGet();
        try {
            String url = buildUrl(reqUrl, params);
            HttpClient client = HttpClientBuilder.create().build();
            request.setHeader("Accept-Encoding", "gzip");
            request.setURI(new URI(url));
            HttpResponse response = client.execute(request);
            inputStream = response.getEntity().getContent();
            String result = getJsonStringFromGzip(inputStream);
            return result;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            request.releaseConnection();
        }

    }

    /**
     * @param @param  reqUrl
     * @param @param  params
     * @param @return
     * @param @throws Exception
     * @Description: http post 请求共用方法
     */
    @SuppressWarnings("resource")
    public static String sendPost(String reqUrl, Map<String, String> params)
            throws Exception {
        try {
            Set<String> set = params.keySet();
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for (String key : set) {
                list.add(new BasicNameValuePair(key, params.get(key)));
            }
            if (list.size() > 0) {
                try {
                    HttpClient client = HttpClientBuilder.create().build();
                    HttpPost request = new HttpPost(reqUrl);

                    request.setHeader("Accept-Encoding", "gzip");
                    request.setEntity(new UrlEncodedFormEntity(list, StandardCharsets.UTF_8));

                    HttpResponse response = client.execute(request);

                    InputStream inputStream = response.getEntity().getContent();
                    try {
                        String result = getJsonStringFromGzip(inputStream);

                        return result;
                    } finally {
                        inputStream.close();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw new Exception("网络连接失败,请连接网络后再试");
                }
            } else {
                throw new Exception("参数不全，请稍后重试");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("发送未知异常");
        }
    }

    /**
     * @param @param  urls
     * @param @param  params
     * @param @return
     * @param @throws ClientProtocolException
     * @param @throws IOException
     * @Description: http post 请求 json 数据
     */
    public static String sendPostBuffer(String urls, String params)
            throws ClientProtocolException, IOException {
        HttpPost request = new HttpPost(urls);

        StringEntity se = new StringEntity(params, StandardCharsets.UTF_8);
        request.setEntity(se);
        // 发送请求
        @SuppressWarnings("resource")
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        // 得到应答的字符串，这也是一个 JSON 格式保存的数据
        String retSrc = EntityUtils.toString(httpResponse.getEntity());
        request.releaseConnection();
        return retSrc;

    }

    /**
     * @param @param  urlStr
     * @param @param  xmlInfo
     * @param @return
     * @Description: http 请求发送 xml 内容
     */
    public static String sendXmlPost(String urlStr, String xmlInfo) {
        // xmlInfo xml 具体字符串

        try {
            URL url = new URL(urlStr);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Pragma:", "no-cache");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "text/xml");
            OutputStreamWriter out = new OutputStreamWriter(
                    con.getOutputStream());
            out.write(new String(xmlInfo.getBytes("utf-8")));
            out.flush();
            out.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String lines = "";
            for (String line = br.readLine(); line != null; line = br
                    .readLine()) {
                lines = lines + line;
            }
            return lines; // 返回请求结果
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "fail";
    }

    private static String getJsonStringFromGzip(InputStream is) {
        String jsonString = null;
        try {
            BufferedInputStream bis = new BufferedInputStream(is);
            bis.mark(2);
            // 取前两个字节
            byte[] header = new byte[2];
            int result = bis.read(header);
            // reset 输入流到开始位置
            bis.reset();
            // 判断是否是 GZIP 格式
            int headerData = getShort(header);
            // Gzip 流 的前两个字节是 0x1f8b
            if (result != -1 && headerData == 0x1f8b) {
                is = new GZIPInputStream(bis);
            } else {
                is = bis;
            }
            InputStreamReader reader = new InputStreamReader(is, "utf-8");
            char[] data = new char[100];
            int readSize;
            StringBuffer sb = new StringBuffer();
            while ((readSize = reader.read(data)) > 0) {
                sb.append(data, 0, readSize);
            }
            jsonString = sb.toString();
            bis.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonString;
    }

    private static int getShort(byte[] data) {
        return (data[0] << 8) | data[1] & 0xFF;
    }

    /**
     * 构建 get 方式的 url
     *
     * @param reqUrl 基础的 url 地址
     * @param params 查询参数
     * @return 构建好的 url
     */
    public static String buildUrl(String reqUrl, Map<String, String> params) {
        StringBuilder query = new StringBuilder();
        Set<String> set = params.keySet();
        for (String key : set) {
            query.append(String.format("%s=%s&", key, params.get(key)));
        }
        return reqUrl + "?" + query.toString();
    }


    /**
     * 忽略SSL证书的get请求
     * @date: 2019年11月14日上午10:57:31
     */
    public static String sendGetNoSSL(String url) {
        String resultStr = "";
        try {
            CloseableHttpClient buildSSLCloseableHttpClient = buildSSLCloseableHttpClient();
            System.setProperty("jsse.enableSNIExtension", "false");
            HttpGet httpGet = new HttpGet(url);
            String encoding = DatatypeConverter.printBase64Binary("ed800cb6-f012-478a-ad94-e095adb74677:9c1f4563-589e-4494-a182-3f1c4b321c29".getBytes("UTF-8"));
            httpGet.setHeader("Authorization", "Basic " + encoding);
            HttpResponse response = buildSSLCloseableHttpClient.execute(httpGet);
            //4.获取响应对象中的响应码
            StatusLine statusLine = response.getStatusLine();//获取请求对象中的响应行对象
            int responseCode = statusLine.getStatusCode();//从状态行中获取状态码
            System.out.println(responseCode);
            if (responseCode == 200) {
                //5.  可以接收和发送消息
                HttpEntity entity = response.getEntity();
                //6.从消息载体对象中获取操作的读取流对象
                InputStream input = entity.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(input));
                String str1 = br.readLine();
                String result = new String(str1.getBytes("gbk"), "utf-8");
                System.out.println("服务器的响应结果:" + result);
                resultStr = result;
                br.close();
                input.close();
                // 释放资源
                buildSSLCloseableHttpClient.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultStr;

    }

    private static CloseableHttpClient buildSSLCloseableHttpClient()
            throws Exception {
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null,
                new TrustStrategy() {
                    // 信任所有
                    @Override
                    public boolean isTrusted(X509Certificate[] chain,
                                             String authType) throws CertificateException {
                        return true;
                    }
                }).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslContext, new String[]{"TLSv1"}, null,
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }

}

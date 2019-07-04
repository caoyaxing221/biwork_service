package com.biwork.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by zhy on 2017/2/27.
 */
public class HttpJsonUtils {
    static Logger log = LoggerFactory.getLogger(HttpJsonUtils.class);

    /**
     * 为了扩展 数据库保存 URL  公钥 这里使用传值
     *
     * @param json
     * @param urlStr
     * @return
     * @throws Exception
     */
    public static String httpClientUtils(String json, String urlStr) {
        log.warn("请求URL:" + urlStr);
        log.warn("请求参数:" + json);
        URL url = null;
        HttpURLConnection urlCon = null;
        OutputStreamWriter out = null;
        try {
            url = new URL(urlStr);
            urlCon = (HttpURLConnection) url.openConnection();
            urlCon.setDoOutput(true);
            urlCon.setDoInput(true);
            urlCon.setUseCaches(false);
            urlCon.setRequestMethod("POST");
            urlCon.setConnectTimeout(60000);
            urlCon.setReadTimeout(60000);
            urlCon.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            urlCon.setRequestProperty("Content-Type", "application/json");
            urlCon.connect();
            out = new OutputStreamWriter(urlCon.getOutputStream(), "UTF-8"); // utf-8编码
            out.write(json);
            out.flush();
            out.close();
            log.warn("数据发送完成,等待响应...");
            // 读取响应
            BufferedReader rd = null;
            if (urlCon.getResponseCode() == 200){
                log.warn("http post请求连接成功");
                rd = new BufferedReader(new InputStreamReader(urlCon.getInputStream(),"UTF-8"));
            }else{
                log.warn("http post请求连接失败！");
                rd = new BufferedReader(new InputStreamReader(urlCon.getErrorStream()));
            }
            StringBuffer buffer = new StringBuffer();
            String readLine = "";
            while ((readLine = rd.readLine()) != null){
                buffer.append(readLine);
            }
            rd.close();
            return buffer.toString();
        } catch (Exception e) {
            log.warn("数据发送失败{}",e);
        }finally {
            urlCon.disconnect();
        }
        return null;
    }

}

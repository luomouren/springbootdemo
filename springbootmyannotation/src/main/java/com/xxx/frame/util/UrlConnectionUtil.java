package com.xxx.frame.util;

import com.xxx.frame.constants.AnnotationConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author:@luomouren
 * @Description: GET、POST请求
 * @Date: 2018-01-03 19:54
 */
public class UrlConnectionUtil {


    /**
     * 发送Get请求
     *
     * @param url get请求的URL
     * @return 请求结果String
     */
    public static String sendGet(String url) {
        String result = null;
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 账号
            String key = AnnotationConstants.KEY;
            // 密码
            String password = AnnotationConstants.PASSWORD;
            // 当前时间
            String qts = String.valueOf(System.currentTimeMillis() / 1000);
            // 签名
            String sig = SignCertificationUtils.createSign(key, password, qts);

            // 设置通用的请求属性-加密请求header
            connection.setRequestProperty("sig", sig);
            connection.setRequestProperty("qts", qts);
            connection.setRequestProperty("key", key);

            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            // 防止打开url500情况下抛异常
            try {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } catch (Exception e) {
            }
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定URL发送POST方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return URL所代表远程资源的响应
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";

        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();

            // 设置通用的请求属性--安全认证
            String key = AnnotationConstants.KEY;
            String password = AnnotationConstants.PASSWORD;
            String qts = String.valueOf(System.currentTimeMillis() / 1000);
            String sig = SignCertificationUtils.createSign(key, password, qts);
            conn.setRequestProperty("sig", sig);
            conn.setRequestProperty("qts", qts);
            conn.setRequestProperty("key", key);

            // 设置通用的请求属性
            conn.setRequestProperty("accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += "/n" + line;
            }
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


    public static void main(String[] args) {
        /*Map<String, Double> fieldData1 = new HashMap<String, Double>();
        fieldData1.put("t", 23.56);
        fieldData1.put("h", 53.34);
        fieldData1.put("pm10", 32.00);

        Map<String, Double> fieldData2 = new HashMap<String, Double>();
        fieldData2.put("t", 23.56);
        fieldData2.put("h", 53.34);
        fieldData2.put("pm10", 32.00);

        Map<String, Double> fieldData3 = new HashMap<String, Double>();
        fieldData3.put("t", 23.56);
        fieldData3.put("h", 53.34);
        fieldData3.put("pm10", 32.00);

        List<DeviceData> list = new ArrayList<DeviceData>();
        DeviceData data1 = new DeviceData(System.currentTimeMillis(), "A1YP5214006D022D", fieldData1);
        DeviceData data2 = new DeviceData(System.currentTimeMillis(), "A1YP5214006D032D", fieldData2);
        DeviceData data3 = new DeviceData(System.currentTimeMillis(), "A1YP5214006D042D", fieldData3);
        list.add(data1);
        list.add(data2);
        list.add(data3);

        String jsonString = JSON.toJSONString(list);
        System.out.println(jsonString);

        // 测试 设备上传监测项数据
        String url = "http://jinuo.bjsogd.com:8092/ems_api/deviceData/uploadDevicesData";
        System.out.println(sendPost(url, jsonString));*/
    }
}

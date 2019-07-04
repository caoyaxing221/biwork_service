package com.biwork.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.X509TrustManager;
import org.apache.http.Consts;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.alibaba.druid.util.HttpClientUtils;
import com.alibaba.fastjson.JSONObject;


public class HttpUtil {
	private static DefaultTrustManager trustManager = new DefaultTrustManager();

	private static ConnectionKeepAliveStrategy myStrategy = null;
	static {
		myStrategy = new ConnectionKeepAliveStrategy() {
			public long getKeepAliveDuration(HttpResponse response,
					HttpContext context) {
				HeaderElementIterator it = new BasicHeaderElementIterator(
						response.headerIterator("Keep-Alive"));
				while (it.hasNext()) {
					HeaderElement he = it.nextElement();
					String param = he.getName();
					String value = he.getValue();
					if ((value != null) && (param.equalsIgnoreCase("timeout"))) {
						return Long.parseLong(value) * 1000L;
					}
				}
				return 5000L;
			}
		};
	}

	private static CloseableHttpClient httpclient = HttpClientBuilder.create()
			.setMaxConnTotal(90).setMaxConnPerRoute(15).setConnectionTimeToLive(10, TimeUnit.SECONDS)
			.setKeepAliveStrategy(myStrategy).build();

	public static JSONObject post(Map<String, String> params, String url)
			throws Exception {
		HttpPost post = new HttpPost(url);

		List<NameValuePair> list = new ArrayList<NameValuePair>();

		Iterator<String> it = params.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			list.add(new BasicNameValuePair(key, (String) params.get(key)));
		}
		post.setEntity(new UrlEncodedFormEntity(list, Consts.UTF_8));
		CloseableHttpResponse response = httpclient.execute(post);
		JSONObject jo = null;
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			String str = EntityUtils.toString(entity);
			if (entity != null)
				jo = JSONObject.parseObject(str);
		} else {
			EntityUtils.consume(response.getEntity());
		}

		return jo;
	}
	
	public static String postAndReturnString(Map<String, Object> params,
			String url) throws Exception {
		HttpPost post = new HttpPost(url);

		List<NameValuePair> list = new ArrayList<NameValuePair>();

		Iterator<String> it = params.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			System.out.println(key);
			list.add(new BasicNameValuePair(key, params.get(key).toString()));
		}
		post.setEntity(new UrlEncodedFormEntity(list, Consts.UTF_8));
		CloseableHttpResponse response = httpclient.execute(post);
		String jo = null;
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			jo = EntityUtils.toString(entity);
		} else {
			EntityUtils.consume(response.getEntity());
		}

		return jo;
	}
	
	public static String postJson(Map<String, Object> params,String url){
		String result="";
		HttpPost httpPost =null;
		try{
			String encoderJson = URLEncoder.encode(JSONObject.toJSONString(params), HTTP.UTF_8);
	        HttpClient httpClient = new DefaultHttpClient();
	        httpPost = new HttpPost(url);
	        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
	        
	        StringEntity se = new StringEntity(encoderJson);
	        se.setContentType("text/json");
	        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
	        httpPost.setEntity(se);
	        HttpResponse response = httpClient.execute(httpPost);
	        if(response != null){  
	            HttpEntity resEntity = response.getEntity();  
	            if(resEntity != null){  
	                result = EntityUtils.toString(resEntity,"UTF-8");  
	            }  
	        }  
		  }catch(Exception ex){  
	          ex.printStackTrace();  
	      } finally {
				// 释放链接
	    	  if(httpPost!=null){
	    		  httpPost.releaseConnection();
	    	  }
	      }
      return result;
	}
	/*public static String postAndReturnString(Map<String, String> params,
			String url) throws Exception {
		HttpPost post = new HttpPost(url);

		List<NameValuePair> list = new ArrayList<NameValuePair>();

		Iterator<String> it = params.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			list.add(new BasicNameValuePair(key, (String) params.get(key)));
		}
		post.setEntity(new UrlEncodedFormEntity(list, Consts.UTF_8));
		CloseableHttpResponse response = httpclient.execute(post);
		String jo = null;
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			jo = EntityUtils.toString(entity);
		} else {
			EntityUtils.consume(response.getEntity());
		}

		return jo;
	}*/

	public static String testPost(Map<String, String> params, String url)
			throws Exception {
		HttpPost post = new HttpPost(url);
		post.addHeader(
				"User-Agent",
				"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.2; .NET4.0C; .NET4.0E)");
		List<NameValuePair> list = new ArrayList<NameValuePair>();

		Iterator<String> it = params.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			list.add(new BasicNameValuePair(key, (String) params.get(key)));
		}
		post.setEntity(new UrlEncodedFormEntity(list, Consts.UTF_8));
		CloseableHttpResponse response = httpclient.execute(post);

		String redirectUrl = null;
		if (response.getStatusLine().getStatusCode() == 302) {
			redirectUrl = response.getFirstHeader("Location").getValue();
		} else if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			redirectUrl = EntityUtils.toString(entity);
		} else {
			HttpEntity entity = response.getEntity();
			redirectUrl = EntityUtils.toString(entity);
		}

		return redirectUrl;

	}

	public static String testGet(String url) throws Exception {
		HttpGet post = new HttpGet(url);

		CloseableHttpResponse response = httpclient.execute(post);
		HttpEntity entity = response.getEntity();

		String str = EntityUtils.toString(entity);
		return str;

	}

	public static String write(String content, String reqUrl) {
		try {
			// SSLContext sslContext = SSLContext.getInstance("SSL");
			// sslContext.init(new KeyManager[0],
			// new TrustManager[] { trustManager }, new SecureRandom());
			// SSLContext.setDefault(sslContext);

			URL url = new URL(reqUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(90000);
			conn.setReadTimeout(90000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestProperty("Content-Type",
					"application/json;charset=utf-8");

			conn.connect();
			if (content != null) {
				OutputStream out = conn.getOutputStream();
				out.write(content.getBytes("utf-8"));
			}
			int code = conn.getResponseCode();

			InputStream in = conn.getInputStream();
			byte[] data = new byte[1024];
			StringBuffer resp = new StringBuffer("");
			while (in.read(data) != -1) {
				resp.append(new String(data, "utf-8").trim());
			}

			in.close();
			conn.disconnect();
			return resp.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String writegbk(String content, String reqUrl) {

		HttpPost method = new HttpPost(reqUrl);
		String entity="响应";
		try {
//			method.setEntity(new StringEntity(content,ContentType.create("text/xml", "utf-8")));
			method.setEntity(new StringEntity(content,ContentType.create("application/json", "utf-8")));
			CloseableHttpResponse r = httpclient.execute(method);
			if (r.getStatusLine().getStatusCode() == 200) {
				 entity = EntityUtils.toString(r.getEntity());
				System.out.println("【URL为：】" + reqUrl + "【包体为:】" + content
						+ "【响应串为:】" + entity);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return entity;

	}

	private static class DefaultTrustManager implements X509TrustManager {
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	}

	public static void main(String[] args) {
		// String content="<xml>"+
		// "<buyer_logon_id><![CDATA[187****1887]]></buyer_logon_id>"+
		// "<buyer_user_id><![CDATA[2088302283704988]]></buyer_user_id>"+
		// "<charset><![CDATA[UTF-8]]></charset>"+
		// "<fee_type><![CDATA[CNY]]></fee_type>"+
		// "<fund_bill_list><![CDATA[[{\"amount\":\"0.01\",\"fundChannel\":\"ALIPAYACCOUNT\"}]]]></fund_bill_list>"+
		// "<mch_id><![CDATA[102520047234]]></mch_id>"+
		// "<nonce_str><![CDATA[1481016518557]]></nonce_str>"+
		// "<openid><![CDATA[2088302283704988]]></openid>"+
		// "<out_trade_no><![CDATA[ZX10252004723420161206172724]]></out_trade_no>"+
		// "<out_transaction_id><![CDATA[2016120621001004980229953238]]></out_transaction_id>"+
		// "<pay_result><![CDATA[0]]></pay_result>"+
		// "<result_code><![CDATA[0]]></result_code>"+
		// "<sign><![CDATA[D20FDE8A5E1F65E5173E3A500BF68DC8]]></sign>"+
		// "<sign_type><![CDATA[MD5]]></sign_type>"+
		// "<status><![CDATA[0]]></status>"+
		// "<time_end><![CDATA[20161206172838]]></time_end>"+
		// "<total_fee><![CDATA[1]]></total_fee>"+
		// "<trade_type><![CDATA[pay.alipay.native]]></trade_type>"+
		// "<transaction_id><![CDATA[102520047234201612067038222281]]></transaction_id>"+
		// "<version><![CDATA[2.0]]></version>"+
		// "</xml>";

		// String
		// content="<xml><buyer_logon_id><![CDATA[187****1887]]></buyer_logon_id>"+
		// "<buyer_user_id><![CDATA[2088302283704988]]></buyer_user_id>"+
		// "<charset><![CDATA[UTF-8]]></charset>"+
		// "<fee_type><![CDATA[CNY]]></fee_type>"+
		// "<fund_bill_list><![CDATA[[{\"amount\":\"0.01\",\"fundChannel\":\"ALIPAYACCOUNT\"}]]]></fund_bill_list>"+
		// "<mch_id><![CDATA[102520047234]]></mch_id>"+
		// "<nonce_str><![CDATA[1481018789344]]></nonce_str>"+
		// "<openid><![CDATA[2088302283704988]]></openid>"+
		// "<out_trade_no><![CDATA[ZX10252004723420161206180515]]></out_trade_no>"+
		// "<out_transaction_id><![CDATA[2016120621001004980230001965]]></out_transaction_id>"+
		// "<pay_result><![CDATA[0]]></pay_result>"+
		// "<result_code><![CDATA[0]]></result_code>"+
		// "<sign><![CDATA[5FE0C4E0AA93B7F7A74D88C4D8289E0E]]></sign>"+
		// "<sign_type><![CDATA[MD5]]></sign_type>"+
		// "<status><![CDATA[0]]></status>"+
		// "<time_end><![CDATA[20161206180629]]></time_end>"+
		// "<total_fee><![CDATA[1]]></total_fee>"+
		// "<trade_type><![CDATA[pay.alipay.jspay]]></trade_type>"+
		// "<transaction_id><![CDATA[102520047234201612065186106263]]></transaction_id>"+
		// "<version><![CDATA[2.0]]></version>"+
		// "</xml>";

		// String content="<xml><bank_type><![CDATA[CFT]]></bank_type>"+
		// "<charset><![CDATA[UTF-8]]></charset>"+
		// "<fee_type><![CDATA[CNY]]></fee_type>"+
		// "<is_subscribe><![CDATA[N]]></is_subscribe>"+
		// "<mch_id><![CDATA[102520047234]]></mch_id>"+
		// "<nonce_str><![CDATA[1481077959619]]></nonce_str>"+
		// "<openid><![CDATA[oMJGHs9_UeLAniAPcdlXYWFNfoCk]]></openid>"+
		// "<out_trade_no><![CDATA[ZX10252004723420161209152003]]></out_trade_no>"+
		// "<out_transaction_id><![CDATA[4004552001201612072036874010]]></out_transaction_id>"+
		// "<pay_result><![CDATA[0]]></pay_result>"+
		// "<result_code><![CDATA[0]]></result_code>"+
		// "<sign><![CDATA[A41A40A5B3929B2C6D7512DA7137693C]]></sign>"+
		// "<sign_type><![CDATA[MD5]]></sign_type>"+
		// "<status><![CDATA[0]]></status>"+
		// "<time_end><![CDATA[20161207103239]]></time_end>"+
		// "<total_fee><![CDATA[1]]></total_fee>"+
		// "<trade_type><![CDATA[pay.weixin.native]]></trade_type>"+
		// "<transaction_id><![CDATA[102520047234201612075187311997]]></transaction_id>"+
		// "<version><![CDATA[2.0]]></version>"+
		// "</xml>";
		// write("{\"merOrdDate\":\"20161219\",\"mercId\":\"800028000050004\",\"mercOrdNo\":\"201612191846390001\",\"merchantSign\":\"MIIGfQYJKoZIhvcNAQcCoIIGbjCCBmoCAQExDjAMBggqhkiG9w0CBQUAMAsGCSqG\nSIb3DQEHAaCCBHcwggRzMIIDW6ADAgECAhQQWTt7mgfF6wgtK3jHqwQ8FArFXjAN\nBgkqhkiG9w0BAQUFADCBhzELMAkGA1UEBhMCQ04xLTArBgNVBAoMJOatpuaxieWQ\niOS8l+aYk+WuneenkeaKgOaciemZkOWFrOWPuDEYMBYGA1UECwwP5L+h5oGv5oqA\n5pyv6YOoMS8wLQYDVQQDDCbmrabmsYnlkIjkvJfmmJPlrp3np5HmioDmnInpmZDl\nhazlj7hDQTAeFw0xNjAzMDkwMjQ0MTBaFw0xNzAzMDkwMjQ0MTBaMIHYMSYwJAYJ\nKoZIhvcNAQkBFhdndW9qaWFuZ2h1YUB1bGljLmNvbS5jbjExMC8GA1UECwwoVUlE\nOuatpuaxieWQiOS8l+aYk+WuneenkeaKgOaciemZkOWFrOWPuDEeMBwGA1UECwwV\nVU5BTUU6ODAwMDI3MDAwMDUwMDAxMRIwEAYDVQQDDAnpg63msZ/ljY4xGDAWBgNV\nBAsMD+S/oeaBr+aKgOacr+mDqDEtMCsGA1UECgwk5q2m5rGJ5ZCI5LyX5piT5a6d\n56eR5oqA5pyJ6ZmQ5YWs5Y+4MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKC\nAQEAyC1J1K43DymzNF4ijMTiagk4H7l9THo3lv0bQmWviNwAJaeD39D4abtNZlGk\nQY2x26Qy7PWRwrXBO/uNpfxQr4YgEP5270Xs15kSxKsTPHd91LS6sidxiYXP34hy\nfagYjR3ItvID4LIuFtGbQfuID53xm+rDkVzRFpQpuRPCCTW5z92DOb/qNMeiOxAf\nCjpaqgxMsF8wOzHFHUMZP8N2UrWe5kQ8APqX1WlDhBpALjFIODbxg7a4dLQL269Z\n3gInnaY2/S0HKyCFdHkzn4skzcLCSIRs0XvH2CPkPD+0O1XvsDPuCzvf+NpgSD7w\nrXYqy9zyadrBY/kbv9WXlc7GcQIDAQABo4GDMIGAMAkGA1UdEwQCMAAwCwYDVR0P\nBAQDAgWgMGYGA1UdHwRfMF0wW6BZoFeGVWh0dHA6Ly90b3BjYS5pdHJ1cy5jb20u\nY24vcHVibGljL2l0cnVzY3JsP0NBPTcyRDY5RTQyNDZEODY5MDQ1RUVCQTYzQkY0\nNDQ4NzNCNUMxQzM4RTgwDQYJKoZIhvcNAQEFBQADggEBAKrUtxw2qnRvfEybKRi7\nCreXFRppcPQGgWgJtHhpLy3levJVQYN3UVayX11wLxvJEJ6MYJCyMRDNZP8nRWIa\nYYTXChY/UWN321Nv+nvFAw129DrEhsH7pmNsUw3nSYJsmQMD2meKBBfPkCTegyUN\nr95GfY1HICRYcsw1tuX4+iQgdKopa3i+uQ249vqjaNiTcNGV2M5DSi8e8XY+edpo\nN147xT/LEvpgzhuNJ/hcJL8jNybUePylpBMlFm9OHYQH8uLj8RhtCPoufQ9zZHaz\nR033HWArJGsBxwJT9cXs+VtZ89T7QDZhbYnV6mSSist2A3+pexOySwnTXMy423v2\n2YQxggHLMIIBxwIBATCBoDCBhzELMAkGA1UEBhMCQ04xLTArBgNVBAoMJOatpuax\nieWQiOS8l+aYk+WuneenkeaKgOaciemZkOWFrOWPuDEYMBYGA1UECwwP5L+h5oGv\n5oqA5pyv6YOoMS8wLQYDVQQDDCbmrabmsYnlkIjkvJfmmJPlrp3np5HmioDmnInp\nmZDlhazlj7hDQQIUEFk7e5oHxesILSt4x6sEPBQKxV4wDAYIKoZIhvcNAgUFADAN\nBgkqhkiG9w0BAQEFAASCAQCk4MS3+asvcV9WyWJE1pmVpW2+UrfrmSUjbDowjDr8\nNvj+03exw29b11hx29fE8tkXAtVkinyrJQjdakRRAqycsbi2qXQoLe3CtbLinXuA\n3PZ5lgbn4l63UPm8fnzQtkielpRif5uyCK2DiQk/qNnNyvnQ9sUH/V6nM5nc2NT7\nERsG38F1M2gSpSmnf7NefQGnpwK+kXT7pwkun53gIgACvAcdJB9gtS3U+HWsX/3L\nIRcIkbB0SrpyUzOpPQya24eSQuGox7p3lnilIsEpoDRAzycAIXpv7rf5B133MrVg\nN6Z+n+H3Ng6onKhIsU+8h+cQUXAjFpOo7Re0rUOU14to\",\"payDate\":\"20161219\",\"payInOrdNo\":\"201612190054294388\",\"status\":\"S\",\"sysCnl\":\"11\"}",
		// "http://127.0.0.1:8081/middlepaytrx/online/channelServerNotify/HEZHONG");

		// Map<String,String> params=new HashMap<String,String>();
		// params.put("r5_business", "WX");
		// params.put("trxType", "WX_SCANCODE");
		// params.put("r6_timestamp", "2016-12-09 15:20:03");
		// params.put("r4_bankId", "WX");
		// params.put("r8_orderStatus", "SUCCESS");
		// params.put("sign", "c960c27c69d70d06da7d5b3504eb7e49");
		// params.put("r1_merchantNo", "B100001338");
		// params.put("r9_serialNumber", "PN1612091520034JB");
		// params.put("r3_amount", "0.01");
		// params.put("r7_completeDate", "2016-12-09 15:48:53");
		// params.put("r2_orderNumber", "HUIRONGB10000133820161209152003");
		// params.put("r10_t0PayResult", "false");
		// params.put("retCode", "0000");
		// try {
		// System.out.println(postAndReturnString(params,
		// "http://127.0.0.1:8081/middlepaytrx/online/channelServerNotify/HUIRONG"));
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// Map<String,String> params=new HashMap<String,String>();
		// params.put("cardNo", "vdoV+ajwbeN1/QPjUXUDG2jCIK9+ErjV");
		// try {
		// // testGet("http://localhost:8081/middlepaytrx/wx/scanCommonCode");
		// post(params, "http://localhost:8081/middlepaytrx/wx/scanCommonCode");
		//
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		try {
			String str=HttpJsonUtils.httpClientUtils("","http://api.map.baidu.com/geocoder/v2/?callback=renderReverse&output=json&pois=0&coordtype=gcj02ll&location=39.980966,116.354736&ak=W9qKeY8rDzaF44GnNbFR1S3xPy5IwEZ8");
			System.out.println(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

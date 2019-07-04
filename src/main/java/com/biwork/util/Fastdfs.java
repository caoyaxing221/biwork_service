package com.biwork.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;


public class Fastdfs {
	public static String upload(String imageUrl) {
		StringBuilder str = new StringBuilder();
		try {
			//根据图片url转成文件流
			byte[] byteArray=Fastdfs.Image2Base64(imageUrl);
			ClassPathResource cpr = new ClassPathResource("fdfs_client.conf");
			ClientGlobal.init(cpr.getClassLoader().getResource("fdfs_client.conf").getPath());

			// 创建一个TrackerClient对象。
			TrackerClient trackerClient = new TrackerClient();
			// 创建一个TrackerServer对象。
			TrackerServer trackerServer = trackerClient.getConnection();
			// 声明一个StorageServer对象，null。
			StorageServer storageServer = null;
			// 获得StorageClient对象。
			StorageClient storageClient = new StorageClient(trackerServer, storageServer);
			// 直接调用StorageClient对象方法上传文件即可。
			String[] strings;
			
			strings = storageClient.upload_appender_file(byteArray, "jpg", null);

			for (String string : strings) {
				str.append(string).append("/");
			}
			str = str.delete(str.length() - 1, str.length());
			System.out.println(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str.toString();
	}
	public static String upload2(byte[] byteArray) {
		StringBuilder str = new StringBuilder();
		try {
			ClassPathResource cpr = new ClassPathResource("fdfs_client.conf");
			ClientGlobal.init(cpr.getClassLoader().getResource("fdfs_client.conf").getPath().replace("%20", " "));

			// 创建一个TrackerClient对象。
			TrackerClient trackerClient = new TrackerClient();
			// 创建一个TrackerServer对象。
			TrackerServer trackerServer = trackerClient.getConnection();
			// 声明一个StorageServer对象，null。
			StorageServer storageServer = null;
			// 获得StorageClient对象。
			StorageClient storageClient = new StorageClient(trackerServer, storageServer);
			// 直接调用StorageClient对象方法上传文件即可。
			String[] strings;

			strings = storageClient.upload_appender_file(byteArray, "jpg", null);


			for (String string : strings) {
				str.append(string).append("/");
			}
			str = str.delete(str.length() - 1, str.length());
			System.out.println(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str.toString();
	}

	public static String uploadVideo(byte[] byteArray) {
		StringBuilder str = new StringBuilder();
		try {
			ClassPathResource cpr = new ClassPathResource("fdfs_client.conf");
			ClientGlobal.init(cpr.getClassLoader().getResource("fdfs_client.conf").getPath());

			// 创建一个TrackerClient对象。
			TrackerClient trackerClient = new TrackerClient();
			// 创建一个TrackerServer对象。
			TrackerServer trackerServer = trackerClient.getConnection();
			// 声明一个StorageServer对象，null。
			StorageServer storageServer = null;
			// 获得StorageClient对象。
			StorageClient storageClient = new StorageClient(trackerServer, storageServer);
			// 直接调用StorageClient对象方法上传文件即可。
			String[] strings;

			strings = storageClient.upload_appender_file(byteArray, "mp4", null);


			for (String string : strings) {
				str.append(string).append("/");
			}
			str = str.delete(str.length() - 1, str.length());
			System.out.println(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str.toString();
	}
	
	public static String uploadPdf(byte[] byteArray) {
		StringBuilder str = new StringBuilder();
		try {
			ClassPathResource cpr = new ClassPathResource("fdfs_client.conf");
			ClientGlobal.init(cpr.getClassLoader().getResource("fdfs_client.conf").getPath());

			// 创建一个TrackerClient对象。
			TrackerClient trackerClient = new TrackerClient();
			// 创建一个TrackerServer对象。
			TrackerServer trackerServer = trackerClient.getConnection();
			// 声明一个StorageServer对象，null。
			StorageServer storageServer = null;
			// 获得StorageClient对象。
			StorageClient storageClient = new StorageClient(trackerServer, storageServer);
			// 直接调用StorageClient对象方法上传文件即可。
			String[] strings;

			strings = storageClient.upload_appender_file(byteArray, "pdf", null);


			for (String string : strings) {
				str.append(string).append("/");
			}
			str = str.delete(str.length() - 1, str.length());
			System.out.println(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str.toString();
	}
	
	public static String getToken(String remoteFilename, long ts, String secretKey) throws Exception{
	 	byte[] bsFilename = remoteFilename.getBytes("UTF-8");
	 	byte[] bsKey = secretKey.getBytes("UTF-8");
	 	byte[] bsTimestamp = (new Long(ts)).toString().getBytes("UTF-8");
	 	
	 	byte[] buff = new byte[bsFilename.length + bsKey.length + bsTimestamp.length];
	 	System.arraycopy(bsFilename, 0, buff, 0, bsFilename.length);
	 	System.arraycopy(bsKey, 0, buff, bsFilename.length, bsKey.length);
	 	System.arraycopy(bsTimestamp, 0, buff, bsFilename.length + bsKey.length, bsTimestamp.length);
	 	
	 	return md5(buff);
	 }
	 
	 /**
	 * md5 function
	 * @param source the input buffer
	 * @return md5 string
	 */
	 public static String md5(byte[] source) throws Exception{
	  	char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',  'e', 'f'};
	    java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
	    md.update(source);
	    byte tmp[] = md.digest();
	    char str[] = new char[32];
	    int k = 0;
	    for (int i = 0; i < 16; i++)
	    {
	     str[k++] = hexDigits[tmp[i] >>> 4 & 0xf];
	     str[k++] = hexDigits[tmp[i] & 0xf];
	    }
	    
	  	return new String(str);
	 }
	 
	public static byte[] Image2Base64(String imgUrl) throws Exception {
		URL url = null;
		InputStream is = null;
		ByteArrayOutputStream outStream = null;
		HttpURLConnection httpUrl = null;
		byte[] byteArray=null;
		try {
			url = new URL(imgUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			httpUrl.getInputStream();
			is = httpUrl.getInputStream();

			outStream = new ByteArrayOutputStream();
			// 创建一个Buffer字符串
			byte[] buffer = new byte[1024];
			// 每次读取的字符串长度，如果为-1，代表全部读取完毕
			int len = 0;
			
			// 使用一个输入流从buffer里把数据读取出来
			while ((len = is.read(buffer)) != -1) {
				// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
				outStream.write(buffer, 0, len);
			}
			System.out.println("encoding.byte数组:"+outStream.toByteArray().length);
			return outStream.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			is.close();
			outStream.close();
			httpUrl.disconnect();
		}
		return byteArray;
	}
	
	public static void main(String[] args) throws Exception {
//		TestFastdfs.upload("http://img03.tooopen.com/images/20131111/sy_46708898917.jpg");
		
/*		long lts = System.currentTimeMillis()/1000L;
		String remoteFilename = "M00/00/00/rBA8ElkH_l2ED019AAAAALJRpS4580.jpg";
		System.out.println("lts="+lts);
		String token = getToken(remoteFilename, lts, "FastDFS1234567890");
		System.out.println("token="+token);
		System.out.println("httpurl=http://172.16.60.18:8888/group1/"+remoteFilename+"?token="+token+"&ts="+lts);*/

		//String imgUrl =  "E:/ceshi.pdf";
		//byte[] bytes = Image2Base64(imgUrl);
		/*File file = new File("D:/test.jpg");// D:/test.jpg
		byte[] fileByte =Base64Util.getFileByte(file);
		//String result = byteToBase64Encoding(fileByte);
		String s = Base64Util.byteToBase64Encoding(fileByte);

		// 快惠卡
		String urlFront = Fastdfs.upload2(fileByte);
		System.out.println(urlFront);*/
		String aa=Fastdfs.upload("http://pic13.photophoto.cn/20091109/0034034824143467_b.jpg");
		System.out.println(aa);
		ClassPathResource cpr = new ClassPathResource("fdfs_client.conf");
		ClientGlobal.init(cpr.getClassLoader().getResource("fdfs_client.conf").getPath());
//		File file = new File("E:/111.jpg");
//		byte[] fileByte =Base64Util.getFileByte(file);
//		String s = Base64Util.byteToBase64Encoding(fileByte);
//		System.out.println(s);

	}
}

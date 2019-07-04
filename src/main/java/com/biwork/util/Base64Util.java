package com.biwork.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class Base64Util {
	
	private static Logger log = Logger.getLogger(Base64Util.class); 

	/**
	 * @param b 鏂囦欢鐨刡yte鏁扮祫
	 * @return base64缂栫爜
	 */
	public static String byteToBase64Encoding(byte[] b) throws UnsupportedEncodingException{
		
		byte[] bytes = Base64.encodeBase64(b);
		
		String result = new String(bytes);
		return result;
	}
	
	/**
	 * @param data 闇�瑙ｇ爜鐨勫瓧绗︿覆
	 * @return base64瑙ｇ爜
	 */
	public static byte[] byteToBase64Decoding(String data){
		if(StringUtils.isNotBlank(data)){
			return Base64.decodeBase64(data);
		}
		return null;
	}
	
	/**
	 * 鍘嬬缉byte[]
	 */
    public static String compressData(byte[] data) {  
        try {  
        	log.info("鍘嬬缉鍓嶆枃浠跺ぇ灏廳ata===>" + data.length);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();  
            DeflaterOutputStream zos = new DeflaterOutputStream(bos);  
            zos.write(data);  
            zos.close();  
            log.info("鍘嬬缉鍚庢枃浠跺ぇ灏廱os.toByteArray()===>" + bos.toByteArray().length);
            return byteToBase64Encoding(bos.toByteArray()); //鍏堝帇缂╋紝鍦ㄨ浆鐮�
        } catch (Exception ex) {  
        	log.error("compressData failed:" + ex.getMessage());
            return "ZIP_ERR";  
        }  
    }  
    
    /**
     * 鍏堣В鐮侊紝鍦ㄨВ鍘�
     */
    public static byte[] decompressData(String encdata) {
        try {
        	log.info("鎺ユ敹瑙ｇ爜鍓嶅ぇ灏�==>" + encdata.getBytes().length);
        	byte[] byteToBaseData = byteToBase64Decoding(encdata);
        	log.info("Base64瑙ｇ爜鍚庡ぇ灏�==>" + byteToBaseData.length);
        	if(byteToBaseData != null){
	            ByteArrayOutputStream bos = new ByteArrayOutputStream();
	            InflaterOutputStream zos = new InflaterOutputStream(bos);
	            zos.write(byteToBaseData);
	            zos.close();
	            log.info("瑙ｅ帇鍚庡ぇ灏�==>" + bos.toByteArray().length);
	            return bos.toByteArray();
        	}
        } catch (Exception ex) {
            log.error("decompressData failed:" + ex.getMessage());
        }
        return null;
    }

	public static byte[] getBytesFromFile(File f) {
		if (f == null) {
			return null;
		}
		try {
			FileInputStream stream = new FileInputStream(f);
			ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = stream.read(b)) != -1)
				out.write(b, 0, n);
			stream.close();
			out.close();
			return out.toByteArray();
		} catch (IOException e) {
		}
		return null;
	}
    
    /**
	 * @param file 闇�杞崲base64浜岃繘鍒剁殑鏂囦欢
	 */
	public static byte[] getFileByte(File file) throws IOException{
		BufferedInputStream bis = null;
		ByteArrayOutputStream bos = null;
		try {
			bos = new ByteArrayOutputStream((int)file.length());
			bis = new BufferedInputStream(new FileInputStream(file));
			
			byte[] buffer = new byte[1024];
			int b = -1;
			while((b = bis.read(buffer)) != -1){
				bos.write(buffer, 0, b);
			}
			return bos.toByteArray();
		} catch (Exception e) {
			 log.error("getFileByte failed:" + e.getMessage());
		} finally {
			bos.close();
			bis.close();
		}
		return null;
	}
	
	//娓│
	public static void main(String[] args) throws IOException {
		File file = new File("G:\\123.png");
		byte[] fileByte = getFileByte(file);
		String result = byteToBase64Encoding(fileByte);
//		log.info("瑙ｇ爜===>" + result);
		
		File file2 = new File("G:\\abc.png");
//		byteToBase64Decoding(result, file2);
//		log.info("瑙ｇ爜===>瀹屾瘯");
	/*	String str = "eyJTYWxlQmlsbCI6eyJTTEJfSUQiOiIiLCJCaWxsTm8iOiIiLCJCaWxsVHlwZSI6MCwiTWVtYmVy%0D%0ASWQiOiIiLCJNZW1iZXJOYW1lIjoiIiwiQ3VzdG9tZXJUeXBlIjoiIiwiVG90YWxQcm9kdWN0Ijo0%0D%0ALCJUb3RhbE51bSI6NC4wMDAwMDAwMDAwMDAwMDAwLCJUb3RhbE1vbmV5Ijo5My4wMDAwMDAwMDAw%0D%0AMDAwMDAsIkRpc2NvdW50IjowLjAwMDAwMDAwMDAwMDAwMDAwLCJEaXNjb3VudFJhdGUiOjEwLjAw%0D%0AMDAwMDAwMDAwMDAwMCwiVG90YWxEaXNjb3VudCI6MC4wMDAwMDAwMDAwMDAwMDAwMCwiUmVhbFBh%0D%0AeU1vbmV5Ijo5My4wMDAwMDAwMDAwMDAwMDAsIlJlY2VpdmVNb25leSI6OTMuMDAwMDAwMDAwMDAw%0D%0AMDAwLCJTYWxlck5hbWUiOiIxNTI2MDIwNTkwNiIsIlJldFJlYXNvbklkIjoiIiwiU3RhdHVzIjow%0D%0ALCJQYXlUeXBlIjowLCJSZW1hcmsiOiIiLCJTaG9waW5nR3VpZGVJZCI6IiIsIkd1aWRlTmFtZSI6%0D%0AIiIsIlNhbGVUaW1lIjoiIiwiT3BlcmF0b3JJZCI6IjM3MGQwNDVhOGRiN2UzMzZkNTBkYzMyZTFh%0D%0AYWE4ZTdlIiwiVXBkYXRlcklkIjoiMzcwZDA0NWE4ZGI3ZTMzNmQ1MGRjMzJlMWFhYThlN2UiLCJD%0D%0AcmVhdGVUaW1lIjoiMjAxNS0wNy0wMiAxNzowNToxNCIsIlVwZGF0ZVRpbWUiOiIyMDE1LTA3LTAy%0D%0AIDE3OjA1OjE0IiwiQ29tcGFueUlkIjoiYzkzNjQzMzEzNTc3YmFmNzM1NDFjYjVlYTFkNWViZjIi%0D%0AfSwiU2FsZURldGFpbCI6W3siU0xEX0lEIjoiIiwiQmlsbFR5cGUiOjAsIkJpbGxObyI6IiIsIlN0%0D%0AYXR1cyI6MCwiU2t1SWQiOiI3NTE4MDM5MDkwMzI3MTk0MzY5NTE5OTc5ZTk0NzZlNyIsIlByb2R1%0D%0AY3RJZCI6IjI4MzBhNjg1YTZjZWFjMGJiODI1OTI1YTM0ZGE0YjlmIiwiQmFyY29kZSI6IiIsIlBy%0D%0Ab2R1Y3ROYW1lIjoi6LWg5ZOB5rWL6K%2BVMiIsIlVuaXQiOiLljIUiLCJSYXRlIjowLjAwMDAwMDAw%0D%0AMDAwMDAwMDAwLCJOdW0iOjEuMDAwMDAwMDAwMDAwMDAwMCwiQ3VyU3RvY2siOjAuMDAwMDAwMDAw%0D%0AMDAwMDAwMDAsIkNvc3RQcmljZSI6NS4wMDAwMDAwMDAwMDAwMDAwLCJTYWxlUHJpY2UiOjguMDAw%0D%0AMDAwMDAwMDAwMDAwMCwiVG90YWxNb25leSI6OC4wMDAwMDAwMDAwMDAwMDAwLCJEaXNjb3VudFBy%0D%0AaWNlIjowLjAwMDAwMDAwMDAwMDAwMDAwLCJUb3RhbERpc2NvdW50IjowLjAwMDAwMDAwMDAwMDAw%0D%0AMDAwLCJSZWFsUHJpY2UiOjAuMDAwMDAwMDAwMDAwMDAwMDAsIk1lbWJlcklkIjoiIiwiTWVtYmVy%0D%0ATmFtZSI6IiIsIkltYWdlUGF0aCI6IiIsIlF1aWNrTm8iOiJaUENTMiIsIlN0YW5kYXJkIjoiIiwi%0D%0AUGFja2V0U3RhdHVzIjowLCJJc0FkZCI6MCwiQXR0cmlidXRlIjoiIiwiSXNUcmFkZSI6MCwiT3Bl%0D%0AcmF0b3JJZCI6IjM3MGQwNDVhOGRiN2UzMzZkNTBkYzMyZTFhYWE4ZTdlIiwiVXBkYXRlcklkIjoi%0D%0AMzcwZDA0NWE4ZGI3ZTMzNmQ1MGRjMzJlMWFhYThlN2UiLCJDcmVhdGVUaW1lIjoiMjAxNS0wNy0w%0D%0AMiAxNzowNToxNCIsIlVwZGF0ZVRpbWUiOiIyMDE1LTA3LTAyIDE3OjA1OjE0IiwiQ29tcGFueUlk%0D%0AIjoiYzkzNjQzMzEzNTc3YmFmNzM1NDFjYjVlYTFkNWViZjIifSx7IlNMRF9JRCI6IiIsIkJpbGxU%0D%0AeXBlIjowLCJCaWxsTm8iOiIiLCJTdGF0dXMiOjAsIlNrdUlkIjoiNmVhNDk1YjYwMjdmOTY4YzZj%0D%0AMTFjYjFjNGM3MDllM2IiLCJQcm9kdWN0SWQiOiI3MmQxYTY3ODIwYTUzODAzNmVjNmE0ZTkxYjU4%0D%0AZDMxZiIsIkJhcmNvZGUiOiIiLCJQcm9kdWN0TmFtZSI6Iui1oOWTgea1i%2BivlTEiLCJVbml0Ijoi%0D%0A5YyFIiwiUmF0ZSI6MC4wMDAwMDAwMDAwMDAwMDAwMCwiTnVtIjoxLjAwMDAwMDAwMDAwMDAwMDAs%0D%0AIkN1clN0b2NrIjowLjAwMDAwMDAwMDAwMDAwMDAwLCJDb3N0UHJpY2UiOjMuMDAwMDAwMDAwMDAw%0D%0AMDAwMCwiU2FsZVByaWNlIjo1LjAwMDAwMDAwMDAwMDAwMDAsIlRvdGFsTW9uZXkiOjUuMDAwMDAw%0D%0AMDAwMDAwMDAwMCwiRGlzY291bnRQcmljZSI6MC4wMDAwMDAwMDAwMDAwMDAwMCwiVG90YWxEaXNj%0D%0Ab3VudCI6MC4wMDAwMDAwMDAwMDAwMDAwMCwiUmVhbFByaWNlIjowLjAwMDAwMDAwMDAwMDAwMDAw%0D%0ALCJNZW1iZXJJZCI6IiIsIk1lbWJlck5hbWUiOiIiLCJJbWFnZVBhdGgiOiIiLCJRdWlja05vIjoi%0D%0AWlBDUzEiLCJTdGFuZGFyZCI6IiIsIlBhY2tldFN0YXR1cyI6MCwiSXNBZGQiOjAsIkF0dHJpYnV0%0D%0AZSI6IiIsIklzVHJhZGUiOjAsIk9wZXJhdG9ySWQiOiIzNzBkMDQ1YThkYjdlMzM2ZDUwZGMzMmUx%0D%0AYWFhOGU3ZSIsIlVwZGF0ZXJJZCI6IjM3MGQwNDVhOGRiN2UzMzZkNTBkYzMyZTFhYWE4ZTdlIiwi%0D%0AQ3JlYXRlVGltZSI6IjIwMTUtMDctMDIgMTc6MDU6MTQiLCJVcGRhdGVUaW1lIjoiMjAxNS0wNy0w%0D%0AMiAxNzowNToxNCIsIkNvbXBhbnlJZCI6ImM5MzY0MzMxMzU3N2JhZjczNTQxY2I1ZWExZDVlYmYy%0D%0AIn0seyJTTERfSUQiOiIiLCJCaWxsVHlwZSI6MCwiQmlsbE5vIjoiIiwiU3RhdHVzIjowLCJTa3VJ%0D%0AZCI6ImFkZjIzOTM0YTRiNDdjZGE5NTMzNzIyZDI2Y2Q4ZmRmIiwiUHJvZHVjdElkIjoiYmUyMjVj%0D%0ANDA3NjBiMThiNWU3ZjUwOWM4MDgwNmJkYjMiLCJCYXJjb2RlIjoiNjkwOTY0MDAwMDE3MCIsIlBy%0D%0Ab2R1Y3ROYW1lIjoi5Zub54m56YWS5LiJ5pifNDUlNDYwTUwiLCJVbml0Ijoi55uSIiwiUmF0ZSI6%0D%0AMC4wMDAwMDAwMDAwMDAwMDAwMCwiTnVtIjoxLjAwMDAwMDAwMDAwMDAwMDAsIkN1clN0b2NrIjow%0D%0ALjAwMDAwMDAwMDAwMDAwMDAwLCJDb3N0UHJpY2UiOjUwLjAwMDAwMDAwMDAwMDAwMCwiU2FsZVBy%0D%0AaWNlIjo3NS4wMDAwMDAwMDAwMDAwMDAsIlRvdGFsTW9uZXkiOjc1LjAwMDAwMDAwMDAwMDAwMCwi%0D%0ARGlzY291bnRQcmljZSI6MC4wMDAwMDAwMDAwMDAwMDAwMCwiVG90YWxEaXNjb3VudCI6MC4wMDAw%0D%0AMDAwMDAwMDAwMDAwMCwiUmVhbFByaWNlIjowLjAwMDAwMDAwMDAwMDAwMDAwLCJNZW1iZXJJZCI6%0D%0AIiIsIk1lbWJlck5hbWUiOiIiLCJJbWFnZVBhdGgiOiIiLCJRdWlja05vIjoiU1RKU1g0NTQ2ME1M%0D%0AIiwiU3RhbmRhcmQiOiIiLCJQYWNrZXRTdGF0dXMiOjAsIklzQWRkIjowLCJBdHRyaWJ1dGUiOiIi%0D%0ALCJJc1RyYWRlIjowLCJPcGVyYXRvcklkIjoiMzcwZDA0NWE4ZGI3ZTMzNmQ1MGRjMzJlMWFhYThl%0D%0AN2UiLCJVcGRhdGVySWQiOiIzNzBkMDQ1YThkYjdlMzM2ZDUwZGMzMmUxYWFhOGU3ZSIsIkNyZWF0%0D%0AZVRpbWUiOiIyMDE1LTA3LTAyIDE3OjA1OjE0IiwiVXBkYXRlVGltZSI6IjIwMTUtMDctMDIgMTc6%0D%0AMDU6MTQiLCJDb21wYW55SWQiOiJjOTM2NDMzMTM1NzdiYWY3MzU0MWNiNWVhMWQ1ZWJmMiJ9LHsi%0D%0AU0xEX0lEIjoiIiwiQmlsbFR5cGUiOjAsIkJpbGxObyI6IiIsIlN0YXR1cyI6MCwiU2t1SWQiOiJh%0D%0ANTBkYmJkZDkxNWM3OGNlYjgzYjE4M2Y0MzRkYmRmNiIsIlByb2R1Y3RJZCI6ImZmYWI4NDBhMmE4%0D%0AZWQwNTg1MDU0NWVjODJjYWE1ZDJjIiwiQmFyY29kZSI6IjY5MjY4OTI1MjQwNjMiLCJQcm9kdWN0%0D%0ATmFtZSI6IumTtum5rSDoirHnlJ%2FniZvlpbblpI3lkIjom4vnmb3ppa7mlpkgNTAwbWwg55O2Iiwi%0D%0AVW5pdCI6IuS4qiIsIlJhdGUiOjAuMDAwMDAwMDAwMDAwMDAwMDAsIk51bSI6MS4wMDAwMDAwMDAw%0D%0AMDAwMDAwLCJDdXJTdG9jayI6MC4wMDAwMDAwMDAwMDAwMDAwMCwiQ29zdFByaWNlIjo0LjAwMDAw%0D%0AMDAwMDAwMDAwMDAsIlNhbGVQcmljZSI6NS4wMDAwMDAwMDAwMDAwMDAwLCJUb3RhbE1vbmV5Ijo1%0D%0ALjAwMDAwMDAwMDAwMDAwMDAsIkRpc2NvdW50UHJpY2UiOjAuMDAwMDAwMDAwMDAwMDAwMDAsIlRv%0D%0AdGFsRGlzY291bnQiOjAuMDAwMDAwMDAwMDAwMDAwMDAsIlJlYWxQcmljZSI6MC4wMDAwMDAwMDAw%0D%0AMDAwMDAwMCwiTWVtYmVySWQiOiIiLCJNZW1iZXJOYW1lIjoiIiwiSW1hZ2VQYXRoIjoiIiwiUXVp%0D%0AY2tObyI6IllMIEhTTk5GSERCWUwgNTAwbWwgUCIsIlN0YW5kYXJkIjoiNTAwbWwgIiwiUGFja2V0%0D%0AU3RhdHVzIjowLCJJc0FkZCI6MCwiQXR0cmlidXRlIjoiIiwiSXNUcmFkZSI6MCwiT3BlcmF0b3JJ%0D%0AZCI6IjM3MGQwNDVhOGRiN2UzMzZkNTBkYzMyZTFhYWE4ZTdlIiwiVXBkYXRlcklkIjoiMzcwZDA0%0D%0ANWE4ZGI3ZTMzNmQ1MGRjMzJlMWFhYThlN2UiLCJDcmVhdGVUaW1lIjoiMjAxNS0wNy0wMiAxNzow%0D%0ANToxNCIsIlVwZGF0ZVRpbWUiOiIyMDE1LTA3LTAyIDE3OjA1OjE0IiwiQ29tcGFueUlkIjoiYzkz%0D%0ANjQzMzEzNTc3YmFmNzM1NDFjYjVlYTFkNWViZjIifV0sIk1lbWJlckNvbnN1bWUiOnsiTWVtYmVy%0D%0AUG9pbnRzIjoiIiwiRXhjaGFuZ2VQb2ludHMiOiIiLCJBZGRQb2ludHMiOiIiLCJNZW1iZXJEZWJ0%0D%0AIjoiIn19&behavior=eyJ1c2VySWQiOiIzNzBkMDQ1YThkYjdlMzM2ZDUwZGMzMmUxYWFhOGU3ZSIsInVzZXJOYW1lIjoi%0D%0AMTUyNjAyMDU5MDYiLCJzdGFmZklkIjoiIiwiYWNjZXNzdG9rZW4iOiIxYmI3YjVkNjc2ZmQyNzIy%0D%0AZGE4YzU0N2I4YTI4NmIwZCIsInBsYXRmb3JtIjoicGMiLCJ2ZXJzaW9uIjoiVjQuNS4xLjAiLCJz%0D%0AdG9yZWlkIjoiYzkzNjQzMzEzNTc3YmFmNzM1NDFjYjVlYTFkNWViZjIifQ%3D%3D&timestamp=1435827915&sig=56d4c31bed70da2524f4add52b71aff7";
		
		String decode = URLDecoder.decode(str, "UTF-8");
		System.out.println(decode);
		
		String json = "";
		if(decode.contains("&")){
			json = new String(Base64.decodeBase64(decode.split("&")[0]));
		}else{
			json = new String(Base64.decodeBase64(decode));
		}
		
		System.out.println(json);*/
	}

}

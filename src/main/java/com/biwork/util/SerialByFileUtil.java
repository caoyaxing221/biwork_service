package com.biwork.util;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Hashtable;

public class SerialByFileUtil {

	
	public synchronized static void set(String key, long value) {
		Serial.set(key,value);
	}
	public synchronized static String get(String key,boolean initFlag) {
		Long seq=Serial.get(key,initFlag);
		StringBuffer buffer=new StringBuffer();
		int length=String.valueOf(seq).length();
		if(length<8){
			for(int i=0;i<8-length;i++){
				buffer.append(0);
			}
		}
		buffer.append(seq);
		return buffer.toString();
	}

	
	
	static class Serial {
		private static final Hashtable<String, MappedByteBuffer> serials = new Hashtable<String, MappedByteBuffer>();
		private static final Hashtable<String, FileChannel> fchannels = new Hashtable<String, FileChannel>();
		public Serial() {
		}
		public synchronized static long get(String key,boolean initFlag) {
			try {
				String appKey;
				FileChannel fc;
				MappedByteBuffer serial;
				appKey = (new StringBuilder()).append(
						key).append(".serial").toString();
				fc = (FileChannel) fchannels.get(appKey);
				serial = (MappedByteBuffer) serials.get(appKey);
				long serno;
				if (initFlag==true||serial == null) {
					RandomAccessFile RAFile = new RandomAccessFile(PropertiesUtil.getProperty("user.dir")+appKey, "rw");
					if (RAFile.length() < 8L)
						RAFile.writeLong(Long.parseLong(PropertiesUtil.getProperty("init.line.index")));
					fc = RAFile.getChannel();
					int size = (int) fc.size();
					serial = fc.map(
							java.nio.channels.FileChannel.MapMode.READ_WRITE, 0L,
							size);
					fchannels.put(appKey, fc);
					serials.put(appKey, serial);
				}
				serial.rewind();
				serno = serial.getLong()+1;
				serial.flip();
				serial.putLong(serno);
				return serno;
			} catch (IOException e) {
				e.printStackTrace();
				return 0L;
			}
		}
		public synchronized static void set(String key,long value) {
			try {
				String appKey;
				FileChannel fc;
				MappedByteBuffer serial;
				appKey = (new StringBuilder()).append(
						key).append(".serial").toString();
				fc = (FileChannel) fchannels.get(appKey);
				serial = (MappedByteBuffer) serials.get(appKey);
				if (serial == null) {
					RandomAccessFile RAFile = new RandomAccessFile(PropertiesUtil.getProperty("user.dir")+appKey, "rw");
					if (RAFile.length() < 23L)
						RAFile.writeLong(Long.parseLong(PropertiesUtil.getProperty("init.line.index")));
					fc = RAFile.getChannel();
					int size = (int) fc.size();
					serial = fc.map(
							java.nio.channels.FileChannel.MapMode.READ_WRITE, 0L,
							size);
					fchannels.put(appKey, fc);
					serials.put(appKey, serial);
				}
				FileLock flock = fc.lock();
				serial.rewind();
				serial.getLong();
				serial.flip();
				serial.putLong(value);
				flock.release();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	public static void main(String args[]) throws IOException, InterruptedException {
		for(int i=0;i<1000;i++){
			System.out.println(get("rpid",true));
		}
	}
}

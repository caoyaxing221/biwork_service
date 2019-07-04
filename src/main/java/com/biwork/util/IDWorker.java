package com.biwork.util;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * 根据IP不同，避免分布程序中可能的重复 理论上限每秒生成1000 * 512个顺序ID，实际在个人机器上最多大概 1000 * 27个
 * 
 */
public class IDWorker {
	private static long lastTimestamp = -1L;
	private static long sequence = 0L;
	private static long sequenceBits = 9L;
	private static long sequenceMask = -1L ^ (-1L << (int) sequenceBits);
	static String workId = "123456";
	static {
		try {
			final InetAddress host = InetAddress.getLocalHost();
			final String address = host.getHostAddress();
			String[] arrStr = address.split("\\.");
			StringBuffer sb = new StringBuffer();
			for (int i = 2; i < arrStr.length; i++) {
				sb.append(String.format("%03d", Integer.valueOf(arrStr[i])));
			}
			workId = sb.toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public static String nextID(String flag) {
		synchronized (IDWorker.class) {
			long timestamp;
			while (true) {
				timestamp = timeGen();
				if (timestamp < lastTimestamp) {
					continue;
				} else if (timestamp == lastTimestamp) {
					sequence = (sequence + 1) & sequenceMask;
					if (sequence == 0) {
						timestamp = tilNextMillis(lastTimestamp);
					}
				} else {
					sequence = 0L;
				}
				lastTimestamp = timestamp;
				break;
			}
			String timeStr = String.valueOf(timestamp);
//			return timeStr.substring(0, 14) + flag + workId + timeStr.substring(14) + String.format("%03d", sequence);
			return timeStr.substring(0, 14) + flag  + timeStr.substring(14) + String.format("%03d", sequence);
		}
	}

	protected static long tilNextMillis(long lastTimestamp) {
		long timestamp = timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	protected static long timeGen() {
		String t = new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date());
		if(t.length()<17){
			t = t.substring(0, 14) + "0" + t.substring(14);
		}
		return Long.valueOf(t);
	}

	public static void main(String[] s) throws Exception {
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					int i = 0;
					while (i < 100) {
						try {
							System.out.println(IDWorker.nextID(""));
						} catch (Exception e) {
							e.printStackTrace();
						}
						i++;
					}
				}
			}) {
			}.start();
		}
//		System.out.println(timeGen());
	}
}

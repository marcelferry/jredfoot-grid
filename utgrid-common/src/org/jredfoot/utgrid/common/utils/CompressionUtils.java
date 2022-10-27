package org.jredfoot.utgrid.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class CompressionUtils
{
	/**
	 * Logger for this class.
	 */
	private static Log log = LogFactory.getLog(CompressionUtils.class);
	/**
	 * Determines whether debug log statements are enabled.
	 */
	private static boolean debugEnabled = log.isDebugEnabled();

	/**
	 * Instantiation of this class is not permitted.
	 */
	private CompressionUtils()
	{
	}

	/**
	 * Compress an array of bytes using the gzip API.
	 * @param bytes the array of bytes to compress.
	 * @param start the start position in the array of bytes.
	 * @param length the number of bytes to compress.
	 * @return the compressed bytes as an array of bytes.
	 * @throws Exception if an error occurs while compressing.
	 */
	public static byte[] zip(byte[] bytes, int start, int length) throws Exception
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		//GZIPOutputStream gzos = new GZIPOutputStream(baos);
		DataOutputStream dos = null;
		/*
		DeflaterOutputStream gzos = new DeflaterOutputStream(baos);
		dos = new DataOutputStream(gzos);
		*/
		dos = new DataOutputStream(baos);
		dos.writeInt(length);
		dos.write(bytes, start, length);
		dos.flush();
		dos.close();
		//if (debugEnabled) log.debug("compressed "+length+" bytes into "+baos.size());
		return baos.toByteArray();
	}

	/**
	 * Uncompress an array of bytes using the gzip API.
	 * @param bytes the array of bytes to uncompress.
	 * @param start the start position in the array of bytes.
	 * @param length the number of bytes to uncompress.
	 * @return the uncompressed bytes as an array.
	 * @throws Exception if an error occurs while uncompressing.
	 */
	public static byte[] unzip(byte[] bytes, int start, int length) throws Exception
	{
		int count = 0;
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes, start, length);
		//GZIPInputStream gzis = new GZIPInputStream(bais);
		DataInputStream dis = null;
		/*
		InflaterInputStream gzis = new InflaterInputStream(bais);
		dis = new DataInputStream(gzis);
		*/
		dis = new DataInputStream(bais);
		int len = dis.readInt();
		byte[] result = new byte[len];
		while (count < len)
		{
			int offset = count;
			int n = dis.read(result, offset, len-count);
			if (n <= 0) break;
			count += n;
		}
		dis.close();
		if (debugEnabled) log.debug("uncompressed " + length + " bytes into " + len);
		return result;
	}
}

package org.jredfoot.utgrid.cert.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.globus.common.CoGProperties;
import org.globus.gsi.CertificateRevocationLists;
import org.globus.gsi.GlobusCredential;
import org.globus.gsi.TrustedCertificates;
import org.globus.gsi.proxy.ProxyPathValidator;
import org.globus.gsi.proxy.ProxyPathValidatorException;

public class Utils {

	public static <T> List<T> asList(T... a) {
		if (a == null) {
			return new ArrayList<T>();
		} else {
			return Arrays.asList(a);
		}
	}

    
	public static File getCaGridUserHome() {
		String userHome = System.getProperty("user.home");
		File userHomeF = new File(userHome);
		File caGridCache = new File(userHomeF.getAbsolutePath()
				+ File.separator + ".cagrid");
		if (!caGridCache.exists()) {
			caGridCache.mkdirs();
		}
		return caGridCache;
	}

    
	public static File getTrustedCerificatesDirectory() {
		String caDir = CoGProperties.getDefault().getCaCertLocations();
		if (caDir != null) {
			return new File(caDir);
		} else {
			String userHome = System.getProperty("user.home");
			File userHomeF = new File(userHome);
			File dir = new File(userHomeF.getAbsolutePath() + File.separator
					+ ".globus" + File.separator + "certificates");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			return dir;
		}
	}

    
	public static void validateGlobusCredential(GlobusCredential cred)
			throws ProxyPathValidatorException {
		validateCertificateChain(cred.getCertificateChain());
	}
    

	public static void validateCertificateChain(X509Certificate[] chain)
			throws ProxyPathValidatorException {
		ProxyPathValidator validator = new ProxyPathValidator();
		validator.validate(chain, TrustedCertificates
				.getDefaultTrustedCertificates().getCertificates(),
				CertificateRevocationLists
						.getDefaultCertificateRevocationLists());

	}
    
    
	public static String simplifyErrorMessage(String m) {
		if ((m == null) || (m.equalsIgnoreCase("null"))) {
			m = "Unknown Error";
		} else if (m.indexOf("Connection refused") >= 0) {
			m = "Error establishing a connection to the requested service, the service may not exist or may be down.";
		} else if (m.indexOf("Unknown CA") >= 0) {
			m = "Could establish a connection with the service, the service CA is not trusted.";
		}
		return m;
	}
    
	public static void copyFile(File in, File out) throws IOException {
        File inCannon = in.getCanonicalFile();
        File outCannon = out.getCanonicalFile();
		// avoids copying a file to itself
		if (inCannon.equals(outCannon)) {
			return;
		}
		// ensure the output file location exists
		outCannon.getParentFile().mkdirs();

		BufferedInputStream fis = new BufferedInputStream(
            new FileInputStream(inCannon));
		BufferedOutputStream fos = new BufferedOutputStream(
			new FileOutputStream(outCannon));

		// a temporary buffer to read into
		byte[] tmpBuffer = new byte[8192];
		int len = 0;
		while ((len = fis.read(tmpBuffer)) != -1) {
			// add the temp data to the output
			fos.write(tmpBuffer, 0, len);
		}
		// close the input stream
		fis.close();
		// close the output stream
		fos.flush();
		fos.close();
	}
    

	// Copies all files under srcDir to dstDir.
	// If dstDir does not exist, it will be created.
	public static void copyDirectory(File srcDir, File dstDir)
			throws IOException {
		if (srcDir.isDirectory()) {
			if (!dstDir.exists()) {
				dstDir.mkdir();
			}

			String[] children = srcDir.list();
			for (int i = 0; i < children.length; i++) {
				copyDirectory(new File(srcDir, children[i]), new File(dstDir,
						children[i]));
			}
		} else {
			copyFile(srcDir, dstDir);
		}
	}
    

	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					System.err.println("could not remove directory: "
							+ dir.getAbsolutePath());
					return false;
				}
			}
		}
		return dir.delete();
	}
    

	/**
	 * Merges the two arrays (not necessarily creating a new array). If both are
	 * null, null is returned. If one is null, the other is returned.
	 * 
	 * @throws ArrayStoreException
	 *             uses System.arrarycopy and has same contract
	 */
	public static java.lang.Object concatenateArrays(Class resultClass,
			java.lang.Object arr1, java.lang.Object arr2)
			throws ArrayStoreException {
		if (arr1 == null) {
			return arr2;
		} else if (arr2 == null) {
			return arr1;
		}
		java.lang.Object newArray = Array.newInstance(resultClass, Array
				.getLength(arr1)
				+ Array.getLength(arr2));
		System.arraycopy(arr1, 0, newArray, 0, Array.getLength(arr1));
		System.arraycopy(arr2, 0, newArray, Array.getLength(arr1), Array
				.getLength(arr2));

		return newArray;
	}
    

	/**
	 * Appends to an array
	 * 
	 * @param array
	 *            The array to append to
	 * @param appendix
	 *            The object to append to the array
	 * @return An array with the new item appended
	 */
	public static java.lang.Object appendToArray(java.lang.Object array,
			java.lang.Object appendix) {
		Class arrayType = array.getClass().getComponentType();
		java.lang.Object newArray = Array.newInstance(arrayType, Array
				.getLength(array) + 1);
		System.arraycopy(array, 0, newArray, 0, Array.getLength(array));
		Array.set(newArray, Array.getLength(newArray) - 1, appendix);
		return newArray;
	}
    

	/**
	 * Removed an object from an array.
	 * 
	 * @param array
	 * @param removal
	 * @return An array with the item removed
	 */
	public static java.lang.Object removeFromArray(java.lang.Object array,
			java.lang.Object removal) {
		Class arrayType = array.getClass().getComponentType();
		int length = Array.getLength(array);
		List<Object> temp = new ArrayList<Object>(length - 1);
		for (int i = 0; i < length; i++) {
			java.lang.Object o = Array.get(array, i);
			if (!o.equals(removal)) {
				temp.add(o);
			}
		}
		java.lang.Object newArray = Array.newInstance(arrayType, temp.size());
		System.arraycopy(temp.toArray(), 0, newArray, 0, temp.size());
		return newArray;
	}
    

	public static StringBuffer fileToStringBuffer(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		StringBuffer sb = new StringBuffer();
		try {
			String s = null;
			while ((s = br.readLine()) != null) {
				sb.append(s + "\n");
			}
		} finally {
			br.close();
		}

		return sb;
	}
    

	public static StringBuffer inputStreamToStringBuffer(InputStream stream)
			throws IOException {
		InputStreamReader reader = new InputStreamReader(stream);
		StringBuffer str = new StringBuffer();
		char[] buff = new char[8192];
		int len = 0;
		while ((len = reader.read(buff)) != -1) {
			str.append(buff, 0, len);
		}
		reader.close();
		return str;
	}
    

	public static String clean(String s) {
		if ((s == null) || (s.trim().length() == 0)) {
			return null;
		} else {
			return s;
		}
	}

    
	public static void stringBufferToFile(StringBuffer string, String fileName)
			throws IOException {
		FileWriter fw = new FileWriter(new File(fileName));
		fw.write(string.toString());
		fw.close();
	}

    
	public static boolean equals(Object o1, Object o2) {
		if (o1 == null) {
			return o2 == null;
		}
		return o1.equals(o2);
	}
    


	public static List<File> recursiveListFiles(File baseDir,
			final FileFilter filter) {
		FileFilter dirFilter = new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.isDirectory() || filter.accept(pathname);
			}
		};
		File[] fileArray = baseDir.listFiles(dirFilter);
		List<File> files = new ArrayList<File>(fileArray.length);
		for (int i = 0; i < fileArray.length; i++) {
			if (fileArray[i].isDirectory()) {
				files.addAll(recursiveListFiles(fileArray[i], filter));
			} else {
				files.add(fileArray[i]);
			}
		}
		return files;
	}
    

	/**
	 * Gets a relative path from the source file to the destination
	 * 
	 * @param source
	 *            The source file or location
	 * @param destination
	 *            The file to target with the relative path
	 * @return The relative path from the source file's directory to the
	 *         destination file
	 */
	public static String getRelativePath(File source, File destination)
			throws IOException {
		String sourceDir = null;
		String destDir = null;
		if (source.isDirectory()) {
			sourceDir = source.getCanonicalPath();
		} else {
			sourceDir = source.getParentFile().getCanonicalPath();
		}
		if (destination.isDirectory()) {
			destDir = destination.getCanonicalPath();
		} else {
			destDir = destination.getParentFile().getCanonicalPath();
		}

		// find the overlap in the source and dest paths
		String overlap = findOverlap(sourceDir, destDir);
        // strip off a training File.separator
        if (overlap.endsWith(File.separator)) {
            if (overlap.equals(File.separator)) {
                overlap = "";
            } else {
                overlap = overlap.substring(0, overlap.length()
                    - File.separator.length() - 1);
            }
		}
		int overlapDirs = countChars(overlap, File.separatorChar);
		if (overlapDirs == 0) {
			// no overlap at all, return full path of destination file
			return destination.getCanonicalPath();
		}
		// difference is the number of path elements to back up before moving
		// down the tree
		int parentDirsNeeded = countChars(sourceDir, File.separatorChar)
				- overlapDirs;
		// difference is the number of path elements above the file to keep
		int parentDirsKept = countChars(destDir, File.separatorChar)
				- overlapDirs;

		// build the path
		StringBuffer relPath = new StringBuffer();
		for (int i = 0; i < parentDirsNeeded; i++) {
			relPath.append("..").append(File.separatorChar);
		}
		List<String> parentPaths = new LinkedList<String>();
		File parentDir = new File(destDir);
		for (int i = 0; i < parentDirsKept; i++) {
			parentPaths.add(parentDir.getName());
			parentDir = parentDir.getParentFile();
		}
		Collections.reverse(parentPaths);
		for (Iterator i = parentPaths.iterator(); i.hasNext();) {
			relPath.append(i.next()).append(File.separatorChar);
		}
		if (!destination.isDirectory()) {
			relPath.append(destination.getName());
		}
		return relPath.toString();
	}
    

	private static String findOverlap(String s1, String s2) {
		// TODO: More efficient would be some kind of binary search, divide and
		// conquer
		StringBuffer overlap = new StringBuffer();
		int count = Math.min(s1.length(), s2.length());
		for (int i = 0; i < count; i++) {
			char c1 = s1.charAt(i);
			char c2 = s2.charAt(i);
			if (c1 == c2) {
				overlap.append(c1);
			} else {
				break;
			}
		}
		return overlap.toString();
	}
    

	private static int countChars(String s, char c) {
		int count = 0;
		int index = -1;
		while ((index = s.indexOf(c, index + 1)) != -1) {
			count++;
		}
		return count;
	}
    
}

package com.datou.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 压缩文件操作的工具类
 * 
 * @author anbaihong
 * @date 2019年3月15日 下午2:31:57
 * @comment
 */
public final class ZipUtil
{

	private ZipUtil() {
		
	}

	/**
	 * 压缩zip格式文件
	 *
	 * @param targetFile
	 *            输出的文件
	 * @param sourceFiles
	 *            待压缩的文件数组
	 * @return true : 压缩成功 false: 压缩失败
	 */
	public static boolean compressZip(File targetFile, File... sourceFiles) {
		boolean flag = false;
		ZipOutputStream zipOut = null;
		BufferedOutputStream out = null;

		FileUtil.mkDirs(targetFile.getParentFile());

		if (targetFile.exists() && !targetFile.delete())
			return flag;
		try {
			zipOut = new ZipOutputStream(new FileOutputStream(targetFile));
			out = new BufferedOutputStream(zipOut);
			flag = compressZip(zipOut, out, "", sourceFiles);
		} catch (IOException e) {
			targetFile.delete();
		} finally {
			IOUtil.close(zipOut, out);
		}
		return flag;
	}

	/**
	 * 压缩zip格式文件
	 * 
	 * @param zipOut
	 * @param out
	 * @param filePath
	 *            压缩后的一级目录名
	 * @param sourceFiles
	 * @return true : 压缩成功 false: 压缩失败
	 * @throws IOException
	 */
	private static boolean compressZip(ZipOutputStream zipOut, BufferedOutputStream out, String filePath,
			File... sourceFiles) throws IOException {
		if (null != filePath && !"".equals(filePath)) {
			filePath += filePath.endsWith(File.separator) ? "" : File.separator;
		} else {
			filePath = "";
		}
		boolean flag = true;
		for (File file : sourceFiles) {
			if (null == file) {
				continue;
			}
			if (file.isDirectory()) {
				File[] fileList = file.listFiles();
				if (null == fileList) {
					return false;
				} else if (fileList.length < 1) {
					//如果空文件夹也进行压缩	
					//zipOut.putNextEntry(new ZipEntry(filePath + file.getName()+"/"));
					zipOut.putNextEntry(new ZipEntry(filePath + file.getName()));
				} else {
					// 只要flag有一次为false，整个递归的结果都为false
					flag = compressZip(zipOut, out, filePath + file.getName(), fileList) && flag;
				}
			} else {
				BufferedInputStream in = null;
				try {
					zipOut.putNextEntry(new ZipEntry(filePath + file.getName()));
					in = new BufferedInputStream(new FileInputStream(file));
					IOUtil.write(in, out);

				} finally {
					IOUtil.close(in);
				}
			}
		}
		return flag;
	}

	/**
	 * 解压zip格式文件
	 *
	 * @param originFile
	 *            zip文件
	 * @param targetDir
	 *            要解压到的目标路径
	 * @return void
	 * @throws IOException
	 */
	public static void decompressZip(File originFile, String targetDir) throws IOException {
		if (!targetDir.endsWith(File.separator)) {
			targetDir += File.separator;
		}

		InputStream inputStream = null;
		OutputStream outputStream = null;
		ZipEntry zipEntry = null;
		ZipFile zipFile = new ZipFile(originFile);

		try {
			Enumeration<? extends ZipEntry> entry = zipFile.entries();
			while (entry.hasMoreElements()) {
				zipEntry = entry.nextElement();
				String fileName = zipEntry.getName();
				File outputFile = new File(targetDir + fileName);
				if (zipEntry.isDirectory()) {
					FileUtil.mkDirs(outputFile);
					continue;
				} else if (!outputFile.getParentFile().exists()) {
					FileUtil.mkDirs(outputFile.getParent());
				}
				inputStream = zipFile.getInputStream(zipEntry);
				outputStream = new FileOutputStream(outputFile);

				IOUtil.write(inputStream, outputStream);
			}
		} finally {
			IOUtil.close(inputStream, outputStream, zipFile);
		}
	}
	
	

}

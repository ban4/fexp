package com.datou.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import com.datou.util.enums.DateEnum;
import com.datou.util.time.DateUtil;

/**
 * 文件操作的工具类
 * 
 * @author anbaihong
 * @date 2019年3月15日 下午2:17:38
 * @comment 
 */
public final class FileUtil {
	
	private FileUtil() {
		
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param filePath
	 *            要进行判断的文件的具体存储路径
	 * @return true :该文件存在 false:该文件不存在
	 */
	public static boolean exists(String filePath) {
		return new File(filePath).exists();
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param file
	 *            要进行判断的文件的对象
	 * @return true :该文件存在。 false:该文件不存在
	 */
	public static boolean exists(File file) {
		return file.exists();
	}

	/**
	 * 创建文件
	 * 
	 * @param filePath
	 *            文件的绝对路径
	 * @return true :创建成功 false:创建失败
	 */
	public static boolean createFile(String filePath) {
		return createFile(new File(filePath));
	}

	/**
	 * 创建文件
	 * 
	 * @param file
	 *            封装有文件具体存储路径的文件对象
	 * @return true :创建成功 false:创建失败
	 */
	public static boolean createFile(File file) {
		// 1.判断该文件是否存在，不存在则进行创建
		if (!file.exists()) {
			// 2.获取该文件的存储目录，并进行创建
			if (mkDirs(file.getParentFile())) {
				try {
					// 3.创建该文件
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return file.exists();
	}

	/**
	 * 创建文件夹,包括多层次的文件夹
	 * 
	 * @param dirPath
	 *            文件夹的具体存储路径
	 * @return true :创建成功 false:创建失败
	 */
	public static boolean mkDirs(String dirPath) {
		return mkDirs(new File(dirPath));
	}

	/**
	 * 创建文件夹,包括多层次的文件夹
	 * 
	 * @param dir
	 *            封装有文件夹具体存储路径的文件对象
	 * @return true :创建成功 false:创建失败
	 */
	public static boolean mkDirs(File dir) {
		// 当该目录对象不为空，并且该目录不存在时才创建该目录
		if ((null != dir) && (!dir.exists())) {
			dir.mkdirs();
		}
		return dir.exists();
	}

	/**
	 * 删除文件夹或文件
	 * 
	 * @param filePath
	 *            文件的具体存储路径
	 */
	public static boolean deleteFile(String filePath) {
		return deleteFile(new File(filePath));
	}

	/**
	 * 删除文件夹或文件
	 * 
	 * @param file
	 *            封装有文件夹具体存储路径的文件对象
	 */
	public static boolean deleteFile(File file) {
		if (!file.exists())
			return false;

		if (file.isDirectory()) {
			File files[] = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteFile(files[i]);
			}
		}
		return file.delete();
	}

	/**
	 * 复制文件夹
	 * 
	 * @param sourceDir
	 *            源文件夹
	 * @param targetDir
	 *            目标文件夹
	 * @throws IOException
	 */
	public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
		// 新建目标目录
		mkDirs(targetDir);

		// 获取源文件夹当下的文件或目录
		File[] file = (new File(sourceDir)).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// 源文件
				File sourceFile = file[i];
				// 目标文件
				File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
				copyFile(sourceFile, targetFile);
			}
			if (file[i].isDirectory()) {
				// 准备复制的源文件夹
				String dir1 = sourceDir + file[i].getName();
				// 准备复制的目标文件夹
				String dir2 = targetDir + File.separator + file[i].getName();
				copyDirectiory(dir1, dir2);
			}
		}
	}

	/**
	 * 复制文件
	 * 
	 * @param sourcefile
	 *            源文件
	 * @param targetFile
	 *            目标文件
	 * @throws IOException
	 */
	public static void copyFile(File sourcefile, File targetFile) {
		FileInputStream input = null;
		BufferedInputStream inbuff = null;
		FileOutputStream out = null;
		BufferedOutputStream outbuff = null;

		try {
			// 新建文件输入流并对它进行缓冲
			input = new FileInputStream(sourcefile);
			inbuff = new BufferedInputStream(input);

			// 新建文件输出流并对它进行缓冲
			out = new FileOutputStream(targetFile);
			outbuff = new BufferedOutputStream(out);

			IOUtil.write(inbuff, outbuff);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭流
			IOUtil.close(inbuff, outbuff, input, out);
		}
	}

	/**
	 * 在指定的父文件夹的基础上创建临时文件夹
	 * 
	 * @param parentDirPath
	 *            父文件夹
	 * @return String 创建好的临时文件夹的绝对路径
	 */
	public static String createTempDir(String parentDirPath) {
		String tempDir = null;
		if (!exists(parentDirPath)) {
			mkDirs(parentDirPath);
		}
		tempDir = parentDirPath + File.separator
				+ DateUtil.format(new Date(), DateEnum.YYYYMMDDHHMMSS_FORMAT.getFormat()) + File.separator;

		if (exists(tempDir)) {
			deleteFile(tempDir);
			mkDirs(tempDir);
		} else {
			mkDirs(tempDir);
		}
		return tempDir;
	}

	
	public static String readFile2Str(File file) {
		InputStream in = null;
		BufferedReader reader = null;
		StringBuffer buffer = new StringBuffer();

		try {
			in = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(in));

			String line = reader.readLine();

			while (line != null) {
				buffer.append(line);
				buffer.append("\n");
				line = reader.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtil.close(reader);
			IOUtil.close(in);
		}

		return buffer.toString();
	}
	
	

}

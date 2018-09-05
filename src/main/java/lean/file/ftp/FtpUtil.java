/**
 * Copyright: 版权所有 ( c ) 北京瑞和云图科技有限公司 2015。保留所有权利。
 * Author:Philip
 * Created: 2015
 */
package lean.file.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

/**
 * FTP文件传送工具
 * Author:Philip
 */
public class FtpUtil {
	private FTPClient ftp;

	/**
	 * 进行初始化配置连接FTP服务器。
	 * 
	 * @param ipAddress
	 *            FTP服务器的IP地址
	 * @param port
	 *            FTP服务器的端口
	 * @param userName
	 *            FTP服务器的用户名
	 * @param passWord
	 *            FTP服务器的密码
	 */
	public boolean isFtpUtil(String ipAddress, int port, String userName,
			String passWord) {
		boolean flag = false;
		ftp = new FTPClient();
		try {
			ftp.connect(ipAddress, port);// 连接FTP服务器
			ftp.login(userName, passWord);// 登陆FTP服务器
			if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
				ftp.disconnect();
				flag = false;
			} else {
				flag = true;
			}
		} catch (SocketException e) {
		} catch (IOException e) {
		}
		return flag;
	}

	/**
	 * 上传文件到FTP服务器
	 * 
	 * @param local
	 *            本地文件名称，绝对路径
	 * @param remote
	 *            远程文件路径,支持多级目录嵌套，支持递归创建不存在的目录结构
	 * @throws IOException
	 */
	public void upload(String local, String remote) throws IOException {
		// 设置PassiveMode传输
		ftp.enterLocalPassiveMode();
		// 设置以二进制流的方式传输
		ftp.setFileType(FTP.BINARY_FILE_TYPE);
		// 对远程目录的处理
		String remoteFileName = remote;
		if (remote.contains("/")) {
			remoteFileName = remote.substring(remote.lastIndexOf("/") + 1);
			// 创建服务器远程目录结构，创建失败直接返回
			if (!CreateDirecroty(remote)) {
				return;
			}
		}
		File f = new File(local);
		uploadFile(remoteFileName, f);
	}

	public void uploadFile(String remoteFile, File localFile)
			throws IOException {
		InputStream in = new FileInputStream(localFile);
		ftp.storeFile(remoteFile, in);
		in.close();
	}

	/**
	 * 递归创建远程服务器目录
	 * 
	 * @param remote
	 *            远程服务器文件绝对路径
	 * 
	 * @return isuccess 目录创建是否成功
	 * @throws IOException
	 */
	public boolean CreateDirecroty(String remote) throws IOException {
		boolean isuccess = true;
		String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
		// 如果远程目录不存在，则递归创建远程服务器目录
		if (!directory.equalsIgnoreCase("/")
				&& !ftp.changeWorkingDirectory(new String(directory))) {
			int start = 0;
			int end = 0;
			if (directory.startsWith("/")) {
				start = 1;
			} else {
				start = 0;
			}
			end = directory.indexOf("/", start);
			while (true) {
				String subDirectory = new String(remote.substring(start, end));
				if (!ftp.changeWorkingDirectory(subDirectory)) {
					if (ftp.makeDirectory(subDirectory)) {
						ftp.changeWorkingDirectory(subDirectory);
					} else {
						isuccess = false;
						return isuccess;
					}
				}
				start = end + 1;
				end = directory.indexOf("/", start);
				// 检查所有目录是否创建完毕
				if (end <= start) {
					break;
				}
			}
		}
		return isuccess;
	}

	public boolean uploadAll(String filename, String uploadpath)
			throws Exception {
		boolean isuccess = false;
		File file = new File(filename);
		// 要上传的是否存在
		if (!file.exists()) {
			return isuccess;
		}
		// 要上传的是否是文件夹
		if (!file.isDirectory()) {
			return isuccess;
		}
		File[] flles = file.listFiles();
		for (File files : flles) {
			if (files.exists()) {
				if (files.isDirectory()) {
					this.uploadAll(files.getAbsoluteFile().toString(),
							uploadpath);
				} else {
					String local = files.getCanonicalPath().replaceAll("\\\\",
							"/");
					String remote = uploadpath
							+ local.substring(local.indexOf("/") + 1);
					upload(local, remote);
					ftp.changeWorkingDirectory("/");
				}
			}
		}
		return true;
	}
}

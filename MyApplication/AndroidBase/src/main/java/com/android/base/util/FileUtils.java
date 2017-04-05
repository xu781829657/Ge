package com.android.base.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.android.base.frame.Base;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 文件操作工具类
 *
 * @description：
 * @author ldm
 * @date 2016-4-18 上午11:26:25
 */
public class FileUtils {
	/**
	 * 检测SD卡是否存在
	 */
	public static boolean isExistSDcard() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}

	/**
	 * 从指定文件夹获取文件
	 *
	 * @description：
	 * @author ldm
	 * @date 2016-4-18 上午11:42:24
	 */
	public static File getAppointFile(String folderPath, String fileNmae) {
		File file = new File(getSaveFolder(folderPath).getAbsolutePath()
				+ File.separator + fileNmae);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * 获取文件夹对象
	 *
	 * @return 返回SD卡下的指定文件夹对象，若文件夹不存在则创建
	 */
	public static File getSaveFolder(String folderName) {
		File file = new File(Environment.getExternalStorageDirectory()
				.getAbsoluteFile()
				+ File.separator
				+ folderName
				+ File.separator);
		file.mkdirs();
		return file;
	}

	/**
	 * 文件流关闭操作
	 *
	 * @description：
	 * @author ldm
	 * @date 2016-4-18 上午11:28:07
	 */
	public static void close(Closeable... closeables) {
		if (null == closeables || closeables.length <= 0) {
			return;
		}
		for (Closeable cb : closeables) {
			try {
				if (null == cb) {
					continue;
				}
				cb.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 上传文件到服务器 (原帖地址：http://blog.csdn.net/lk_blog/article/details/7706348)
	 *
	 * @description：
	 * @author ldm
	 * @date 2016-4-18 下午1:38:33
	 */
	public static String sendFile(String serverUrl, String filePath,
								  String newName) throws Exception {
		String end = "\r\n";
		String hyphens = "--";
		String boundary = "*****";

		URL url = new URL(serverUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		/* 允许Input、Output，不使用Cache */
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);
		/* 设置传送的method=POST */
		con.setRequestMethod("POST");
		/* setRequestProperty */

		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
		con.setRequestProperty("Content-Type", "multipart/form-data;boundary="
				+ boundary);
		/* 设置DataOutputStream */
		DataOutputStream ds = new DataOutputStream(con.getOutputStream());
		ds.writeBytes(hyphens + boundary + end);
		ds.writeBytes("Content-Disposition: form-data; "
				+ "name=\"file1\";filename=\"" + newName + "\"" + end);
		ds.writeBytes(end);

		/* 取得文件的FileInputStream */
		FileInputStream fStream = new FileInputStream(filePath);
		/* 设置每次写入1024bytes */
		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];

		int length = -1;
		/* 从文件读取数据至缓冲区 */
		while ((length = fStream.read(buffer)) != -1) {
			/* 将资料写入DataOutputStream中 */
			ds.write(buffer, 0, length);
		}
		ds.writeBytes(end);
		ds.writeBytes(hyphens + boundary + hyphens + end);

		/* close streams */
		fStream.close();
		ds.flush();

		/* 取得Response内容 */
		InputStream is = con.getInputStream();
		int ch;
		StringBuffer b = new StringBuffer();
		while ((ch = is.read()) != -1) {
			b.append((char) ch);
		}
		/* 关闭DataOutputStream */
		ds.close();
		return b.toString();
	}

	/***
	 * File 返回SD卡路径（File 类型）
	 */
	private static File SDPath() {
		return Environment.getExternalStorageDirectory();
	}

	private static File PackagPath() {
		return new File(Base.getContext().getFilesDir().getPath());
	}

	/***
	 * boolean 检查是否存在SD卡
	 */
	public static boolean checkSD() {
		boolean bo = false;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			bo = true;
		}
		return bo;
	}

	/**
	 * Try to return the absolute file path from the given Uri
	 *
	 * @param context
	 * @param uri
	 * @return the file path or null
	 */
	public static String getRealFilePath(final Context context, final Uri uri) {
		if (null == uri)
			return null;
		final String scheme = uri.getScheme();
		String data = null;
		if (scheme == null)
			data = uri.getPath();
		else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
			data = uri.getPath();
		} else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
			Cursor cursor = context.getContentResolver().query(uri,
					new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null);
			if (null != cursor) {
				if (cursor.moveToFirst()) {
					int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
					if (index > -1) {
						data = cursor.getString(index);
					}
				}
				cursor.close();
			}
		}
		return data;
	}

	/***
	 * String 返回SD卡路径(String 类型)
	 */
	public static String getSDCardPath() {
		File sdcardDir = null;
		boolean sdcardExist = FileUtils.checkSD();
		if (sdcardExist) {
			sdcardDir = FileUtils.SDPath();
		} else {
			sdcardDir = FileUtils.PackagPath();
		}
		return sdcardDir.getPath().toString();
	}

	public static final String IMAGE_PATH = "/60kou/image/";

	/***
	 * void 保存图片 bitmap:传入的图片 context：上下文 imageName：要保存图片的名字格式，是商户号+终端号+系统参看号
	 * pathBoolean：是选择保存图片的路径
	 */
	public static File saveBitmap(Bitmap bitmap, Context context,
											 String imageName) {
		// ToastShow show = new ToastShow(context);

		if (FileUtils.checkSD()) {
			Bitmap bmp = bitmap;
			String savePath = getSDCardPath() + IMAGE_PATH;
			try {
				File path = new File(savePath);
				String filepath = savePath + imageName + ".jpg";
				File file = new File(filepath);
				// 判断路径是否存在
				if (!path.exists()) {
					path.mkdirs();
				}
				// 判断文件夹是否文件
				if (!file.exists()) {
					file.createNewFile();
				}

				FileOutputStream fos = null;
				fos = new FileOutputStream(file);

				if (null != fos) {
					// 设置保存图片类型
					bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
					// 倒序关闭输入输出流
					fos.flush();
					fos.close();
				}

			} catch (Exception e) {
				LogUtils.d("文件保存失败！");
			}
		} else {
			// show.show("SD卡不可用");
		}
		return new File(getSDCardPath() + IMAGE_PATH + imageName + ".jpg");
	}

	public static Bitmap getFileFromSd(Context context, String imageName) {
		// ToastShow show= new ToastShow(context);
		Bitmap bm = null;
		if (checkSD()) {
			String savePath = getSDCardPath() + IMAGE_PATH;
			try {
				File file = new File(savePath);
				// 判断路径是否存在
				if (file.exists()) {
					for (File temp : file.listFiles()) {
						// File mfile=new File(path);
						// if (mfile.exists()) {//若该文件存在

						String tempName = temp.getName().substring(0,
								temp.getName().lastIndexOf("."));
						if (imageName.endsWith(tempName)) {
							bm = BitmapFactory.decodeFile(savePath
									+ temp.getName());
						}

					}
				}

			} catch (Exception e) {
				// show.show("文件删除失败");
			}
		} else {
			// show.show("文件删除失败");
		}
		return bm;
	}


	public static String convertFileSize(long size) {
		long kb = 1024;
		long mb = kb * 1024;
		long gb = mb * 1024;

		if (size >= gb) {
			return String.format("%.1f GB", (float) size / gb);
		} else if (size >= mb) {
			float f = (float) size / mb;
			return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
		} else if (size >= kb) {
			float f = (float) size / kb;
			return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
		} else
			return String.format("%d B", size);
	}

}
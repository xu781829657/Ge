package com.android.base.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.provider.MediaStore;

import com.android.base.frame.activity.BaseActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * 照片工具
 * 
 * @author yejingjie
 * 
 */
public class PhotoUtils {
	/* 用来标识请求照相功能的activity */
	private static final int CAMERA_WITH_DATA = 0x1111;
	/* 用来标识请求gallery的activity */
	private static final int PHOTO_PICKED_WITH_DATA = 0x1112;
	private static final int PHOTO_RESULT = 0x1113;

	public static final String ACTION_OPEN_DOCUMENT = "android.intent.action.OPEN_DOCUMENT";
	/* 拍照的照片存储位置 */
	private File PHOTO_DIR = new File(FileUtils.getSDCardPath()
			+ FileUtils.IMAGE_PATH);

	private File mCurrentPhotoFile;// 照相机拍照得到的图片

	/**
	 * 拍照获取图片
	 * 
	 */
	public void doTakePhoto(Activity ac) {
		try {
			// Launch camera to take photo for selected contact
			PHOTO_DIR.mkdirs();// 创建照片的存储目录
			mCurrentPhotoFile = new File(PHOTO_DIR, "xxx.jpg");// 给新照的照片文件命名
			final Intent intent = getTakePickIntent(mCurrentPhotoFile);
			ac.startActivityForResult(intent, CAMERA_WITH_DATA);
		} catch (ActivityNotFoundException e) {
			// Toast.makeText(this, R.string.photoPickerNotFoundText,
			// Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 请求Gallery程序
	 * 
	 * @param ac
	 */
	public void doPickPhotoFromGallery(Activity ac) {
		Intent intent = new Intent(Intent.ACTION_PICK,
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		intent.setType("image/*");
		ac.startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
	}

	public static interface OnAccomplishCallBack {
		public void onAccomplish(Bitmap bitmap, byte[] photoBmpData);
	}

	// 因为调用了Camera和Gally所以要判断他们各自的返回情况,他们启动时是这样的startActivityForResult
	// @SuppressLint("NewApi")
	public void onActivityResult(BaseActivity ac, int requestCode, int resultCode,
								 Intent data, OnAccomplishCallBack listener) {
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		switch (requestCode) {
		case PHOTO_PICKED_WITH_DATA: // 调用Gallery返回的
			if (null != data) {
				Uri originalUri = data.getData();
				doCropPhoto(ac, originalUri);
			}
			break;
		case CAMERA_WITH_DATA: {// 照相机程序返回的,再次调用图片剪辑程序去修剪图片
			if (null != mCurrentPhotoFile) {
				doCropPhoto(ac, mCurrentPhotoFile);
			}
			break;
		}
		case PHOTO_RESULT:
			if (null != data) {
				final Bitmap photoBmp = data.getParcelableExtra("data");
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				photoBmp.compress(CompressFormat.PNG, 0 /* ignored for PNG */,
						bos);
				byte[] photoBmpData = bos.toByteArray();
				if (null != listener) {
					listener.onAccomplish(photoBmp, photoBmpData);
				}
				// // text
				// ByteArrayInputStream bs = new
				// ByteArrayInputStream(photoBmpData);
				// Bitmap bmpTest = BitmapFactory.decodeStream(bs);
				// CommonMethod.saveBitmap(bmpTest, ac, "test");
				// System.out.println("jason length= "+photoBmpData.length);
				// ByteArrayInputStream bs = new
				// ByteArrayInputStream(bitmapdata);
				// System.out.println("jason photo=" + photo);
			}
			break;
		}
	}

	protected void doCropPhoto(Activity ac, Uri photoUri) {
		try {
			// 启动gallery去剪辑这个照片
			final Intent intent = getCropImageIntent(photoUri);
			ac.startActivityForResult(intent, PHOTO_RESULT);
		} catch (Exception e) {
			// Toast.makeText(this, R.string.photoPickerNotFoundText,
			// Toast.LENGTH_LONG).show();
		}
	}

	protected void doCropPhoto(Activity ac, File f) {
		try {
			// 启动gallery去剪辑这个照片
			final Intent intent = getCropImageIntent(Uri.fromFile(f));
			ac.startActivityForResult(intent, PHOTO_RESULT);
		} catch (Exception e) {
			// Toast.makeText(this, R.string.photoPickerNotFoundText,
			// Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * Constructs an intent for image cropping. 调用图片剪辑程序
	 */
	public Intent getCropImageIntent(Uri photoUri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(photoUri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 80);
		intent.putExtra("outputY", 80);
		intent.putExtra("return-data", true);
		return intent;
	}

	private Intent getTakePickIntent(File f) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		return intent;
	}

	// /**
	// * 用当前时间给取得的图片命名
	// *
	// */
	// private String getPhotoFileName() {
	// Date date = new Date(System.currentTimeMillis());
	// SimpleDateFormat dateFormat = new SimpleDateFormat(
	// "'IMG'_yyyy-MM-dd_HH:mm:ss");
	// return dateFormat.format(date) + ".jpg";
	// }

}

package com.quanying.app.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer.RoundedDrawable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PhotoUtilEx {

	/**
	 * TAG
	 */
	private static final String TAG = "PhotoUtil";

	public static final int PHOTO_FLAG_REQUESTCODE_TAKE = 0x10001;
	public static final int PHOTO_FLAG_REQUESTCODE_EDIT = 0x10002;
	public static final int PHOTO_FLAG_REQUESTCODE_SELECT = 0x10003;
	Activity activity;
	String outFile;
	String tempFile;
	int shapeFlag;

	public PhotoUtilEx(Activity a, int shapeFlag) {
		activity = a;
		this.shapeFlag = shapeFlag;
	}

	public static int computeSampleSize(int w, int h, int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(w, h, minSideLength, maxNumOfPixels);
		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}

	private static int computeInitialSampleSize(double w, double h, int minSideLength, int maxNumOfPixels) {
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128
				: (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}
		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	/**
	 * 通过压缩图片的尺寸来压缩图片大小
	 * 
	 * @param pathName
	 *            图片的完整路
	 * @param targetWidth
	 *            缩放的目标宽
	 * @param targetHeight
	 *            缩放的目标高
	 * @return 缩放后的图片
	 */
	public static boolean compressBySize(String pathName, int targetWidth, int targetHeight) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;// 不去真的解析图片，只是获取图片的头部信息，包含宽高等
		Bitmap bitmap = BitmapFactory.decodeFile(pathName, opts);
		// 得到图片的宽度高度；
		int imgWidth = opts.outWidth;
		int imgHeight = opts.outHeight;
		// 分别计算图片宽度、高度与目标宽度、高度的比例；取大于等于该比例的小整数；
		int widthRatio = (int) Math.ceil(((float) imgWidth) / targetWidth);
		int heightRatio = (int) Math.ceil(((float) imgHeight) / targetHeight);
		if (widthRatio > 1 || widthRatio > 1) {
			boolean ok = false;
			int i = 1;
			if (widthRatio > heightRatio) {
				while (!ok) {
					int x = (int) Math.pow(2, i);
					if (x >= widthRatio) {
						ok = true;
						widthRatio = x;
					}
					i++;
				}
				opts.inSampleSize = widthRatio;
			} else {
				while (!ok) {
					int x = (int) Math.pow(2, i);
					if (x >= heightRatio) {
						ok = true;
						heightRatio = x;
					}
					i++;
				}
				opts.inSampleSize = heightRatio;
			}
		}
		// 设置好缩放比例后，加载图片进内容
		opts.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(pathName, opts);
		File f = new File(pathName);
		try {
			return bitmap.compress(CompressFormat.PNG, 100, new FileOutputStream(f));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

//	public static boolean compressBySize(String pathName, int targetWidth, int targetHeight) {
//		BitmapFactory.Options opts = new BitmapFactory.Options();
//		opts.inJustDecodeBounds = false;
//		Bitmap bitmap = BitmapFactory.decodeFile(pathName, opts);
//		int width = bitmap.getWidth();
//		int height = bitmap.getHeight();
//		Matrix matrix = new Matrix();
//		float scaleWidht = ((float) width / targetWidth);
//		float scaleHeight = ((float) height / targetHeight);
//		Bitmap newbmp = null;
//		if (scaleWidht > 1 || scaleHeight > 1) {
//			float t = scaleWidht > scaleHeight ? scaleWidht : scaleHeight;
//			newbmp = Bitmap.createBitmap(bitmap, 0, 0, (int) (width / t), (int) (height / t));
//			if (!bitmap.isRecycled()) {
//				bitmap.recycle();
//			}
//		} else {
//			newbmp = bitmap;
//		}
//		File f = new File(pathName);
//		try {
//			boolean ok = newbmp.compress(CompressFormat.PNG, 100, new FileOutputStream(f));
//			if (!newbmp.isRecycled()) {
//				newbmp.recycle();
//			}
//			return ok;
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}


	public static Bitmap compressImage(Bitmap btm_PhotoImage, File file_after) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		btm_PhotoImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			btm_PhotoImage.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			if (options == 0) {
				break;
			}
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		if (file_after.exists()) {
			file_after.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(file_after);
			bitmap.compress(Bitmap.CompressFormat.JPEG, options, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			Log.e(TAG, "没有找到相应的图片文件！");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	public boolean onActivityResult(ImageView view, int requestCode, int resultCode, Intent data) {
		if (requestCode == PhotoUtilEx.PHOTO_FLAG_REQUESTCODE_TAKE) {
			if (resultCode == Activity.RESULT_OK) {
				// BitmapFactory.Options option = new BitmapFactory.Options();
				// option.inJustDecodeBounds = true;
				// BitmapFactory.decodeFile(tempFile, option);
				// int sampleSize = computeSampleSize(option.outWidth,
				// option.outHeight, -1, 1024 * 768);
				// int w = option.outWidth / sampleSize;
				// int h = option.outHeight / sampleSize;
				editPhoto(Uri.fromFile(new File(tempFile)), 100, 1);
			}
		} else if (requestCode == PHOTO_FLAG_REQUESTCODE_EDIT) {
			if (resultCode == Activity.RESULT_OK) {
				// if (data == null) {
				// Toast.makeText(activity, "照相失败", Toast.LENGTH_SHORT).show();
				// return false;
				// }
				// Bundle extras = data.getExtras();
				// if (extras != null) {
				Bitmap photo = BitmapFactory.decodeFile(tempFile);
				Bitmap bmp = compressImage(photo, new File(outFile));
				if (photo != null && !photo.isRecycled()) {
					photo.recycle();
				}
				try {
					File file = new File(tempFile);
					file.delete();
				} catch (Exception e) {
				}
				if (bmp != null) {
					if (shapeFlag == 1) {
						// 圆形
						RoundedDrawable cr = new RoundedDrawable(bmp, -1, 1);
						view.setImageDrawable(cr);
					} else {
						// 方形
						view.setImageBitmap(bmp);
					}
					return true;
				}
				Toast.makeText(activity, "保存图片出错", Toast.LENGTH_SHORT).show();
				// } else {
				// Toast.makeText(activity, "裁剪图片出错", Toast.LENGTH_SHORT)
				// .show();
				// }
			} else {
				try {
					File file = new File(tempFile);
					file.delete();
				} catch (Exception e) {
				}
			}
		} else if (requestCode == PHOTO_FLAG_REQUESTCODE_SELECT && resultCode == Activity.RESULT_OK) {
			// BitmapFactory.Options option = new BitmapFactory.Options();
			// option.inJustDecodeBounds = true;
			// BitmapFactory.decodeFile(data.getData().getPath(), option);
			// int sampleSize = computeSampleSize(option.outWidth,
			// option.outHeight, -1, 1024 * 768);
			// int w = option.outWidth / sampleSize;
			// int h = option.outHeight / sampleSize;
			editPhoto(data.getData(), 100, 2);
		}
		return false;
	}

	// public static Bitmap compressPhotoAndSaveFile(Bitmap bmp, File f) {
	// if (bmp == null) {
	// return null;
	// }
	// // int inSampleSize = computeSampleSize(bmp.getWidth(), bmp.getHeight(),
	// // -1, 1024 * 768);
	// // Bitmap photo = Bitmap.createScaledBitmap(bmp, bmp.getWidth()
	// // / inSampleSize, bmp.getHeight() / inSampleSize, false);
	// // FileOutputStream outputStream = null;
	// // try {
	// // outputStream = new FileOutputStream(f);
	// // if (photo.compress(CompressFormat.JPEG, 100, outputStream)) {
	// // return photo;
	// // }
	// // } catch (Exception e) {
	// // e.printStackTrace();
	// // } finally {
	// // if (outputStream != null) {
	// // try {
	// // outputStream.close();
	// // } catch (IOException e) {
	// // // TODO Auto-generated catch block
	// // e.printStackTrace();
	// // }
	// // }
	// // }
	// return compressImage(bmp, f);
	// }
	/**
	 * 图片转换Base64串
	 */
	public static String Bitmap2Bytes(File file_Bitmap, boolean compress) {
		try {
			if (file_Bitmap.exists()) {
				Bitmap bm = BitmapFactory.decodeFile(file_Bitmap.getPath());
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				bm.compress(Bitmap.CompressFormat.JPEG, compress ? 90 : 100, baos);
				return new String(Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT));
			}
		} catch (Exception e) {

		}
		return "";
	}

	public void editPhoto(Uri uri, int len, int type) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", len);
		intent.putExtra("outputY", len);
		intent.putExtra("return-data", false);
		if (type == 1) {
			intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		} else {
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(tempFile)));
		}
		activity.startActivityForResult(intent, PHOTO_FLAG_REQUESTCODE_EDIT);
	}

	public void takePhoto(String file) {
		outFile = file;
		tempFile = outFile.substring(0, outFile.length() - 4) + System.currentTimeMillis()
				+ outFile.substring(outFile.length() - 4);
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(tempFile)));
		activity.startActivityForResult(intent, PHOTO_FLAG_REQUESTCODE_TAKE);
	}

	public void selectPhoto(String file) {
		outFile = file;
		tempFile = outFile.substring(0, outFile.length() - 4) + System.currentTimeMillis()
				+ outFile.substring(outFile.length() - 4);
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		activity.startActivityForResult(intent, PHOTO_FLAG_REQUESTCODE_SELECT);
	}
}

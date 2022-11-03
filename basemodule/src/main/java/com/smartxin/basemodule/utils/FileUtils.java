package com.smartxin.basemodule.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Author: zhengwenxin
 * CreateDate: 2022/2/18 17:18
 * Description: 该类主要处理媒体库中图片和视频，包括：
 * <1> 保存在手机应用私有文件夹</>
 * <2> 保存在手机 图库等共有目录，根据 android Q版本 分情况使用保存方法
 */
public class FileUtils {

    /**
     * 检查包含外部存储的卷是否可用于读写。
     *
     * @return
     */
    private static boolean isExternalStorageWritable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 应用私有文件夹路径
     * 返回图片路径
     */
    public static String getImageExternalPath() {
        String path = "";
        try {
            if (isExternalStorageWritable()) {
                path = Utils.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
            } else
                path = Utils.getContext().getFilesDir().getAbsolutePath() + File.separator + Environment.DIRECTORY_PICTURES;

        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
        return path;
    }

    /**
     * 应用私有文件夹路径
     * 返回视频路径
     */
    public static String getMovieExternalPath() {
        String path = "";
        try {
            if (isExternalStorageWritable()) {
                return Utils.getContext().getExternalFilesDir(Environment.DIRECTORY_MOVIES).getAbsolutePath();
            } else
                return Utils.getContext().getFilesDir().getAbsolutePath() + File.separator + Environment.DIRECTORY_MOVIES;
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
        return path;
    }

    /**
     * 应用私有文件夹存储保存
     * 创建图片文件
     */
    public static File createPictureFile() {
        return new File(getImageExternalPath(), System.currentTimeMillis() + ".jpg");
    }

    /**
     * 应用私有文件夹存储保存
     * 创建图片文件
     *
     * @param fileName 文件名
     */
    public static File createPictureFile(String fileName) {
        return new File(getImageExternalPath(), fileName + ".jpg");
    }

    /**
     * 应用私有文件夹存储保存
     * 创建图片文件
     *
     * @param imagePath 文件保存地址
     * @param fileName  文件名
     */
    public static File createPictureFile(@NonNull String imagePath, String fileName) {
        return new File(imagePath, fileName + ".jpg");
    }

    /**
     * 应用私有文件夹存储保存
     * 创建视频文件
     */
    public static File createVideoFile() {
        return new File(getMovieExternalPath(), System.currentTimeMillis() + ".mp4");
    }

    /**
     * 应用私有文件夹存储保存
     * 创建视频文件
     *
     * @param fileName 文件名
     */
    public static File createVideoFile(String fileName) {
        return new File(getMovieExternalPath(), fileName + ".mp4");
    }

    /**
     * 应用私有文件夹存储保存
     * 创建视频文件
     *
     * @param videoPath 视频文件保存地址
     * @param fileName  文件名
     */
    public static File createVideoFile(@NonNull String videoPath, String fileName) {
        return new File(videoPath, fileName + ".mp4");
    }

    /**
     * Android 10 以下在媒体库中创建应用文件夹
     * 获取外部存储相册文件夹
     */
    public static String getImageMediaStorePath() {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!path.exists()) {
            path.mkdirs();
        }
        String pathStr = path.getAbsolutePath();
        File file = new File(pathStr);
        if (!file.exists()) {
            file.mkdirs();
        }
        return pathStr;
    }

    /**
     * Android 10 以下在媒体库中创建应用文件夹
     * 获取外部存储相册文件夹
     */
    public static String getVideoMediaStorePath() {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        if (!path.exists()) {
            path.mkdirs();
        }
        String pathStr = path.getAbsolutePath();
        File file = new File(pathStr);
        if (!file.exists()) {
            file.mkdirs();
        }
        return pathStr;
    }

    /**
     * 应用共享媒体库存储
     * 根据android版本判断保存方法
     * android Q以下使用 创建 File 保存文件，android Q 以上使用 分区保存
     * 创建图片文件
     *
     * @param imageName 文件名称
     * @return uri
     */
    public static Uri createImageMediaStore(@NonNull String imageName) throws FileNotFoundException {
        Context context = Utils.getContext();
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //先在MediaStore中查询，有的话直接返回
            uri = queryImageUri(context, imageName);
            if (uri != null)
                return uri;
            uri = getImageUriAboveQ(context, imageName);
            if (uri != null)
                return uri;
        } else {
            File file = new File(getImageMediaStorePath(), imageName + ".jpg");
            uri = Uri.fromFile(file);
            if (uri != null)
                return uri;
        }
        return null;
    }


    /**
     * Android 10 以上
     * 分区存储插入数据
     *
     * @param imageName 图片名称
     */
    public static Uri getImageUriAboveQ(Context context, String imageName) {

        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, imageName);//图片名
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");//类型
        values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);//保存路径

        return resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    /**
     * 共享媒体库 查询图片
     *
     * @param imageName 图片名称
     */
    public static Uri queryImageUri(Context context, String imageName) {

        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,  //数据资源路径
                new String[]{MediaStore.Images.Media._ID},     //查询的列
                MediaStore.Images.Media.DISPLAY_NAME + "=?",   //查询的条件
                new String[]{imageName + ".jpg"},   //条件填充值
                null);

        if (null != cursor && cursor.moveToFirst()) {
            long id = cursor.getLong(0);
            Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            return ContentUris.withAppendedId(contentUri, id);

        } else
            return null;
    }

    /**
     * 删除图片
     *
     * @param imageName 图片真实路径
     */
    public static boolean deleteImage(Context context, String imageName) {

        boolean result = false;
        Uri uri = queryImageUri(context, imageName);
        if (uri != null) {  //媒体库上查不到，则检索文件
            int count = context.getContentResolver().delete(uri, null, null);
            result = count == 1;
        } else {
            File file = new File(getImageMediaStorePath(), imageName + ".jpg");
            result = file.delete();
        }
        return result;
    }

    /**
     * 更新图片名称
     *
     * @param imageName 文件名
     * @param newName   新名称
     * @return
     */
    public static boolean updateImage(Context context, String imageName, String newName) throws FileNotFoundException {

        Uri uri = queryImageUri(context, imageName);
        boolean result = false;
        if (uri != null) {   //媒体库上查不到，则检索文件
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, newName);
            int count = context.getContentResolver().update(uri, contentValues,
                    MediaStore.Images.Media.DISPLAY_NAME + "=?", // 查询条件
                    new String[]{imageName + ".jpg"});  //条件值
            result = count == 1;
        } else {
            File file = new File(FileUtils.getImageMediaStorePath(), imageName + ".jpg");
            File newFile = new File(FileUtils.getImageMediaStorePath(), newName + ".jpg");
            result = file.renameTo(newFile);
        }
        return result;
    }

    /***********************************************  视频  **************************************************/

    /**
     * 应用共享媒体库存储
     *
     * 根据android版本判断保存方法
     * android Q以下使用 创建 File 保存文件，android Q 以上使用 分区保存
     * 创建视频文件
     *
     * @param videoName 文件名称
     * @return uri
     */
    public static Uri createVideoMediaStore(@NonNull String videoName) throws FileNotFoundException {
        Context context = Utils.getContext();
        Uri uri;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //先在MediaStore中查询，有的话直接返回
            uri = queryVideoUri(context, videoName);
            if (uri != null)
                return uri;
            uri = getVideoUriAboveQ(context, videoName);
            if (uri != null)
                return uri;
        } else {
            File file = new File(getVideoMediaStorePath(), videoName + ".mp4");
            uri = Uri.fromFile(file);
            if (uri != null)
                return uri;
        }
        return null;
    }

    /**
     * Android 10 以上
     * 分区存储插入数据
     */
    public static Uri getVideoUriAboveQ(Context context, String videoName) {

        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(MediaStore.Video.Media.DISPLAY_NAME, videoName);//图片名
        values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");//类型
        values.put(MediaStore.Video.Media.RELATIVE_PATH, Environment.DIRECTORY_MOVIES);//保存路径
        return resolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);

    }

    /**
     * 共享媒体库 查询视频
     */
    public static Uri queryVideoUri(Context context, String videoName) {

        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,  //数据资源路径
                new String[]{MediaStore.Video.Media._ID},     //查询的列
                MediaStore.Video.Media.DISPLAY_NAME + "=?",   //查询的条件
                new String[]{videoName + ".mp4"},   //条件填充值
                null);
        if (null != cursor && cursor.moveToFirst()) {
            long id = cursor.getLong(0);
            Uri contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            return ContentUris.withAppendedId(contentUri, id);

        } else
            return null;
    }

    /**
     * 删除视频
     */
    public static boolean deleteVideo(Context context, String videoName) {

        Uri uri = queryVideoUri(context, videoName);
        boolean result = false;
        if (uri != null) {   //媒体库上查不到，则检索文件
            int count = context.getContentResolver().delete(uri, null, null);
            result = count == 1;
        } else {
            File file = new File(getVideoMediaStorePath(), videoName + ".mp4");
            result = file.delete();
        }
        return result;
    }

    /**
     * 更新视频名称
     */
    public static boolean updateVideo(Context context, String videoName, String newName) throws FileNotFoundException {

        Uri uri = queryVideoUri(context, videoName);
        boolean result = false;
        if (uri != null) {   //媒体库上查不到，则检索文件
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Video.Media.DISPLAY_NAME, newName);

            int count = context.getContentResolver().update(uri,
                    contentValues,
                    MediaStore.Video.Media.DISPLAY_NAME + "=?",  // 查询条件
                    new String[]{videoName + ".mp4"});   //条件值
            result = count == 1;

        } else {
            File file = new File(FileUtils.getVideoMediaStorePath(), videoName + ".mp4");
            File newFile = new File(FileUtils.getVideoMediaStorePath(), newName + ".mp4");
            result = file.renameTo(newFile);
        }
        return result;
    }

    /*******************************************  数据流处理  ************************************************/

    /**
     * 写入文件
     */
    public static boolean InputFileFromStream(FileInputStream inputStream, OutputStream outputStream) {
        //从输入流里读取数据
        try {
            byte[] in = new byte[1024];
            int len = 0;
            do {
                len = inputStream.read(in);

                if (len != -1) {
                    outputStream.write(in, 0, len);
                    outputStream.flush();
                }
            } while (len != -1);

            inputStream.close();
            outputStream.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 通过uri获取文件输出流
     */
    public static FileOutputStream getSaveToGalleryVideoOutputStream(Context context, Uri uri) throws FileNotFoundException {
        ParcelFileDescriptor fileDescriptor = context.getContentResolver().openFileDescriptor(uri, "w");
        FileOutputStream outputStream = new FileOutputStream(fileDescriptor.getFileDescriptor());
        return outputStream;
    }


}

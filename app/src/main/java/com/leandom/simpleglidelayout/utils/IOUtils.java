package com.leandom.simpleglidelayout.utils;

import android.content.Context;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class IOUtils {

    public static void save2Sdcard(String path, String filecontent, boolean append) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            String sdcardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            FileOutputStream outStream = null;
            try {
                String savePath = sdcardPath + path;
                File parentFile = new File(new File(savePath).getParent());
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                outStream = new FileOutputStream(sdcardPath + path, append);
                outStream.write(filecontent.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (outStream != null) {
                    try {
                        outStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static String readStringAndCloseStream(InputStream is) {
        byte[] buffer = new byte[1024];
        if (is != null) {
            int length = 0;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                while ((length = is.read(buffer)) != -1) {
                    bos.write(buffer, 0, length);
                }
                return bos.toString("UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static byte[] readBytesAndCloseStream(InputStream is) {
        byte[] buffer = new byte[1024];
        if (is != null) {
            int length = 0;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                while ((length = is.read(buffer)) != -1) {
                    bos.write(buffer, 0, length);
                }
                return bos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    public static byte[] getByteFromAssets(Context context, String fileName) {

        try {
            InputStream is = context.getResources().getAssets().open(fileName);
            return readBytesAndCloseStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getStringFromAssets(Context context, String fileName) {
        try {
            InputStream is = context.getResources().getAssets().open(fileName);
            return readStringAndCloseStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}

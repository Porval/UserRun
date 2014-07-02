package com.jp.userrun.utils;

import com.jp.userrun.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

public class IOUtils {
    private static final String TAG = IOUtils.class.getSimpleName();

    /**
     * 用于通知读写进度
     */
    public static interface ProgressListener {
        /**
         * 有新进度时会叫到此方法。
         *
         * @param bytesDone  已经读写完成的总字节数
         * @param totalBytes 需要读写的总字节数
         */
        public void onNewProgress(long bytesDone, long totalBytes);
    }


    /**
     * Writes string to file. Basically same as "echo -n $string > $filename"
     *
     * @param fileName file to be written to
     * @param string   string to be written
     * @throws java.io.IOException
     */
    public static void writeStringToFile(String fileName, String string) throws IOException {
        FileWriter out = new FileWriter(fileName);
        try {
            out.write(string);
        } finally {
            out.close();
        }
    }

    /**
     * Write specified InputStream to file.
     * This will close InputStream after write.
     *
     * @param fileName file to be written to
     * @param stream   stream to be written
     * @throws java.io.IOException
     * @throws InterruptedException
     */
    public static void writeStreamToFile(String fileName, InputStream stream)
            throws IOException, InterruptedException {
        writeStreamToFile(fileName, stream, false, 0, 0, 0, null);
    }

    /**
     * Write specified InputStream to file.
     * This will close InputStream after write.
     *
     * @param fileName      file to be written to
     * @param stream        stream to be written
     * @param append        true: 往文件末尾追加, false: 覆盖现有文件
     * @param bytesInterval 每隔多少字节通知一次进度更新
     * @param bytesDone     已经完成的字节数
     * @param bytesTotal    总共需要写入的字节数
     * @param listener      listener to be called
     * @throws java.io.IOException
     * @throws InterruptedException
     */
    public static void writeStreamToFile(String fileName, InputStream stream, boolean append,
                                         long bytesInterval, long bytesDone, long bytesTotal, ProgressListener listener)
            throws IOException, InterruptedException {
        BufferedOutputStream outputStream = null;
        try {
            File file = new File(fileName);
            File folder = file.getParentFile();
            if (folder.isDirectory() || folder.mkdirs()) {
                byte[] buffer = new byte[8192];
                int len;
                long totalBytesWritten = bytesDone;
                long lastTotalBytesWritten = bytesDone;
                outputStream = new BufferedOutputStream(new FileOutputStream(file, append));
                while ((len = stream.read(buffer, 0, buffer.length)) != -1) {
                    outputStream.write(buffer, 0, len);
                    totalBytesWritten += len;
                    if (listener != null) {
                        if (totalBytesWritten - lastTotalBytesWritten >= bytesInterval
                                || totalBytesWritten >= bytesTotal) {
                            listener.onNewProgress(totalBytesWritten, bytesTotal);
                            lastTotalBytesWritten = totalBytesWritten;
                        }
                    }
                    if (Thread.interrupted()) {
                        throw new InterruptedException();
                    }
                }
            } else {
                throw new IOException("file creation failed.");
            }
        } finally {
            closeSilently(outputStream);
            closeSilently(stream);
        }
    }

    /**
     * 将一个InputStream转换成String
     *
     * @param stream stream to be read
     * @return String
     * @throws java.io.IOException
     */
    public static String streamToString(InputStream stream) throws IOException {
        int len;
        byte[] buffer = new byte[4096];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            while ((len = stream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            return outputStream.toString(Constants.DEFAULT_CHARSET);
        } finally {
            IOUtils.closeSilently(outputStream);
        }
    }

    /**
     * 将一个InputStream转换成JSONObject
     *
     * @param stream stream to be read
     * @return JSONObject
     * @throws java.io.IOException
     * @throws org.json.JSONException
     */
    public static JSONObject streamToJSONObject(InputStream stream) throws IOException, JSONException {
        return new JSONObject(streamToString(stream));
    }

    /**
     * 从一个文件中读取数据并转换成JSONObject
     *
     * @param filePath file to be read
     * @return JSONObject
     * @throws java.io.IOException
     * @throws org.json.JSONException
     */
    public static JSONObject fileToJSONObject(String filePath) throws IOException, JSONException {
        BufferedInputStream in = null;
        try {
            File file = new File(filePath);
            in = new BufferedInputStream(new FileInputStream(file));
            return streamToJSONObject(in);
        } finally {
            IOUtils.closeSilently(in);
        }
    }

    /**
     * 关闭一个Closable，不会抛异常。
     *
     * @param closeable the object which to be closed
     */
    public static void closeSilently(Closeable closeable) {
        if (closeable == null) {
            return;
        }

        try {
            closeable.close();
        } catch (Throwable e) {
            Logger.e(TAG, e);
        }
    }

    /**
     * 将InputStream的内容写入到OutputStream中。
     *
     * @param in  InputStream
     * @param out OutputStream
     * @throws java.io.IOException
     */
    public static void copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[8192];
        int len;
        while ((len = in.read(buffer, 0, buffer.length)) != -1) {
            out.write(buffer, 0, len);
        }
    }

    public static String convertStreamToString(InputStream is) throws IOException {
        Writer writer = new StringWriter();

        char[] buffer = new char[2048];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is,
                    Constants.DEFAULT_CHARSET));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }
        String text = writer.toString();
        return text;
    }
}

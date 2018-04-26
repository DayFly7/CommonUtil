package com.qy.common;

import android.os.AsyncTask;
import android.os.Environment;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2018-3-14.
 */

public class DownLoadUtil {

    public static void startDownload(String url,String fileName,OnDownLoadListener listener){
        DownloadApk downloadApk = new DownloadApk(listener);
        downloadApk.execute(url,fileName);
    }

    private static class DownloadApk extends AsyncTask<String, Integer, String> {
        private OnDownLoadListener listener;
        String ph;
        String fileName;

        public DownloadApk(OnDownLoadListener listener) {
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listener.prepare();
        }

        @Override
        protected String doInBackground(String... params) {
            String downloadUrl = params[0];
            fileName = params[1];
            ph = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
            InputStream is = null;
            byte[] buf = new byte[2048];
            int len = 0;
            long sum = 0;
            FileOutputStream fos = null;
            try {
                URL url = new URL(downloadUrl);
                URLConnection conn = url.openConnection();
                is = conn.getInputStream();
                int total = conn.getContentLength();
                fos = new FileOutputStream(ph + fileName);
                sum = 0;

                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                    sum += len;
                    int progress = (int) (sum * 1.0f / total * 100);
                    listener.update(progress);
                }
                fos.flush();
            } catch(Exception e) {
                listener.error();
            } finally {
                try {
                    if (is != null)
                        is.close();
                } catch (IOException e) {

                }
                try {
                    if (fos != null)
                        fos.close();
                } catch (IOException e) {
                }
            }
            return ph + fileName;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            listener.update(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            listener.success(ph + fileName);
        }
    }

    public interface OnDownLoadListener{
        void prepare();
        void success(String filePath);
        void error();
        void update(int progress);
    }

}

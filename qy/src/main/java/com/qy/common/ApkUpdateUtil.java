package com.qy.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2018-3-14.
 */

public class ApkUpdateUtil {
    public static void startUpdateApk(Context context,String url,String fileName,OnDownLoadListener listener){
        DownloadApk downloadApk = new DownloadApk(context,listener);
        downloadApk.execute(url,fileName);
    }

    private static class DownloadApk extends AsyncTask<String, Integer, String> {
        private OnDownLoadListener listener;
        private Context context;

        public DownloadApk(Context context,OnDownLoadListener listener) {
            this.context = context;
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listener.pre();
        }

        @Override
        protected String doInBackground(String... params) {
            String downloadUrl = params[0];
            String fileName = params[1];
            String ph = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
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
            return downloadUrl + fileName;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            listener.update(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            installApk(context,new File(s));
            listener.success();
        }

        //安装apk
        private void installApk(Context context,File file) {
            Intent intent = new Intent();
            // 执行动作
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setAction(Intent.ACTION_VIEW);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName(), file);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.fromFile(file),
                        "application/vnd.android.package-archive");
            }
            context.startActivity(intent);
        }

    }


    public interface OnDownLoadListener{
        void pre();
        void success();
        void error();
        void update(int progress);
    }


}

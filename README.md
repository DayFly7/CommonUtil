# CommonUtil
## 使用方式<br>
```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
dependencies {
    compile 'com.github.DayFly7:CommonUtil:v1.0.2'
}
```
## 介绍<br>
CommonUtil体积小，只集成常用功能<br>
#### 1.Log和Toast <br>
#### 2.String字符串的操作和判断
#### 3.日期的各种格式转换
#### 4.屏幕分辨率相关功能
#### 5.状态栏工具
#### 6.SharePreference存取对象和list
#### 7.AsyncTask下载文件
#### 8.安装apk
```
        不要忘记在xml里加上
        <provider
            android:name="android.support.v4.content.FileProvider"
            android:authorities="包名"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/file_paths" />
        </provider>
```

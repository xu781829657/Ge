package com.android.ge.app;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.base.frame.Base;
import com.android.ge.utils.PreferencesUtils;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by xudengwang on 2017/3/14.
 */

public class AppApplication extends Application {
    public int ENVIRONMENT;

    private static AppApplication singleton;

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        Base.initialize(this);
        ENVIRONMENT = PreferencesUtils.getEnvironment(this);
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        CrashReport.initCrashReport(context, "cd7d79cf05", true, strategy);
        //CrashReport.initCrashReport(getApplicationContext(), "cd7d79cf05", false);
    }

    public static AppApplication getInstance() {
        return singleton;
    }


    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}

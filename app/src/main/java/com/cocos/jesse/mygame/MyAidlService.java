package com.cocos.jesse.mygame;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * aidl
 * Created by Jesse on 2018/8/9.
 */

public class MyAidlService extends Service {
    private String str;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    private IBinder iBinder = new AidlTest.Stub() {
        @Override
        public void setData(String data) throws RemoteException {
            str = data;
        }

        @Override
        public String getData() throws RemoteException {
            return str;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

    }
}

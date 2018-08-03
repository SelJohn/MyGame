package com.cocos.jesse.planefightmodule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.sound.SoundEngine;

public class MainActivity extends AppCompatActivity {

    private CCDirector director;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CCGLSurfaceView surfaceView = new CCGLSurfaceView(this);

        setContentView(surfaceView);
        //  程序只能有一个导演
        director = CCDirector.sharedDirector();
        director.attachInView(surfaceView);// 开启线程

        director.setDeviceOrientation(CCDirector.kCCDeviceOrientationPortrait);// 设置游戏方向 水平
        director.setDisplayFPS(true);//是否展示帧率
        //  director.setAnimationInterval(1.0f/30);// 锁定帧率  指定一个帧率  向下锁定
        director.setScreenSize(720, 1280);//设置屏幕的大小   可以自动屏幕适配

        CCScene ccScene = CCScene.node();// 为了api 和cocos-iphone 一致
        ccScene.addChild(new FirstLayer());//场景添加了图层
        director.runWithScene(ccScene);//  运行场景
        SoundEngine engine = SoundEngine.sharedEngine();
// 1 上下文 2. 音乐资源的id  3 是否循环播放
        engine.playSound(CCDirector.theApp, R.raw.white, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        director.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        director.onPause();
        SoundEngine.sharedEngine().pauseSound();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        director.end();// 游戏结束了
    }

}

package com.cocos.jesse.planefightmodule;


import android.view.MotionEvent;

import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

/**
 * Created by Jesse on 2018/7/26.
 */
public class FirstLayer extends CCLayer {
    static final int kTagSprite = 1;
    static final int manSpeed = 500;
    private CCSprite[] fires;// 子弹
    private CCSprite[] enemys;// 敌机

    private int numberOfFire;
    private int numberOfEnemy;

    public FirstLayer() {
        init();
    }

    /*
     * 初始化背景，子弹，敌机，英雄的精灵对象
     */
    private void init() {

        CCSprite background = CCSprite
                .sprite("background.jpg");
        background.setScale(3.0);
        this.addChild(background);
        CCSprite sprite = CCSprite.sprite("hero.png");
        this.addChild(sprite, 0, kTagSprite);

        fires = new CCSprite[10];//循环回收利用，只使用10个子弹，避免浪费内存
        enemys = new CCSprite[10];//和上面同理
        for (int i = 0; i < 10; i++) {
            fires[i] = CCSprite.sprite("bullet.png");
            fires[i].setPosition(-100, -100);
            addChild(fires[i]);
        }
        for (int i = 0; i < 10; i++) {
            enemys[i] = CCSprite.sprite("enemy.png");
            enemys[i].setPosition(-200, -200);
            addChild(enemys[i]);
        }

        numberOfFire = 0;
        numberOfEnemy = 0;
        schedule("fire", 0.2f);// 英雄飞机开火的调度器，每隔0.2s发射一颗子弹
        schedule("createEnemy", 0.5f);// 创建敌机调度器，每隔0.5s创建一个敌机
        scheduleUpdate(); // 默认调度器，进行子弹和敌机的碰撞检测
        isTouchEnabled_ = true;//设置为可触摸
    }

    @Override
    public boolean ccTouchesBegan(MotionEvent event) {
        CGPoint point = new CGPoint();
        point.set(event.getX(), event.getY());
        CGPoint convertedLocation = CCDirector.sharedDirector().convertToGL(
                point);

        CCNode sprite = getChildByTag(kTagSprite);
        sprite.stopAllActions();

        sprite.runAction(CCMoveTo.action(getDistance(convertedLocation)
                        / manSpeed,
                CCNode.ccp(convertedLocation.x, convertedLocation.y)));
        return super.ccTouchesBegan(event);
    }

    public float getDistance(CGPoint touchPoint) {
        CCNode sprite = getChildByTag(kTagSprite);
        CGPoint nowPoint = sprite.getPosition();
        float dx = nowPoint.x - touchPoint.x;
        float dy = nowPoint.y - touchPoint.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public void update(float dt) {
        // Log.i("", "默认调度器");

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (enemys[i].getBoundingBox().contains(
                        fires[j].getPosition().x, fires[j].getPosition().y)) {
                    enemys[i].stopAllActions();

                    fires[j].stopAllActions();
                    enemys[i].setPosition(-100, -100);
                    fires[j].setPosition(-200, -100);
                    break;
                    // return;
                }
            }
        }
    }

    public void enemysCallback() {


    }

    public void fire(float dt) {
        fires[numberOfFire]
                .setPosition(getChildByTag(kTagSprite).getPosition());
        fires[numberOfFire].setVisible(true);
        fires[numberOfFire].runAction(CCMoveBy.action((float) 1.5,
                CCNode.ccp(0, 1380)));

        numberOfFire++;
        if (numberOfFire >= 10)
            numberOfFire -= 10;
    }

    public void createEnemy(float dt) {

        enemys[numberOfEnemy].setPosition((float) (720 * Math.random()), 1280);
        enemys[numberOfEnemy].setScale(1.5);
        enemys[numberOfEnemy].setVisible(true);
        enemys[numberOfEnemy].runAction(CCMoveBy.action((float) 3.5,
                CCNode.ccp(0, -1400)));

        numberOfEnemy++;
        if (numberOfEnemy >= 10)
            numberOfEnemy -= 10;
    }
}
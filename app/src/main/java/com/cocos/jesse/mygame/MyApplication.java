package com.cocos.jesse.mygame;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 *
 * Created by Jesse on 2018/8/1.
 */

public class MyApplication extends TinkerApplication {
    protected MyApplication() {
        super( ShareConstants.TINKER_ENABLE_ALL, "xxx.xxx.MyApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }

}

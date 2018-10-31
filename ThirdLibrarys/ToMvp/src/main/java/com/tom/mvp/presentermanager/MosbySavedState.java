package com.tom.mvp.presentermanager;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.os.ParcelableCompat;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.support.v4.view.AbsSavedState;

/**
 * 作者:  tom_flying
 * 邮箱: tom_flying@163.com
 * 博客:  http://www.tianfeifei.com
 * 创建日期: 2018/10/31
 * 描述：
 */
public class MosbySavedState extends AbsSavedState {

    public static final Creator<MosbySavedState> CREATOR =
            ParcelableCompat.newCreator(new ParcelableCompatCreatorCallbacks<MosbySavedState>() {
                @Override
                public MosbySavedState createFromParcel(Parcel in, ClassLoader loader) {
                    if (loader == null) {
                        loader = MosbySavedState.class.getClassLoader();
                    }
                    return new MosbySavedState(in, loader);
                }

                @Override
                public MosbySavedState[] newArray(int size) {
                    return new MosbySavedState[size];
                }
            });

    private String mosbyViewId;

    public MosbySavedState(Parcelable superState, String mosbyViewId) {
        super(superState);
        this.mosbyViewId = mosbyViewId;
    }

    protected MosbySavedState(Parcel in, ClassLoader loader) {
        super(in, loader);
        this.mosbyViewId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeString(mosbyViewId);
    }

    public String getMosbyViewId() {
        return mosbyViewId;
    }

}

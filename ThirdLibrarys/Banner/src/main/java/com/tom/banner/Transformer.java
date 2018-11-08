package com.tom.banner;

import com.tom.banner.transformer.AccordionTransformer;
import com.tom.banner.transformer.BackgroundToForegroundTransformer;
import com.tom.banner.transformer.CubeInTransformer;
import com.tom.banner.transformer.CubeOutTransformer;
import com.tom.banner.transformer.DefaultTransformer;
import com.tom.banner.transformer.DepthPageTransformer;
import com.tom.banner.transformer.FlipHorizontalTransformer;
import com.tom.banner.transformer.FlipVerticalTransformer;
import com.tom.banner.transformer.ForegroundToBackgroundTransformer;
import com.tom.banner.transformer.RotateDownTransformer;
import com.tom.banner.transformer.RotateUpTransformer;
import com.tom.banner.transformer.ScaleInOutTransformer;
import com.tom.banner.transformer.StackTransformer;
import com.tom.banner.transformer.TabletTransformer;
import com.tom.banner.transformer.ZoomInTransformer;
import com.tom.banner.transformer.ZoomOutSlideTransformer;
import com.tom.banner.transformer.ZoomOutTranformer;

import androidx.viewpager.widget.ViewPager.PageTransformer;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/5/8
 * 描述：切换时的动画效果
 */
public class Transformer {
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}

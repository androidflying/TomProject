package com.android.tomflying.widget;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.os.Build;

import androidx.appcompat.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import com.android.tomflying.BuildConfig;
import com.android.tomflying.R;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/8/17
 * 描述：
 */
public class AlignTextView extends AppCompatTextView {
    private final static String TAG = AlignTextView.class.getSimpleName();
    /**
     * 空格;
     */
    private final static char SPACE = ' ';
    /**
     * 增加空格的位置
     */
    private List<Integer> addCharPosition = new ArrayList<>();
    /**
     * 标点符号
     */
    private static List<Character> punctuation = new ArrayList<>();
    /**
     * 旧文本，本来应该显示的文本
     */
    private CharSequence oldText = "";
    /**
     * 新文本，真正显示的文本
     */
    private CharSequence newText = "";
    /**
     * 旧文本是否已经处理为新文本
     */
    private boolean inProcess = false;
    /**
     * 是否添加过边距
     */
    private boolean isAddPadding = false;
    /**
     * 是否转换标点符号
     */
    private boolean isConvert = false;

    //标点符号用于在textview右侧多出空间时，将空间加到标点符号的后面,以便于右端对齐
    static {
        punctuation.clear();
        punctuation.add(',');
        punctuation.add('.');
        punctuation.add('?');
        punctuation.add('!');
        punctuation.add(';');
        punctuation.add('，');
        punctuation.add('。');
        punctuation.add('？');
        punctuation.add('！');
        punctuation.add('；');
        punctuation.add('）');
        punctuation.add('】');
        punctuation.add(')');
        punctuation.add(']');
        punctuation.add('}');
    }

    public AlignTextView(Context context) {
        super(context);
    }

    public AlignTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AlignTextView);
        isConvert = ta.getBoolean(R.styleable.AlignTextView_punctuationConvert, false);
        ta.recycle();

        //判断使用xml中是用android:text
        TypedArray tsa = context.obtainStyledAttributes(attrs, new int[]{
                android.R.attr.text
        });
        String text = tsa.getString(0);
        tsa.recycle();
        if (!TextUtils.isEmpty(text)) {
            setText(text);
        }
    }

    /**
     * 监听文本复制，对于复制的文本进行空格剔除
     *
     * @param id 操作id(复制，全部选择等)
     * @return 是否操作成功
     */
    @Override
    public boolean onTextContextMenuItem(int id) {
        if (id == android.R.id.copy) {

            if (isFocused()) {
                final int selStart = getSelectionStart();
                final int selEnd = getSelectionEnd();

                int min = Math.max(0, Math.min(selStart, selEnd));
                int max = Math.max(0, Math.max(selStart, selEnd));

                //利用反射获取选择的文本信息，同时关闭操作框
                try {
                    Class cls = getClass().getSuperclass();
                    Method getSelectTextMethod = cls.getDeclaredMethod("getTransformedText", int.class, int.class);
                    getSelectTextMethod.setAccessible(true);
                    CharSequence selectedText = (CharSequence) getSelectTextMethod.invoke(this,
                            min, max);
                    copy(selectedText.toString());

                    Method closeMenuMethod;
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                        closeMenuMethod = cls.getDeclaredMethod("stopSelectionActionMode");
                    } else {
                        closeMenuMethod = cls.getDeclaredMethod("stopTextActionMode");
                    }
                    closeMenuMethod.setAccessible(true);
                    closeMenuMethod.invoke(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return true;
        } else {
            return super.onTextContextMenuItem(id);
        }
    }


    /**
     * 复制文本到剪切板，去除添加字符
     *
     * @param text 文本
     */
    private void copy(String text) {
        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context
                .CLIPBOARD_SERVICE);
        int start = newText.toString().indexOf(text);
        int end = start + text.length();
        StringBuilder sb = new StringBuilder(text);
        for (int i = addCharPosition.size() - 1; i >= 0; i--) {
            int position = addCharPosition.get(i);
            if (position < end && position >= start) {
                sb.deleteCharAt(position - start);
            }
        }
        try {
            android.content.ClipData clip = android.content.ClipData.newPlainText(null, sb.toString());
            clipboard.setPrimaryClip(clip);
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    /**
     * 重置状态
     */
    public void reset() {
        inProcess = false;
        addCharPosition.clear();
        newText = "";
        newText = "";
    }

    /**
     * 处理多行文本
     *
     * @param paint 画笔
     * @param text  文本
     * @param width 最大可用宽度
     * @return 处理后的文本
     */
    private String processText(Paint paint, String text, int width) {
        if (text == null || text.length() == 0) {
            return "";
        }
        String[] lines = text.split("\\n");
        StringBuilder newText = new StringBuilder();
        for (String line : lines) {
            newText.append('\n');
            newText.append(processLine(paint, line, width, newText.length() - 1));
        }
        if (newText.length() > 0) {
            newText.deleteCharAt(0);
        }
        return newText.toString();
    }


    /**
     * 处理单行文本
     *
     * @param paint                     画笔
     * @param text                      文本
     * @param width                     最大可用宽度
     * @param addCharacterStartPosition 添加文本的起始位置
     * @return 处理后的文本
     */
    private String processLine(Paint paint, String text, int width, int addCharacterStartPosition) {
        if (text == null || text.length() == 0) {
            return "";
        }

        StringBuilder old = new StringBuilder(text);
        // 起始位置
        int startPosition = 0;

        float chineseWidth = paint.measureText("中");
        float spaceWidth = paint.measureText(SPACE + "");

        //最大可容纳的汉字，每一次从此位置向后推进计算
        int maxChineseCount = (int) (width / chineseWidth);

        //减少一个汉字宽度，保证每一行前后都有一个空格
        maxChineseCount--;

        //如果不能容纳汉字，直接返回空串
        if (maxChineseCount <= 0) {
            return "";
        }

        for (int i = maxChineseCount; i < old.length(); i++) {
            if (paint.measureText(old.substring(startPosition, i + 1)) > (width - spaceWidth)) {

                //右侧多余空隙宽度
                float gap = (width - spaceWidth - paint.measureText(old.substring(startPosition,
                        i)));

                List<Integer> positions = new ArrayList<>();
                for (int j = startPosition; j < i; j++) {
                    char ch = old.charAt(j);
                    if (punctuation.contains(ch)) {
                        positions.add(j + 1);
                    }
                }

                //空隙最多可以使用几个空格替换
                int number = (int) (gap / spaceWidth);

                //多增加的空格数量
                int use = 0;

                if (positions.size() > 0) {
                    for (int k = 0; k < positions.size() && number > 0; k++) {
                        int times = number / (positions.size() - k);
                        int position = positions.get(k / positions.size());
                        for (int m = 0; m < times; m++) {
                            old.insert(position + m, SPACE);
                            addCharPosition.add(position + m + addCharacterStartPosition);
                            use++;
                            number--;
                        }
                    }
                }

                //指针移动，将段尾添加空格进行分行处理
                i = i + use;
                old.insert(i, SPACE);
                addCharPosition.add(i + addCharacterStartPosition);

                startPosition = i + 1;
                i = i + maxChineseCount;
            }
        }

        return old.toString();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        //父类初始化的时候子类暂时没有初始化, 覆盖方法会被执行，屏蔽掉
        if (addCharPosition == null) {
            super.setText(text, type);
            return;
        }
        if (!inProcess && (text != null && !text.equals(newText))) {
            oldText = text;
            process(false);
            super.setText(newText, type);
        } else {
            //恢复初始状态
            inProcess = false;
            super.setText(text, type);
        }
    }

    /**
     * 获取真正的text
     *
     * @return 返回text
     */
    public CharSequence getRealText() {
        return oldText;
    }

    /**
     * 文本转化
     *
     * @param setText 是否设置textView的文本
     */
    private void process(boolean setText) {
        if (oldText == null) {
            oldText = "";
        }
        if (!inProcess && getVisibility() == VISIBLE) {
            addCharPosition.clear();

            //转化字符，5.0系统对字体处理有所变动
            if (isConvert) {
                oldText = replacePunctuation(oldText.toString());
            }

            if (getWidth() == 0) {
                //没有测量完毕，等待测量完毕后处理
                post(new Runnable() {
                    @Override
                    public void run() {
                        process(true);
                    }
                });
                return;
            }

            //添加过边距之后不再次添加
            if (!isAddPadding) {
                int spaceWidth = (int) (getPaint().measureText(SPACE + ""));
                newText = processText(getPaint(), oldText.toString(), getWidth() - getPaddingLeft
                        () -
                        getPaddingRight() - spaceWidth);
                setPadding(getPaddingLeft() + spaceWidth, getPaddingTop(), getPaddingRight(),
                        getPaddingBottom());
                isAddPadding = true;
            } else {
                newText = processText(getPaint(), oldText.toString(), getWidth() - getPaddingLeft
                        () -
                        getPaddingRight());
            }
            inProcess = true;
            if (setText) {
                setText(newText);
            }
        }
    }

    /**
     * 是否转化标点符号，将中文标点转化为英文标点
     *
     * @param convert 是否转化
     */
    public void setPunctuationConvert(boolean convert) {
        isConvert = convert;
    }


    /**
     * 将中文标点替换为英文标点
     *
     * @param text 要替换的文本
     * @return 替换后的文本
     */
    private String replacePunctuation(String text) {
        text = text.replace('，', ',').replace('。', '.').replace('【', '[').replace('】', ']')
                .replace('？', '?').replace('！', '!').replace('（', '(').replace('）', ')').replace
                        ('“', '"').replace('”', '"');
        return text;
    }
}

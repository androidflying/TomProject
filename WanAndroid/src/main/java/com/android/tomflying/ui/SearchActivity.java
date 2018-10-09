package com.android.tomflying.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.tomflying.ApiConstant;
import com.android.tomflying.R;
import com.android.tomflying.adapter.SearchKeyAdapter;
import com.android.tomflying.base.MyActivity;
import com.android.tomflying.bean.LzyResponse;
import com.android.tomflying.bean.SearchBean;
import com.android.tomflying.bean.SearchKeyBean;
import com.android.tomflying.bean.SearchKeySectionBean;
import com.android.tomflying.dao.DBHelper;
import com.android.tomflying.dao.SearchBeanDao;
import com.android.tomflying.util.JsonCallback;
import com.android.tomflying.util.OkHttpUtil;
import com.qmuiteam.tom.util.QMUIDisplayHelper;
import com.qmuiteam.tom.widget.QMUITopBar;
import com.qmuiteam.tom.widget.dialog.QMUIDialog;
import com.qmuiteam.tom.widget.dialog.QMUITipDialog;
import com.tom.baselib.utils.ActivityUtils;
import com.tom.baselib.utils.KeyboardUtils;
import com.tom.baselib.utils.TimeUtils;
import com.tom.brvah.BaseQuickAdapter;
import com.tom.brvah.itemdecoration.SpaceItemDecoration;
import com.tom.brvah.manager.FlowLayoutManager;
import com.tom.network.OkGo;
import com.tom.network.model.Response;

import java.util.ArrayList;
import java.util.List;


/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 描述：
 */
public class SearchActivity extends MyActivity {
    private QMUITopBar topBar;
    private EditText editText;
    private RecyclerView recyclerView;
    private SearchKeyAdapter adapter;
    private ArrayList<SearchKeySectionBean> searchBeans = new ArrayList<>();
    private ArrayList<SearchKeySectionBean> searchHistory = new ArrayList<>();
    private int hotCount;

    @Override
    public boolean isTransparent() {
        return super.isTransparent();
    }

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        topBar = findViewById(R.id.topbar);

        editText = new EditText(mActivity);
        editText.setBackgroundResource(R.drawable.bg_et);
        editText.setHint("发现更多干货");
        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        editText.setSingleLine(true);
        editText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(QMUIDisplayHelper.dpToPx(240), RelativeLayout.LayoutParams.WRAP_CONTENT);
        editText.setLayoutParams(lp);
        topBar.setCenterView(editText);
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.finishActivity(SearchActivity.class, true);
            }
        });
        topBar.addRightTextButton("搜索", R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchKey();
            }
        });

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (KeyEvent.KEYCODE_ENTER == keyCode && event.getAction() == KeyEvent.ACTION_DOWN) {
                    searchKey();
                }
                return KeyEvent.KEYCODE_ENTER == keyCode && event.getAction() == KeyEvent.ACTION_DOWN;
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == KeyEvent.ACTION_DOWN || actionId == EditorInfo.IME_ACTION_DONE) {
                    searchKey();
                }
                return actionId == KeyEvent.ACTION_DOWN || actionId == EditorInfo.IME_ACTION_DONE;

            }
        });
        recyclerView = findViewById(R.id.recyclerView);

        initSearchKey();

    }

    private void searchKey() {
        if (!TextUtils.isEmpty(editText.getText())) {
            KeyboardUtils.hideSoftInput(editText);
            insertKey(editText.getText().toString());
            Bundle bundle = new Bundle();
            bundle.putString(SearchResultActivity.SEARCH_CONTENT, editText.getText().toString());
            ActivityUtils.startActivity(bundle, SearchResultActivity.class);
            editText.setText("");
            editText.clearFocus();
        } else {
            showWarning();
        }
    }

    private void initSearchKey() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new FlowLayoutManager());
        adapter = new SearchKeyAdapter(searchBeans);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpaceItemDecoration(20));
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SearchKeySectionBean bean = searchBeans.get(position);
                if (!bean.isHeader) {
                    Bundle bundle = new Bundle();
                    bundle.putString(SearchResultActivity.SEARCH_CONTENT, searchBeans.get(position).t.getName());
                    ActivityUtils.startActivity(bundle, SearchResultActivity.class);
                }
            }
        });

        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                SearchKeySectionBean bean = searchBeans.get(position);
                if (!bean.isHeader) {

                    if (position > hotCount) {
                        deleteKey(bean);
                    }
                }
                return !bean.isHeader;
            }
        });
    }

    private void insertKey(String s) {
        SearchBeanDao dao = DBHelper.getDao().getSession().getSearchBeanDao();
        SearchBean bean = new SearchBean();
        bean.setName(s);
        long time = TimeUtils.getNowMills();
        bean.setTime(time);
        dao.insertOrReplace(bean);
        getHistoryKey();

        adapter.notifyDataSetChanged();
    }

    private void showWarning() {
        final QMUITipDialog dialog = new QMUITipDialog.Builder(mActivity)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_INFO)
                .setTipWord("你不告诉我搜啥会中!!!")
                .create();

        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1000);
    }

    private void deleteKey(final SearchKeySectionBean bean) {
        new QMUIDialog.MenuDialogBuilder(mActivity)
                .addItem("删除该历史记录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SearchBeanDao dao = DBHelper.getDao().getSession().getSearchBeanDao();
                        dao.delete(bean.t);
                        getHistoryKey();
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                })
                .create().show();


    }

    private void getHistoryKey() {
        searchBeans.removeAll(searchHistory);
        searchHistory.clear();
        SearchBeanDao dao = DBHelper.getDao().getSession().getSearchBeanDao();
        List<SearchBean> list = dao.queryBuilder().orderDesc(SearchBeanDao.Properties.Time).build().list();
        for (int i = 0; i < list.size(); i++) {
            searchHistory.add(new SearchKeySectionBean(list.get(i)));
        }
        if (list.size() > 0) {
            searchHistory.add(new SearchKeySectionBean(true, "(长按即可删除搜索记录)"));
        }
        searchBeans.addAll(searchHistory);

    }

    @Override
    public void doBusiness() {
        getHotKey();
    }

    private void getHotKey() {
        OkHttpUtil.getRequest(ApiConstant.Guide.HOT_KEY_URL, this, null, new JsonCallback<LzyResponse<List<SearchKeyBean>>>() {
            @Override
            public void onSuccess(Response<LzyResponse<List<SearchKeyBean>>> response) {
                addHotKey(response.body().data);
                hotCount = response.body().data.size();
            }
        });
    }

    private void addHotKey(List<SearchKeyBean> datas) {
        searchBeans.add(new SearchKeySectionBean(true, "热门搜索"));
        for (int i = 0; i < datas.size(); i++) {
            SearchBean bean = new SearchBean();
            bean.setName(datas.get(i).getName());
            searchBeans.add(new SearchKeySectionBean(bean));
        }
        searchBeans.add(new SearchKeySectionBean(true, "历史搜索"));
        getHistoryKey();

        adapter.setNewData(searchBeans);
    }

    @Override
    public void onWidgetClick(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}

package io.rong.callkit;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.rong.callkit.util.CallKitSearchBarListener;
import io.rong.callkit.util.CallKitSearchBarView;
import io.rong.callkit.util.CallKitUtils;
import io.rong.calllib.RongCallCommon;
import io.rong.imkit.RongContext;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imkit.userInfoCache.RongUserInfoManager;
import io.rong.imkit.widget.AsyncImageView;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * @author dengxudong
 * @version $Rev$
 */
public class CallSelectMemberActivity extends BaseNoActionBarActivity {

    ArrayList<String> selectedMember;
    private ArrayList<String> observerMember;
    TextView txtvStart, callkit_conference_selected_number;
    ListAdapter mAdapter;
    ListView mList;
    RongCallCommon.CallMediaType mMediaType;
    private Conversation.ConversationType conversationType;
    private EditText searchView;
    private ArrayList<String> allMembers = null;

    private HashMap<String, String> tempNickmembers = new HashMap<>();

    private ArrayList<String> searchMembers = new ArrayList<>();
    private ArrayList<String> invitedMembers;
    private ArrayList<String> tempMembers = new ArrayList<>();

    private UserInfo userInfo;
    private GroupUserInfo groupUserInfo;

    private String groupId;
    private RelativeLayout rlSearchTop;
    private RelativeLayout rlActionBar;
    private ImageView ivBack;
    private CallKitSearchBarView searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_call_select_member2);
        RongContext.getInstance().getEventBus().register(this);


        initTopBar();

        selectedMember = new ArrayList<>();
        observerMember = new ArrayList<>();

        Intent intent = getIntent();
        int type = intent.getIntExtra("mediaType", RongCallCommon.CallMediaType.VIDEO.getValue());
        mMediaType = RongCallCommon.CallMediaType.valueOf(type);
        int conType = intent.getIntExtra("conversationType", 0);
        conversationType = Conversation.ConversationType.setValue(conType);
        invitedMembers = intent.getStringArrayListExtra("invitedMembers");
        allMembers = intent.getStringArrayListExtra("allMembers");
        groupId = intent.getStringExtra("groupId");
        RongCallKit.GroupMembersProvider provider = RongCallKit.getGroupMemberProvider();
        if (groupId != null && allMembers == null && provider != null) {
            allMembers = provider.getMemberList(groupId, new RongCallKit.OnGroupMembersResult() {
                @Override
                public void onGotMemberList(ArrayList<String> members) {
                    if (mAdapter != null) {
                        if (members != null && members.size() > 0) {
                            mAdapter.setAllMembers(members);
                            allMembers = members;
                            mAdapter.notifyDataSetChanged();

                            /**转换昵称***/
                            for (int i = 0; i < allMembers.size(); i++) {
                                String userNickName = allMembers.get(i);
                                userInfo = RongContext.getInstance().getUserInfoFromCache(userNickName);
                                String displayName = "";
                                if (conversationType != null && conversationType.equals(Conversation.ConversationType.GROUP)) {
                                    groupUserInfo = RongUserInfoManager.getInstance().getGroupUserInfo(groupId, userNickName);
                                    if (groupUserInfo != null && !TextUtils.isEmpty(groupUserInfo.getNickname())) {
                                        displayName = groupUserInfo.getNickname();
                                        userNickName = displayName;
                                    }
                                }
                                if (TextUtils.isEmpty(displayName)) {
                                    if (userInfo != null) {
                                        userNickName = userInfo.getName();
                                    }
                                }
                                tempNickmembers.put(allMembers.get(i), userNickName);
                            }
                        }
                    }
                }
            });
        }

        callkit_conference_selected_number = findViewById(R.id.callkit_conference_selected_number);
        callkit_conference_selected_number.setText(getString(R.string.callkit_selected_contacts_count, allMembers == null ? 0 : allMembers.size()));
        txtvStart = findViewById(R.id.callkit_btn_ok);
        txtvStart.setEnabled(false);
        txtvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putStringArrayListExtra("invited", selectedMember);
                intent.putStringArrayListExtra("observers", observerMember);
                setResult(RESULT_OK, intent);
                CallSelectMemberActivity.this.finish();
            }
        });

        if (allMembers == null) {
            allMembers = invitedMembers;
        }

        mList = findViewById(R.id.calkit_list_view_select_member);
        if (invitedMembers != null && invitedMembers.size() > 0) {
            mAdapter = new ListAdapter(allMembers, invitedMembers);
            mList.setAdapter(mAdapter);
            mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    View v = view.findViewById(R.id.rc_checkbox);
                    String userId = (String) v.getTag();
                    if (!invitedMembers.contains(userId)) {
                        if (mMediaType.equals(RongCallCommon.CallMediaType.VIDEO)
                                && !v.isSelected() && selectedMember.size() + invitedMembers.size() == 6) {
//                            Toast.makeText(CallSelectMemberActivity.this,
//                                    String.format(getString(R.string.rc_voip_over_limit), 9),
//                                    Toast.LENGTH_SHORT)
//                                    .show();
//                            return;

                            CallPromptDialog dialog = CallPromptDialog.newInstance(CallSelectMemberActivity.this, getString(R.string.rc_voip_video_observer));
                            dialog.setPromptButtonClickedListener(new CallPromptDialog.OnPromptButtonClickedListener() {
                                @Override
                                public void onPositiveButtonClicked() {
                                }

                                @Override
                                public void onNegativeButtonClicked() {

                                }
                            });
                            dialog.disableCancel();
                            dialog.setCancelable(false);
                            dialog.show();
                        }
                        if (selectedMember.contains(userId)) {
                            selectedMember.remove(userId);
                        }

                        v.setSelected(!v.isSelected());
                        if (v.isSelected()) {
                            selectedMember.add(userId);
                        }
                        if (selectedMember.size() > 6) {
                            observerMember.add(userId);
                        }
                        if (selectedMember.size() > 0) {
                            txtvStart.setEnabled(true);
                            txtvStart.setTextColor(getResources().getColor(R.color.rc_voip_check_enable));
                        } else {
                            txtvStart.setEnabled(false);
                            txtvStart.setTextColor(getResources().getColor(R.color.rc_voip_check_disable));
                        }
                    }
                    if (searchMembers != null) {
                        callkit_conference_selected_number.setText(getString(R.string.callkit_selected_contacts_count, selectedMember.size()));
                    }
                }
            });
        }
        rlSearchTop = findViewById(R.id.rl_search_top);
        ivBack = findViewById(R.id.iv_back);
        searchBar = findViewById(R.id.search_bar);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlSearchTop.setVisibility(View.GONE);
                rlActionBar.setVisibility(View.VISIBLE);
                mAdapter.setAllMembers(allMembers);
                mAdapter.notifyDataSetChanged();
                CallKitUtils.closeKeyBoard(CallSelectMemberActivity.this, null);
            }
        });
        searchBar.setSearchBarListener(new CallKitSearchBarListener() {
            @Override
            public void onSearchStart(String content) {
                if (allMembers != null && allMembers.size() > 0) {
                    startSearchMember(content);
                } else {
                    Toast.makeText(CallSelectMemberActivity.this, "暂无数据提供搜索！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onSoftSearchKeyClick() {
            }

            @Override
            public void onClearButtonClick() {
            }
        });

    }

    private void startSearchMember(String searchEditContent) {
        try {
            searchMembers.clear();
            tempMembers.clear();
            if (!TextUtils.isEmpty(searchEditContent)) {
                for (String name : allMembers) {
                    if (tempNickmembers.get(name).indexOf(searchEditContent) != -1) {
                        searchMembers.add(name);
                    }
                }
                tempMembers.addAll(searchMembers);
            } else {
                tempMembers.addAll(allMembers);
            }
        } catch (Exception e) {
            e.printStackTrace();
            tempMembers.addAll(allMembers);
        }
        setData();
    }

    private void setData() {
        if (null != tempMembers && tempMembers.size() > 0) {
            ListAdapter adapter = new ListAdapter(tempMembers, invitedMembers);
            mList.setAdapter(adapter);
            mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    View v = view.findViewById(R.id.rc_checkbox);
                    String userId = (String) v.getTag();
                    if (!invitedMembers.contains(userId)) {
                        if (mMediaType.equals(RongCallCommon.CallMediaType.VIDEO)
                                && !v.isSelected() && selectedMember.size() + invitedMembers.size() == 6) {
//                            Toast.makeText(CallSelectMemberActivity.this,
//                                    String.format(getString(R.string.rc_voip_over_limit), 9),
//                                    Toast.LENGTH_SHORT)
//                                    .show();
//                            return;

                            CallPromptDialog dialog = CallPromptDialog.newInstance(CallSelectMemberActivity.this, getString(R.string.rc_voip_video_observer));
                            dialog.setPromptButtonClickedListener(new CallPromptDialog.OnPromptButtonClickedListener() {
                                @Override
                                public void onPositiveButtonClicked() {
                                }

                                @Override
                                public void onNegativeButtonClicked() {

                                }
                            });
                            dialog.disableCancel();
                            dialog.setCancelable(false);
                            dialog.show();
                        }
                        if (selectedMember.contains(userId)) {
                            selectedMember.remove(userId);
                        }

                        v.setSelected(!v.isSelected());
                        if (v.isSelected()) {
                            selectedMember.add(userId);
                        }
                        if (selectedMember.size() > 6) {
                            observerMember.add(userId);
                        }
                        if (selectedMember.size() > 0) {
                            txtvStart.setEnabled(true);
                            txtvStart.setTextColor(getResources().getColor(R.color.rc_voip_check_enable));
                        } else {
                            txtvStart.setEnabled(false);
                            txtvStart.setTextColor(getResources().getColor(R.color.rc_voip_check_disable));
                        }
                    }
                    if (searchMembers != null) {
                        callkit_conference_selected_number.setText(getString(R.string.callkit_selected_contacts_count, selectedMember.size()));
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        RongContext.getInstance().getEventBus().unregister(this);
        super.onDestroy();
    }

    class ListAdapter extends BaseAdapter {
        List<String> mallMembers;
        List<String> invitedMembers;

        public ListAdapter(List<String> allMembers, List<String> invitedMembers) {
            this.mallMembers = allMembers;
            this.invitedMembers = invitedMembers;
        }

        public void setAllMembers(List<String> allMembers) {
            this.mallMembers = allMembers;
        }

        @Override
        public int getCount() {
            return mallMembers.size();
        }

        @Override
        public Object getItem(int position) {
            return mallMembers.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(CallSelectMemberActivity.this).inflate(R.layout.rc_voip_listitem_select_member, null);
                holder.checkbox = convertView.findViewById(R.id.rc_checkbox);
                holder.portrait = convertView.findViewById(R.id.rc_user_portrait);
                holder.name = convertView.findViewById(R.id.rc_user_name);
                convertView.setTag(holder);
            }

            holder = (ViewHolder) convertView.getTag();
            holder.checkbox.setTag(mallMembers.get(position));
            if (invitedMembers.contains(mallMembers.get(position))) {
                holder.checkbox.setClickable(false);
                holder.checkbox.setEnabled(false);
                holder.checkbox.setImageResource(R.drawable.rc_voip_icon_checkbox_checked);
            } else {
                if (selectedMember.contains(mallMembers.get(position))) {
                    holder.checkbox.setImageResource(R.drawable.rc_voip_checkbox);
                    holder.checkbox.setSelected(true);
                } else {
                    holder.checkbox.setImageResource(R.drawable.rc_voip_checkbox);
                    holder.checkbox.setSelected(false);
                }
                holder.checkbox.setClickable(false);
                holder.checkbox.setEnabled(true);
            }
            UserInfo userInfo = RongContext.getInstance().getUserInfoFromCache(mallMembers.get(position));
            String displayName = "";
            if (conversationType != null && conversationType.equals(Conversation.ConversationType.GROUP)) {
                GroupUserInfo groupUserInfo = RongUserInfoManager.getInstance().getGroupUserInfo(groupId, mallMembers.get(position));
                if (groupUserInfo != null && !TextUtils.isEmpty(groupUserInfo.getNickname())) {
                    displayName = groupUserInfo.getNickname();
                    holder.name.setText(displayName);
                }
            }
            if (TextUtils.isEmpty(displayName)) {
                if (userInfo != null) {
                    holder.name.setText(userInfo.getName());
                } else {
                    holder.name.setText(mallMembers.get(position));
                }
            }
            if (userInfo != null) {
                holder.portrait.setAvatar(userInfo.getPortraitUri());
            } else {
                holder.portrait.setAvatar(null);
            }
            return convertView;
        }
    }

    public void onEventMainThread(UserInfo userInfo) {
        if (mList != null) {
            int first = mList.getFirstVisiblePosition() - mList.getHeaderViewsCount();
            int last = mList.getLastVisiblePosition() - mList.getHeaderViewsCount();

            int index = first - 1;

            while (++index <= last && index >= 0 && index < mAdapter.getCount()) {
                if (mAdapter.getItem(index).equals(userInfo.getUserId())) {
                    mAdapter.getView(index, mList.getChildAt(index - mList.getFirstVisiblePosition() + mList.getHeaderViewsCount()), mList);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    class ViewHolder {
        ImageView checkbox;
        AsyncImageView portrait;
        TextView name;
    }

    public void initTopBar() {
        rlActionBar = findViewById(R.id.rl_actionbar);
        ImageButton backImgBtn = findViewById(R.id.imgbtn_custom_nav_back);
        backImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView titleTextView = findViewById(R.id.tv_custom_nav_title);
        titleTextView.setText("选择联系人");
        titleTextView.setTextSize(18);
        titleTextView.setTextColor(getResources().getColor(R.color.callkit_normal_text));

        findViewById(R.id.imgbtn_custom_nav_option).setVisibility(View.VISIBLE);
        ((ImageButton) findViewById(R.id.imgbtn_custom_nav_option)).setImageResource(R.drawable.callkit_ic_search_focused_x);
        findViewById(R.id.imgbtn_custom_nav_option).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlSearchTop.setVisibility(View.VISIBLE);
                rlActionBar.setVisibility(View.GONE);
            }
        });
    }
}

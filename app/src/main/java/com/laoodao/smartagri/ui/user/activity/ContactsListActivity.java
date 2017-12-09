package com.laoodao.smartagri.ui.user.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.ChatAvatar;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.user.adapter.ContactsAdapter;
import com.laoodao.smartagri.ui.user.contract.ContactsListContract;
import com.laoodao.smartagri.ui.user.presenter.ContactsListPresenter;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by WORK on 2017/8/16.
 */

public class ContactsListActivity extends BaseActivity<ContactsListPresenter> implements ContactsListContract.ChatListView {


    protected ContactsAdapter adapter;
    protected List<EMConversation> conversations = new ArrayList<EMConversation>();
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;
    @BindView(R.id.et_chat)
    EditText mEtChat;
    private EMMessageListener msgListener;
    private String uids = "";
    private List<ChatAvatar> mAvatarData = new ArrayList<>();

    @Override
    public void complete() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contacts_list;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void configViews() {
        if (!EMClient.getInstance().isLoggedInBefore()) {
            UiUtils.makeText("登录异常");
            return;
        }
        EMClient.getInstance().groupManager().loadAllGroups();
        EMClient.getInstance().chatManager().loadAllConversations();
        initRecyclerView();
        conversations.clear();
        conversations.addAll(loadConversationList());
        mLlEmpty.setVisibility(conversations.size() < 1 ? View.VISIBLE : View.GONE);
        getAvatarData();
        listener();
    }

    private void initRecyclerView() {
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
    }

    private void listener() {
        mEtChat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (adapter==null){
                    return;
                }
                String content = s.toString();
                if (content.length() > 0) {
                    if (adapter.getConversationList().size()<1){
                        return;
                    }
                    adapter.getFilter().filter(content);
                } else {
                    adapter.setData(conversations, mAvatarData);
                }
            }
        });
    }

    private void getAvatarData() {
        for (int i = 0; i < conversations.size(); i++) {
            uids += conversations.get(i).conversationId() + ",";
        }
        mPresenter.getAvatar(uids);
    }

    private void refresh() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                conversations.clear();
                conversations.addAll(loadConversationList());
                adapter = new ContactsAdapter(ContactsListActivity.this, conversations, mAvatarData);
                adapter.setOnDelListener(count -> {
                    mLlEmpty.setVisibility(count < 1 ? View.VISIBLE : View.GONE);
                });
                if (adapter != null && mRecyclerview != null) {
                    mRecyclerview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
        super.onDestroy();
    }

    protected List<EMConversation> loadConversationList() {
        // get all conversations
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        /**
         * lastMsgTime will change if there is new message during sorting
         * so use synchronized to make sure timestamp of last message won't change.
         */
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getAllMessages().size() != 0) {
                    sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));
                }
            }
        }
        try {
            // Internal is TimSort algorithm, has bug
            sortConversationByLastChatTime(sortList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<EMConversation> list = new ArrayList<EMConversation>();
        for (Pair<Long, EMConversation> sortItem : sortList) {
            list.add(sortItem.second);
        }
        return list;
    }

    private void sortConversationByLastChatTime(List<Pair<Long, EMConversation>> conversationList) {
        Collections.sort(conversationList, new Comparator<Pair<Long, EMConversation>>() {
            @Override
            public int compare(final Pair<Long, EMConversation> con1, final Pair<Long, EMConversation> con2) {

                if (con1.first.equals(con2.first)) {
                    return 0;
                } else if (con2.first.longValue() > con1.first.longValue()) {
                    return 1;
                } else {
                    return -1;
                }
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setAvatar(List<ChatAvatar> list) {
        mAvatarData.clear();
        this.mAvatarData.addAll(list);
        adapter = new ContactsAdapter(this, conversations, mAvatarData);
        adapter.setOnDelListener(count -> {
            mLlEmpty.setVisibility(count < 1 ? View.VISIBLE : View.GONE);
        });
        mRecyclerview.setAdapter(adapter);
        msgListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
//                for (EMMessage message : messages) {
//                    DemoHelper.getInstance().getNotifier().onNewMsg(message);
//                    EaseUI.getInstance().getNotifier().onNewMsg(message);
//                }
                //收到消息
                refresh();
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
                //收到已读回执
            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
                //收到已送达回执
            }

            @Override
            public void onMessageRecalled(List<EMMessage> messages) {
                //消息被撤回
                refresh();
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
            }
        };
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }
}

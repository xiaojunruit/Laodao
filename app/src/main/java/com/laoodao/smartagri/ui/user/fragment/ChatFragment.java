package com.laoodao.smartagri.ui.user.fragment;

import android.net.Uri;
import android.view.View;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.laoodao.smartagri.ui.user.activity.ChatActivity;
import com.laoodao.smartagri.utils.LogUtil;

import java.io.File;

/**
 * Created by WORK on 2017/8/12.
 */

public class ChatFragment extends EaseChatFragment implements EaseChatFragment.EaseChatFragmentHelper {

    @Override
    protected void setUpView() {
        setChatFragmentHelper(this);
        super.setUpView();
    }

    @Override
    public void onSendVoice(String content) {
        ((ChatActivity) getActivity()).requestSendVoice(toFile(content));
    }

    @Override
    public void onSendText(String content) {
        ((ChatActivity) getActivity()).requestSendText(content);

    }

    @Override
    public void onSendImage(String content) {
        ((ChatActivity) getActivity()).requestSendImage(toFile(content));
    }

    private File toFile(String url) {
        File file = new File(url);
        return file;
    }

    @Override
    public void onSetMessageAttributes(EMMessage message) {

    }

    @Override
    public void onEnterToChatDetails() {

    }

    @Override
    public void onAvatarClick(String username) {
    }

    @Override
    public void onAvatarLongClick(String username) {

    }

    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        return false;
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {

    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        return false;
    }

    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return null;
    }
}

package com.ongtonnesoup.permissions;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.Stack;

public class PerMissionsMessageHandler extends Handler {

    private final PerMissionsMessageHandlerHandler callback;

    private Stack<Message> stack = new Stack<>();
    private boolean isPaused = false;

    public PerMissionsMessageHandler(PerMissionsMessageHandlerHandler callback) {
        this.callback = callback;
    }

    public synchronized void pause() {
        isPaused = true;
    }

    public synchronized void resume() {
        isPaused = false;
        while (!stack.empty()) {
            sendMessageAtFrontOfQueue(stack.pop());
        }
    }

    @Override
    public void handleMessage(Message msg) {
        if (isPaused) {
            stack.push(Message.obtain(msg));
            return;
        }

        if (msg.what == PerMissions.REQUEST_PERMISSIONS) {
            Bundle args = msg.getData();
            String[] permissions = PerMissionsResultBundleHelper.getPermissions(args);
            int[] grantResults = PerMissionsResultBundleHelper.getGrantResults(args);
            callback.onRequestPermissionsResult(permissions, grantResults);
        }
    }

}

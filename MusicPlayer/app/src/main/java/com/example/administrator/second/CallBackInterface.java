package com.example.administrator.second;

interface CallBackInterface {
    public void callPlay(boolean isContinue);
    public void callPause();
    public void callSeekTo(int position);
}

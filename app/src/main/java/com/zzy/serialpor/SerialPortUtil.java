package com.zzy.serialpor;

import android.util.Log;

import java.io.IOException;

public class SerialPortUtil extends SerialPortUtilBase {
    private static final String TAG = "SerialPortUtil";

    private ReadThread mReadThread;

    private static final int MAX_BUFFER = 2048;  // 考虑到接收的信息量可能会比较大，取2048byte
    private byte[] mBuffer = new byte[MAX_BUFFER];// 串口接收buffer
    private int mNumber = 0;// 接收byte数
    private Status mStatus = Status.IDLE;// 当前设备状态
    enum Status {
        IDLE,// 闲置状态
        WORKING// 工作状态
    }

    public SerialPortUtil(String path, int baudrate, int parity) {
        super(path, baudrate, parity);
        if (isSerialPortValid()) {
            // 串口打开完成
            clearRcvBuffer();
            startThread();
        }
    }

    private class ReadThread extends Thread {
        @Override
        public void run() {
            Log.d(TAG, "开始读取串口消息");
            super.run();
            while (!isInterrupted()) {
                try {
                    byte buffer;
                    int ret = inputStream.available();
                    if (ret <= 0) {
                        continue;
                    }
                    if (mStatus == Status.IDLE) { // 开机电流冲击产生的垃圾数据
                        inputStream.skip(ret);
                        continue;
                    }

                    buffer = (byte) inputStream.read();// 阻塞直到读到数据，每次只读取一个byte

                    if (mStatus == Status.WORKING) {
                        parse(buffer);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 报文解析
    private void parse(byte buffer) {
        switch (mBuffer[0]) {
            case 0:

                break;
            default:
                mBuffer[mNumber] = buffer;// 组装报文
                mNumber++;
                break;
        }

    }

    // 关闭接收消息线程
    private void closeThread() {
        if (mReadThread != null) {
            mReadThread.interrupt();
        }
    }

    // 启动接收消息线程
    private void startThread() {
        mReadThread = new ReadThread();
        mReadThread.start();
    }

    // 清空受信Buffer和受信byte数
    private void clearRcvBuffer() {
        for (int i = 0; i < MAX_BUFFER; i++) {
            mBuffer[i] = 0;
        }
        mNumber = 0;
        // todo 暂时注释
//        mDataLength = 0;
//        mReplyDataMax = MAX_BUFFER;     // 回复数据的长度
    }


}

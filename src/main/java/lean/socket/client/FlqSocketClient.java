package lean.socket.client;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by 浩 on 2017/3/10.
 */
public class FlqSocketClient {

    private final static int DEFAULT_CONNECT_TIMEOUT = 3000;

    private final static int DEFAULT_READ_TIMEOUT = 5000;

    private static final Socket socket = new Socket();

    private String ip;

    private int port;

    private CountDownLatch countDownLatch;

    public FlqSocketClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    private synchronized void connect(String ip, int port, int connectTimeout, int readTimeout) throws RuntimeException {
        connectTimeout = connectTimeout < 0 ? DEFAULT_CONNECT_TIMEOUT : connectTimeout;
        readTimeout = readTimeout < 0 ? DEFAULT_READ_TIMEOUT : readTimeout;
        if(!socket.isConnected()){
            SocketAddress socketAddress = new InetSocketAddress(ip, port);
            try {
                socket.connect(socketAddress, connectTimeout);
                socket.setSoTimeout(readTimeout);
                socket.setKeepAlive(true);
            } catch (IOException e) {
                throw new RuntimeException("socket connect failed!", e);
            }
        }
    }

    public synchronized byte[] send(MsgStruct struct) throws RuntimeException {

        connect(this.ip, this.port, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
        countDownLatch = new CountDownLatch(1);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new WriteThread(this, struct.getData()));
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException("", e);
        }
        try {
            return getResponse(struct);
        } catch (RuntimeException e) {
            throw e;
        } finally {
            try {
                socket.shutdownInput();
                socket.shutdownOutput();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized byte[] getResponse(MsgStruct struct) throws RuntimeException {

        InputStream is;
        try {
            is = socket.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException("", e);
        }
        DataInputStream reader = new DataInputStream(is);
        int lenByteCount = struct.getLenByteCount();
        byte[] lenByte = new byte[lenByteCount];
        try {
            reader.read(lenByte);
        } catch (IOException e) {
            throw new RuntimeException("", e);
        }
        int len = ByteUtil.cBytes4ToInt(lenByte);
        System.out.println(len);
        int msgTypeByteCount = struct.getMsgTypeByteCount();
        byte[] typeBytes = new byte[msgTypeByteCount];
        try {
            reader.read(typeBytes);
        } catch (IOException e) {
            new RuntimeException("", e);
        }
        int type = ByteUtil.cBytes4ToInt(typeBytes);
        System.out.println(type);
        byte[] data = new byte[len - lenByteCount - msgTypeByteCount];
        try {
            reader.read(data);
        } catch (IOException e) {
            throw new RuntimeException("", e);
        }
        return data;
    }

    public int getPort() {
        return port;
    }

    class WriteThread implements Runnable {

        FlqSocketClient client;

        byte[] message;

        public WriteThread(FlqSocketClient client, byte[] message) {
            this.client = client;
            this.message = message;
        }

        @Override
        public void run() {
            try {
                OutputStream os = client.socket.getOutputStream();
                BufferedOutputStream writer = new BufferedOutputStream(os);
                writer.write(message);
                writer.flush();
            } catch (IOException e) {
            } finally {
                client.countDownLatch.countDown();
            }
        }
    }
}

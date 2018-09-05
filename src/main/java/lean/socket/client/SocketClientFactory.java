package lean.socket.client;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by 浩 on 2017/3/9.
 */
public class SocketClientFactory {

    private static Map<String, FlqSocketClient> socketClientMap = new ConcurrentHashMap<>();

    public static synchronized FlqSocketClient getClient(String ip, int port) {

        FlqSocketClient client = socketClientMap.get(ip);
        if (null == client) {
            client = new FlqSocketClient(ip, port);
            socketClientMap.put(ip, client);
            return client;
        } else if (client.getPort() != port) {
            FlqSocketClient flqSocketClient = new FlqSocketClient(ip, port);
            socketClientMap.put(ip, flqSocketClient);
            return flqSocketClient;
        }
        return client;
    }

    static class FlqSocketClient {

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
            if (!socket.isConnected()) {
                SocketAddress socketAddress = new InetSocketAddress(ip, port);
                try {
                    socket.connect(socketAddress, connectTimeout);
                } catch (IOException e) {
                    throw new RuntimeException("socket connect failed!", e);
                }
                try {
                    socket.setSoTimeout(readTimeout);
                } catch (SocketException e) {
                    throw new RuntimeException("socket set timeout failed!", e);
                }
            }
        }

        public synchronized String request(byte[] message) throws RuntimeException {

            connect(this.ip, this.port, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
            countDownLatch = new CountDownLatch(1);
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(new WriteThread(this, message));
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException("", e);
            }
            byte[] resultBytes;
            try {
                resultBytes = getResponse();
            } catch (RuntimeException e) {
                throw e;
            }
            return ByteUtil.bytetoString(resultBytes);
        }

        private synchronized byte[] getResponse() throws RuntimeException {

            InputStream is = null;
            try {
                is = socket.getInputStream();
            } catch (IOException e) {
                throw new RuntimeException("", e);
            }
            DataInputStream reader = new DataInputStream(is);
            byte[] lenByte = new byte[4];
            try {
                reader.read(lenByte);
            } catch (IOException e) {
                throw new RuntimeException("", e);
            }
            int len = ByteUtil.cBytes4ToInt(lenByte);
            System.out.println(len);
            byte[] typeBytes = new byte[4];
            try {
                reader.read(typeBytes);
            } catch (IOException e) {
                new RuntimeException("", e);
            }
            int type = ByteUtil.cBytes4ToInt(typeBytes);
            System.out.println(type);
            byte[] data = new byte[len - 8];
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
}
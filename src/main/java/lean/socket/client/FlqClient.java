package lean.socket.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.*;

/**
 * Created by 浩 on 2017/3/13.
 */
public class FlqClient {

    private final static int DEFAULT_CONNECT_TIMEOUT = 3000;

    private final static int DEFAULT_READ_TIMEOUT = 5000;

    private final static int THREAD_POOL_SIZE = 1;

    private String ip;

    private int port;

    public FlqClient(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public byte[] request(MsgStruct msgStruct) {

        try {
            SocketChannel channel = SocketChannel.open();
            channel.configureBlocking(false);
            channel.connect(new InetSocketAddress(ip, port));
            channel.socket().setSoTimeout(DEFAULT_READ_TIMEOUT);
            Executor pool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
            CountDownLatch latch = new CountDownLatch(THREAD_POOL_SIZE);
            Selector selector = Selector.open();
            TcpReadThread tcpReadThread = new TcpReadThread(latch, msgStruct, channel, selector);
            FutureTask<byte[]> task = new FutureTask<>(tcpReadThread);
            pool.execute(task);
            latch.await(20,TimeUnit.SECONDS);
            if(channel.isConnected()){
                channel.close();
                tcpReadThread.socketChannel = null;
            }
            if(selector.isOpen()){
                selector.close();
                tcpReadThread.selector = null;
            }
            byte[] bytes = task.get();
            return bytes;
        } catch (IOException e) {
            throw new RuntimeException("");
        } catch (InterruptedException e) {
            throw new RuntimeException("");
        } catch (ExecutionException e) {
            throw new RuntimeException("");
        } catch (RuntimeException e){
            throw e;
        }
    }

    class TcpReadThread implements Callable<byte[]>{

        private CountDownLatch latch;

        private MsgStruct msgStruct;

        private Selector selector;

        private SocketChannel socketChannel;

        public TcpReadThread(CountDownLatch latch, MsgStruct msgStruct, SocketChannel channel, Selector selector) throws IOException {
            this.latch = latch;
            this.msgStruct = msgStruct;
            this.selector = selector;
            this.socketChannel = channel;
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }

        @Override
        public byte[] call() {
            byte[] resultByte = null;
            try {
                while(selector.isOpen()){
                    selector.select(DEFAULT_CONNECT_TIMEOUT);
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if(key.isConnectable()){
                            SocketChannel channel = (SocketChannel) key.channel();
                            if(channel.isConnectionPending()){
                                channel.finishConnect();
                            }
                            channel.configureBlocking(false);
                            channel.write(ByteBuffer.wrap(msgStruct.getData()));
                            channel.register(selector, SelectionKey.OP_READ);
                        } else if (key.isReadable()){
                            resultByte = read(key);
                            selector.close();
                            socketChannel.close();
                            latch.countDown();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resultByte;
        }

        private byte[] read(SelectionKey key) throws RuntimeException {
            SocketChannel channel = (SocketChannel) key.channel();
            ByteBuffer lenBuffer = ByteBuffer.allocate(msgStruct.getLenByteCount());
            try {
                channel.read(lenBuffer);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(String.format("read length byte array error from %s:%s ", ip, port), e);
            }
            byte[] lenArray = lenBuffer.array();
            int len = ByteUtil.cBytes4ToInt(lenArray);
            ByteBuffer typeBuffer = ByteBuffer.allocate(msgStruct.getMsgTypeByteCount());
            byte[] typeArray = typeBuffer.array();
            try {
                channel.read(typeBuffer);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(String.format("read type byte array error from %s:%s ", ip, port), e);
            }
            ByteUtil.cBytes4ToInt(typeArray);
            ByteBuffer contentBuffer = ByteBuffer.allocate(len);
            try {
                channel.read(contentBuffer);
            } catch (IOException e) {
                throw new RuntimeException(String.format("read content byte array error from %s:%s ", ip, port), e);
            }
            return contentBuffer.array();
        }

    }

    public static void main(String[] args) {

        String ip = "10.2.4.10";
        int port = 8001;
        byte[] jsonByte = new byte[0];
        byte[] lenByte = ByteUtil.cInt2byteArray(8);
        byte[] typeByte = ByteUtil.cInt2byteArray(3);
        byte[] message = ByteUtil.arraycopy(lenByte, typeByte, jsonByte);
        MsgStruct msgStruct = new MsgStruct();
        msgStruct.setData(message);
        FlqClient flqClient = new FlqClient(ip, port);
        byte[] result = flqClient.request(msgStruct);
        System.out.println(ByteUtil.bytetoString(result));
    }

}

package lean.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;

public class Main {

    private static final int SESSION_TIMEOUT = 30000;

    private ZooKeeper zk;

    private Watcher watcher = new Watcher() {

        @Override
        public void process(WatchedEvent watchedEvent) {
            System.out.println(watchedEvent.toString());
        }
    };

    private void create() throws IOException {
        zk = new ZooKeeper("10.2.6.1", SESSION_TIMEOUT, this.watcher);
    }

    private void operate() throws KeeperException, InterruptedException {
        zk.delete("/zoo", -1);
        System.out.println("===========创建节点============");
        zk.create("/zoo", "myData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        System.out.println("========查看是否创建成功======== ");
        System.out.println(new String(zk.getData("/zoo", true, null)));

        System.out.println("==========修改节点数据=========");
        zk.setData("/zoo", "bmxy".getBytes(), -1);

        System.out.println("========查看是否修改成功========");
        System.out.println(new String(zk.getData("/zoo", false, null)));

        System.out.println("============删除节点===========");
//        zk.delete("/zoo", -1);

        System.out.println("=======查看节点是否被删除========");
        System.out.println("节点状态： [" + zk.exists("/zoo", false) + "]");
    }

    private void close() throws InterruptedException {
        zk.close();
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        Main main = new Main();
        main.create();
        main.operate();
        main.close();
    }
}

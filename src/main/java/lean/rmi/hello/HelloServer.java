package lean.rmi.hello;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by 浩 on 2017/5/2.
 */
public class HelloServer {

    public static void main(String[] args) {

        try {
            IHello iHello = new IHelloImpl();
            Registry registry = LocateRegistry.createRegistry(9999);
            Naming.bind("rmi://192.168.2.11:9999/iHello", iHello);
            System.out.println(">>>INFO:远程IHello对象绑定成功！");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}

package lean.rmi.hello;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by 浩 on 2017/5/2.
 */
public class HelloClient {

    public static void main(String[] args) {
        try {
            IHello iHello = (IHello) Naming.lookup("rmi://10.2.6.20:9999/iHello");
            System.out.println(iHello.helloWorld());
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}

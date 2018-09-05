package lean.rmi.hello;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by 浩 on 2017/5/2.
 */
public interface IHello extends Remote {

    String helloWorld() throws RemoteException;

}

package lean.rmi.hello;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by 浩 on 2017/5/2.
 */
public class IHelloImpl extends UnicastRemoteObject implements IHello {
    protected IHelloImpl() throws RemoteException {
    }

    protected IHelloImpl(int port) throws RemoteException {
        super(port);
    }

    protected IHelloImpl(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
    }

    @Override
    public String helloWorld() throws RemoteException {
        return "hello world";
    }
}

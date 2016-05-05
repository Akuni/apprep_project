package td1;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.*;

public class TestRMI {
	public static void main(String args[]) {
		// -Djava.rmi.server.hostname="10.212.120.205"
		try {
			Servor s = new Servor();
			Naming.rebind("rmi://localhost:2000/Hello", s);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

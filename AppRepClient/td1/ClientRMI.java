package td1;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientRMI {

	
	public static void main(String args[]) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			Distante id = null;
			try {
				id = (Distante) Naming.lookup("rmi://localhost:2000/Hello");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			Resultat r = id.sayResultat();
			System.out.println(r.toString());
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

}

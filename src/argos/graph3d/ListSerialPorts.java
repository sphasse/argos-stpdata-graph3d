package argos.graph3d;

import java.io.PrintStream;
import java.util.Enumeration;
import purejavacomm.CommPortIdentifier;

public class ListSerialPorts
{
  public static void main(String[] ap)
  {
    new ListSerialPorts().list();
    System.out.println("done");
  }
  
  protected void list()
  {
    Enumeration pList = CommPortIdentifier.getPortIdentifiers();
    while (pList.hasMoreElements())
    {
      CommPortIdentifier cpi = (CommPortIdentifier)pList.nextElement();
      System.out.print("Port " + cpi.getName() + " ");
      if (cpi.getPortType() == 1) {
        System.out.println("is a Serial Port: " + cpi);
      } else if (cpi.getPortType() == 2) {
        System.out.println("is a Parallel Port: " + cpi);
      } else {
        System.out.println("is an Unknown Port: " + cpi);
      }
    }
  }
}
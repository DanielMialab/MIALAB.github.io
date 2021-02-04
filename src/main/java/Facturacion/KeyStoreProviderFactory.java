/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facturacion;


import Facturacion.KeyStoreProvider;
import Facturacion.DylibKeyStoreProvider;
import Facturacion.AppleKeyStoreProvider;
import java.io.File;
import java.util.logging.Logger;

public class KeyStoreProviderFactory {
  private static final Logger log = Logger.getLogger(KeyStoreProviderFactory.class.getName());
  
  public static KeyStoreProvider createKeyStoreProvider() {
    String osName = System.getProperty("os.name");
    if (osName.toUpperCase().indexOf("WINDOWS") == 0)
      return new WindowsOtherKeyStoreProvider(); 
    if (osName.toUpperCase().indexOf("LINUX") == 0) {
      if (existeLibreriaLinux() == true)
        return new LinuxEProKeyStoreProvider(); 
      return new LinuxKeyStoreProvider();
    } 
    if (osName.toUpperCase().indexOf("MAC") == 0) {
      if (existeLibreriaMac() == true)
        return new DylibKeyStoreProvider(); 
      return new AppleKeyStoreProvider();
    } 
    throw new IllegalArgumentException("Sistema operativo no soportado!");
  }
  
  private static boolean existeLibreriaLinux() {
    boolean resultado = false;
    File lib = new File("/usr/lib/libeTPkcs11.so");
    File lib32 = new File("/usr/lib32/libeTPkcs11.so");
    File lib64 = new File("/usr/lib64/libeTPkcs11.so");
    if (lib.exists() == true || lib32.exists() == true || lib64.exists() == true)
      resultado = true; 
    return resultado;
  }
  
  public static boolean existeLibreriaMac() {
    boolean resultado = false;
    File lib = new File("/usr/local/lib/libeTPkcs11.dylib");
    if (lib.exists() == true)
      resultado = true; 
    return resultado;
  }
}

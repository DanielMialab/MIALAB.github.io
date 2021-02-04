/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facturacion;

public class LinuxKeyStoreProvider extends PKCS11KeyStoreProvider {
  private static final String CONFIG;
  
  static {
    StringBuffer config = new StringBuffer();
    config.append("name=Safenetikey2032\n");
    config.append("library=/usr/local/SafeNet/lib/libsfntpkcs11.so\n");
    config.append("disabledMechanisms={ CKM_SHA1_RSA_PKCS }");
    CONFIG = config.toString();
  }
  
  public String getConfig() {
    return CONFIG;
  }
}


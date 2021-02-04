/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facturacion;

public class DylibKeyStoreProvider extends PKCS11KeyStoreProvider {
  private static String CONFIG;
  
  public DylibKeyStoreProvider() {
    StringBuffer config = new StringBuffer();
    config.append("name=eToken\n");
    config.append("library=/usr/local/lib/libeTPkcs11.dylib\n");
    CONFIG = config.toString();
  }
  
  public String getConfig() {
    return CONFIG;
  }
}

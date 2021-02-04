/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facturacion;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

class ProxyAuthenticator extends Authenticator {
  private String user;
  
  private String password;
  
  public ProxyAuthenticator(String user, String password) {
    this.user = user;
    this.password = password;
  }
  
  protected PasswordAuthentication getPasswordAuthentication() {
    return new PasswordAuthentication(this.user, this.password.toCharArray());
  }
}

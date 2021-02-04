/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autorizacion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "autorizacionComprobanteLote1", propOrder = {"claveAccesoLote"})
public class AutorizacionComprobanteLote {
    
    
  protected String claveAccesoLote;
  
  public String getClaveAccesoLote() {
    return this.claveAccesoLote;
  }
  
  public void setClaveAccesoLote(String value) {
    this.claveAccesoLote = value;
  }
}

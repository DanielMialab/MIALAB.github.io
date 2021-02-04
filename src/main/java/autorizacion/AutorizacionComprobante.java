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
@XmlType(name = "autorizacionComprobante1", propOrder = {"claveAccesoComprobante"})
public class AutorizacionComprobante {
    
    
  protected String claveAccesoComprobante;
  
  public String getClaveAccesoComprobante() {
    return this.claveAccesoComprobante;
  }
  
  public void setClaveAccesoComprobante(String value) {
    this.claveAccesoComprobante = value;
  }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recepcion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validarComprobante1", propOrder = {"xml"})
public class ValidarComprobante {
  protected byte[] xml;
  
  public byte[] getXml() {
    return this.xml;
  }
  
  public void setXml(byte[] value) {
    this.xml = value;
  }
}

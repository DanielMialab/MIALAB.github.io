/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autorizacion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "autorizacionComprobanteLoteResponse1", propOrder = {"respuestaAutorizacionLote"})
public class AutorizacionComprobanteLoteResponse {
  @XmlElement(name = "RespuestaAutorizacionLote")
  protected RespuestaLote respuestaAutorizacionLote;
  
  public RespuestaLote getRespuestaAutorizacionLote() {
    return this.respuestaAutorizacionLote;
  }
  
  public void setRespuestaAutorizacionLote(RespuestaLote value) {
    this.respuestaAutorizacionLote = value;
  }
}
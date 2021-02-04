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
@XmlType(name = "autorizacionComprobanteResponse1", propOrder = {"respuestaAutorizacionComprobante"})
public class AutorizacionComprobanteResponse {
  @XmlElement(name = "RespuestaAutorizacionComprobante")
  protected RespuestaComprobante respuestaAutorizacionComprobante;
  
  public RespuestaComprobante getRespuestaAutorizacionComprobante() {
    return this.respuestaAutorizacionComprobante;
  }
  
  public void setRespuestaAutorizacionComprobante(RespuestaComprobante value) {
    this.respuestaAutorizacionComprobante = value;
  }
}
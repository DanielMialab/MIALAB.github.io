/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recepcion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validarComprobanteResponse1", propOrder = {"respuestaRecepcionComprobante"})
public class ValidarComprobanteResponse {
  @XmlElement(name = "RespuestaRecepcionComprobante")
  protected RespuestaSolicitud respuestaRecepcionComprobante;
  
  public RespuestaSolicitud getRespuestaRecepcionComprobante() {
    return this.respuestaRecepcionComprobante;
  }
  
  public void setRespuestaRecepcionComprobante(RespuestaSolicitud value) {
    this.respuestaRecepcionComprobante = value;
  }
}

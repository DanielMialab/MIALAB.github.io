/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autorizacion;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(name = "AutorizacionComprobantesOffline", targetNamespace = "http://ec.gob.sri.ws.autorizacion")
@XmlSeeAlso({ObjectFactory.class})



public interface AutorizacionComprobantesOffline {
  @WebMethod
  @WebResult(name = "RespuestaAutorizacionComprobante", targetNamespace = "")
  @RequestWrapper(localName = "autorizacionComprobante", targetNamespace = "http://ec.gob.sri.ws.autorizacion", className = "autorizacion.ws.sri.gob.ec.AutorizacionComprobante")
  @ResponseWrapper(localName = "autorizacionComprobanteResponse", targetNamespace = "http://ec.gob.sri.ws.autorizacion", className = "autorizacion.ws.sri.gob.ec.AutorizacionComprobanteResponse")
  RespuestaComprobante autorizacionComprobante(@WebParam(name = "claveAccesoComprobante", targetNamespace = "") String paramString);
  
  @WebMethod
  @WebResult(name = "RespuestaAutorizacionLote", targetNamespace = "")
  @RequestWrapper(localName = "autorizacionComprobanteLote", targetNamespace = "http://ec.gob.sri.ws.autorizacion", className = "autorizacion.ws.sri.gob.ec.AutorizacionComprobanteLote")
  @ResponseWrapper(localName = "autorizacionComprobanteLoteResponse", targetNamespace = "http://ec.gob.sri.ws.autorizacion", className = "autorizacion.ws.sri.gob.ec.AutorizacionComprobanteLoteResponse")
  RespuestaLote autorizacionComprobanteLote(@WebParam(name = "claveAccesoLote", targetNamespace = "") String paramString);
}


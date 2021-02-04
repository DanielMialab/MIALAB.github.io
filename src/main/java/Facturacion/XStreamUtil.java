/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facturacion;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import autorizacion.Mensaje;
import autorizacion.Autorizacion;
import autorizacion.RespuestaComprobante;
import autorizacion.RespuestaLote;
import java.io.Writer;

public class XStreamUtil {
  public static XStream getLoteXStream() {
    XStream xstream = new XStream((HierarchicalStreamDriver)new XppDriver() {
          public HierarchicalStreamWriter createWriter(Writer out) {
            return (HierarchicalStreamWriter)new PrettyPrintWriter(out) {
                protected void writeText(QuickWriter writer, String text) {
                  writer.write(text);
                }
              };
          }
        });
    xstream.alias("lote", LoteXml.class);
    xstream.alias("comprobante", ComprobanteXml.class);
    xstream.registerConverter(new ComprobanteXmlConverter());
    return xstream;
  }
  
  public static XStream getRespuestaXStream() {
    XStream xstream = new XStream((HierarchicalStreamDriver)new XppDriver() {
          public HierarchicalStreamWriter createWriter(Writer out) {
            return (HierarchicalStreamWriter)new PrettyPrintWriter(out) {
                protected void writeText(QuickWriter writer, String text) {
                  writer.write(text);
                }
              };
          }
        });
    xstream.alias("respuesta", RespuestaComprobante.class);
    xstream.alias("autorizacion", Autorizacion.class);
    xstream.alias("fechaAutorizacion", XMLGregorianCalendarImpl.class);
    xstream.alias("mensaje", Mensaje.class);
    xstream.registerConverter(new RespuestaDateConverter());
    return xstream;
  }
  
  public static XStream getRespuestaLoteXStream() {
    XStream xstream = new XStream((HierarchicalStreamDriver)new XppDriver() {
          public HierarchicalStreamWriter createWriter(Writer out) {
            return (HierarchicalStreamWriter)new PrettyPrintWriter(out) {
                protected void writeText(QuickWriter writer, String text) {
                  writer.write(text);
                }
              };
          }
        });
    xstream.alias("respuesta", RespuestaLote.class);
    xstream.alias("autorizacion", Autorizacion.class);
    xstream.alias("fechaAutorizacion", XMLGregorianCalendarImpl.class);
    xstream.alias("mensaje", Mensaje.class);
    xstream.registerConverter(new RespuestaDateConverter());
    return xstream;
  }
}
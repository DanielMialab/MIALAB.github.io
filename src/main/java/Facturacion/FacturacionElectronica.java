/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facturacion;



import Facturacion.XAdESBESSignature;
import Facturacion.n_cst_autoriza;
import Facturacion.XStreamUtil;
import autorizacion.Autorizacion;
import autorizacion.AutorizacionComprobante;
import autorizacion.AutorizacionComprobantesOffline;
import autorizacion.AutorizacionComprobantesOfflineService;
import autorizacion.Mensaje;
import autorizacion.RespuestaComprobante;
import autorizacion.RespuestaLote;
import com.thoughtworks.xstream.XStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.math.BigInteger;
import java.net.Authenticator;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import recepcion.Comprobante;
import recepcion.RecepcionComprobantesOffline;
import recepcion.RecepcionComprobantesOfflineService;
import recepcion.RespuestaSolicitud;
import Modelos.Factura.Factura;

/**
 *
 * @author User
 */
public class FacturacionElectronica {
    
    private String proxy;
    private int requestTimeout;
    
    public FacturacionElectronica(){
        requestTimeout =30000;
    }
    
    // firma documento electronico
      public String firmar_doc_elec_xml_1(String archivoACrear, String dirFirmados, String dirNoFirmados, String path_firma_xml, String password, String tipo)
    {
        String msj = "";
        try
        {
            XAdESBESSignature firma_xades = new XAdESBESSignature(dirNoFirmados, archivoACrear, path_firma_xml, password, dirFirmados);
            firma_xades.setPARENT_SIGN_NODO(tipo);
            msj = firma_xades.execute();
        }
        catch(Throwable t)
        {
            String result = (new StringBuilder()).append(t.toString()).append("\n").toString();
            StackTraceElement trace[] = t.getStackTrace();
            for(int i = 0; i < trace.length; i++)
                result = (new StringBuilder()).append(result).append(trace[i].toString()).append("\n").toString();

            msj = result;
        }
        return msj;
    }
      
      // autoriza dpcuemento de forma individual
public n_cst_autoriza autorizar_doc_individual_off(String claveAcceso, String tipoAmbiente, String dirAutorizados, String nombreArchivo) {
    String lstr_url = "";
    n_cst_autoriza aut = new n_cst_autoriza();

    if (tipoAmbiente.equals("1"))
      lstr_url = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl"; 
    if (tipoAmbiente.equals("2"))
      lstr_url = "https://cel.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl"; 
    try {
      AutorizacionComprobantesOfflineService acs = new AutorizacionComprobantesOfflineService(new URL(lstr_url), new QName("http://ec.gob.sri.ws.autorizacion", "AutorizacionComprobantesOfflineService"));
      AutorizacionComprobantesOffline aucom = acs.getAutorizacionComprobantesOfflinePort();
      BindingProvider bp = (BindingProvider)aucom;
      bp.getRequestContext().put("com.sun.xml.internal.ws.request.timeout", Integer.valueOf(this.requestTimeout));
      RespuestaComprobante rc = aucom.autorizacionComprobante(claveAcceso);
      if (!rc.getAutorizaciones().getAutorizacion().isEmpty()) {
        n_cst_autoriza[] arrayOfN_cst_autoriza = new n_cst_autoriza[rc.getAutorizaciones().getAutorizacion().size()];
        int j = 0;
        for (Autorizacion item : rc.getAutorizaciones().getAutorizacion()) {
          aut = new n_cst_autoriza();
          aut.estado = item.getEstado();
          aut.claveAcceso = rc.getClaveAccesoConsultada();
          aut.fechaAutorizacion = String.valueOf(item.getFechaAutorizacion().getYear()) + "-" + String.valueOf(item.getFechaAutorizacion().getMonth()) + "-" + String.valueOf(item.getFechaAutorizacion().getDay()) + " " + String.valueOf(item.getFechaAutorizacion().getHour()) + ":" + String.valueOf(item.getFechaAutorizacion().getMinute());
          aut.numeroAutorizacion = item.getNumeroAutorizacion();
          int i = 1;
          for (Mensaje im : item.getMensajes().getMensaje()) {
            aut.agregar_mensaje(i, im.getMensaje());
            aut.agregar_info(i, im.getInformacionAdicional());
            i++;
          } 
          if (item.getEstado().equals("AUTORIZADO")) {
            item.setComprobante("<![CDATA[" + item.getComprobante() + "]]>");
            XStream xstream = XStreamUtil.getRespuestaXStream();
            Writer writer = null;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            writer = new OutputStreamWriter(outputStream, "UTF-8");
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            xstream.toXML(item, writer);
            String xmlAutorizacion = outputStream.toString("UTF-8");
            stringToArchivo(dirAutorizados + File.separator + nombreArchivo, xmlAutorizacion);
            break;
          } 
          if (item.getEstado().equals("NO AUTORIZADO"));
          arrayOfN_cst_autoriza[j] = aut;
          j++;
        } 
        return aut;
      } 
      n_cst_autoriza[] autorizaciones = new n_cst_autoriza[1];
      autorizaciones[0] = new n_cst_autoriza();
      (autorizaciones[0]).error = "No hay autorizaciones disponibles";
      return autorizaciones[0];
    } catch (Exception ex) {
      n_cst_autoriza[] arrayOfN_cst_autoriza = new n_cst_autoriza[1];
      arrayOfN_cst_autoriza[0] = new n_cst_autoriza();
      if (ex.getMessage() != null) {
        (arrayOfN_cst_autoriza[0]).error = ex.getMessage();
      } else {
        (arrayOfN_cst_autoriza[0]).error = ex.toString();
      } 
      return arrayOfN_cst_autoriza[0];
    } 
  }
      
 public byte[] archivoToByte(File file) throws IOException {
    byte[] buffer = new byte[(int)file.length()];
    InputStream ios = null;
    try {
      ios = new FileInputStream(file);
      if (ios.read(buffer) == -1)
        throw new IOException("EOF reached while trying to read the whole file"); 
      String nn = new String(buffer);
      return buffer;
    } finally {
      try {
        if (ios != null)
          ios.close(); 
      } catch (IOException e) {}
    } 
  } 
  
 
 // valida docuemento (envía al sri  y devuelve clave de acceso)
 public n_cst_autoriza validar_doc_individual_off(String xml, String tipoAmbiente, String dirAutorizados, String nombreArchivo) {
    String lstr_url = "";
    n_cst_autoriza aut = new n_cst_autoriza();

    if (tipoAmbiente.equals("1"))// ambiente de pruebas
      lstr_url = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl"; 
    if (tipoAmbiente.equals("2"))// ambiente de producción
      lstr_url = "https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl"; 
    try {
      RecepcionComprobantesOfflineService  rcs = new RecepcionComprobantesOfflineService(new URL(lstr_url), new QName("http://ec.gob.sri.ws.recepcion", "RecepcionComprobantesOfflineService"));
      RecepcionComprobantesOffline recom = rcs.getRecepcionComprobantesOfflinePort();
      RespuestaSolicitud rs = recom.validarComprobante(archivoToByte(new File(xml)));
      rs.getComprobantes().getComprobante().isEmpty();
      if (!rs.getComprobantes().getComprobante().isEmpty()) {
        n_cst_autoriza[] arrayOfN_cst_autoriza = new n_cst_autoriza[rs.getComprobantes().getComprobante().size()];
        int j = 0;
        for (Comprobante item : rs.getComprobantes().getComprobante()) {
          aut = new n_cst_autoriza();
          aut.estado = rs.getEstado();
          aut.claveAcceso = item.getClaveAcceso();
          int i = 1;
          for (recepcion.Mensaje im : item.getMensajes().getMensaje()) {
            aut.agregar_mensaje(i, im.getMensaje());
            aut.agregar_info(i, im.getInformacionAdicional());
            i++;
          } 
          XStream xstream = XStreamUtil.getRespuestaXStream();
          Writer writer = null;
          ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
          writer = new OutputStreamWriter(outputStream, "UTF-8");
          writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
          xstream.toXML(item, writer);
          String xmlAutorizacion = outputStream.toString("UTF-8");
          stringToArchivo(dirAutorizados + File.separator + nombreArchivo, xmlAutorizacion);
          arrayOfN_cst_autoriza[j] = aut;
          j++;
        } 
        return aut;
      } 
      n_cst_autoriza[] autorizaciones = new n_cst_autoriza[1];
      autorizaciones[0] = new n_cst_autoriza();
      (autorizaciones[0]).estado = rs.getEstado();
      return autorizaciones[0];
    } catch (Exception ex) {
      n_cst_autoriza[] arrayOfN_cst_autoriza = new n_cst_autoriza[1];
      arrayOfN_cst_autoriza[0] = new n_cst_autoriza();
      if (ex.getMessage() != null) {
        (arrayOfN_cst_autoriza[0]).error = ex.getMessage();
      } else {
        (arrayOfN_cst_autoriza[0]).error = ex.toString();
      } 
      return arrayOfN_cst_autoriza[0];
    } 
  }
   
 /// firma documento dependiendo del tipo de firma archivo o token 
  public String firmar_doc_elec(String archivo_firma_xml,String archivoACrear, String dirFirmados, String rucEmisor, String tokenId, String password) {
    
      if (tokenId.equalsIgnoreCase("BCE_ALADDIN"))// firma con token 
      try {
        String respuestaFirma = null;
        respuestaFirma = X509Utils.firmaValidaArchivo(new File(archivoACrear), dirFirmados, rucEmisor, tokenId, password);
        System.out.print(respuestaFirma);
        return respuestaFirma;
      } catch (Exception ex) {
        return ex.getMessage();
      }  
    if (tokenId.equalsIgnoreCase("BCE_ARCHIVO")) {// firma con archivo p12 
      System.out.println(archivo_firma_xml);
      try {
        String respuestaFirma = null;
        respuestaFirma = firmar_doc_elec_xml_1(archivoACrear.substring(archivoACrear.lastIndexOf("\\") + 1), dirFirmados, archivoACrear.substring(0, archivoACrear.lastIndexOf("\\") + 1), archivo_firma_xml, password, "comprobante");
        return respuestaFirma;
      } catch (Exception ex) {
        return ex.getMessage();
      } 
    } 
    return "Token " + tokenId + " no instalado.";
  }
  public static String firmar_doc_elec_2(String archivoACrear, String dirFirmados, String rucEmisor, String tokenId, String password) {
    try {
      String respuestaFirma = null;
      respuestaFirma = X509Utils.firmaValidaArchivo(new File(archivoACrear), dirFirmados, rucEmisor, tokenId, password);
      System.out.print(respuestaFirma);
      return respuestaFirma;
    } catch (Exception ex) {
      return ex.getMessage();
    } 
  }
  public File stringToArchivo(String rutaArchivo, String contenidoArchivo) {
    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(rutaArchivo);
      OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
      for (int i = 0; i < contenidoArchivo.length(); i++)
        out.write(contenidoArchivo.charAt(i)); 
      out.close();
      return new File(rutaArchivo);
    } catch (Exception ex) {
      return null;
    } finally {
      try {
        if (fos != null)
          fos.close(); 
      } catch (Exception ex) {}
    } 
  }
  
  // valida docuementos
  public n_cst_autoriza validar_doc_lote(String xml, String tipoAmbiente, String dirAutorizados, String nombreArchivo) {
    String lstr_url = "";
    n_cst_autoriza aut = new n_cst_autoriza();

;
    if (tipoAmbiente.equals("1"))
      lstr_url = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantes?wsdl"; 
    if (tipoAmbiente.equals("2"))
      lstr_url = "https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantes?wsdl"; 
    try {
        
        
        
      RecepcionComprobantesOfflineService rcs = new RecepcionComprobantesOfflineService(new URL(lstr_url), new QName("http://ec.gob.sri.ws.recepcion", "RecepcionComprobantesService"));
      RecepcionComprobantesOffline recom = rcs.getRecepcionComprobantesOfflinePort();
      remover_bom(new File(xml), new File(dirAutorizados + "\\" + nombreArchivo));
      RespuestaSolicitud rs = recom.validarComprobante(archivoToByte(new File(dirAutorizados + "\\" + nombreArchivo)));
      rs.getComprobantes().getComprobante().isEmpty();
      if (!rs.getComprobantes().getComprobante().isEmpty()) {
        n_cst_autoriza[] arrayOfN_cst_autoriza = new n_cst_autoriza[rs.getComprobantes().getComprobante().size()];
        int j = 0;
        for (Comprobante item : rs.getComprobantes().getComprobante()) {
          aut = new n_cst_autoriza();
          aut.estado = rs.getEstado();
          aut.claveAcceso = item.getClaveAcceso();
          int i = 1;
          for (recepcion.Mensaje im : item.getMensajes().getMensaje()) {
            aut.agregar_mensaje(i, im.getMensaje());
            aut.agregar_info(i, im.getInformacionAdicional());
            i++;
          } 
          arrayOfN_cst_autoriza[j] = aut;
          j++;
        } 
        return aut;
      } 
      n_cst_autoriza[] autorizaciones = new n_cst_autoriza[1];
      autorizaciones[0] = new n_cst_autoriza();
      (autorizaciones[0]).estado = rs.getEstado();
      return autorizaciones[0];
    } catch (Exception ex) {
      n_cst_autoriza[] arrayOfN_cst_autoriza = new n_cst_autoriza[1];
      arrayOfN_cst_autoriza[0] = new n_cst_autoriza();
      if (ex.getMessage() != null) {
        (arrayOfN_cst_autoriza[0]).error = ex.getMessage();
      } else {
        (arrayOfN_cst_autoriza[0]).error = ex.toString();
      } 
      return arrayOfN_cst_autoriza[0];
    } 
  }
    public void remover_bom(File sourceFile, File destFile) throws IOException {
    if (!destFile.exists())
      destFile.createNewFile(); 
    FileChannel source = null;
    FileChannel destination = null;
    try {
      source = (new FileInputStream(sourceFile)).getChannel();
      source.position(3L);
      destination = (new FileOutputStream(destFile)).getChannel();
      destination.transferFrom(source, 0L, source.size() - 3L);
    } finally {
      if (source != null)
        source.close(); 
      if (destination != null)
        destination.close(); 
    } 
  }
    public String convertir_imagen(String astr_ruta) {
    ImageToPDF lpdf_pdf = new ImageToPDF();
    String lstr_pdf = astr_ruta + ".pdf";
    lpdf_pdf.ImageToPDF(astr_ruta, lstr_pdf);
    return lstr_pdf;
  }
    
     public String concatenar_pdf(String astr_lista) {
    String[] lastr = astr_lista.split(";");
    ImageToPDF lpdf_pdf = new ImageToPDF();
    lpdf_pdf.ConcatPDFFiles(lastr);
    return "Se generó con exito";
  }
     // valida documento xml tipo byte 
     public n_cst_autoriza validar_doc_individual_byte(byte[] xml, String tipoAmbiente, String dirAutorizados, String nombreArchivo) {
    String lstr_url = "";
    n_cst_autoriza aut = new n_cst_autoriza();

    if (tipoAmbiente.equals("1"))
      lstr_url = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantes?wsdl"; 
    if (tipoAmbiente.equals("2"))
      lstr_url = "https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantes?wsdl"; 
    try {
      RecepcionComprobantesOfflineService rcs = new RecepcionComprobantesOfflineService(new URL(lstr_url), new QName("http://ec.gob.sri.ws.recepcion", "RecepcionComprobantesService"));
      RespuestaSolicitud rs = rcs.getRecepcionComprobantesOfflinePort().validarComprobante(xml);
      rs.getComprobantes().getComprobante().isEmpty();
      if (!rs.getComprobantes().getComprobante().isEmpty()) {
        n_cst_autoriza[] arrayOfN_cst_autoriza = new n_cst_autoriza[rs.getComprobantes().getComprobante().size()];
        int j = 0;
        for (Comprobante item : rs.getComprobantes().getComprobante()) {
          aut = new n_cst_autoriza();
          aut.estado = rs.getEstado();
          aut.claveAcceso = item.getClaveAcceso();
          int i = 1;
          for (recepcion.Mensaje im : item.getMensajes().getMensaje()) {
            aut.agregar_mensaje(i, im.getMensaje());
            aut.agregar_info(i, im.getInformacionAdicional());
            i++;
          } 
          arrayOfN_cst_autoriza[j] = aut;
          j++;
        } 
        return aut;
      } 
      n_cst_autoriza[] autorizaciones = new n_cst_autoriza[1];
      autorizaciones[0] = new n_cst_autoriza();
      (autorizaciones[0]).estado = rs.getEstado();
      return autorizaciones[0];
    } catch (Exception ex) {
      n_cst_autoriza[] arrayOfN_cst_autoriza = new n_cst_autoriza[1];
      arrayOfN_cst_autoriza[0] = new n_cst_autoriza();
      if (ex.getMessage() != null) {
        (arrayOfN_cst_autoriza[0]).error = ex.getMessage();
      } else {
        (arrayOfN_cst_autoriza[0]).error = ex.toString();
      } 
      return arrayOfN_cst_autoriza[0];
    } 
  }
     
     
     /// autpriza documentos
    public n_cst_autoriza[] autorizar_doc_lote(String claveAcceso, String tipoAmbiente, String dirAutorizados, String nombreArchivo) {
    String lstr_url = "";
    n_cst_autoriza aut = new n_cst_autoriza();

    if (tipoAmbiente.equals("1"))
      lstr_url = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantes?wsdl"; 
    if (tipoAmbiente.equals("2"))
      lstr_url = "https://cel.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantes?wsdl"; 
    try {
      AutorizacionComprobantesOfflineService acs = new AutorizacionComprobantesOfflineService(new URL(lstr_url), new QName("http://ec.gob.sri.ws.autorizacion", "AutorizacionComprobantesService"));
      AutorizacionComprobantesOffline aucom = acs.getAutorizacionComprobantesOfflinePort();
      BindingProvider bp = (BindingProvider)aucom;
      bp.getRequestContext().put("com.sun.xml.internal.ws.request.timeout", Integer.valueOf(this.requestTimeout));
      RespuestaLote rc = aucom.autorizacionComprobanteLote(claveAcceso);
      if (!rc.getAutorizaciones().getAutorizacion().isEmpty()) {
        n_cst_autoriza[] arrayOfN_cst_autoriza = new n_cst_autoriza[rc.getAutorizaciones().getAutorizacion().size()];
        int j = 0;
        for (Autorizacion item : rc.getAutorizaciones().getAutorizacion()) {
          aut = new n_cst_autoriza();
          aut.estado = item.getEstado();
          aut.claveAcceso = rc.getClaveAccesoLoteConsultada();
          aut.fechaAutorizacion = String.valueOf(item.getFechaAutorizacion().getYear()) + "-" + String.valueOf(item.getFechaAutorizacion().getMonth()) + "-" + String.valueOf(item.getFechaAutorizacion().getDay()) + " " + String.valueOf(item.getFechaAutorizacion().getHour()) + ":" + String.valueOf(item.getFechaAutorizacion().getMinute());
          aut.numeroAutorizacion = item.getNumeroAutorizacion();
          int i = 1;
          for (Mensaje im : item.getMensajes().getMensaje()) {
            aut.agregar_mensaje(i, im.getMensaje());
            aut.agregar_info(i, im.getInformacionAdicional());
            i++;
          } 
          if (item.getEstado().equals("AUTORIZADO")) {
            item.setComprobante("<![CDATA[" + item.getComprobante() + "]]>");
            XStream xstream = XStreamUtil.getRespuestaXStream();
            Writer writer = null;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            writer = new OutputStreamWriter(outputStream, "UTF-8");
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            xstream.toXML(item, writer);
            String xmlAutorizacion = outputStream.toString("UTF-8");
            stringToArchivo(dirAutorizados + File.separator + nombreArchivo, xmlAutorizacion);
          } 
          if (item.getEstado().equals("NO AUTORIZADO"));
          arrayOfN_cst_autoriza[j] = aut;
          j++;
        } 
        return arrayOfN_cst_autoriza;
      } 
      n_cst_autoriza[] autorizaciones = new n_cst_autoriza[1];
      autorizaciones[0] = new n_cst_autoriza();
      (autorizaciones[0]).error = "No hay autorizaciones disponibles";
      return autorizaciones;
    } catch (Exception ex) {
      n_cst_autoriza[] arrayOfN_cst_autoriza = new n_cst_autoriza[1];
      arrayOfN_cst_autoriza[0] = new n_cst_autoriza();
      if (ex.getMessage() != null) {
        (arrayOfN_cst_autoriza[0]).error = ex.getMessage();
      } else {
        (arrayOfN_cst_autoriza[0]).error = ex.toString();
      } 
      return arrayOfN_cst_autoriza;
    } 
  }
    
    

      
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebafacturacionelec;

import Facturacion.ComprobantesFacturacion;
import Facturacion.FacturacionElectronica;
import Facturacion.Util.ArchivoUtils;
import Facturacion.Util.ClaveDeAcceso;
import Facturacion.Util.TipoAmbienteEnum;
import Facturacion.Util.TipoComprobanteEnum;
import Modelos.ClaveContingencia;
import Facturacion.n_cst_autoriza;
import Modelos.Factura.Factura;


/**
 *
 * @author User
 */
public class PruebaFacturacionElec {
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//       
//        //ejemplo llamada metodos Y clases
//        ClaveContingencia claveContingencia = new ClaveContingencia();
//        ArchivoUtils util = new ArchivoUtils();
//        ComprobantesFacturacion comprobante = new ComprobantesFacturacion();
//        Modelos.Factura.Factura facturaLLena = comprobante.generarComprobante();
//        String claveAcceso =  facturaLLena.getInfoTributaria().getClaveAcceso();
//        Long secuencial = Long.valueOf(facturaLLena.getInfoTributaria().getSecuencial());
//        String NombreXml= claveAcceso+".xml";
//        String directorioCrear="C:/COMPROBANTES_ELECTRONICOS/GENERADOS/"+NombreXml;
//        String respuesta = ArchivoUtils.crearArchivoXml2(directorioCrear, facturaLLena, claveContingencia, secuencial, TipoComprobanteEnum.FACTURA.getCode());
//       
//        
//       
//      
//        
//        System.out.println("Iniciando facturacion");
//        String archivoACrear = "C:\\COMPROBANTES_ELECTRONICOS\\GENERADOS\\"+NombreXml;
//        String dirFirmados="C:\\COMPROBANTES_ELECTRONICOS\\FIRMADOS";
//        String dirNoFirmados ="C:\\COMPROBANTES_ELECTRONICOS\\NOFIRMADOS";
//        String dirAutorizados="C:\\COMPROBANTES_ELECTRONICOS\\\\AUTORIZADOS";
//        
//        String passwordFirmaDigital ="Atmatias13.";/// password firma digital
//        String tipo=TipoAmbienteEnum.PRUEBAS.getCode();// TipoAmbienteEnum.PRODICCION.getCode();ambiente 1 desarrollo 2 producción 
//        String archivo_firma_xml="c:/COMPROBANTES_ELECTRONICOS/CERTIFICADO/andrea_maribel_tapia_jaramillo.p12";
//        FacturacionElectronica firma = new FacturacionElectronica();
//        // ejemplo  firma documento
//        String respuestaFirma = firma.firmar_doc_elec_xml_1(archivoACrear.substring(archivoACrear.lastIndexOf("\\") + 1), dirFirmados, archivoACrear.substring(0, archivoACrear.lastIndexOf("\\") + 1), archivo_firma_xml, passwordFirmaDigital, "comprobante");
//        
//        String dirFirmados1 = "C:\\COMPROBANTES_ELECTRONICOS\\FIRMADOS\\"+NombreXml;
////        // ejemplo valida documento
//        n_cst_autoriza respuestaValida = firma.validar_doc_individual_off(dirFirmados1, tipo, dirNoFirmados,NombreXml);
//        System.out.println("respuesta factura : "+respuestaValida.claveAcceso+": FIN RES");
//
//        // ejemplo autoriza documento 
//        n_cst_autoriza respuestaAutoriza = firma.autorizar_doc_individual_off(respuestaValida.claveAcceso, tipo, dirAutorizados,NombreXml);
//        System.out.println("respuesta Autoriza:"+respuestaAutoriza.estado);
        
        
        
//        String ClaveAcceso ="1912202001110470781300110010010000000201234567811";
//        n_cst_autoriza respuestaAutoriza = firma.autorizar_doc_individual_off(ClaveAcceso, tipo, dirAutorizados,NombreXml);
//        System.out.println("respuesta Aukkjkjktoriza:"+respuestaAutoriza.estado);
        
    }
    
}

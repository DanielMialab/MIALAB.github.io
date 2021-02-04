/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facturacion;
import Facturacion.GenericXMLSignature;
import es.mityc.firmaJava.libreria.xades.*;
import es.mityc.javasign.xml.refs.InternObjectToSign;
import es.mityc.javasign.xml.refs.ObjectToSign;
import java.io.PrintStream;
/**
 *
 * @author User
 */
public class XAdESBESSignature extends GenericXMLSignature {
    
    
    private String RESOURCE_TO_SIGN;
    private String SIGN_FILE_NAME;
    private String PARENT_SIGN_NODO;
    
    public XAdESBESSignature()
    {
        RESOURCE_TO_SIGN = " ";
        SIGN_FILE_NAME = " ";
        PARENT_SIGN_NODO = " ";
    }

    public XAdESBESSignature(String path_resource, String file_name, String p12_resource, String p12_password, String output)
    {
        RESOURCE_TO_SIGN = " ";
        SIGN_FILE_NAME = " ";
        PARENT_SIGN_NODO = " ";
        setRESOURCE_TO_SIGN((new StringBuilder()).append(path_resource).append(file_name).toString());
        setSIGN_FILE_NAME(file_name);
        setPKCS12_RESOURCE(p12_resource);
        setPKCS12_PASSWORD(p12_password);
        setOUTPUT_DIRECTORY(output);
        System.out.println((new StringBuilder()).append(RESOURCE_TO_SIGN).append(" ").append(SIGN_FILE_NAME).append(" ").append(PKCS12_RESOURCE).append(" ").append(PKCS12_PASSWORD).append(" ").append(OUTPUT_DIRECTORY).toString());
    }

    protected DataToSign createDataToSign()
    {
        DataToSign dataToSign = new DataToSign();
        dataToSign.setXadesFormat(EnumFormatoFirma.XAdES_BES);
        dataToSign.setEsquema(XAdESSchemas.XAdES_132);
        dataToSign.setXMLEncoding("UTF-8");
        dataToSign.setEnveloped(true);
        if(PARENT_SIGN_NODO.equals("lote"))
        {
            dataToSign.addObject(new ObjectToSign(new InternObjectToSign("lote"), "contenido lote", null, "text/xml", null));
            dataToSign.setParentSignNode("lote");
        } else
        {
            dataToSign.addObject(new ObjectToSign(new InternObjectToSign("comprobante"), "contenido comprobante", null, "text/xml", null));
            dataToSign.setParentSignNode("comprobante");
        }
        org.w3c.dom.Document docToSign = getDocument(RESOURCE_TO_SIGN);
        dataToSign.setDocument(docToSign);
        return dataToSign;
    }

    protected String getSignatureFileName()
    {
        return SIGN_FILE_NAME;
    }

    public String getRESOURCE_TO_SIGN()
    {
        return RESOURCE_TO_SIGN;
    }

    public void setRESOURCE_TO_SIGN(String RESOURCE_TO_SIGN)
    {
        this.RESOURCE_TO_SIGN = RESOURCE_TO_SIGN;
    }

    public String getSIGN_FILE_NAME()
    {
        return SIGN_FILE_NAME;
    }

    public void setSIGN_FILE_NAME(String SIGN_FILE_NAME)
    {
        this.SIGN_FILE_NAME = SIGN_FILE_NAME;
    }

    public String getPARENT_SIGN_NODO()
    {
        return PARENT_SIGN_NODO;
    }

    public void setPARENT_SIGN_NODO(String PARENT_SIGN_NODO)
    {
        this.PARENT_SIGN_NODO = PARENT_SIGN_NODO;
    }

    
}

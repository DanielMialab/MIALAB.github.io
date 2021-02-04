/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facturacion;


import es.mityc.firmaJava.libreria.utilidades.UtilidadTratarNodo;
import es.mityc.firmaJava.libreria.xades.DataToSign;
import es.mityc.firmaJava.libreria.xades.FirmaXML;
import es.mityc.javasign.pkstore.CertStoreException;
import es.mityc.javasign.pkstore.IPKStoreManager;
import es.mityc.javasign.pkstore.keystore.KSStore;



import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author User
 */
public abstract class GenericXMLSignature {
    
    
    
    public String PKCS12_RESOURCE;
    public String PKCS12_PASSWORD;
    public String OUTPUT_DIRECTORY;
    
     public GenericXMLSignature()
    {
        PKCS12_RESOURCE = " ";
        PKCS12_PASSWORD = " ";
        OUTPUT_DIRECTORY = " ";
    }
     
     
     public String execute()
    {
        IPKStoreManager storeManager = getPKStoreManager();
        if(storeManager == null)
        {
            System.err.println("El gestor de claves no se ha obtenido correctamente.");
            return "No se firm\363 el documento: Contrase\361a incorrecta.";
        }
        X509Certificate certificate = getFirstCertificate(storeManager);
        if(certificate == null)
        {
            System.err.println("No existe ning\372n certificado para firmar.");
            return "No existe ning\372n certificado para firmar.";
        }
        PrivateKey privateKey;
        try
        {
            privateKey = storeManager.getPrivateKey(certificate);
        }
        catch(CertStoreException e)
        {
            System.err.println("Error al acceder al almac\351n.");
            return "Error al acceder al almac\351n.";
        }
        Provider provider = storeManager.getProvider(certificate);
        DataToSign dataToSign = createDataToSign();
        FirmaXML firma = new FirmaXML();
        Document docSigned = null;
        try
        {
            Object res[] = firma.signFile(certificate, dataToSign, privateKey, provider);
            docSigned = (Document)res[0];
        }
        catch(Exception ex)
        {
            System.err.println("Error realizando la firma");
            ex.printStackTrace();
            return "Error realizando la firma";
        }
        String filePath = (new StringBuilder()).append(OUTPUT_DIRECTORY).append("/").append(getSignatureFileName()).toString();
        System.out.println((new StringBuilder()).append("Firma salvada en en: ").append(filePath).append(" ").append(getSignatureFileName()).toString());
        return saveDocumentToFileUnsafeMode(docSigned, filePath);
    }
     
         protected abstract DataToSign createDataToSign();

    protected abstract String getSignatureFileName();
    
     private String saveDocumentToFile(Document document, String pathfile)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(pathfile);
            UtilidadTratarNodo.saveDocumentToOutputStream(document, fos, true);
        }
        catch(FileNotFoundException e)
        {
            System.err.println("Error al salvar el documento");
            e.printStackTrace();
            if(e.getMessage() != null)
                return e.getMessage();
            else
                return "Error al salvar el documento";
        }
        return null;
    }

    private String saveDocumentToFileUnsafeMode(Document document, String pathfile)
    {
        TransformerFactory tfactory = TransformerFactory.newInstance();
        try
        {
            Transformer serializer = tfactory.newTransformer();
            serializer.transform(new DOMSource(document), new StreamResult(new File(pathfile)));
        }
        catch(TransformerException e)
        {
            System.err.println("Error al salvar el documento");
            e.printStackTrace();
            return "Error al salvar el documento";
        }
        return null;
    }
    
    protected Document getDocument(String resource)
    {
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        try
        {
            FileInputStream file = new FileInputStream(resource);
            doc = dbf.newDocumentBuilder().parse(file);
        }
        catch(ParserConfigurationException ex)
        {
            System.err.println("Error al parsear el documento");
            ex.printStackTrace();
        }
        catch(SAXException ex)
        {
            System.err.println("Error al parsear el documento");
            ex.printStackTrace();
        }
        catch(IOException ex)
        {
            System.err.println("Error al parsear el documento");
            ex.printStackTrace();
        }
        catch(IllegalArgumentException ex)
        {
            System.err.println("Error al parsear el documento");
            ex.printStackTrace();
        }
        return doc;
    }
    
    protected String getDocumentAsString(String resource)
    {
        Document doc = getDocument(resource);
        TransformerFactory tfactory = TransformerFactory.newInstance();
        StringWriter stringWriter = new StringWriter();
        try
        {
            Transformer serializer = tfactory.newTransformer();
            serializer.transform(new DOMSource(doc), new StreamResult(stringWriter));
        }
        catch(TransformerException e)
        {
            System.err.println("Error al imprimir el documento");
            e.printStackTrace();
        }
        return stringWriter.toString();
    }
    
    protected IPKStoreManager getPKStoreManager()
    {
        IPKStoreManager storeManager = null;
        try
        {
            KeyStore ks = KeyStore.getInstance("PKCS12");
            FileInputStream file = new FileInputStream(PKCS12_RESOURCE);
            ks.load(file, PKCS12_PASSWORD.toCharArray());
            storeManager = new KSStore(ks, new PassStoreKS(PKCS12_PASSWORD));
        }
        catch(KeyStoreException ex)
        {
            System.err.println("No se puede generar KeyStore PKCS12");
            ex.printStackTrace();
        }
        catch(NoSuchAlgorithmException ex)
        {
            System.err.println("No se puede generar KeyStore PKCS12");
            ex.printStackTrace();
        }
        catch(CertificateException ex)
        {
            System.err.println("No se puede generar KeyStore PKCS12");
            ex.printStackTrace();
        }
        catch(IOException ex)
        {
            System.err.println("No se firmo el archivo: Contrase\361a incorrecta");
            ex.printStackTrace();
        }
        return storeManager;
    }
    
    protected X509Certificate getFirstCertificate(IPKStoreManager storeManager)
    {
        List certs = null;
        try
        {
            certs = storeManager.getSignCertificates();
        }
        catch(CertStoreException ex)
        {
            System.err.println("Fallo obteniendo listado de certificados");
        }
        if(certs == null || certs.size() == 0)
            System.err.println("Lista de certificados vac\355a");
        X509Certificate certificate = (X509Certificate)certs.get(1);
        return certificate;
    }

    public String getOUTPUT_DIRECTORY()
    {
        return OUTPUT_DIRECTORY;
    }

    public void setOUTPUT_DIRECTORY(String OUTPUT_DIRECTORY)
    {
        this.OUTPUT_DIRECTORY = OUTPUT_DIRECTORY;
    }

    public String getPKCS12_PASSWORD()
    {
        return PKCS12_PASSWORD;
    }

    public void setPKCS12_PASSWORD(String PKCS12_PASSWORD)
    {
        this.PKCS12_PASSWORD = PKCS12_PASSWORD;
    }

    public String getPKCS12_RESOURCE()
    {
        return PKCS12_RESOURCE;
    }

    public void setPKCS12_RESOURCE(String PKCS12_RESOURCE)
    {
        this.PKCS12_RESOURCE = PKCS12_RESOURCE;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facturacion;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class validador {
  public String validar_xml(String xml, String xsd) throws Exception {
    StringBuffer listaError = new StringBuffer();
    try {
      Source xmlFile = new StreamSource(new File(xml));
      Source schemaFile = new StreamSource(new File(xsd));
      SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
      Schema schema = schemaFactory.newSchema(schemaFile);
      Validator validator = schema.newValidator();
      final List<SAXParseException> exceptions = new LinkedList<SAXParseException>();
      validator.setErrorHandler(new ErrorHandler() {
            public void warning(SAXParseException exception) throws SAXException {
              exceptions.add(exception);
            }
            
            public void fatalError(SAXParseException exception) throws SAXException {
              exceptions.add(exception);
            }
            
            public void error(SAXParseException exception) throws SAXException {
              exceptions.add(exception);
            }
          });
      validator.validate(xmlFile);
      if (exceptions.size() == 0) {
        System.out.println("ARCHIVO " + xmlFile.getSystemId() + " ES VALIDO");
        listaError.append("");
      } else {
        System.out.println("FILE " + xmlFile.getSystemId() + " ES INVALIDO");
        listaError.append("ARCHIVO " + xmlFile.getSystemId() + " ES INVALIDO" + "\n");
        System.out.println("Numero de errores: " + exceptions.size());
        for (int i = 0; i < exceptions.size(); i++) {
          i++;
          System.out.println("Error # " + i + ":");
          listaError.append("Error # " + i + ":" + "\n");
          i--;
          System.out.println(" - Linea: " + ((SAXParseException)exceptions.get(i)).getLineNumber());
          listaError.append(" - Linea: " + ((SAXParseException)exceptions.get(i)).getLineNumber() + "\n");
          System.out.println(" - Columna: " + ((SAXParseException)exceptions.get(i)).getColumnNumber());
          listaError.append(" - Columna: " + ((SAXParseException)exceptions.get(i)).getColumnNumber() + "\n");
          System.out.println(" - Mensaje: " + ((SAXParseException)exceptions.get(i)).getLocalizedMessage());
          listaError.append(" - Mensaje: " + ((SAXParseException)exceptions.get(i)).getLocalizedMessage() + "\n");
          System.out.println("------------------------------");
          listaError.append("------------------------------\n");
        } 
      } 
      return listaError.toString();
    } catch (SAXException e) {
      e.printStackTrace();
      return e.getMessage();
    } catch (IOException e) {
      e.printStackTrace();
      return e.getMessage();
    } 
  }
}
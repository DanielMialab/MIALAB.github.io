/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facturacion;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PRAcroForm;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.SimpleBookmark;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ImageToPDF {
  public static void main(String... args) {
    String[] pdfs = new String[3];
    ImageToPDF itp = new ImageToPDF();
    pdfs[0] = "d:/prueba_laar/fac_002005000000056.pdf";
    pdfs[1] = "d:/prueba_laar/view_002.pdf";
    pdfs[2] = "d:/prueba_laar/output.pdf";
    itp.PDFToImage();
  }
  
  public void PDFToImage() {
    Document document = new Document();
    String output = "d:/prueba_laar/fac_002005000000056.pdf";
    try {
      File file = File.createTempFile("ttt", ".tmp");
      PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
      document.open();
      int backPage = 1;
      PdfReader reader = new PdfReader(output);
      PdfImportedPage importedPage = writer.getImportedPage(reader, 1);
      Image image = Image.getInstance((PdfTemplate)importedPage);
      image.toString();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void ImageToPDF(String input, String output) {
    Document document = new Document();
    input = "d:/prueba_laar/view_002.jpeg";
    output = "d:/prueba_laar/view_002.pdf";
    try {
      FileOutputStream fos = new FileOutputStream(output);
      PdfWriter writer = PdfWriter.getInstance(document, fos);
      writer.open();
      document.open();
      document.add((Element)Image.getInstance(input));
      document.close();
      writer.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void ConcatPDFFiles(String[] args) {
    try {
      if (args.length < 2) {
        System.out.println("Usage: ConcatPDFFiles file1.pdf [file2.pdf... fileN.pdf] out.pdf");
        System.exit(1);
      } 
      int pageOffset = 0;
      ArrayList master = new ArrayList();
      int f = 0;
      String outFile = args[args.length - 1];
      Document document = null;
      PdfCopy writer = null;
      while (f < args.length - 1) {
        PdfReader reader = new PdfReader(args[f]);
        reader.consolidateNamedDestinations();
        int n = reader.getNumberOfPages();
        List bookmarks = SimpleBookmark.getBookmark(reader);
        if (bookmarks != null) {
          if (pageOffset != 0)
            SimpleBookmark.shiftPageNumbers(bookmarks, pageOffset, null); 
          master.addAll(bookmarks);
        } 
        pageOffset += n;
        if (f == 0) {
          document = new Document(reader.getPageSizeWithRotation(1));
          writer = new PdfCopy(document, new FileOutputStream(outFile));
          document.open();
        } 
        for (int i = 0; i < n; ) {
          i++;
          PdfImportedPage page = writer.getImportedPage(reader, i);
          writer.addPage(page);
        } 
        PRAcroForm form = reader.getAcroForm();
        if (form != null)
          writer.copyAcroForm(reader); 
        f++;
      } 
      if (!master.isEmpty())
        writer.setOutlines(master); 
      document.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
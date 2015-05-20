package com.sabre.tripcase.tcp.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.stream.StreamResult;

import org.docx4j.convert.out.html.AbstractHtmlExporter;
import org.docx4j.convert.out.html.AbstractHtmlExporter.HtmlSettings;
import org.docx4j.convert.out.html.HtmlExporterNG2;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
     
    public class DocToHTMLConverter {
     
        public static void main(String[] args) {
            createHTML(); 
        }

        private static void createHTML() {
            try {
                long start = System.currentTimeMillis();
     
                // 1) Load DOCX into WordprocessingMLPackage
                InputStream is = new FileInputStream(new File(
                        "C:\\Users\\Sriram\\Downloads\\NonPDFAttachments\\ANF_International_Phone_Information.docx"));
                WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
                        .load(is);
     
                // 2) Prepare HTML settings
                HtmlSettings htmlSettings = new HtmlSettings();
     
                // 3) Convert WordprocessingMLPackage to HTML
                OutputStream out = new FileOutputStream(new File(
                        "C:\\Users\\Sriram\\Downloads\\NonPDFAttachments\\ANF_International_Phone_Information.html"));
                AbstractHtmlExporter exporter = new HtmlExporterNG2();
                StreamResult result = new StreamResult(out);
                exporter.html(wordMLPackage, result, htmlSettings);
     
                System.err.println("Generated html with "
                        + (System.currentTimeMillis() - start) + "ms");
     
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
     
    }




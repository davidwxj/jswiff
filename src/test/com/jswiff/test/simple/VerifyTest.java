package com.jswiff.test.simple;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.MessageDigest;

import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.dom4j.io.SAXReader;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jswiff.SWFDocument;
import com.jswiff.SWFReader;
import com.jswiff.xml.Transformer;
import com.jswiff.xml.XMLWriter;

public class VerifyTest {
  
  private static class SwfFilter implements FilenameFilter {
    public boolean accept(File dir, String name) {
      if (name.length() < 4) return false;
      return name.substring( name.length() - 4 ).equalsIgnoreCase(".swf");
    }
  }
  
  private static final char[] hexChars ={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
  
  @BeforeClass
  public static void prepare() {
    XMLUnit.setIgnoreWhitespace(true);
  }
  
  @Test
  public void testSwfXml() throws Exception {
    SAXReader reader = new SAXReader(false);
    reader.setStripWhitespaceText(true);
    File tmpDir = new File("tmp");
    for (File swfFile : tmpDir.listFiles(new SwfFilter())) {
      
      String fPath = swfFile.getPath();
      String fBase = fPath.substring(0, fPath.length()-4);
      
      //Get XML control string for file
      File xmlFile = new File( fBase.concat(".xml") );
      Assert.assertTrue("xml dump for '" + fPath + "' does not exist!", xmlFile.canRead());
      String oldXmlString = reader.read(xmlFile).asXML();
      
      //Get XML dump, and compare to control string
      String newXmlString = getSwfXmlString( buildSwfDocument(swfFile) );
      XMLAssert.assertXMLEqual("Comparing xml for '" + swfFile.getName() + "'", oldXmlString, newXmlString);
      
      //Convert XML string back to SWF
      StringReader xmlSource = new StringReader(newXmlString);
      File swfDest = new File( fBase.concat("_REBUILT.swf") );
      BufferedOutputStream fout = new BufferedOutputStream(new FileOutputStream(swfDest));
      Transformer.toSWF(xmlSource, fout);
      fout.close();
      
      //Get control MD5 hash
      BufferedReader fr = new BufferedReader(new FileReader( fBase.concat(".md5") ));
      String oldMD5String = fr.readLine();
      fr.close();
      
      //Ensure MD5 hash of rebuilt SWF is as expected
      FileInputStream fin = new FileInputStream(swfDest);
      byte[] swfData = new byte[(int)swfDest.length()];
      fin.read(swfData, 0, swfData.length);
      Assert.assertEquals("Comparing MD5 hashes for '" + fBase + "'", oldMD5String, getMD5Hash(swfData));
      
      swfDest.delete();
    }
  }
  
  private SWFDocument buildSwfDocument(File swfFile) throws Exception {
    BufferedInputStream fin = new BufferedInputStream( new FileInputStream( swfFile ) );
    SWFReader swfReader = new SWFReader(fin);
    return swfReader.read();
  }
  
  private String getSwfXmlString(SWFDocument swfDoc) throws Exception {
    StringWriter xmlString = new StringWriter();
    new XMLWriter(swfDoc).write(xmlString, false);
    return xmlString.toString();
  }
  
  private void dumpSwfXml(SWFDocument swfDoc, File dest) throws Exception {
    OutputStreamWriter fout = new OutputStreamWriter(new FileOutputStream(dest), "UTF-8");
    new XMLWriter(swfDoc).write(fout, true);
    fout.close();
  }
  
  private String getMD5Hash(byte[] data) throws Exception {
    MessageDigest md = MessageDigest.getInstance("MD5");
    md.update(data, 0, data.length);
    byte[] b = md.digest();
    String hex = "";
    int i, msb, lsb = 0;
    // MSB maps to idx 0
    for (i = 0; i < b.length; i++) {
      msb = (b[i] & 0x000000FF) / 16;
      lsb = (b[i] & 0x000000FF) % 16;
      hex = hex + hexChars[msb] + hexChars[lsb];
    }
    return(hex);
  }

}

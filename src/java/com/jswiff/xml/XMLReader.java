/*
 * Copyright © 2004-2010 Ralf Sippl <ralf.sippl@gmail.com>
 * Copyright © 2008-2010 Ben Stock <bs.stock+jswiff@gmail.com>
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS "AS IS AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of the copyright holders.
 *
 */

package com.jswiff.xml;

import com.jswiff.SWFDocument;
import com.jswiff.constants.TagConstants.TagType;
import com.jswiff.exception.DocumentException;
import com.jswiff.exception.InvalidNameException;
import com.jswiff.swfrecords.tags.Tag;
import com.jswiff.util.Base64;

import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.EnumSet;


/**
 * Converts an XML file to a SWF document.
 */
public class XMLReader {
  private SWFDocument swfDocument = new SWFDocument();
  private ArrayList<Tag> tags     = new ArrayList<Tag>();
  private SAXReader saxReader     = new SAXReader();
  {
    addHandlers();
  }

  /**
   * Creates a new XMLReader instance.
   *
   * @param stream XML source stream
   *
   * @throws DocumentException if the XML could not be parsed
   */
  public XMLReader(InputStream stream) throws DocumentException {
    try {
      saxReader.read(stream);
    } catch (org.dom4j.DocumentException e) {
      throw new DocumentException(e.getMessage());
    }
  }

  /**
   * Creates a new XMLReader instance.
   *
   * @param reader XML source reader
   *
   * @throws DocumentException if the XML could not be parsed
   */
  public XMLReader(Reader reader) throws DocumentException {
    try {
      saxReader.read(reader);
    } catch (org.dom4j.DocumentException e) {
      throw new DocumentException(e.getMessage());
    }
  }

  /**
   * Returns the SWF document generated from the parsed XML.
   *
   * @return SWF document
   */
  public SWFDocument getDocument() {
    swfDocument.addTags(tags);
    return swfDocument;
  }

  private void addHandlers() {
    HeaderHandler headerHandler = new HeaderHandler();
    saxReader.addHandler("/swfdocument/header", headerHandler);
    saxReader.addHandler("/swfdocument/unknowntag", new UnknownTagHandler());
    
    EnumSet<TagType> tagTypes = EnumSet.allOf(TagType.class);
    tagTypes.remove(TagType.UNKNOWN_TAG);
    tagTypes.remove(TagType.END);
    tagTypes.remove(TagType.FILE_ATTRIBUTES);
    tagTypes.remove(TagType.SET_BACKGROUND_COLOR);
    for (TagType t : tagTypes) {
      saxReader.addHandler(
          "/swfdocument/".concat(t.toString()), new TagHandler(t));
    }
  }

  private void parseHeader(Element headerElement) {
    if (RecordXMLReader.getBooleanAttribute("omitbinarydata", headerElement)) {
      throw new IllegalArgumentException("XML files with omitbinarydata flag set cannot be transformed back to SWF!");
    }
    short swfVersion = RecordXMLReader.getShortAttribute("swfversion", headerElement);
    swfDocument.setVersion(swfVersion);
    swfDocument.setCompressed(
      RecordXMLReader.getBooleanAttribute("compressed", headerElement));
    Element framesElement = RecordXMLReader.getElement("frames", headerElement);
    swfDocument.setFrameCount(
      RecordXMLReader.getIntAttribute("count", framesElement));
    swfDocument.setFrameRate(
      RecordXMLReader.getShortAttribute("rate", framesElement));
    Element sizeElement = RecordXMLReader.getElement("size", framesElement);
    swfDocument.setFrameSize(RecordXMLReader.readRect(sizeElement));
    Element backgroundColorElement = RecordXMLReader.getElement(
        "bgcolor", headerElement);
    swfDocument.setBackgroundColor(
      RecordXMLReader.readRGB(backgroundColorElement));
    if (swfVersion >= 8) {
      String access = RecordXMLReader.getStringAttribute(
          "access", headerElement);
      if (access.equals("local")) {
        swfDocument.setAccessMode(SWFDocument.ACCESS_MODE_LOCAL);
      } else if (access.equals("network")) {
        swfDocument.setAccessMode(SWFDocument.ACCESS_MODE_NETWORK);
      }
      Element metadata = headerElement.element("metadata");
      if (metadata != null) {
        swfDocument.setMetadata(Base64.decodeString(metadata.getText()));
      }
    }
  }

  private class TagHandler implements ElementHandler {
    private final TagType tagType;

    public TagHandler(TagType tagType) {
      this.tagType = tagType;
    }

    public void onEnd(ElementPath path) {
      Element tagElement = path.getCurrent();
      Tag tag;
      try {
        tag = TagXMLReader.readTag(tagElement, tagType);
        tags.add(tag);
      } catch (InvalidNameException e) {
        //FIXME: Handle exception properly
        e.printStackTrace();
      } catch (IOException ioe) {
        //FIXME: Handle exception properly
        ioe.printStackTrace();
      }
      tagElement.detach(); // prune element from tree
      tagElement = null;
    }

    public void onStart(ElementPath path) {
    }
  }

  private class UnknownTagHandler implements ElementHandler {
    public void onEnd(ElementPath path) {
      Element tagElement = path.getCurrent();
      Tag tag            = TagXMLReader.readUnknownTag(tagElement);
      tags.add(tag);
      tagElement.detach(); // prune element from tree
    }

    public void onStart(ElementPath path) {
    }
  }

  private class HeaderHandler implements ElementHandler {
    public void onEnd(ElementPath path) {
      Element headerElement = path.getCurrent();
      parseHeader(headerElement);
      headerElement.detach();
      headerElement = null;
    }

    public void onStart(ElementPath path) {
    }
  }
}

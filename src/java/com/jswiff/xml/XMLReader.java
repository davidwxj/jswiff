/*
 * JSwiff is an open source Java API for Adobe Flash file generation
 * and manipulation.
 *
 * Copyright (C) 2004-2008 Ralf Terdic (contact@jswiff.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses/>.
 */

package com.jswiff.xml;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.EnumSet;

import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;
import org.dom4j.io.SAXReader;

import com.jswiff.SWFDocument;
import com.jswiff.constants.TagConstants.TagType;
import com.jswiff.exception.DocumentException;
import com.jswiff.exception.InvalidNameException;
import com.jswiff.swfrecords.tags.Tag;
import com.jswiff.util.Base64;


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

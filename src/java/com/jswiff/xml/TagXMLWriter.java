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

import com.jswiff.constants.TagConstants.LangCode;
import com.jswiff.io.OutputBitStream;
import com.jswiff.swfrecords.ButtonCondAction;
import com.jswiff.swfrecords.ButtonRecord;
import com.jswiff.swfrecords.CXform;
import com.jswiff.swfrecords.FrameData;
import com.jswiff.swfrecords.KerningRecord;
import com.jswiff.swfrecords.MorphFillStyles;
import com.jswiff.swfrecords.MorphLineStyles;
import com.jswiff.swfrecords.SceneData;
import com.jswiff.swfrecords.Shape;
import com.jswiff.swfrecords.TextRecord;
import com.jswiff.swfrecords.abc.AbcFile;
import com.jswiff.swfrecords.tags.DebugId;
import com.jswiff.swfrecords.tags.DefineBinaryData;
import com.jswiff.swfrecords.tags.DefineBits;
import com.jswiff.swfrecords.tags.DefineBitsJPEG2;
import com.jswiff.swfrecords.tags.DefineBitsJPEG3;
import com.jswiff.swfrecords.tags.DefineBitsLossless;
import com.jswiff.swfrecords.tags.DefineBitsLossless2;
import com.jswiff.swfrecords.tags.DefineButton;
import com.jswiff.swfrecords.tags.DefineButton2;
import com.jswiff.swfrecords.tags.DefineButtonCXform;
import com.jswiff.swfrecords.tags.DefineButtonSound;
import com.jswiff.swfrecords.tags.DefineEditText;
import com.jswiff.swfrecords.tags.DefineFont;
import com.jswiff.swfrecords.tags.DefineFont2;
import com.jswiff.swfrecords.tags.DefineFont3;
import com.jswiff.swfrecords.tags.DefineFontAlignment;
import com.jswiff.swfrecords.tags.DefineFontInfo;
import com.jswiff.swfrecords.tags.DefineFontInfo2;
import com.jswiff.swfrecords.tags.DefineFontName;
import com.jswiff.swfrecords.tags.DefineMorphShape;
import com.jswiff.swfrecords.tags.DefineMorphShape2;
import com.jswiff.swfrecords.tags.DefineSceneFrameData;
import com.jswiff.swfrecords.tags.DefineShape;
import com.jswiff.swfrecords.tags.DefineShape2;
import com.jswiff.swfrecords.tags.DefineShape3;
import com.jswiff.swfrecords.tags.DefineShape4;
import com.jswiff.swfrecords.tags.DefineSound;
import com.jswiff.swfrecords.tags.DefineSprite;
import com.jswiff.swfrecords.tags.DefineText;
import com.jswiff.swfrecords.tags.DefineText2;
import com.jswiff.swfrecords.tags.DefineVideoStream;
import com.jswiff.swfrecords.tags.DoAbc;
import com.jswiff.swfrecords.tags.DoAbcDefine;
import com.jswiff.swfrecords.tags.DoAction;
import com.jswiff.swfrecords.tags.DoInitAction;
import com.jswiff.swfrecords.tags.EnableDebugger;
import com.jswiff.swfrecords.tags.EnableDebugger2;
import com.jswiff.swfrecords.tags.ExportAssets;
import com.jswiff.swfrecords.tags.FlashTypeSettings;
import com.jswiff.swfrecords.tags.FrameLabel;
import com.jswiff.swfrecords.tags.FreeCharacter;
import com.jswiff.swfrecords.tags.ImportAssets;
import com.jswiff.swfrecords.tags.ImportAssets2;
import com.jswiff.swfrecords.tags.JPEGTables;
import com.jswiff.swfrecords.tags.MalformedTag;
import com.jswiff.swfrecords.tags.PlaceObject;
import com.jswiff.swfrecords.tags.PlaceObject2;
import com.jswiff.swfrecords.tags.PlaceObject3;
import com.jswiff.swfrecords.tags.ProductInfo;
import com.jswiff.swfrecords.tags.Protect;
import com.jswiff.swfrecords.tags.RemoveObject;
import com.jswiff.swfrecords.tags.RemoveObject2;
import com.jswiff.swfrecords.tags.Scale9Grid;
import com.jswiff.swfrecords.tags.ScriptLimits;
import com.jswiff.swfrecords.tags.SetTabIndex;
import com.jswiff.swfrecords.tags.ShowFrame;
import com.jswiff.swfrecords.tags.SoundStreamBlock;
import com.jswiff.swfrecords.tags.SoundStreamHead;
import com.jswiff.swfrecords.tags.SoundStreamHead2;
import com.jswiff.swfrecords.tags.StartSound;
import com.jswiff.swfrecords.tags.SymbolClass;
import com.jswiff.swfrecords.tags.Tag;
import com.jswiff.swfrecords.tags.UnknownTag;
import com.jswiff.swfrecords.tags.VideoFrame;
import com.jswiff.swfrecords.tags.SymbolClass.SymbolReference;
import com.jswiff.util.Base64;
import com.jswiff.util.StringUtilities;

import org.dom4j.Element;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;


/*
 * Writes SWF tags to XML.
 */
class TagXMLWriter {
  
  static void writeTag(Element parentElement, Tag tag) {
    switch (tag.tagType()) {
      case DEBUG_ID:
        writeDebugId(parentElement, (DebugId) tag);
        break;
      case DEFINE_BINARY_DATA:
        writeDefineBinaryData(parentElement, (DefineBinaryData) tag);
        break;
      case DEFINE_BITS:
        writeDefineBits(parentElement, (DefineBits) tag);
        break;
      case DEFINE_BITS_JPEG_2:
        writeDefineBitsJPEG2(parentElement, (DefineBitsJPEG2) tag);
        break;
      case DEFINE_BITS_JPEG_3:
        writeDefineBitsJPEG3(parentElement, (DefineBitsJPEG3) tag);
        break;
      case DEFINE_BITS_LOSSLESS:
        writeDefineBitsLossless(parentElement, (DefineBitsLossless) tag);
        break;
      case DEFINE_BITS_LOSSLESS_2:
        writeDefineBitsLossless2(parentElement, (DefineBitsLossless2) tag);
        break;
      case DEFINE_BUTTON:
        writeDefineButton(parentElement, (DefineButton) tag);
        break;
      case DEFINE_BUTTON_2:
        writeDefineButton2(parentElement, (DefineButton2) tag);
        break;
      case DEFINE_BUTTON_C_XFORM:
        writeDefineButtonCXform(parentElement, (DefineButtonCXform) tag);
        break;
      case DEFINE_BUTTON_SOUND:
        writeDefineButtonSound(parentElement, (DefineButtonSound) tag);
        break;
      case DEFINE_EDIT_TEXT:
        writeDefineEditText(parentElement, (DefineEditText) tag);
        break;
      case DEFINE_FONT:
        writeDefineFont(parentElement, (DefineFont) tag);
        break;
      case DEFINE_FONT_2:
        writeDefineFont2(parentElement, (DefineFont2) tag);
        break;
      case DEFINE_FONT_3:
        writeDefineFont3(parentElement, (DefineFont3) tag);
        break;
      case DEFINE_FONT_INFO:
        writeDefineFontInfo(parentElement, (DefineFontInfo) tag);
        break;
      case DEFINE_FONT_INFO_2:
        writeDefineFontInfo2(parentElement, (DefineFontInfo2) tag);
        break;
      case DEFINE_FONT_ALIGNMENT:
        writeDefineFontAlignment(parentElement, (DefineFontAlignment) tag);
        break;
      case DEFINE_FONT_NAME:
        writeDefineFontName(parentElement, (DefineFontName) tag);
        break;
      case FLASHTYPE_SETTINGS:
        writeFlashTypeSettings(parentElement, (FlashTypeSettings) tag);
        break;
      case DEFINE_MORPH_SHAPE:
        writeDefineMorphShape(parentElement, (DefineMorphShape) tag);
        break;
      case DEFINE_MORPH_SHAPE_2:
        writeDefineMorphShape2(parentElement, (DefineMorphShape2) tag);
        break;
      case DEFINE_SCENE_FRAME_DATA:
        writeDefineSceneFrameData(parentElement, (DefineSceneFrameData) tag);
        break;
      case DEFINE_SHAPE:
        writeDefineShape(parentElement, (DefineShape) tag);
        break;
      case DEFINE_SHAPE_2:
        writeDefineShape2(parentElement, (DefineShape2) tag);
        break;
      case DEFINE_SHAPE_3:
        writeDefineShape3(parentElement, (DefineShape3) tag);
        break;
      case DEFINE_SHAPE_4:
        writeDefineShape4(parentElement, (DefineShape4) tag);
        break;
      case DEFINE_SOUND:
        writeDefineSound(parentElement, (DefineSound) tag);
        break;
      case DEFINE_SPRITE:
        writeDefineSprite(parentElement, (DefineSprite) tag);
        break;
      case DEFINE_TEXT:
        writeDefineText(parentElement, (DefineText) tag);
        break;
      case DEFINE_TEXT_2:
        writeDefineText2(parentElement, (DefineText2) tag);
        break;
      case DEFINE_VIDEO_STREAM:
        writeDefineVideoStream(parentElement, (DefineVideoStream) tag);
        break;
      case DO_ABC:
        writeDoAbc(parentElement, (DoAbc) tag);
        break;
      case DO_ABC_DEFINE:
        writeDoAbcDefine(parentElement, (DoAbcDefine) tag);
        break;
      case DO_ACTION:
        writeDoAction(parentElement, (DoAction) tag);
        break;
      case DO_INIT_ACTION:
        writeDoInitAction(parentElement, (DoInitAction) tag);
        break;
      case ENABLE_DEBUGGER:
        writeEnableDebugger(parentElement, (EnableDebugger) tag);
        break;
      case ENABLE_DEBUGGER_2:
        writeEnableDebugger2(parentElement, (EnableDebugger2) tag);
        break;
      case EXPORT_ASSETS:
        writeExportAssets(parentElement, (ExportAssets) tag);
        break;
      case FRAME_LABEL:
        writeFrameLabel(parentElement, (FrameLabel) tag);
        break;
      case FREE_CHARACTER:
        writeFreeCharacter(parentElement, (FreeCharacter) tag);
        break;
      case IMPORT_ASSETS:
        writeImportAssets(parentElement, (ImportAssets) tag);
        break;
      case IMPORT_ASSETS_2:
        writeImportAssets2(parentElement, (ImportAssets2) tag);
        break;
      case JPEG_TABLES:
        writeJPEGTables(parentElement, (JPEGTables) tag);
        break;
      case MALFORMED:
        writeMalformedTag(parentElement, (MalformedTag) tag);
        break;
      case PLACE_OBJECT:
        writePlaceObject(parentElement, (PlaceObject) tag);
        break;
      case PLACE_OBJECT_2:
        writePlaceObject2(parentElement, (PlaceObject2) tag);
        break;
      case PLACE_OBJECT_3:
        writePlaceObject3(parentElement, (PlaceObject3) tag);
        break;
      case PRODUCT_INFO:
        writeProductInfo(parentElement, (ProductInfo) tag);
        break;
      case PROTECT:
        writeProtect(parentElement, (Protect) tag);
        break;
      case REMOVE_OBJECT:
        writeRemoveObject(parentElement, (RemoveObject) tag);
        break;
      case REMOVE_OBJECT_2:
        writeRemoveObject2(parentElement, (RemoveObject2) tag);
        break;
      case SCRIPT_LIMITS:
        writeScriptLimits(parentElement, (ScriptLimits) tag);
        break;
      case SET_TAB_INDEX:
        writeSetTabIndex(parentElement, (SetTabIndex) tag);
        break;
      case SHOW_FRAME:
        writeShowFrame(parentElement, (ShowFrame) tag);
        break;
      case SCALE_9_GRID:
        writeScale9Grid(parentElement, (Scale9Grid) tag);
        break;
      case SOUND_STREAM_BLOCK:
        writeSoundStreamBlock(parentElement, (SoundStreamBlock) tag);
        break;
      case SOUND_STREAM_HEAD:
        writeSoundStreamHead(parentElement, (SoundStreamHead) tag);
        break;
      case SOUND_STREAM_HEAD_2:
        writeSoundStreamHead2(parentElement, (SoundStreamHead2) tag);
        break;
      case START_SOUND:
        writeStartSound(parentElement, (StartSound) tag);
        break;
      case SYMBOL_CLASS:
        writeSymbolClass(parentElement, (SymbolClass) tag);
        break;
      case VIDEO_FRAME:
        writeVideoFrame(parentElement, (VideoFrame) tag);
        break;
      case UNKNOWN_TAG:
        writeUnknownTag(parentElement, (UnknownTag)tag);
        break;
     //Not covered (header tags):
     //case END:
     //case FILE_ATTRIBUTES:
     //case METADATA:
     //case SET_BACKGROUND_COLOR:
      default:
        throw new AssertionError("Tag type '" + tag.tagType() + "' not handled!");
    }
  }

  private static String getSoundFormatString(byte format) {
    switch (format) {
      case DefineSound.FORMAT_ADPCM:
        return "adpcm";
      case DefineSound.FORMAT_MP3:
        return "mp3";
      case DefineSound.FORMAT_NELLYMOSER:
        return "nellymoser";
      case DefineSound.FORMAT_UNCOMPRESSED:
        return "uncompressed";
      case DefineSound.FORMAT_UNCOMPRESSED_LITTLE_ENDIAN:
        return "uncompressedle";
      default:
        throw new IllegalArgumentException("Illegal sound format: " + format);
    }
  }

  private static String getSoundRateString(byte rate) {
    switch (rate) {
      case DefineSound.RATE_5500_HZ:
        return "5500";
      case DefineSound.RATE_11000_HZ:
        return "11000";
      case DefineSound.RATE_22000_HZ:
        return "22000";
      case DefineSound.RATE_44000_HZ:
        return "44000";
      default:
        throw new IllegalArgumentException("Illegal sound rate: " + rate);
    }
  }

  private static void writeDebugId(Element parentElement, DebugId tag) {
    Element element = parentElement.addElement("debugid");
    element.addAttribute("id", tag.getId().toString());
  }
  
  private static void writeDefineBinaryData(Element parentElement, DefineBinaryData tag) {
    Element element = parentElement.addElement("definebinarydata");
    element.addAttribute("charid", Integer.toString(tag.getCharacterId()));
    String data = XMLWriter.isOmitBinaryData() ? "" : Base64.encode(tag.getBinaryData());
    element.addElement("data").addText(data);
  }
  
  private static void writeDefineBits(Element parentElement, DefineBits tag) {
    Element element = parentElement.addElement("definebits");
    element.addAttribute("charid", Integer.toString(tag.getCharacterId()));
    String data = XMLWriter.isOmitBinaryData() ? "" : Base64.encode(tag.getJpegData());
    element.addElement("jpegdata").addText(data);
  }

  private static void writeDefineBitsJPEG2(
    Element parentElement, DefineBitsJPEG2 tag) {
    Element element = parentElement.addElement("definebitsjpeg2");
    element.addAttribute("charid", Integer.toString(tag.getCharacterId()));
    String data = XMLWriter.isOmitBinaryData() ? "" : Base64.encode(tag.getJpegData());
    element.addElement("jpegdata").addText(data);
  }

  private static void writeDefineBitsJPEG3(
    Element parentElement, DefineBitsJPEG3 tag) {
    Element element = parentElement.addElement("definebitsjpeg3");
    element.addAttribute("charid", Integer.toString(tag.getCharacterId()));
    String jpegData = XMLWriter.isOmitBinaryData() ? "" : Base64.encode(tag.getJpegData());
    element.addElement("jpegdata").addText(jpegData);
    String alphaData = XMLWriter.isOmitBinaryData() ? "" : Base64.encode(tag.getBitmapAlphaData());
    element.addElement("alphadata").addText(alphaData);
  }

  private static void writeDefineBitsLossless(
    Element parentElement, DefineBitsLossless tag) {
    Element element = parentElement.addElement("definebitslossless");
    element.addAttribute("charid", Integer.toString(tag.getCharacterId()));
    String format;
    switch (tag.getFormat()) {
      case DefineBitsLossless.FORMAT_8_BIT_COLORMAPPED:
        format = "8bit";
        break;
      case DefineBitsLossless.FORMAT_15_BIT_RGB:
        format = "15bit";
        break;
      case DefineBitsLossless.FORMAT_24_BIT_RGB:
        format = "24bit";
        break;
      default:
        throw new IllegalArgumentException(
          "Illegal lossless bitmap format: " + tag.getFormat());
    }
    element.addAttribute("format", format);
    element.addAttribute("width", Integer.toString(tag.getWidth()));
    element.addAttribute("height", Integer.toString(tag.getHeight()));
    RecordXMLWriter.writeZlibBitmapData(element, tag.getZlibBitmapData());
  }

  private static void writeDefineBitsLossless2(
    Element parentElement, DefineBitsLossless2 tag) {
    Element element = parentElement.addElement("definebitslossless2");
    element.addAttribute("charid", Integer.toString(tag.getCharacterId()));
    String format;
    switch (tag.getFormat()) {
      case DefineBitsLossless2.FORMAT_8_BIT_COLORMAPPED:
        format = "8bit";
        break;
      case DefineBitsLossless2.FORMAT_32_BIT_RGBA:
        format = "32bit";
        break;
      default:
        throw new IllegalArgumentException(
          "Illegal lossless bitmap format: " + tag.getFormat());
    }
    element.addAttribute("format", format);
    element.addAttribute("width", Integer.toString(tag.getWidth()));
    element.addAttribute("height", Integer.toString(tag.getHeight()));
    RecordXMLWriter.writeZlibBitmapData(element, tag.getZlibBitmapData());
  }

  private static void writeDefineButton(
    Element parentElement, DefineButton tag) {
    Element element = parentElement.addElement("definebutton");
    element.addAttribute("charid", Integer.toString(tag.getCharacterId()));
    Element charsElement      = element.addElement("chars");
    ButtonRecord[] characters = tag.getCharacters();
    for (int i = 0; i < characters.length; i++) {
      RecordXMLWriter.writeButtonRecord(charsElement, characters[i]);
    }
    RecordXMLWriter.writeActionBlock(element, tag.getActions());
  }

  private static void writeDefineButton2(
    Element parentElement, DefineButton2 tag) {
    Element element = parentElement.addElement("definebutton2");
    element.addAttribute("charid", Integer.toString(tag.getCharacterId()));
    if (tag.isTrackAsMenu()) {
      element.addAttribute("trackasmenu", "true");
    }
    Element charsElement      = element.addElement("chars");
    ButtonRecord[] characters = tag.getCharacters();
    for (int i = 0; i < characters.length; i++) {
      RecordXMLWriter.writeButtonRecord(charsElement, characters[i]);
    }
    Element actionsElement     = element.addElement("actions");
    ButtonCondAction[] actions = tag.getActions();
    if (actions != null) {
      for (int i = 0; i < actions.length; i++) {
        RecordXMLWriter.writeButtonCondAction(actionsElement, actions[i]);
      }
    }
  }

  private static void writeDefineButtonCXform(
    Element parentElement, DefineButtonCXform tag) {
    Element element = parentElement.addElement("definebuttoncxform");
    element.addAttribute("charid", Integer.toString(tag.getCharacterId()));
    RecordXMLWriter.writeCXForm(element, tag.getColorTransform());
  }

  private static void writeDefineButtonSound(
    Element parentElement, DefineButtonSound tag) {
    Element element = parentElement.addElement("definebuttonsound");
    element.addAttribute("buttonid", Integer.toString(tag.getButtonId()));
    if (tag.getOverUpToIdleSoundId() != 0) {
      Element overUpToIdle = element.addElement("overuptoidle");
      overUpToIdle.addAttribute(
        "soundid", Integer.toString(tag.getOverUpToIdleSoundId()));
      RecordXMLWriter.writeSoundInfo(
        overUpToIdle, tag.getOverUpToIdleSoundInfo());
    }
    if (tag.getIdleToOverUpSoundId() != 0) {
      Element idleToOverUp = element.addElement("idletooverup");
      idleToOverUp.addAttribute(
        "soundid", Integer.toString(tag.getIdleToOverUpSoundId()));
      RecordXMLWriter.writeSoundInfo(
        idleToOverUp, tag.getIdleToOverUpSoundInfo());
    }
    if (tag.getOverUpToOverDownSoundId() != 0) {
      Element overUpToOverDown = element.addElement("overuptooverdown");
      overUpToOverDown.addAttribute(
        "soundid", Integer.toString(tag.getOverUpToOverDownSoundId()));
      RecordXMLWriter.writeSoundInfo(
        overUpToOverDown, tag.getOverUpToOverDownSoundInfo());
    }
    if (tag.getOverDownToOverUpSoundId() != 0) {
      Element overDownToOverUp = element.addElement("overdowntooverup");
      overDownToOverUp.addAttribute(
        "soundid", Integer.toString(tag.getOverDownToOverUpSoundId()));
      RecordXMLWriter.writeSoundInfo(
        overDownToOverUp, tag.getOverDownToOverUpSoundInfo());
    }
  }

  private static void writeDefineEditText(
    Element parentElement, DefineEditText tag) {
    Element element = parentElement.addElement("defineedittext");
    element.addAttribute("charid", Integer.toString(tag.getCharacterId()));
    if (tag.isWordWrap()) {
      element.addAttribute("wordwrap", "true");
    }
    if (tag.isMultiline()) {
      element.addAttribute("multiline", "true");
    }
    if (tag.isPassword()) {
      element.addAttribute("password", "true");
    }
    if (tag.isReadOnly()) {
      element.addAttribute("readonly", "true");
    }
    if (tag.isAutoSize()) {
      element.addAttribute("autosize", "true");
    }
    if (tag.isNoSelect()) {
      element.addAttribute("noselect", "true");
    }
    if (tag.isBorder()) {
      element.addAttribute("border", "true");
    }
    if (tag.isHtml()) {
      element.addAttribute("html", "true");
    }
    if (tag.isUseOutlines()) {
      element.addAttribute("useoutlines", "true");
    }
    if (tag.hasMaxLength()) {
      element.addAttribute("maxlength", Integer.toString(tag.getMaxLength()));
    }
    String var = tag.getVariableName();
    if ((var != null) && (var.length() > 0)) {
      element.addAttribute("variable", var);
    }
    RecordXMLWriter.writeRect(element, "bounds", tag.getBounds());
    if (tag.hasText()) {
      element.addElement("initialtext").addText(tag.getInitialText());
    }
    if (tag.hasTextColor()) {
      RecordXMLWriter.writeRGBA(element, "color", tag.getTextColor());
    }
    if (tag.hasFont()) {
      Element font = element.addElement("font");
      font.addAttribute("fontid", Integer.toString(tag.getFontId()));
      font.addAttribute("height", Integer.toString(tag.getFontHeight()));
    }
    if (tag.hasLayout()) {
      Element layout = element.addElement("layout");
      String align;
      switch (tag.getAlign()) {
        case DefineEditText.ALIGN_LEFT:
          align = "left";
          break;
        case DefineEditText.ALIGN_CENTER:
          align = "center";
          break;
        case DefineEditText.ALIGN_JUSTIFY:
          align = "justify";
          break;
        case DefineEditText.ALIGN_RIGHT:
          align = "right";
          break;
        default:
          throw new IllegalArgumentException(
            "Illegal text alignment: " + tag.getAlign());
      }
      layout.addAttribute("align", align);
      layout.addAttribute("leftmargin", Integer.toString(tag.getLeftMargin()));
      layout.addAttribute(
        "rightmargin", Integer.toString(tag.getRightMargin()));
      layout.addAttribute("indent", Integer.toString(tag.getIndent()));
      layout.addAttribute("leading", Integer.toString(tag.getLeading()));
    }
  }

  private static void writeDefineFont(Element parentElement, DefineFont tag) {
    Element element = parentElement.addElement("definefont");
    element.addAttribute("charid", Integer.toString(tag.getCharacterId()));
    Element glyphShapeTableElement = element.addElement("glyphshapetable");
    Shape[] glyphShapeTable        = tag.getGlyphShapeTable();
    for (int i = 0; i < glyphShapeTable.length; i++) {
      RecordXMLWriter.writeShape(glyphShapeTableElement, glyphShapeTable[i]);
    }
  }

  private static void writeDefineFont2(Element parentElement, DefineFont2 tag) {
    Element element = parentElement.addElement("definefont2");
    element.addAttribute("charid", Integer.toString(tag.getCharacterId()));
    RecordXMLWriter.addAttributeWithCharCheck(element, "fontname", tag.getFontName());
    if (tag.isANSI()) {
      element.addAttribute("ansi", "true");
    } else if (tag.isShiftJIS()) {
      element.addAttribute("shiftjis", "true");
    }
    if (tag.isBold()) {
      element.addAttribute("bold", "true");
    }
    if (tag.isItalic()) {
      element.addAttribute("italic", "true");
    }
    if (tag.isSmallText()) {
      element.addAttribute("smalltext", "true");
    }
    writeLanguage(element, tag.getLanguageCode());
    Shape[] table = tag.getGlyphShapeTable();
    if (table != null) {
      Element glyphShapeTableElement = element.addElement("glyphshapetable");
      char[] codeTable               = tag.getCodeTable();
      for (int i = 0; i < table.length; i++) {
        Element glyphElement = glyphShapeTableElement.addElement("glyph");
        RecordXMLWriter.addCharAttribute(glyphElement, "char", codeTable[i]);
        RecordXMLWriter.writeShape(glyphElement, table[i]);
        if (tag.hasLayout()) {
          glyphElement.addAttribute(
            "advance", Short.toString(tag.getAdvanceTable()[i]));
          RecordXMLWriter.writeRect(
            glyphElement, "bounds", tag.getBoundsTable()[i]);
        }
      }
    }
    if (tag.hasLayout()) {
      Element layout = element.addElement("layout");
      layout.addAttribute("ascent", Integer.toString(tag.getAscent()));
      layout.addAttribute("descent", Integer.toString(tag.getDescent()));
      layout.addAttribute("leading", Integer.toString(tag.getLeading()));
      KerningRecord[] kerningTable = tag.getKerningTable();
      if ((kerningTable != null) && (kerningTable.length > 0)) {
        Element kerningTableElement = layout.addElement("kerningtable");
        for (int i = 0; i < kerningTable.length; i++) {
          KerningRecord record  = kerningTable[i];
          Element recordElement = kerningTableElement.addElement(
              "kerningrecord");
          RecordXMLWriter.addCharAttribute(recordElement, "left", record.getLeft());
          RecordXMLWriter.addCharAttribute(recordElement, "right", record.getRight());
          recordElement.addAttribute(
            "adjust", Short.toString(record.getAdjustment()));
        }
      }
    }
  }

  private static void writeDefineFont3(Element parentElement, DefineFont3 tag) {
    Element element = parentElement.addElement("definefont3");
    element.addAttribute("charid", Integer.toString(tag.getCharacterId()));
    element.addAttribute("fontname", tag.getFontName());
    if (tag.isBold()) {
      element.addAttribute("bold", "true");
    }
    if (tag.isItalic()) {
      element.addAttribute("italic", "true");
    }
    if (tag.isSmallText()) {
      element.addAttribute("smalltext", "true");
    }
    writeLanguage(element, tag.getLanguageCode());
    Shape[] table = tag.getGlyphShapeTable();
    if (table != null) {
      Element glyphShapeTableElement = element.addElement("glyphshapetable");
      char[] codeTable               = tag.getCodeTable();
      for (int i = 0; i < table.length; i++) {
        Element glyphElement = glyphShapeTableElement.addElement("glyph");
        RecordXMLWriter.addCharAttribute(glyphElement, "char", codeTable[i]);
        RecordXMLWriter.writeShape(glyphElement, table[i]);
        if (tag.hasLayout()) {
          glyphElement.addAttribute(
            "advance", Short.toString(tag.getAdvanceTable()[i]));
          RecordXMLWriter.writeRect(
            glyphElement, "bounds", tag.getBoundsTable()[i]);
        }
      }
    }
    if (tag.hasLayout()) {
      Element layout = element.addElement("layout");
      layout.addAttribute("ascent", Integer.toString(tag.getAscent()));
      layout.addAttribute("descent", Integer.toString(tag.getDescent()));
      layout.addAttribute("leading", Integer.toString(tag.getLeading()));
      KerningRecord[] kerningTable = tag.getKerningTable();
      if ((kerningTable != null) && (kerningTable.length > 0)) {
        Element kerningTableElement = layout.addElement("kerningtable");
        for (int i = 0; i < kerningTable.length; i++) {
          KerningRecord record  = kerningTable[i];
          Element recordElement = kerningTableElement.addElement(
              "kerningrecord");
          RecordXMLWriter.addCharAttribute(recordElement, "left", record.getLeft());
          RecordXMLWriter.addCharAttribute(recordElement, "right", record.getRight());
          recordElement.addAttribute("adjust", Short.toString(record.getAdjustment()));
        }
      }
    }
  }

  private static void writeDefineFontAlignment(
    Element parentElement, DefineFontAlignment tag) {
    Element element = parentElement.addElement("definefontalignment");
    element.addAttribute("fontid", Integer.toString(tag.getFontId()));
    switch (tag.getThickness()) {
      case DefineFontAlignment.THIN:
        element.addAttribute("thickness", "thin");
        break;
      case DefineFontAlignment.MEDIUM:
        element.addAttribute("thickness", "medium");
        break;
      case DefineFontAlignment.THICK:
        element.addAttribute("thickness", "thick");
        break;
    }
    RecordXMLWriter.writeAlignmentZones(element, tag.getAlignmentZones());
  }

  private static void writeDefineFontInfo(
    Element parentElement, DefineFontInfo tag) {
    Element element = parentElement.addElement("definefontinfo");
    element.addAttribute("fontid", Integer.toString(tag.getFontId()));
    RecordXMLWriter.addAttributeWithCharCheck(element, "fontname", tag.getFontName());
    if (tag.isANSI()) {
      element.addAttribute("ansi", "true");
    } else if (tag.isShiftJIS()) {
      element.addAttribute("shiftjis", "true");
    }
    if (tag.isBold()) {
      element.addAttribute("bold", "true");
    }
    if (tag.isItalic()) {
      element.addAttribute("italic", "true");
    }
    if (tag.isSmallText()) {
      element.addAttribute("smalltext", "true");
    }
    char[] codeTable = tag.getCodeTable();
    for (int i = 0; i < codeTable.length; i++) {
      RecordXMLWriter.addCharAsTextNode(element.addElement("char"), codeTable[i]);
    }
  }

  private static void writeDefineFontInfo2(
    Element parentElement, DefineFontInfo2 tag) {
    Element element = parentElement.addElement("definefontinfo2");
    element.addAttribute("fontid", Integer.toString(tag.getFontId()));
    RecordXMLWriter.addAttributeWithCharCheck(element, "fontname", tag.getFontName());
    if (tag.isBold()) {
      element.addAttribute("bold", "true");
    }
    if (tag.isItalic()) {
      element.addAttribute("italic", "true");
    }
    if (tag.isSmallText()) {
      element.addAttribute("smalltext", "true");
    }
    writeLanguage(element, tag.getLangCode());
    char[] codeTable = tag.getCodeTable();
    for (int i = 0; i < codeTable.length; i++) {
      RecordXMLWriter.addCharAsTextNode(element.addElement("char"), codeTable[i]);
    }
  }
  
  private static void writeDefineFontName(Element parentElement, DefineFontName tag) {
    Element element = parentElement.addElement("definefontname");
    element.addAttribute("fontid", Integer.toString(tag.getFontId()));
    element.addAttribute("fontname", tag.getFontName());
    element.addAttribute("fontlicense", tag.getFontLicense());
  }

  private static void writeDefineMorphShape(
    Element parentElement, DefineMorphShape tag) {
    Element element = parentElement.addElement("definemorphshape");
    element.addAttribute("charid", Integer.toString(tag.getCharacterId()));
    Shape startShape                = tag.getStartShape();
    Shape endShape                  = tag.getEndShape();
    MorphLineStyles morphLineStyles = tag.getMorphLineStyles();
    MorphFillStyles morphFillStyles = tag.getMorphFillStyles();
    boolean zeroOffset              = ((startShape == null) ||
      (endShape == null) || (morphLineStyles == null) ||
      (morphFillStyles == null));
    Element startElement            = element.addElement("start");
    RecordXMLWriter.writeRect(startElement, "bounds", tag.getStartBounds());
    Element endElement = element.addElement("end");
    RecordXMLWriter.writeRect(endElement, "bounds", tag.getEndBounds());
    if (!zeroOffset) {
      RecordXMLWriter.writeShape(startElement, startShape);
      RecordXMLWriter.writeShape(endElement, endShape);
      RecordXMLWriter.writeMorphLineStyles(element, morphLineStyles);
      RecordXMLWriter.writeMorphFillStyles(element, morphFillStyles);
    }
  }

  private static void writeDefineMorphShape2(
    Element parentElement, DefineMorphShape2 tag) {
    Element element = parentElement.addElement("definemorphshape2");
    element.addAttribute("charid", Integer.toString(tag.getCharacterId()));
    Shape startShape                = tag.getStartShape();
    Shape endShape                  = tag.getEndShape();
    MorphLineStyles morphLineStyles = tag.getMorphLineStyles();
    MorphFillStyles morphFillStyles = tag.getMorphFillStyles();
    boolean zeroOffset              = ((startShape == null) ||
      (endShape == null) || (morphLineStyles == null) ||
      (morphFillStyles == null));
    Element startElement            = element.addElement("start");
    RecordXMLWriter.writeRect(
      startElement, "shapebounds", tag.getStartShapeBounds());
    RecordXMLWriter.writeRect(
      startElement, "edgebounds", tag.getStartEdgeBounds());
    Element endElement = element.addElement("end");
    RecordXMLWriter.writeRect(
      endElement, "shapebounds", tag.getEndShapeBounds());
    RecordXMLWriter.writeRect(endElement, "edgebounds", tag.getEndEdgeBounds());
    if (!zeroOffset) {
      RecordXMLWriter.writeShape(startElement, startShape);
      RecordXMLWriter.writeShape(endElement, endShape);
      RecordXMLWriter.writeMorphLineStyles(element, morphLineStyles);
      RecordXMLWriter.writeMorphFillStyles(element, morphFillStyles);
    }
  }
  
  private static void writeDefineSceneFrameData(Element parentElement, DefineSceneFrameData tag) {
    Element element = parentElement.addElement("definesceneframedata");
    List<SceneData> sceneEntries = tag.getSceneEntries();
    Element sceneEntriesElement = element.addElement("sceneentries");
    for (Iterator<SceneData> it = sceneEntries.iterator(); it.hasNext(); ) {
      SceneData sceneData = it.next();
      Element sceneDataElement = sceneEntriesElement.addElement("scenedata");
      sceneDataElement.addAttribute("frameoffset", Integer.toString(sceneData.getFrameOffset()));
      sceneDataElement.addAttribute("scenename", sceneData.getSceneName());
    }
    List<FrameData> frameEntries = tag.getFrameEntries();
    Element frameEntriesElement = element.addElement("frameentries");
    for (Iterator<FrameData> it = frameEntries.iterator(); it.hasNext(); ) {
      FrameData frameData = it.next();
      Element frameDataElement = frameEntriesElement.addElement("framedata");
      frameDataElement.addAttribute("framenumber", Integer.toString(frameData.getFrameNumber()));
      frameDataElement.addAttribute("framelabel", frameData.getFrameLabel());
    }
  }
  
  private static void writeDefineShape(Element parentElement, DefineShape tag) {
    Element element = parentElement.addElement("defineshape");
    element.addAttribute("charid", Integer.toString(tag.getCharacterId()));
    RecordXMLWriter.writeRect(element, "bounds", tag.getShapeBounds());
    RecordXMLWriter.writeShapeWithStyle(element, tag.getShapes());
  }

  private static void writeDefineShape2(
    Element parentElement, DefineShape2 tag) {
    Element element = parentElement.addElement("defineshape2");
    element.addAttribute("charid", Integer.toString(tag.getCharacterId()));
    RecordXMLWriter.writeRect(element, "bounds", tag.getShapeBounds());
    RecordXMLWriter.writeShapeWithStyle(element, tag.getShapes());
  }

  private static void writeDefineShape3(
    Element parentElement, DefineShape3 tag) {
    Element element = parentElement.addElement("defineshape3");
    element.addAttribute("charid", Integer.toString(tag.getCharacterId()));
    RecordXMLWriter.writeRect(element, "bounds", tag.getShapeBounds());
    RecordXMLWriter.writeShapeWithStyle(element, tag.getShapes());
  }

  private static void writeDefineShape4(
    Element parentElement, DefineShape4 tag) {
    Element element = parentElement.addElement("defineshape4");
    element.addAttribute("charid", Integer.toString(tag.getCharacterId()));
    RecordXMLWriter.writeRect(element, "shapebounds", tag.getShapeBounds());
    RecordXMLWriter.writeRect(element, "edgebounds", tag.getEdgeBounds());
    RecordXMLWriter.writeShapeWithStyle(element, tag.getShapes());
  }

  private static void writeDefineSound(Element parentElement, DefineSound tag) {
    Element element = parentElement.addElement("definesound");
    element.addAttribute("charid", Integer.toString(tag.getCharacterId()));
    element.addAttribute("format", getSoundFormatString(tag.getFormat()));
    element.addAttribute("rate", getSoundRateString(tag.getRate()));
    if (tag.is16BitSample()) {
      element.addAttribute("sample16bit", "true");
    }
    if (tag.isStereo()) {
      element.addAttribute("stereo", "true");
    }
    element.addAttribute("samplecount", Long.toString(tag.getSampleCount()));
    String data = XMLWriter.isOmitBinaryData() ? "" : Base64.encode(tag.getSoundData());
    element.addText(data);
  }

  private static void writeDefineSprite(
    Element parentElement, DefineSprite tag) {
    Element element = parentElement.addElement("definesprite");
    element.addAttribute("charid", Integer.toString(tag.getCharacterId()));
    for (Tag controlTag : tag.getControlTags()) {
      writeTag(element, controlTag);
    }
  }

  private static void writeDefineText(Element parentElement, DefineText tag) {
    Element element = parentElement.addElement("definetext");
    element.addAttribute("charid", Integer.toString(tag.getCharacterId()));
    RecordXMLWriter.writeRect(element, "bounds", tag.getTextBounds());
    RecordXMLWriter.writeMatrix(element, "matrix", tag.getTextMatrix());
    Element textRecordsElement = element.addElement("textrecords");
    TextRecord[] textRecords   = tag.getTextRecords();
    for (int i = 0; i < textRecords.length; i++) {
      RecordXMLWriter.writeTextRecord(textRecordsElement, textRecords[i]);
    }
  }

  private static void writeDefineText2(Element parentElement, DefineText2 tag) {
    Element element = parentElement.addElement("definetext2");
    element.addAttribute("charid", Integer.toString(tag.getCharacterId()));
    RecordXMLWriter.writeRect(element, "bounds", tag.getTextBounds());
    RecordXMLWriter.writeMatrix(element, "matrix", tag.getTextMatrix());
    Element textRecordsElement = element.addElement("textrecords");
    TextRecord[] textRecords   = tag.getTextRecords();
    for (int i = 0; i < textRecords.length; i++) {
      RecordXMLWriter.writeTextRecord(textRecordsElement, textRecords[i]);
    }
  }

  private static void writeDefineVideoStream(
    Element parentElement, DefineVideoStream tag) {
    Element element = parentElement.addElement("definevideostream");
    element.addAttribute("charid", Integer.toString(tag.getCharacterId()));
    element.addAttribute("numframes", Integer.toString(tag.getNumFrames()));
    element.addAttribute("width", Integer.toString(tag.getWidth()));
    element.addAttribute("height", Integer.toString(tag.getHeight()));
    switch (tag.getDeblocking()) {
      case DefineVideoStream.DEBLOCKING_OFF:
        element.addAttribute("deblocking", "off");
        break;
      case DefineVideoStream.DEBLOCKING_ON:
        element.addAttribute("deblocking", "on");
        break;
      case DefineVideoStream.DEBLOCKING_PACKET:
        element.addAttribute("deblocking", "packet");
        break;
      default:
        throw new IllegalArgumentException(
          "Illegal video deblocking setting: " + tag.getDeblocking());
    }
    if (tag.isSmoothing()) {
      element.addAttribute("smoothing", "true");
    }
    switch (tag.getCodecId()) {
      case DefineVideoStream.CODEC_SCREEN_VIDEO:
        element.addAttribute("codec", "screenvideo");
        break;
      case DefineVideoStream.CODEC_SORENSON_H263:
        element.addAttribute("codec", "h263");
        break;
      case DefineVideoStream.CODEC_VP6:
        element.addAttribute("codec", "vp6");
        break;
      case DefineVideoStream.CODEC_VP6_ALPHA:
        element.addAttribute("codec", "vp6alpha");
        break;
      case DefineVideoStream.CODEC_SCREEN_VIDEO_V2:
        element.addAttribute("codec", "screenvideov2");
        break;
      case DefineVideoStream.CODEC_UNDEFINED:
        element.addAttribute("codec", "undefined");
        break;
      default:
        throw new IllegalArgumentException(
          "Illegal video codec ID: " + tag.getCodecId());
    }
  }

  private static String abcFileToBase64EncodedString(AbcFile abcFile) {
    String encodedData = "";
    if (!XMLWriter.isOmitBinaryData()) {
      try {
        OutputBitStream out = new OutputBitStream();
        abcFile.write(out);
        encodedData = Base64.encode(out.getData());
      } catch (IOException e) {
        // Should never get this far, but just in case ...
        throw new RuntimeException("Failed trying write abcFile to temporary byte array: " + e.getMessage(), e);
      }
    }
    return encodedData;
  }
  
  private static void writeDoAbc(Element parentElement, DoAbc tag) {
    Element element = parentElement.addElement("doabc");
//    RecordXMLWriter.writeAbcFile(element, tag.getAbcFile());
    
    // Dump out the contents of the ABCfile as a Base64 encoded string so we can
    // rebuild the Swf from the Xml later.
    element.addText( abcFileToBase64EncodedString(tag.getAbcFile()) );
  }
  
  private static void writeDoAbcDefine(Element parentElement, DoAbcDefine tag) { 
    Element element = parentElement.addElement("doabcdefine");
    RecordXMLWriter.addAttributeWithCharCheck(element, "abcname", tag.getAbcName());
//    RecordXMLWriter.writeAbcFile(element, tag.getAbcFile());
    
    // Dump out the contents of the ABCfile as a Base64 encoded string so we can
    // rebuild the Swf from the Xml later.
    element.addText( abcFileToBase64EncodedString(tag.getAbcFile()) );
  }
  
  private static void writeDoAction(Element parentElement, DoAction tag) {
    Element element = parentElement.addElement("doaction");
    RecordXMLWriter.writeActionBlock(element, tag.getActions());
  }

  private static void writeDoInitAction(
    Element parentElement, DoInitAction tag) {
    Element element = parentElement.addElement("doinitaction");
    element.addAttribute("spriteid", Integer.toString(tag.getSpriteId()));
    RecordXMLWriter.writeActionBlock(element, tag.getInitActions());
  }

  private static void writeEnableDebugger(
    Element parentElement, EnableDebugger tag) {
    Element element = parentElement.addElement("enabledebugger");
    String password = tag.getPassword();
    if (password != null) {
      RecordXMLWriter.addAttributeWithCharCheck(element, "password", password);
    }
  }

  private static void writeEnableDebugger2(
    Element parentElement, EnableDebugger2 tag) {
    Element element = parentElement.addElement("enabledebugger2");
    String password = tag.getPassword();
    if (password != null) {
      RecordXMLWriter.addAttributeWithCharCheck(element, "password", password);
    }
  }

  private static void writeExportAssets(
    Element parentElement, ExportAssets tag) {
    Element element                       = parentElement.addElement(
        "exportassets");
    ExportAssets.ExportMapping[] mappings = tag.getExportMappings();
    for (int i = 0; i < mappings.length; i++) {
      ExportAssets.ExportMapping mapping = mappings[i];
      Element mappingElement             = element.addElement("exportmapping");
      mappingElement.addAttribute(
        "charid", Integer.toString(mapping.getCharacterId()));
      RecordXMLWriter.addAttributeWithCharCheck(mappingElement, "name", mapping.getName());
    }
  }

  private static void writeFlashTypeSettings(
    Element parentElement, FlashTypeSettings tag) {
    Element element = parentElement.addElement("flashtypesettings");
    element.addAttribute("textid", Integer.toString(tag.getTextId()));
    element.addAttribute("flashtype", Boolean.toString(tag.isFlashType()));
    switch (tag.getGridFit()) {
      case FlashTypeSettings.GRID_FIT_NONE:
        element.addAttribute("gridfit", "none");
        break;
      case FlashTypeSettings.GRID_FIT_PIXEL:
        element.addAttribute("gridfit", "pixel");
        break;
      case FlashTypeSettings.GRID_FIT_SUBPIXEL:
        element.addAttribute("gridfit", "subpixel");
        break;
    }
    element.addAttribute(
      "thickness", StringUtilities.doubleToString(tag.getThickness()));
    element.addAttribute(
      "sharpness", StringUtilities.doubleToString(tag.getSharpness()));
  }

  private static void writeFrameLabel(Element parentElement, FrameLabel tag) {
    Element element = parentElement.addElement("framelabel");
    RecordXMLWriter.addAttributeWithCharCheck(element, "name", tag.getName());
    if (tag.isNamedAnchor()) {
      element.addAttribute("namedanchor", "true");
    }
  }

  private static void writeFreeCharacter(
    Element parentElement, FreeCharacter tag) {
    Element element = parentElement.addElement("freecharacter");
    element.addAttribute("charid", Integer.toString(tag.getCharacterId()));
  }

  private static void writeImportAssets(
    Element parentElement, ImportAssets tag) {
    Element element = parentElement.addElement("importassets");
    RecordXMLWriter.addAttributeWithCharCheck(element, "url", tag.getUrl());
    ImportAssets.ImportMapping[] mappings = tag.getImportMappings();
    for (int i = 0; i < mappings.length; i++) {
      ImportAssets.ImportMapping mapping = mappings[i];
      Element mappingElement             = element.addElement("importmapping");
      RecordXMLWriter.addAttributeWithCharCheck(mappingElement, "name", mapping.getName());
      mappingElement.addAttribute(
        "charid", Integer.toString(mapping.getCharacterId()));
    }
  }

  private static void writeImportAssets2(
    Element parentElement, ImportAssets2 tag) {
    Element element = parentElement.addElement("importassets2");
    RecordXMLWriter.addAttributeWithCharCheck(element, "url", tag.getUrl());
    ImportAssets.ImportMapping[] mappings = tag.getImportMappings();
    for (int i = 0; i < mappings.length; i++) {
      ImportAssets.ImportMapping mapping = mappings[i];
      Element mappingElement             = element.addElement("importmapping");
      RecordXMLWriter.addAttributeWithCharCheck(mappingElement, "name", mapping.getName());
      mappingElement.addAttribute(
        "charid", Integer.toString(mapping.getCharacterId()));
    }
  }

  private static void writeJPEGTables(Element parentElement, JPEGTables tag) {
    Element element = parentElement.addElement("jpegtables");
    String data = XMLWriter.isOmitBinaryData() ? "" : Base64.encode(tag.getJpegData());
    element.addElement("jpegdata").addText(data);
  }

  private static void writeLanguage(Element parentElement, LangCode langCode) {
    if (langCode != null) {
      parentElement.addAttribute("language", langCode.toString());
    }
  }

  private static void writeMalformedTag(
    Element parentElement, MalformedTag tag) {
    Element element = parentElement.addElement("malformedtag");
    element.addAttribute("code", Integer.toString(tag.tagCode()));
    Exception exception      = tag.getException();
    Element exceptionElement = element.addElement("exception");
    exceptionElement.addAttribute("class", exception.getClass().getName());
    exceptionElement.addAttribute("message", exception.toString());
    String data = XMLWriter.isOmitBinaryData() ? "" : Base64.encode(tag.getData());
    element.addElement("data").addText(data);
  }

  private static void writePlaceObject(Element parentElement, PlaceObject tag) {
    Element element = parentElement.addElement("placeobject");
    element.addAttribute("charid", Integer.toString(tag.getCharacterId()));
    element.addAttribute("depth", Integer.toString(tag.getDepth()));
    RecordXMLWriter.writeMatrix(element, tag.getMatrix());
    CXform colorTransform = tag.getColorTransform();
    if (colorTransform != null) {
      RecordXMLWriter.writeCXForm(element, colorTransform);
    }
  }

  private static void writePlaceObject2(
    Element parentElement, PlaceObject2 tag) {
    Element element = parentElement.addElement("placeobject2");
    element.addAttribute("depth", Integer.toString(tag.getDepth()));
    if (tag.hasCharacter()) {
      element.addAttribute("charid", Integer.toString(tag.getCharacterId()));
    }
    if (tag.hasName()) {
      RecordXMLWriter.addAttributeWithCharCheck(element, "name", tag.getName());
    }
    if (tag.isMove()) {
      element.addAttribute("move", "true");
    }
    if (tag.hasMatrix()) {
      RecordXMLWriter.writeMatrix(element, tag.getMatrix());
    }
    if (tag.hasColorTransform()) {
      RecordXMLWriter.writeCXFormWithAlpha(element, tag.getColorTransform());
    }
    if (tag.hasRatio()) {
      element.addAttribute("ratio", Integer.toString(tag.getRatio()));
    }
    if (tag.hasClipDepth()) {
      element.addAttribute("clipdepth", Integer.toString(tag.getClipDepth()));
    }
    if (tag.hasClipActions()) {
      RecordXMLWriter.writeClipActions(element, tag.getClipActions());
    }
  }

  private static void writePlaceObject3(
    Element parentElement, PlaceObject3 tag) {
    Element element = parentElement.addElement("placeobject3");
    element.addAttribute("depth", Integer.toString(tag.getDepth()));
    if (tag.hasCharacter()) {
      element.addAttribute("charid", Integer.toString(tag.getCharacterId()));
    }
    if (tag.hasName()) {
      RecordXMLWriter.addAttributeWithCharCheck(element, "name", tag.getName());
    }
    if (tag.hasImage()) {
      element.addAttribute("hasimage", "true");
    }
    if (tag.hasClassName()) {
      element.addAttribute("hasclassname", "true");
    }
    String className = tag.getClassName();
    if (className != null) {
      RecordXMLWriter.addAttributeWithCharCheck(element, "classname", className);
    }
    if (tag.isMove()) {
      element.addAttribute("move", "true");
    }
    if (tag.hasMatrix()) {
      RecordXMLWriter.writeMatrix(element, tag.getMatrix());
    }
    if (tag.hasColorTransform()) {
      RecordXMLWriter.writeCXFormWithAlpha(element, tag.getColorTransform());
    }
    if (tag.hasRatio()) {
      element.addAttribute("ratio", Integer.toString(tag.getRatio()));
    }
    if (tag.hasClipDepth()) {
      element.addAttribute("clipdepth", Integer.toString(tag.getClipDepth()));
    }
    if (tag.hasClipActions()) {
      RecordXMLWriter.writeClipActions(element, tag.getClipActions());
    }
    if (tag.hasBlendMode()) {
      element.addAttribute("blendmode", tag.getBlendMode().toString());
    }
    if (tag.isCacheAsBitmap()) {
      element.addAttribute("cacheasbitmap", "true");
    }
    if (tag.hasFilters()) {
      RecordXMLWriter.writeFilters(element, tag.getFilters());
    }
  }

  private static void writeProductInfo(Element parentElement, ProductInfo tag) {
    Element element = parentElement.addElement("productinfo");
    element.addAttribute("productid", Integer.toString(tag.getProductId()));
    element.addAttribute("edition", Integer.toString(tag.getEdition()));
    element.addAttribute("majorversion", Short.toString(tag.getMajorVersion()));
    element.addAttribute("minorversion", Short.toString(tag.getMinorVersion()));
    element.addAttribute("buildnumber", Long.toString(tag.getBuildNumber()));
    element.addAttribute("builddate", StringUtilities.dateToString(tag.getBuildDate()));
  }
  
  private static void writeProtect(Element parentElement, Protect tag) {
    Element element = parentElement.addElement("protect");
    String password = tag.getPassword();
    if (password != null) {
      element.addAttribute("password", tag.getPassword());
    }
  }

  private static void writeRemoveObject(
    Element parentElement, RemoveObject tag) {
    Element element = parentElement.addElement("removeobject");
    element.addAttribute("charid", Integer.toString(tag.getCharacterId()));
    element.addAttribute("depth", Integer.toString(tag.getDepth()));
  }

  private static void writeRemoveObject2(
    Element parentElement, RemoveObject2 tag) {
    Element element = parentElement.addElement("removeobject2");
    element.addAttribute("depth", Integer.toString(tag.getDepth()));
  }

  private static void writeScale9Grid(Element parentElement, Scale9Grid tag) {
    Element element = parentElement.addElement("scale9grid");
    element.addAttribute("charid", Integer.toString(tag.getCharacterId()));
    RecordXMLWriter.writeRect(element, "grid", tag.getGrid());
  }

  private static void writeScriptLimits(
    Element parentElement, ScriptLimits tag) {
    Element element = parentElement.addElement("scriptlimits");
    element.addAttribute(
      "maxrecursiondepth", Integer.toString(tag.getMaxRecursionDepth()));
    element.addAttribute(
      "scripttimeout", Integer.toString(tag.getScriptTimeoutSeconds()));
  }

  private static void writeSetTabIndex(Element parentElement, SetTabIndex tag) {
    Element element = parentElement.addElement("settabindex");
    element.addAttribute("depth", Integer.toString(tag.getDepth()));
    element.addAttribute("tabindex", Integer.toString(tag.getTabIndex()));
  }

  private static void writeShowFrame(Element parentElement, ShowFrame tag) {
    parentElement.addElement("showframe");
  }

  private static void writeSoundStreamBlock(
    Element parentElement, SoundStreamBlock tag) {
    Element element = parentElement.addElement("soundstreamblock");
    String data = XMLWriter.isOmitBinaryData() ? "" : Base64.encode(tag.getStreamSoundData());
    element.addElement("streamsounddata").addText(data);
  }

  private static void writeSoundStreamHead(
    Element parentElement, SoundStreamHead tag) {
    Element element = parentElement.addElement("soundstreamhead");
    element.addAttribute(
      "streamformat", getSoundFormatString(tag.getStreamFormat()));
    element.addAttribute("streamrate", getSoundRateString(tag.getStreamRate()));
    if (tag.isStreamStereo()) {
      element.addAttribute("streamstereo", "true");
    }
    element.addAttribute(
      "streamsamplecount", Integer.toString(tag.getStreamSampleCount()));
    element.addAttribute(
      "playbackrate", getSoundRateString(tag.getPlaybackRate()));
    if (tag.isPlaybackStereo()) {
      element.addAttribute("playbackstereo", "true");
    }
    if (tag.getStreamFormat() == SoundStreamHead.FORMAT_MP3) {
      element.addAttribute("latencyseek", Short.toString(tag.getLatencySeek()));
    }
  }

  private static void writeSoundStreamHead2(
    Element parentElement, SoundStreamHead2 tag) {
    Element element = parentElement.addElement("soundstreamhead2");
    element.addAttribute(
      "streamformat", getSoundFormatString(tag.getStreamFormat()));
    element.addAttribute("streamrate", getSoundRateString(tag.getStreamRate()));
    if (tag.isStream16BitSample()) {
      element.addAttribute("streamsample16bit", "true");
    }
    if (tag.isStreamStereo()) {
      element.addAttribute("streamstereo", "true");
    }
    element.addAttribute(
      "streamsamplecount", Integer.toString(tag.getStreamSampleCount()));
    element.addAttribute(
      "playbackrate", getSoundRateString(tag.getPlaybackRate()));
    if (tag.isPlayback16BitSample()) {
      element.addAttribute("playbacksample16bit", "true");
    }
    if (tag.isPlaybackStereo()) {
      element.addAttribute("playbackstereo", "true");
    }
    if (tag.getStreamFormat() == SoundStreamHead.FORMAT_MP3) {
      element.addAttribute("latencyseek", Short.toString(tag.getLatencySeek()));
    }
  }

  private static void writeStartSound(Element parentElement, StartSound tag) {
    Element element = parentElement.addElement("startsound");
    element.addAttribute("soundid", Integer.toString(tag.getSoundId()));
    RecordXMLWriter.writeSoundInfo(element, tag.getSoundInfo());
  }

  private static void writeSymbolClass(
    Element parentElement, SymbolClass tag) {
    Element element = parentElement.addElement("symbolclass");
    List<SymbolReference> references = tag.getReferences();
    for (Iterator<SymbolReference> it = references.iterator(); it.hasNext(); ) {
      SymbolReference symbolReference = it.next();
      Element referenceElement = element.addElement("symbolreference");
      referenceElement.addAttribute("charid", Integer.toString(symbolReference.getCharacterId()));
      RecordXMLWriter.addAttributeWithCharCheck(referenceElement, "name", symbolReference.getName());
    }
  }

  private static void writeUnknownTag(Element parentElement, UnknownTag tag) {
    Element element = parentElement.addElement("unknowntag");
    element.addAttribute("code", Integer.toString(tag.tagCode()));
    String data = XMLWriter.isOmitBinaryData() ? "" : Base64.encode(tag.getData());
    element.addText(data);
  }

  private static void writeVideoFrame(Element parentElement, VideoFrame tag) {
    Element element = parentElement.addElement("videoframe");
    element.addAttribute("streamid", Integer.toString(tag.getStreamId()));
    element.addAttribute("framenum", Integer.toString(tag.getFrameNum()));
    String data = XMLWriter.isOmitBinaryData() ? "" : Base64.encode(tag.getVideoData());
    element.addElement("videodata").addText(data);
  }
}

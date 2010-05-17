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

package com.jswiff.investigator;

import java.util.Iterator;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import com.jswiff.constants.TagConstants;
import com.jswiff.constants.TagConstants.JointStyle;
import com.jswiff.constants.TagConstants.TagType;
import com.jswiff.exception.InvalidCodeException;
import com.jswiff.swfrecords.AlignmentZone;
import com.jswiff.swfrecords.AlphaBitmapData;
import com.jswiff.swfrecords.AlphaColorMapData;
import com.jswiff.swfrecords.BevelFilter;
import com.jswiff.swfrecords.BitmapData;
import com.jswiff.swfrecords.BlurFilter;
import com.jswiff.swfrecords.ButtonCondAction;
import com.jswiff.swfrecords.ButtonRecord;
import com.jswiff.swfrecords.CXform;
import com.jswiff.swfrecords.CXformWithAlpha;
import com.jswiff.swfrecords.ClipActionRecord;
import com.jswiff.swfrecords.ClipActions;
import com.jswiff.swfrecords.ClipEventFlags;
import com.jswiff.swfrecords.ColorMapData;
import com.jswiff.swfrecords.ColorMatrixFilter;
import com.jswiff.swfrecords.ConvolutionFilter;
import com.jswiff.swfrecords.CurvedEdgeRecord;
import com.jswiff.swfrecords.DropShadowFilter;
import com.jswiff.swfrecords.FillStyle;
import com.jswiff.swfrecords.FillStyleArray;
import com.jswiff.swfrecords.Filter;
import com.jswiff.swfrecords.FocalGradient;
import com.jswiff.swfrecords.FocalMorphGradient;
import com.jswiff.swfrecords.FrameData;
import com.jswiff.swfrecords.GlowFilter;
import com.jswiff.swfrecords.GlyphEntry;
import com.jswiff.swfrecords.GradRecord;
import com.jswiff.swfrecords.Gradient;
import com.jswiff.swfrecords.GradientBevelFilter;
import com.jswiff.swfrecords.GradientGlowFilter;
import com.jswiff.swfrecords.KerningRecord;
import com.jswiff.swfrecords.LineStyle;
import com.jswiff.swfrecords.LineStyle2;
import com.jswiff.swfrecords.LineStyleArray;
import com.jswiff.swfrecords.MorphFillStyle;
import com.jswiff.swfrecords.MorphFillStyles;
import com.jswiff.swfrecords.MorphGradRecord;
import com.jswiff.swfrecords.MorphGradient;
import com.jswiff.swfrecords.MorphLineStyle;
import com.jswiff.swfrecords.MorphLineStyle2;
import com.jswiff.swfrecords.MorphLineStyles;
import com.jswiff.swfrecords.RGB;
import com.jswiff.swfrecords.RGBA;
import com.jswiff.swfrecords.Rect;
import com.jswiff.swfrecords.RegisterParam;
import com.jswiff.swfrecords.SWFHeader;
import com.jswiff.swfrecords.SceneData;
import com.jswiff.swfrecords.Shape;
import com.jswiff.swfrecords.ShapeRecord;
import com.jswiff.swfrecords.ShapeWithStyle;
import com.jswiff.swfrecords.SoundEnvelope;
import com.jswiff.swfrecords.SoundInfo;
import com.jswiff.swfrecords.StraightEdgeRecord;
import com.jswiff.swfrecords.StyleChangeRecord;
import com.jswiff.swfrecords.TextRecord;
import com.jswiff.swfrecords.abc.AbcConstantPool;
import com.jswiff.swfrecords.abc.AbcFile;
import com.jswiff.swfrecords.abc.AbcNamespace;
import com.jswiff.swfrecords.abc.AbcNamespaceSet;
import com.jswiff.swfrecords.actions.Action;
import com.jswiff.swfrecords.actions.ActionBlock;
import com.jswiff.swfrecords.actions.ConstantPool;
import com.jswiff.swfrecords.actions.DefineFunction;
import com.jswiff.swfrecords.actions.DefineFunction2;
import com.jswiff.swfrecords.actions.If;
import com.jswiff.swfrecords.actions.Jump;
import com.jswiff.swfrecords.actions.Push;
import com.jswiff.swfrecords.actions.Try;
import com.jswiff.swfrecords.actions.With;
import com.jswiff.swfrecords.actions.Push.StackValue;
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
import com.jswiff.swfrecords.tags.FileAttributes;
import com.jswiff.swfrecords.tags.FlashTypeSettings;
import com.jswiff.swfrecords.tags.FrameLabel;
import com.jswiff.swfrecords.tags.ImportAssets;
import com.jswiff.swfrecords.tags.ImportAssets2;
import com.jswiff.swfrecords.tags.JPEGTables;
import com.jswiff.swfrecords.tags.MalformedTag;
import com.jswiff.swfrecords.tags.Metadata;
import com.jswiff.swfrecords.tags.PlaceObject;
import com.jswiff.swfrecords.tags.PlaceObject2;
import com.jswiff.swfrecords.tags.PlaceObject3;
import com.jswiff.swfrecords.tags.ProductInfo;
import com.jswiff.swfrecords.tags.Protect;
import com.jswiff.swfrecords.tags.RemoveObject;
import com.jswiff.swfrecords.tags.RemoveObject2;
import com.jswiff.swfrecords.tags.Scale9Grid;
import com.jswiff.swfrecords.tags.ScriptLimits;
import com.jswiff.swfrecords.tags.SetBackgroundColor;
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
import com.jswiff.swfrecords.tags.ExportAssets.ExportMapping;
import com.jswiff.swfrecords.tags.ImportAssets.ImportMapping;
import com.jswiff.swfrecords.tags.SymbolClass.SymbolReference;
import com.jswiff.util.HexUtils;


/*
 * This class is used to build the model for the SWF tree representation.
 */
final class SWFTreeBuilder {
  private static int nodes;
  private static List<String> constants;

  @SuppressWarnings("unchecked")
  private static String getFriendlyConstantName(Enum e) {
    return getFriendlyConstantName(e, "_");
  }

  @SuppressWarnings("unchecked")
  private static String getFriendlyConstantName(Enum e, String r) {
    String val = "";
    String[] parts = e.name().split(r);
    for (int i = 0; i < parts.length; i++) {
      val = val.concat( parts[i].substring(0, 1).toUpperCase() + parts[i].substring(1).toLowerCase() );
      if (i < parts.length - 1) val = val.concat(" ");
    }
    return val;
  }

  static void setNodes(int nodes) {
    SWFTreeBuilder.nodes = nodes;
  }

  static int getNodes() {
    return nodes;
  }

  static void addNode(DefaultMutableTreeNode node, Tag tag) {
    switch (tag.tagType()) {
    case DEBUG_ID:
      addNode(node, (DebugId) tag);
      break;
    case DEFINE_BINARY_DATA:
      addNode(node, (DefineBinaryData) tag);
      break;
    case DEFINE_BITS:
      addNode(node, (DefineBits) tag);
      break;
    case DEFINE_BITS_JPEG_2:
      addNode(node, (DefineBitsJPEG2) tag);
      break;
    case DEFINE_BITS_JPEG_3:
      addNode(node, (DefineBitsJPEG3) tag);
      break;
    case DEFINE_BITS_LOSSLESS:
      addNode(node, (DefineBitsLossless) tag);
      break;
    case DEFINE_BITS_LOSSLESS_2:
      addNode(node, (DefineBitsLossless2) tag);
      break;
    case DEFINE_BUTTON:
      addNode(node, (DefineButton) tag);
      break;
    case DEFINE_BUTTON_2:
      addNode(node, (DefineButton2) tag);
      break;
    case DEFINE_BUTTON_C_XFORM:
      addNode(node, (DefineButtonCXform) tag);
      break;
    case DEFINE_BUTTON_SOUND:
      addNode(node, (DefineButtonSound) tag);
      break;
    case DEFINE_EDIT_TEXT:
      addNode(node, (DefineEditText) tag);
      break;
    case DEFINE_FONT:
      addNode(node, (DefineFont) tag);
      break;
    case DEFINE_FONT_2:
      addNode(node, (DefineFont2) tag);
      break;
    case DEFINE_FONT_3:
      addNode(node, (DefineFont3) tag);
      break;
    case DEFINE_FONT_INFO:
      addNode(node, (DefineFontInfo) tag);
      break;
    case DEFINE_FONT_INFO_2:
      addNode(node, (DefineFontInfo2) tag);
      break;
    case DEFINE_FONT_NAME:
      addNode(node, (DefineFontName) tag);
      break;
    case FLASHTYPE_SETTINGS:
      addNode(node, (FlashTypeSettings) tag);
      break;
    case DEFINE_FONT_ALIGNMENT:
      addNode(node, (DefineFontAlignment) tag);
      break;
    case DEFINE_MORPH_SHAPE:
      addNode(node, (DefineMorphShape) tag);
      break;
    case DEFINE_MORPH_SHAPE_2:
      addNode(node, (DefineMorphShape2) tag);
      break;
    case DEFINE_SCENE_FRAME_DATA:
      addNode(node, (DefineSceneFrameData) tag);
      break;
    case DEFINE_SHAPE:
      addNode(node, (DefineShape) tag);
      break;
    case DEFINE_SHAPE_2:
      addNode(node, (DefineShape2) tag);
      break;
    case DEFINE_SHAPE_3:
      addNode(node, (DefineShape3) tag);
      break;
    case DEFINE_SHAPE_4:
      addNode(node, (DefineShape4) tag);
      break;
    case DEFINE_SOUND:
      addNode(node, (DefineSound) tag);
      break;
    case DEFINE_SPRITE:
      addNode(node, (DefineSprite) tag);
      break;
    case DEFINE_TEXT:
      addNode(node, (DefineText) tag);
      break;
    case DEFINE_TEXT_2:
      addNode(node, (DefineText2) tag);
      break;
    case DEFINE_VIDEO_STREAM:
      addNode(node, (DefineVideoStream) tag);
      break;
    case DO_ABC:
      addNode(node, (DoAbc) tag);
      break;
    case DO_ABC_DEFINE:
      addNode(node, (DoAbcDefine) tag);
      break;
    case DO_ACTION:
      addNode(node, (DoAction) tag);
      break;
    case DO_INIT_ACTION:
      addNode(node, (DoInitAction) tag);
      break;
    case ENABLE_DEBUGGER_2:
      addNode(node, (EnableDebugger2) tag);
      break;
    case ENABLE_DEBUGGER:
      addNode(node, (EnableDebugger) tag);
      break;
    case EXPORT_ASSETS:
      addNode(node, (ExportAssets) tag);
      break;
    case FILE_ATTRIBUTES:
      addNode(node, (FileAttributes) tag);
      break;
    case FRAME_LABEL:
      addNode(node, (FrameLabel) tag);
      break;
    case IMPORT_ASSETS:
    case IMPORT_ASSETS_2:
      addNode(node, (ImportAssets) tag);
      break;
    case JPEG_TABLES:
      addNode(node, (JPEGTables) tag);
      break;
    case METADATA:
      addNode(node, (Metadata) tag);
      break;
    case PLACE_OBJECT:
      addNode(node, (PlaceObject) tag);
      break;
    case PLACE_OBJECT_2:
      addNode(node, (PlaceObject2) tag);
      break;
    case PLACE_OBJECT_3:
      addNode(node, (PlaceObject3) tag);
      break;
    case PRODUCT_INFO:
      addNode(node, (ProductInfo) tag);
      break;
    case PROTECT:
      addNode(node, (Protect) tag);
      break;
    case REMOVE_OBJECT:
      addNode(node, (RemoveObject) tag);
      break;
    case REMOVE_OBJECT_2:
      addNode(node, (RemoveObject2) tag);
      break;
    case SCRIPT_LIMITS:
      addNode(node, (ScriptLimits) tag);
      break;
    case SET_BACKGROUND_COLOR:
      addNode(node, (SetBackgroundColor) tag);
      break;
    case SET_TAB_INDEX:
      addNode(node, (SetTabIndex) tag);
      break;
    case SHOW_FRAME:
      addNode(node, (ShowFrame) tag);
      break;
    case SCALE_9_GRID:
      addNode(node, (Scale9Grid) tag);
      break;
    case SOUND_STREAM_BLOCK:
      addNode(node, (SoundStreamBlock) tag);
      break;
    case SOUND_STREAM_HEAD:
      addNode(node, (SoundStreamHead) tag);
      break;
    case SOUND_STREAM_HEAD_2:
      addNode(node, (SoundStreamHead2) tag);
      break;
    case START_SOUND:
      addNode(node, (StartSound) tag);
      break;
    case SYMBOL_CLASS:
      addNode(node, (SymbolClass) tag);
      break;
    case VIDEO_FRAME:
      addNode(node, (VideoFrame) tag);
      break;
    case MALFORMED:
      addNode(node, (MalformedTag) tag);
      break;
    case UNKNOWN_TAG:
      addNode(node, (UnknownTag) tag);
      break;
    default:
      throw new AssertionError("Tag type '" + tag.tagType().name() + "' not handled!");
    }
  }

  static void addNode(DefaultMutableTreeNode node, SWFHeader header) {
    DefaultMutableTreeNode headerNode = addParentNode(
        node, "<html><id:1xf><font color=\"#00A000\">SWF Header</html>");
    addLeaf(headerNode, "compressed: " + header.isCompressed());
    addLeaf(headerNode, "version: " + header.getVersion());
    addLeaf(headerNode, "fileLength: " + header.getFileLength());
    addLeaf(headerNode, "frameSize: " + header.getFrameSize());
    addLeaf(headerNode, "frameRate: " + header.getFrameRate());
    addLeaf(headerNode, "frameCount: " + header.getFrameCount());
    // addLeaf(node, " ");
  }

  private static String getGridFitString(byte gridFit) {
    switch (gridFit) {
    case FlashTypeSettings.GRID_FIT_NONE:
      return "none";
    case FlashTypeSettings.GRID_FIT_PIXEL:
      return "pixel";
    case FlashTypeSettings.GRID_FIT_SUBPIXEL:
      return "subpixel";
    default:
      return "unknown value: " + gridFit;
    }
  }

  private static String getPushDescription(Push push) {
    String result = "Push";
    for (StackValue value : push.getValues()) {
      switch (value.getType()) {
      case CONSTANT_8:
        result += (" c8[" + value.valueString() + "]: '" + constants.get((Integer)value.getValue()) + "'");
        break;
      case CONSTANT_16:
        result += (" c8[" + value.valueString() + "]: '" + constants.get((Integer)value.getValue()) + "'");
        break;
      case UNDEFINED:
        result += " <b>undefined</b>";
        break;
      case NULL:
        result += " <b>null</b>";
        break;
      default :
        result += " " + value.toString();
      }
      result += ";";
    }
    return result;
  }

  private static String getSoundFormatString(byte format) {
    String result = null;
    switch (format) {
    case SoundStreamHead2.FORMAT_ADPCM:
      result = "ADPCM";
      break;
    case SoundStreamHead2.FORMAT_MP3:
      result = "mp3";
      break;
    case SoundStreamHead2.FORMAT_NELLYMOSER:
      result = "Nellymoser";
      break;
    case SoundStreamHead2.FORMAT_UNCOMPRESSED:
      result = "uncompressed";
      break;
    case SoundStreamHead2.FORMAT_UNCOMPRESSED_LITTLE_ENDIAN:
      result = "uncompresed little-endian";
      break;
    default:
      result = "unknown";
    }
    return result;
  }

  private static String getSoundRateString(byte rate) {
    String result = null;
    switch (rate) {
    case SoundStreamHead.RATE_5500_HZ:
      result = "5.5 kHz";
      break;
    case SoundStreamHead.RATE_11000_HZ:
      result = "11 kHz";
      break;
    case SoundStreamHead.RATE_22000_HZ:
      result = "22 kHz";
      break;
    case SoundStreamHead.RATE_44000_HZ:
      result = "44 kHz";
      break;
    default:
      result = "unknown";
    }
    return result;
  }

  private static String getThicknessString(byte thickness) {
    switch (thickness) {
    case DefineFontAlignment.THIN:
      return "thin";
    case DefineFontAlignment.MEDIUM:
      return "medium";
    case DefineFontAlignment.THICK:
      return "thick";
    default:
      return "unknown value: " + thickness;
    }
  }

  private static void addLeaf(DefaultMutableTreeNode node, String string) {
    node.insert(new DefaultMutableTreeNode(string), node.getChildCount());
    nodes++;
  }

  private static void addNode(DefaultMutableTreeNode node, MalformedTag tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node,
    "<html><body bgcolor=\"#FF0000\"><font color=\"#FFFFFF\">Malformed tag</font></body></html>");
    TagType t;
    try {
      t = TagType.lookup(tag.getTagHeader().getCode());
      addLeaf(tagNode, "code: " + t.getCode() + " (" + t.toString() + ")");
    } catch (InvalidCodeException e) {
      addLeaf(tagNode, "INVALID CODE: " + tag.getTagHeader().getCode());
    };
    addLeaf(tagNode, "data size: " + tag.getTagHeader().getLength() + " bytes");
    addLeaf(tagNode, "error: " + tag.getException().getMessage());
  }

  private static void addNode(DefaultMutableTreeNode node, DefineBits tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatDefTag("DefineBits"));
    addLeaf(tagNode, "characterId: " + tag.getCharacterId());
    addLeaf(tagNode, "jpegData: " + " byte[" + tag.getJpegData().length + "]");
  }

  private static void addNode(DefaultMutableTreeNode node, DefineBitsJPEG2 tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatDefTag("DefineBitsJPEG2"));
    addLeaf(tagNode, "characterId: " + tag.getCharacterId());
    addLeaf(tagNode, "jpegData: " + " byte[" + tag.getJpegData().length + "]");
  }

  private static void addNode(DefaultMutableTreeNode node, DefineBitsJPEG3 tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatDefTag("DefineBitsJPEG3"));
    addLeaf(tagNode, "characterId: " + tag.getCharacterId());
    addLeaf(tagNode, "jpegData: " + " byte[" + tag.getJpegData().length + "]");
    addLeaf(
        tagNode,
        "bitmapAlphaData: " + " byte[" + tag.getBitmapAlphaData().length + "]");
  }

  private static void addNode(
      DefaultMutableTreeNode node, DefineBitsLossless tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatDefTag("DefineBitsLossless"));
    addLeaf(tagNode, "characterId: " + tag.getCharacterId());
    short format        = tag.getFormat();
    String formatString = "";
    switch (format) {
    case DefineBitsLossless.FORMAT_8_BIT_COLORMAPPED:
      formatString = "8-bit colormapped image";
      break;
    case DefineBitsLossless.FORMAT_15_BIT_RGB:
      formatString = "15-bit RGB image";
      break;
    case DefineBitsLossless.FORMAT_24_BIT_RGB:
      formatString = "24-bit RGB image";
      break;
    }
    addLeaf(tagNode, "format: " + formatString);
    addLeaf(tagNode, "width: " + tag.getWidth());
    addLeaf(tagNode, "height: " + tag.getHeight());
    if (format == DefineBitsLossless.FORMAT_8_BIT_COLORMAPPED) {
      addNode(
          tagNode, "zlibBitmapData: ", (ColorMapData) tag.getZlibBitmapData());
    } else {
      addNode(
          tagNode, "zlibBitmapData: ", (BitmapData) tag.getZlibBitmapData(),
          format);
    }
  }

  private static void addNode(
      DefaultMutableTreeNode node, String var, BitmapData data, short format) {
    DefaultMutableTreeNode newNode = addParentNode(node, var + "BitmapData");
    if (format == DefineBitsLossless.FORMAT_15_BIT_RGB) {
      addLeaf(
          newNode,
          "bitmapPixelData: Pix15[" + data.getBitmapPixelData().length + "]");
    } else {
      addLeaf(
          newNode,
          "bitmapPixelData: Pix24[" + data.getBitmapPixelData().length + "]");
    }
  }

  private static void addNode(
      DefaultMutableTreeNode node, String var, ColorMapData data) {
    DefaultMutableTreeNode newNode        = addParentNode(
        node, var + "ColorMapData");
    RGB[] colorTable                      = data.getColorTableRGB();
    DefaultMutableTreeNode colorTableNode = addParentNode(
        newNode, "colorTableRGB: RGB[" + colorTable.length + "]");
    for (int i = 0; i < colorTable.length; i++) {
      addLeaf(colorTableNode, colorTable[i].toString());
    }
    addLeaf(
        newNode,
        "colorMapPixelData: short[" + data.getColorMapPixelData().length + "]");
  }

  private static void addNode(
      DefaultMutableTreeNode node, String var, AlphaColorMapData data) {
    DefaultMutableTreeNode newNode        = addParentNode(
        node, var + "AlphaColorMapData");
    RGBA[] colorTable                     = data.getColorTableRGBA();
    DefaultMutableTreeNode colorTableNode = addParentNode(
        newNode, "colorTableRGBA: RGBA[" + colorTable.length + "]");
    for (int i = 0; i < colorTable.length; i++) {
      addLeaf(colorTableNode, i + ": " + colorTable[i].toString());
    }
    addLeaf(
        newNode,
        "colorMapPixelData: short[" + data.getColorMapPixelData().length + "]");
  }

  private static void addNode(
      DefaultMutableTreeNode node, DefineBitsLossless2 tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatDefTag("DefineBitsLossless2"));
    addLeaf(tagNode, "characterId: " + tag.getCharacterId());
    short format        = tag.getFormat();
    String formatString = "";
    switch (format) {
    case DefineBitsLossless2.FORMAT_8_BIT_COLORMAPPED:
      formatString = "8-bit colormapped image";
      break;
    case DefineBitsLossless2.FORMAT_32_BIT_RGBA:
      formatString = "32-bit RGBA image";
      break;
    }
    addLeaf(tagNode, "format: " + formatString);
    addLeaf(tagNode, "width: " + tag.getWidth());
    addLeaf(tagNode, "height: " + tag.getHeight());
    if (format == DefineBitsLossless.FORMAT_8_BIT_COLORMAPPED) {
      addNode(
          tagNode, "zlibBitmapData: ", (AlphaColorMapData) tag.getZlibBitmapData());
    } else {
      DefaultMutableTreeNode zlibNode = addParentNode(
          tagNode, "zlibBitmapData: AlphaBitMapData");
      addLeaf(
          zlibNode,
          "bitmapPixelData: RGBA[" +
          ((AlphaBitmapData) (tag.getZlibBitmapData())).getBitmapPixelData().length +
      "]");
    }
  }

  private static void addNode(DefaultMutableTreeNode node, DefineButton tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatDefTag("DefineButton"));
    addLeaf(tagNode, "characterId: " + tag.getCharacterId());
    addNode(tagNode, "characters: ", tag.getCharacters());
    addNode(tagNode, "actions: ", tag.getActions());
  }

  private static void addNode(DefaultMutableTreeNode node, DefineButton2 tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatDefTag("DefineButton2"));
    addLeaf(tagNode, "characterId: " + tag.getCharacterId());
    addLeaf(tagNode, "trackAsMenu: " + tag.isTrackAsMenu());
    addNode(tagNode, "characters: ", tag.getCharacters());
    if (tag.getActions() != null) {
      addNode(tagNode, "actions: ", tag.getActions());
    }
  }

  private static void addNode(
      DefaultMutableTreeNode node, String var, ButtonCondAction[] actions) {
    DefaultMutableTreeNode newNode = addParentNode(
        node, var + "ButtonCondAction[" + actions.length + "]");
    for (int i = 0; i < actions.length; i++) {
      DefaultMutableTreeNode actionNode = addParentNode(
          newNode, "ButtonCondAction");
      addLeaf(actionNode, "idleToOverDown: " + actions[i].isIdleToOverDown());
      addLeaf(actionNode, "outDownToIdle: " + actions[i].isOutDownToIdle());
      addLeaf(
          actionNode, "outDownToOverDown: " + actions[i].isOutDownToOverDown());
      addLeaf(
          actionNode, "overDownToOutDown: " + actions[i].isOverDownToOutDown());
      addLeaf(
          actionNode, "overDownToOverUp: " + actions[i].isOverDownToOverUp());
      addLeaf(
          actionNode, "overUpToOverDown: " + actions[i].isOverUpToOverDown());
      addLeaf(actionNode, "overUpToIdle: " + actions[i].isOverUpToIdle());
      addLeaf(actionNode, "idleToOverUp: " + actions[i].isIdleToOverUp());
      addLeaf(actionNode, "keyPress: " + actions[i].getKeyPress());
      addLeaf(actionNode, "overDownToIdle: " + actions[i].isOverDownToIdle());
      addNode(actionNode, "actions: ", actions[i].getActions());
    }
  }

  private static void addNode(
      DefaultMutableTreeNode node, String var, ButtonRecord[] characters) {
    DefaultMutableTreeNode newNode = addParentNode(
        node, var + "ButtonRecord[" + characters.length + "]");
    for (int i = 0; i < characters.length; i++) {
      DefaultMutableTreeNode recordNode = addParentNode(
          newNode, "ButtonRecord");
      addLeaf(recordNode, "hitState: " + characters[i].isHitState());
      addLeaf(recordNode, "downState: " + characters[i].isDownState());
      addLeaf(recordNode, "overState: " + characters[i].isOverState());
      addLeaf(recordNode, "upState: " + characters[i].isUpState());
      addLeaf(recordNode, "characterId: " + characters[i].getCharacterId());
      addLeaf(recordNode, "placeDepth: " + characters[i].getPlaceDepth());
      addLeaf(recordNode, "placeMatrix: " + characters[i].getPlaceMatrix());
      CXformWithAlpha colorTransform = characters[i].getColorTransform();
      if (colorTransform != null) {
        addNode(
            recordNode, "colorTransform: ", characters[i].getColorTransform());
      }
    }
  }

  private static void addNode(
      DefaultMutableTreeNode node, DefineButtonCXform tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatDefTag("DefineButtonCXform"));
    addLeaf(tagNode, "characterId: " + tag.getCharacterId());
    addNode(tagNode, "buttonColorTransform: ", tag.getColorTransform());
  }

  private static void addNode(
      DefaultMutableTreeNode node, DefineButtonSound tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatDefTag("DefineButtonSound"));
    addLeaf(tagNode, "buttonId: " + tag.getButtonId());
    if (tag.getOverUpToIdleSoundId() != 0) {
      addLeaf(tagNode, "overUpToIdleSoundId: " + tag.getOverUpToIdleSoundId());
      addNode(
          tagNode, "overUpToIdleSoundInfo: ", tag.getOverUpToIdleSoundInfo());
    }
    if (tag.getIdleToOverUpSoundId() != 0) {
      addLeaf(tagNode, "idleToOverUpSoundId: " + tag.getIdleToOverUpSoundId());
      addNode(
          tagNode, "idleToOverUpSoundInfo: ", tag.getIdleToOverUpSoundInfo());
    }
    if (tag.getOverUpToOverDownSoundId() != 0) {
      addLeaf(
          tagNode, "overUpToOverDownSoundId: " +
          tag.getOverUpToOverDownSoundId());
      addNode(
          tagNode, "overUpToOverDownSoundInfo: ",
          tag.getOverUpToOverDownSoundInfo());
    }
    if (tag.getOverDownToOverUpSoundId() != 0) {
      addLeaf(
          tagNode, "overDownToOverUpSoundId: " +
          tag.getOverDownToOverUpSoundId());
      addNode(
          tagNode, "overDownToOverUpSoundInfo: ",
          tag.getOverDownToOverUpSoundInfo());
    }
  }

  private static void addNode(DefaultMutableTreeNode node, DefineEditText tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatDefTag("DefineEditText"));
    addLeaf(tagNode, "characterId: " + tag.getCharacterId());
    addLeaf(tagNode, "bounds: " + tag.getBounds());
    addLeaf(tagNode, "wordWrap: " + tag.isWordWrap());
    addLeaf(tagNode, "multiline: " + tag.isMultiline());
    addLeaf(tagNode, "password: " + tag.isPassword());
    addLeaf(tagNode, "readOnly: " + tag.isReadOnly());
    addLeaf(tagNode, "autoSize: " + tag.isAutoSize());
    addLeaf(tagNode, "noSelect: " + tag.isNoSelect());
    addLeaf(tagNode, "border: " + tag.isBorder());
    addLeaf(tagNode, "html: " + tag.isHtml());
    addLeaf(tagNode, "useOutlines: " + tag.isUseOutlines());
    if (tag.getFontId() > 0) {
      addLeaf(tagNode, "fontId: " + tag.getFontId());
      addLeaf(tagNode, "fontHeight: " + tag.getFontHeight());
    }
    if (tag.getTextColor() != null) {
      addLeaf(tagNode, "textColor: " + tag.getTextColor());
    }
    if (tag.getMaxLength() > 0) {
      addLeaf(tagNode, "maxLength: " + tag.getMaxLength());
    }
    if (tag.hasLayout()) {
      addLeaf(tagNode, "align: " + tag.getAlign());
      addLeaf(tagNode, "leftMargin: " + tag.getLeftMargin());
      addLeaf(tagNode, "rightMargin: " + tag.getRightMargin());
      addLeaf(tagNode, "indent: " + tag.getIndent());
      addLeaf(tagNode, "leading: " + tag.getLeading());
    }
    addLeaf(tagNode, "variableName: " + tag.getVariableName());
    if (tag.getInitialText() != null) {
      addLeaf(tagNode, "initialText: " + tag.getInitialText());
    }
  }

  private static void addNode(DefaultMutableTreeNode node, DefineFont tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatDefTag("DefineFont"));
    addLeaf(tagNode, "characterId: " + tag.getCharacterId());
    Shape[] shapes                   = tag.getGlyphShapeTable();
    DefaultMutableTreeNode shapeNode = addParentNode(
        tagNode, "glyphShapeTable: Shape[" + shapes.length + "]");
    for (int i = 0; i < shapes.length; i++) {
      addNode(shapeNode, "", shapes[i]);
    }
  }

  private static void addNode(DefaultMutableTreeNode node, DefineFont2 tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatDefTag("DefineFont2"));
    addLeaf(tagNode, "characterId: " + tag.getCharacterId());
    addLeaf(tagNode, "shiftJIS: " + tag.isShiftJIS());
    addLeaf(tagNode, "smallText: " + tag.isSmallText());
    addLeaf(tagNode, "ansi: " + tag.isANSI());
    addLeaf(tagNode, "italic: " + tag.isItalic());
    addLeaf(tagNode, "bold: " + tag.isBold());
    addLeaf(tagNode, "LanguageCode: " + tag.getLanguageCode());
    addLeaf(tagNode, "fontName: " + tag.getFontName());
    addLeaf(tagNode, "hasLayout: " + tag.hasLayout());
    Shape[] shapes = tag.getGlyphShapeTable();
    if (shapes == null) {
      addLeaf(tagNode, "numGlyphs: 0");
    } else {
      addLeaf(tagNode, "numGlyphs: " + shapes.length);
    }
    if (shapes != null) {
      DefaultMutableTreeNode shapeNode = addParentNode(
          tagNode, "glyphShapeTable: Shape[" + shapes.length + "]");
      for (int i = 0; i < shapes.length; i++) {
        addNode(shapeNode, "", shapes[i]);
      }
      char[] table                         = tag.getCodeTable();
      DefaultMutableTreeNode codeTableNode = addParentNode(
          tagNode, "codeTable: char[" + table.length + "]");
      for (int i = 0; i < table.length; i++) {
        addLeaf(codeTableNode, "code " + i + ": " + table[i]);
      }
    }
    if (tag.hasLayout()) {
      addLeaf(tagNode, "ascent: " + tag.getAscent());
      addLeaf(tagNode, "descent: " + tag.getDescent());
      addLeaf(tagNode, "leading: " + tag.getLeading());
      addLeaf(tagNode, "fontAscent: " + tag.getAscent());
      if (shapes != null) {
        short[] advanceTable                    = tag.getAdvanceTable();
        DefaultMutableTreeNode advanceTableNode = addParentNode(
            tagNode, "advanceTable: short[" + advanceTable.length + "]");
        for (int i = 0; i < advanceTable.length; i++) {
          addLeaf(advanceTableNode, i + ": " + advanceTable[i]);
        }
        Rect[] boundsTable                     = tag.getBoundsTable();
        DefaultMutableTreeNode boundsTableNode = addParentNode(
            tagNode, "boundsTable: Rect[" + boundsTable.length + "]");
        for (int i = 0; i < boundsTable.length; i++) {
          addLeaf(boundsTableNode, i + ": " + boundsTable[i]);
        }
      }
      KerningRecord[] kerningTable = tag.getKerningTable();
      if ((kerningTable != null) && (kerningTable.length > 0)) {
        DefaultMutableTreeNode kerningTableNode = addParentNode(
            tagNode, "kerningTable: KerningRecord[" + kerningTable.length +
        "]");
        for (int i = 0; i < kerningTable.length; i++) {
          KerningRecord kerningRecord              = kerningTable[i];
          DefaultMutableTreeNode kerningRecordNode = addParentNode(
              kerningTableNode, "KerningRecord");
          addLeaf(kerningRecordNode, "left: " + kerningRecord.getLeft());
          addLeaf(kerningRecordNode, "right: " + kerningRecord.getRight());
          addLeaf(
              kerningRecordNode, "adjustment: " + kerningRecord.getAdjustment());
        }
      }
    }
  }

  private static void addNode(DefaultMutableTreeNode node, DefineFont3 tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatDefTag("DefineFont3"));
    addLeaf(tagNode, "characterId: " + tag.getCharacterId());
    addLeaf(tagNode, "smallText: " + tag.isSmallText());
    addLeaf(tagNode, "italic: " + tag.isItalic());
    addLeaf(tagNode, "bold: " + tag.isBold());
    addLeaf(tagNode, "LanguageCode: " + tag.getLanguageCode());
    addLeaf(tagNode, "fontName: " + tag.getFontName());
    addLeaf(tagNode, "hasLayout: " + tag.hasLayout());
    Shape[] shapes = tag.getGlyphShapeTable();
    if (shapes == null) {
      addLeaf(tagNode, "numGlyphs: 0");
    } else {
      addLeaf(tagNode, "numGlyphs: " + shapes.length);
    }
    if (shapes != null) {
      DefaultMutableTreeNode shapeNode = addParentNode(
          tagNode, "glyphShapeTable: Shape[" + shapes.length + "]");
      for (int i = 0; i < shapes.length; i++) {
        addNode(shapeNode, "", shapes[i]);
      }
      char[] table                         = tag.getCodeTable();
      DefaultMutableTreeNode codeTableNode = addParentNode(
          tagNode, "codeTable: char[" + table.length + "]");
      for (int i = 0; i < table.length; i++) {
        addLeaf(codeTableNode, "code " + i + ": " + table[i]);
      }
    }
    if (tag.hasLayout()) {
      addLeaf(tagNode, "ascent: " + tag.getAscent());
      addLeaf(tagNode, "descent: " + tag.getDescent());
      addLeaf(tagNode, "leading: " + tag.getLeading());
      addLeaf(tagNode, "fontAscent: " + tag.getAscent());
      if (shapes != null) {
        short[] advanceTable                    = tag.getAdvanceTable();
        DefaultMutableTreeNode advanceTableNode = addParentNode(
            tagNode, "advanceTable: short[" + advanceTable.length + "]");
        for (int i = 0; i < advanceTable.length; i++) {
          addLeaf(advanceTableNode, i + ": " + advanceTable[i]);
        }
        Rect[] boundsTable                     = tag.getBoundsTable();
        DefaultMutableTreeNode boundsTableNode = addParentNode(
            tagNode, "boundsTable: Rect[" + boundsTable.length + "]");
        for (int i = 0; i < boundsTable.length; i++) {
          addLeaf(boundsTableNode, i + ": " + boundsTable[i]);
        }
      }
      KerningRecord[] kerningTable = tag.getKerningTable();
      if ((kerningTable != null) && (kerningTable.length > 0)) {
        DefaultMutableTreeNode kerningTableNode = addParentNode(
            tagNode, "kerningTable: KerningRecord[" + kerningTable.length +
        "]");
        for (int i = 0; i < kerningTable.length; i++) {
          KerningRecord kerningRecord              = kerningTable[i];
          DefaultMutableTreeNode kerningRecordNode = addParentNode(
              kerningTableNode, "KerningRecord");
          addLeaf(kerningRecordNode, "left: " + kerningRecord.getLeft());
          addLeaf(kerningRecordNode, "right: " + kerningRecord.getRight());
          addLeaf(
              kerningRecordNode, "adjustment: " + kerningRecord.getAdjustment());
        }
      }
    }
  }

  private static void addNode(DefaultMutableTreeNode node, DefineFontInfo tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatDefTag("DefineFontInfo"));
    addLeaf(tagNode, "fontId: " + tag.getFontId());
    addLeaf(tagNode, "fontName: " + tag.getFontName());
    addLeaf(tagNode, "smallText: " + tag.isSmallText());
    addLeaf(tagNode, "shiftJIS: " + tag.isShiftJIS());
    addLeaf(tagNode, "ansi: " + tag.isANSI());
    addLeaf(tagNode, "italic: " + tag.isItalic());
    addLeaf(tagNode, "bold: " + tag.isBold());
    char[] table                         = tag.getCodeTable();
    DefaultMutableTreeNode codeTableNode = addParentNode(
        tagNode, "codeTable: char[" + table.length + "]");
    for (int i = 0; i < table.length; i++) {
      addLeaf(codeTableNode, "code " + i + ": " + table[i]);
    }
  }

  private static void addNode(DefaultMutableTreeNode node, DefineFontInfo2 tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatDefTag("DefineFontInfo2"));
    addLeaf(tagNode, "fontId: " + tag.getFontId());
    addLeaf(tagNode, "fontName: " + tag.getFontName());
    addLeaf(tagNode, "smallText: " + tag.isSmallText());
    addLeaf(tagNode, "italic: " + tag.isItalic());
    addLeaf(tagNode, "bold: " + tag.isBold());
    addLeaf(tagNode, "langCode: " + tag.getLangCode());
    char[] table                         = tag.getCodeTable();
    DefaultMutableTreeNode codeTableNode = addParentNode(
        tagNode, "codeTable: char[" + table.length + "]");
    for (int i = 0; i < table.length; i++) {
      addLeaf(codeTableNode, "code " + i + ": " + table[i]);
    }
  }

  private static void addNode(DefaultMutableTreeNode node, DefineFontName tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatDefTag("DefineFontName"));
    addLeaf(tagNode, "fontId: " + tag.getFontId());
    addLeaf(tagNode, "fontName: " + tag.getFontName());
    addLeaf(tagNode, "fontLicense: " + tag.getFontLicense());
  }

  private static void addNode(
      DefaultMutableTreeNode node, FlashTypeSettings tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatDefTag("FlashTypeSettings"));
    addLeaf(tagNode, "textId: " + tag.getTextId());
    addLeaf(tagNode, "flashType: " + tag.isFlashType());
    addLeaf(tagNode, "gridFit: " + getGridFitString(tag.getGridFit()));
    addLeaf(tagNode, "thickness: " + tag.getThickness());
    addLeaf(tagNode, "sharpness: " + tag.getSharpness());
  }

  private static void addNode(
      DefaultMutableTreeNode node, DefineFontAlignment tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatDefTag("DefineFontAlignment"));
    addLeaf(tagNode, "fontId: " + tag.getFontId());
    addLeaf(tagNode, "thickness: " + getThicknessString(tag.getThickness()));
    AlignmentZone[] alignmentZones = tag.getAlignmentZones();
    addNode(tagNode, alignmentZones);
  }

  private static void addNode(
      DefaultMutableTreeNode node, AlignmentZone[] alignmentZones) {
    DefaultMutableTreeNode newNode = addParentNode(
        node, "alignmentZones: AlignmentZone[" + alignmentZones.length + "]");
    for (int i = 0; i < alignmentZones.length; i++) {
      AlignmentZone zone              = alignmentZones[i];
      DefaultMutableTreeNode zoneNode = addParentNode(
          newNode, "AlignmentZone " + i);
      if (zone.hasX()) {
        addLeaf(
            zoneNode, "x: left=" + zone.getLeft() + " width=" + zone.getWidth());
      }
      if (zone.hasY()) {
        addLeaf(
            zoneNode,
            "y: baseline=" + zone.getBaseline() + " height=" + zone.getHeight());
      }
    }
  }

  private static void addNode(
      DefaultMutableTreeNode node, DefineMorphShape tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatDefTag("DefineMorphShape"));
    addLeaf(tagNode, "characterId: " + tag.getCharacterId());
    addLeaf(tagNode, "startBounds: " + tag.getStartBounds());
    addLeaf(tagNode, "endBounds: " + tag.getEndBounds());
    MorphFillStyles morphFillStyles = tag.getMorphFillStyles();
    if (morphFillStyles != null) {
      addNode(tagNode, "morphFillStyles: ", morphFillStyles);
    }
    MorphLineStyles morphLineStyles = tag.getMorphLineStyles();
    if (morphLineStyles != null) {
      addNode(tagNode, "morphLineStyles: ", morphLineStyles);
    }
    Shape startEdges = tag.getStartShape();
    if (startEdges != null) {
      addNode(tagNode, "startEdges: ", startEdges);
    }
    Shape endEdges = tag.getEndShape();
    if (endEdges != null) {
      addNode(tagNode, "endEdges: ", endEdges);
    }
  }

  private static void addNode(
      DefaultMutableTreeNode node, DefineMorphShape2 tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatDefTag("DefineMorphShape2"));
    addLeaf(tagNode, "characterId: " + tag.getCharacterId());
    addLeaf(tagNode, "startBounds: " + tag.getStartShapeBounds());
    addLeaf(tagNode, "endBounds: " + tag.getEndShapeBounds());
    addLeaf(tagNode, "startEdgeBounds: " + tag.getStartEdgeBounds());
    addLeaf(tagNode, "endEdgeBounds: " + tag.getEndEdgeBounds());
    MorphFillStyles morphFillStyles = tag.getMorphFillStyles();
    if (morphFillStyles != null) {
      addNode(tagNode, "morphFillStyles: ", morphFillStyles);
    }
    MorphLineStyles morphLineStyles = tag.getMorphLineStyles();
    if (morphLineStyles != null) {
      addNode(tagNode, "morphLineStyles: ", morphLineStyles);
    }
    Shape startShape = tag.getStartShape();
    if (startShape != null) {
      addNode(tagNode, "startShape: ", startShape);
    }
    Shape endShape = tag.getEndShape();
    if (endShape != null) {
      addNode(tagNode, "endShape: ", endShape);
    }
  }

  private static void addNode(
      DefaultMutableTreeNode node, DefineSceneFrameData tag) {
    addParentNode(node, formatDefTag("DefineSceneFrameData"));
    DefaultMutableTreeNode sceneEntriesElement = addParentNode(
        node, formatDefTag("scene data"));
    List<SceneData> sceneEntries = tag.getSceneEntries();
    for (Iterator<SceneData> it = sceneEntries.iterator(); it.hasNext(); ) {
      SceneData sceneData = it.next();
      addLeaf(sceneEntriesElement, "frame offset: " + sceneData.getFrameOffset() + ", scene name: " + sceneData.getSceneName());
    }
    DefaultMutableTreeNode frameDataElement = addParentNode(
        node, formatDefTag("frame data"));
    List<FrameData> frameEntries = tag.getFrameEntries();
    for (Iterator<FrameData> it = frameEntries.iterator(); it.hasNext(); ) {
      FrameData frameData = it.next();
      addLeaf(frameDataElement, "frame number: " + frameData.getFrameNumber() + ", frame label: " + frameData.getFrameLabel());
    }
  }

  private static void addNode(
      DefaultMutableTreeNode node, String var, Shape shape) {
    DefaultMutableTreeNode newNode = addParentNode(node, var + "Shape");
    ShapeRecord[] shapeRecords     = shape.getShapeRecords();
    if (shapeRecords.length > 0) {
      DefaultMutableTreeNode recordsNode = addParentNode(
          newNode, "shapeRecords: ShapeRecord[" + shapeRecords.length + "]");
      for (int i = 0; i < shapeRecords.length; i++) {
        addNode(recordsNode, shapeRecords[i]);
      }
    }
  }

  private static void addNode(
      DefaultMutableTreeNode node, String var, MorphLineStyles morphLineStyles) {
    DefaultMutableTreeNode newNode = addParentNode(
        node, var + "MorphLineStyles (" + morphLineStyles.getSize() +
    " styles)");
    for (int i = 1; i <= morphLineStyles.getSize(); i++) {
      DefaultMutableTreeNode styleNode = addParentNode(
          newNode, "MorphLineStyle " + i);
      Object style                     = morphLineStyles.getStyle(i);
      if (style instanceof MorphLineStyle) {
        addNode(styleNode, (MorphLineStyle) style);
      } else {
        addNode(styleNode, (MorphLineStyle2) style);
      }
    }
  }

  private static void addNode(
      DefaultMutableTreeNode styleNode, MorphLineStyle style) {
    addLeaf(styleNode, "startWidth: " + style.getStartWidth());
    addLeaf(styleNode, "endWidth: " + style.getEndWidth());
    addLeaf(styleNode, "startColor: " + style.getStartColor());
    addLeaf(styleNode, "endColor: " + style.getEndColor());
  }

  private static void addNode(
      DefaultMutableTreeNode styleNode, MorphLineStyle2 style) {
    addLeaf(styleNode, "startWidth: " + style.getStartWidth());
    addLeaf(styleNode, "endWidth: " + style.getEndWidth());
    addLeaf(styleNode, "startCapStyle: " 
        + getFriendlyConstantName(style.getStartCapStyle()));
    addLeaf(styleNode, "endCapStyle: " 
        + getFriendlyConstantName(style.getEndCapStyle()));
    JointStyle jointStyle = style.getJointStyle();
    addLeaf(styleNode, "jointStyle: " + getFriendlyConstantName(jointStyle));
    if (JointStyle.MITER.equals(jointStyle)) {
      addLeaf(styleNode, "miterLimit: " + style.getMiterLimit());
    }
    addLeaf(styleNode, "pixelHinting: " + style.isPixelHinting());
    addLeaf(styleNode, "close: " + style.isClose());
    addLeaf(styleNode, "scaleStroke: " 
        + getFriendlyConstantName(style.getScaleStroke()));
    MorphFillStyle fillStyle = style.getFillStyle();
    if (fillStyle == null) {
      addLeaf(styleNode, "startColor: " + style.getStartColor());
      addLeaf(styleNode, "endColor: " + style.getEndColor());
    } else {
      addNode(styleNode, fillStyle, 0);
    }
  }

  private static void addNode(
      DefaultMutableTreeNode node, String var, MorphFillStyles morphFillStyles) {
    DefaultMutableTreeNode newNode = addParentNode(
        node, var + "MorphFillStyles (" + morphFillStyles.getSize() +
    " styles)");
    for (int i = 1; i <= morphFillStyles.getSize(); i++) {
      addNode(newNode, morphFillStyles.getStyle(i), i);
    }
  }

  private static void addNode(
      DefaultMutableTreeNode node, MorphFillStyle fillStyle, int index) {
    DefaultMutableTreeNode newNode = addParentNode(
        node, "MorphFillStyle " + index);
    TagConstants.FillType type = fillStyle.getType();
    addLeaf(newNode, "type: " + getFriendlyConstantName(type));
    switch (type.getGroup()) {
    case SOLID:
      addLeaf(newNode, "startColor: " + fillStyle.getStartColor());
      addLeaf(newNode, "endColor: " + fillStyle.getEndColor());
      break;
    case GRADIENT:
      addLeaf(newNode, "startGradientMatrix: " + fillStyle.getStartGradientMatrix());
      addLeaf(newNode, "endGradientMatrix: " + fillStyle.getEndGradientMatrix());
      addNode(newNode, fillStyle.getGradient());
      break;
    case BITMAP:
      addLeaf(newNode, "bitmapId: " + fillStyle.getBitmapId());
      addLeaf(newNode, "startBitmapMatrix: " + fillStyle.getStartBitmapMatrix());
      addLeaf(newNode, "endBitmapMatrix: " + fillStyle.getEndBitmapMatrix());
      break;
    default:
      addLeaf(newNode, "unknown type: " + type);
    }
  }

  private static void addNode(
      DefaultMutableTreeNode node, MorphGradient gradient) {
    DefaultMutableTreeNode morphGradNode = addParentNode(
        node,
        ((gradient instanceof FocalMorphGradient) ? "FocalMorphGradient"
            : "MorphGradient"));
    addLeaf(morphGradNode, "spreadMethod: " 
        + getFriendlyConstantName(gradient.getSpreadMethod()));
    addLeaf(morphGradNode, "interpolationMethod: " 
        + getFriendlyConstantName(gradient.getInterpolationMethod()));
    if (gradient instanceof FocalMorphGradient) {
      FocalMorphGradient focalMorphGradient = (FocalMorphGradient) gradient;
      addLeaf(
          morphGradNode,
          "startFocalPointRatio: " +
          (focalMorphGradient).getStartFocalPointRatio());
      addLeaf(
          morphGradNode,
          "endFocalPointRatio: " + (focalMorphGradient).getEndFocalPointRatio());
    }
    MorphGradRecord[] records = gradient.getGradientRecords();
    for (int i = 0; i < records.length; i++) {
      DefaultMutableTreeNode recordNode = addParentNode(
          morphGradNode, "MorphGradRecord");
      addLeaf(recordNode, "startRatio: " + records[i].getStartRatio());
      addLeaf(recordNode, "startColor: " + records[i].getStartColor());
      addLeaf(recordNode, "endRatio: " + records[i].getEndRatio());
      addLeaf(recordNode, "endColor: " + records[i].getEndColor());
    }
  }

  private static void addNode(DefaultMutableTreeNode node, DefineShape tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatDefTag("DefineShape"));
    addLeaf(tagNode, "characterID: " + tag.getCharacterId());
    addLeaf(tagNode, "shapeBounds: " + tag.getShapeBounds());
    addNode(tagNode, "shapes: ", tag.getShapes());
  }

  private static void addNode(DefaultMutableTreeNode node, DefineShape2 tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatDefTag("DefineShape2"));
    addLeaf(tagNode, "characterID: " + tag.getCharacterId());
    addLeaf(tagNode, "shapeBounds: " + tag.getShapeBounds());
    addNode(tagNode, "shapes: ", tag.getShapes());
  }

  private static void addNode(DefaultMutableTreeNode node, DefineShape3 tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatDefTag("DefineShape3"));
    addLeaf(tagNode, "characterID: " + tag.getCharacterId());
    addLeaf(tagNode, "shapeBounds: " + tag.getShapeBounds());
    addNode(tagNode, "shapes: ", tag.getShapes());
  }

  private static void addNode(DefaultMutableTreeNode node, DefineShape4 tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatDefTag("DefineShape4"));
    addLeaf(tagNode, "characterID: " + tag.getCharacterId());
    addLeaf(tagNode, "shapeBounds: " + tag.getShapeBounds());
    addLeaf(tagNode, "edgeBounds: " + tag.getEdgeBounds());
    addNode(tagNode, "shapes: ", tag.getShapes());
  }

  private static void addNode(
      DefaultMutableTreeNode node, String var, ShapeWithStyle shapeWithStyle) {
    DefaultMutableTreeNode newNode = addParentNode(
        node, var + "ShapeWithStyle");
    addNode(newNode, "fillStyles: ", shapeWithStyle.getFillStyles());
    addNode(newNode, "lineStyles: ", shapeWithStyle.getLineStyles());
    ShapeRecord[] shapeRecords = shapeWithStyle.getShapeRecords();
    if (shapeRecords.length > 0) {
      DefaultMutableTreeNode recordsNode = addParentNode(
          newNode, "shapeRecords: ShapeRecord[" + shapeRecords.length + "]");
      for (int i = 0; i < shapeRecords.length; i++) {
        addNode(recordsNode, shapeRecords[i]);
      }
    }
  }

  private static void addNode(DefaultMutableTreeNode node, ShapeRecord record) {
    if (record instanceof StyleChangeRecord) {
      addNode(node, (StyleChangeRecord) record);
    } else if (record instanceof StraightEdgeRecord) {
      addNode(node, (StraightEdgeRecord) record);
    } else {
      addNode(node, (CurvedEdgeRecord) record);
    }
  }

  private static void addNode(
      DefaultMutableTreeNode node, StraightEdgeRecord record) {
    DefaultMutableTreeNode newNode = addParentNode(node, "StraightEdgeRecord");
    addLeaf(newNode, "deltaX: " + record.getDeltaX());
    addLeaf(newNode, "deltaY: " + record.getDeltaY());
  }

  private static void addNode(
      DefaultMutableTreeNode node, CurvedEdgeRecord record) {
    DefaultMutableTreeNode newNode = addParentNode(node, "CurvedEdgeRecord");
    addLeaf(newNode, "controlDeltaX: " + record.getControlDeltaX());
    addLeaf(newNode, "controlDeltaX: " + record.getControlDeltaY());
    addLeaf(newNode, "anchorDeltaX: " + record.getAnchorDeltaX());
    addLeaf(newNode, "anchorDeltaY: " + record.getAnchorDeltaY());
  }

  private static void addNode(
      DefaultMutableTreeNode node, StyleChangeRecord record) {
    DefaultMutableTreeNode newNode = addParentNode(node, "StyleChangeRecord");
    if (record.hasMoveTo()) {
      addLeaf(newNode, "moveToX: " + record.getMoveToX());
      addLeaf(newNode, "moveToY: " + record.getMoveToY());
    }
    if (record.hasFillStyle0()) {
      addLeaf(newNode, "fillStyle0: " + record.getFillStyle0());
    }
    if (record.hasFillStyle1()) {
      addLeaf(newNode, "fillStyle1: " + record.getFillStyle1());
    }
    if (record.hasLineStyle()) {
      addLeaf(newNode, "lineStyle: " + record.getLineStyle());
    }
    if (record.hasNewStyles()) {
      DefaultMutableTreeNode newStylesNode = addParentNode(
          newNode, "NewStyles");
      addNode(newStylesNode, "newFillStyles: ", record.getNewFillStyles());
      addNode(newStylesNode, "newLineStyles: ", record.getNewLineStyles());
    }
  }

  private static void addNode(
      DefaultMutableTreeNode node, String var, FillStyleArray fillStyleArray) {
    DefaultMutableTreeNode newNode = addParentNode(
        node, var + "FillStyleArray (" + fillStyleArray.getSize() + " styles)");
    for (int i = 1; i <= fillStyleArray.getSize(); i++) {
      addNode(newNode, fillStyleArray.getStyle(i), i);
    }
  }

  private static void addNode(
      DefaultMutableTreeNode node, FillStyle fillStyle, int index) {
    DefaultMutableTreeNode newNode;
    if (index > 0) {
      newNode = addParentNode(node, "FillStyle " + index);
    } else {
      newNode = addParentNode(node, "FillStyle");
    }
    addLeaf(newNode, "type: ".concat(getFriendlyConstantName(fillStyle.getType())));
    switch (fillStyle.getType().getGroup()) {
    case SOLID:
      addLeaf(newNode, "color: " + fillStyle.getColor());
      break;
    case GRADIENT:
      addLeaf(newNode, "gradientMatrix: " + fillStyle.getGradientMatrix());
      addNode(newNode, fillStyle.getGradient());
      break;
    case BITMAP:
      addLeaf(newNode, "bitmapId: " + fillStyle.getBitmapId());
      addLeaf(newNode, "bitmapMatrix: " + fillStyle.getBitmapMatrix());
      break;
    default:
      addLeaf(newNode, "unknown fill type: " + fillStyle.getType());
    }
  }

  private static void addNode(
      DefaultMutableTreeNode node, LineStyle lineStyle, int index) {
    DefaultMutableTreeNode newNode = addParentNode(node, "LineStyle " + index);
    addLeaf(newNode, "width: " + lineStyle.getWidth());
    addLeaf(newNode, "color: " + lineStyle.getColor());
  }

  private static void addNode(
      DefaultMutableTreeNode node, LineStyle2 lineStyle, int index) {
    DefaultMutableTreeNode newNode = addParentNode(node, "LineStyle2 " + index);
    addLeaf(newNode, "width: " + lineStyle.getWidth());
    addLeaf(newNode, "startCapStyle: " 
        + getFriendlyConstantName(lineStyle.getStartCapStyle()));
    addLeaf(newNode, "endCapStyle: " 
        + getFriendlyConstantName(lineStyle.getEndCapStyle()));
    JointStyle jointStyle = lineStyle.getJointStyle();
    addLeaf(newNode, "jointStyle: " + getFriendlyConstantName(jointStyle));
    if (JointStyle.MITER.equals(jointStyle)) {
      addLeaf(newNode, "miterLimit: " + lineStyle.getMiterLimit());
    }
    addLeaf(newNode, "pixelHinting: " + lineStyle.isPixelHinting());
    addLeaf(newNode, "close: " + lineStyle.isClose());
    addLeaf(newNode, "scaleStroke: " 
        + getFriendlyConstantName(lineStyle.getScaleStroke()));
    FillStyle fillStyle = lineStyle.getFillStyle();
    if (fillStyle == null) {
      addLeaf(newNode, "color: " + lineStyle.getColor());
    } else {
      addNode(newNode, fillStyle, 0);
    }
  }

  private static void addNode(
      DefaultMutableTreeNode node, Gradient gradient) {
    DefaultMutableTreeNode newNode = addParentNode(
        node,
        ((gradient instanceof FocalGradient) ? "FocalGradient" : "Gradient"));
    addLeaf(newNode, "spreadMethod: " 
        + getFriendlyConstantName(gradient.getSpreadMethod()));
    addLeaf(newNode, "interpolationMethod: "
        + getFriendlyConstantName(gradient.getInterpolationMethod()));
    if (gradient instanceof FocalGradient) {
      addLeaf(
          newNode,
          "focalPointRatio: " + ((FocalGradient) gradient).getFocalPointRatio());
    }
    GradRecord[] records = gradient.getGradientRecords();
    for (int i = 0; i < records.length; i++) {
      addNode(newNode, records[i]);
    }
  }

  private static void addNode(DefaultMutableTreeNode node, GradRecord record) {
    DefaultMutableTreeNode newNode = addParentNode(node, "GradientRecord");
    addLeaf(newNode, "ratio: " + record.getRatio());
    addLeaf(newNode, "color: " + record.getColor());
  }

  private static void addNode(
      DefaultMutableTreeNode node, String var, LineStyleArray lineStyleArray) {
    DefaultMutableTreeNode newNode = addParentNode(
        node, var + "LineStyleArray (" + lineStyleArray.getSize() + " styles)");
    for (int i = 1; i <= lineStyleArray.getSize(); i++) {
      Object style = lineStyleArray.getStyle(i);
      if (style instanceof LineStyle) {
        addNode(newNode, (LineStyle) style, i);
      } else {
        addNode(newNode, (LineStyle2) style, i);
      }
    }
  }

  private static void addNode(DefaultMutableTreeNode node, DefineSound tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatDefTag("DefineSound"));
    addLeaf(tagNode, "characterId: " + tag.getCharacterId());
    byte format = tag.getFormat();
    addLeaf(
        tagNode, "format: " + format + " (" + getSoundFormatString(format) + ")");
    byte rate = tag.getRate();
    addLeaf(tagNode, "rate: " + rate + " (" + getSoundRateString(rate) + ")");
    int sampleSize = (tag.is16BitSample()) ? 16 : 8;
    addLeaf(tagNode, "sampleSize: " + sampleSize + " bits");
    String type = tag.isStereo() ? "stereo" : "mono";
    addLeaf(tagNode, "type: " + type);
    addLeaf(tagNode, "sampleCount: " + tag.getSampleCount());
    addLeaf(
        tagNode, "soundData: " + " byte[" + tag.getSoundData().length + "]");
  }

  private static void addNode(DefaultMutableTreeNode node, DefineSprite tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatDefTag("DefineSprite"));
    addLeaf(tagNode, "characterId: " + tag.getCharacterId());
    addLeaf(tagNode, "frameCount: " + tag.getFrameCount());
    List<Tag> ctrlTags = tag.getControlTags();
    DefaultMutableTreeNode ctrlTagNode = addParentNode(
        tagNode, "controlTags: Tag[" + ctrlTags.size() + "]");
    for (int i = 0; i < ctrlTags.size(); i++) {
      addNode(ctrlTagNode, ctrlTags.get(i));
    }
  }

  private static void addNode(DefaultMutableTreeNode node, DefineText tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatDefTag("DefineText"));
    addLeaf(tagNode, "characterId: " + tag.getCharacterId());
    addLeaf(tagNode, "textBounds: " + tag.getTextBounds());
    addLeaf(tagNode, "textMatrix: " + tag.getTextMatrix());
    addNode(tagNode, "textRecords: ", tag.getTextRecords());
  }

  private static void addNode(DefaultMutableTreeNode node, DefineText2 tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatDefTag("DefineText2"));
    addLeaf(tagNode, "characterId: " + tag.getCharacterId());
    addLeaf(tagNode, "textBounds: " + tag.getTextBounds());
    addLeaf(tagNode, "textMatrix: " + tag.getTextMatrix());
    addNode(tagNode, "textRecords: ", tag.getTextRecords());
  }

  private static void addNode(
      DefaultMutableTreeNode node, String var, TextRecord[] textRecords) {
    DefaultMutableTreeNode newNode = addParentNode(
        node, var + "TextRecord[" + textRecords.length + "]");
    for (int i = 0; i < textRecords.length; i++) {
      TextRecord record              = textRecords[i];
      DefaultMutableTreeNode recNode = addParentNode(newNode, "TextRecord");
      if (record.getFontId() > 0) {
        addLeaf(recNode, "fontId: " + record.getFontId());
        addLeaf(recNode, "textHeight: " + record.getTextHeight());
      }
      if (record.getTextColor() != null) {
        addLeaf(recNode, "textColor: " + record.getTextColor());
      }
      if (record.getXOffset() != 0) {
        addLeaf(recNode, "xOffset: " + record.getXOffset());
      }
      if (record.getYOffset() != 0) {
        addLeaf(recNode, "yOffset: " + record.getYOffset());
      }
      addNode(recNode, "glyphEntries: ", record.getGlyphEntries());
    }
  }

  private static void addNode(
      DefaultMutableTreeNode node, String var, GlyphEntry[] glyphEntries) {
    DefaultMutableTreeNode newNode = addParentNode(
        node, var + "GlyphEntry[" + glyphEntries.length + "]");
    for (int i = 0; i < glyphEntries.length; i++) {
      GlyphEntry entry                 = glyphEntries[i];
      DefaultMutableTreeNode entryNode = addParentNode(newNode, "GlyphEntry");
      addLeaf(entryNode, "glyphIndex: " + entry.getGlyphIndex());
      addLeaf(entryNode, "glyphAdvance: " + entry.getGlyphAdvance());
    }
  }

  private static void addNode(
      DefaultMutableTreeNode node, DefineVideoStream tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatDefTag("DefineVideoStream"));
    addLeaf(tagNode, "characterId: " + tag.getCharacterId());
    addLeaf(tagNode, "numFrames: " + tag.getNumFrames());
    addLeaf(tagNode, "width: " + tag.getWidth());
    addLeaf(tagNode, "height: " + tag.getHeight());
    String deblocking = "unknown value";
    switch (tag.getDeblocking()) {
    case DefineVideoStream.DEBLOCKING_OFF:
      deblocking = "off";
      break;
    case DefineVideoStream.DEBLOCKING_ON:
      deblocking = "on";
      break;
    case DefineVideoStream.DEBLOCKING_PACKET:
      deblocking = "use video packet setting";
      break;
    }
    addLeaf(tagNode, "deblocking: " + deblocking);
    addLeaf(tagNode, "smoothing: " + (tag.isSmoothing() ? "on" : "off"));
    String codec  = "unknown codec";
    short codecId = tag.getCodecId();
    switch (codecId) {
    case DefineVideoStream.CODEC_SORENSON_H263:
      codec = "Sorenson H.263";
      break;
    case DefineVideoStream.CODEC_SCREEN_VIDEO:
      codec = "Screen Video";
      break;
    case DefineVideoStream.CODEC_VP6:
      codec = "On2 VP6";
      break;
    case DefineVideoStream.CODEC_VP6_ALPHA:
      codec = "On2 VP6 with alpha";
      break;
    case DefineVideoStream.CODEC_SCREEN_VIDEO_V2:
      codec = "Screen Video V2";
      break;
    default:
      codec = "unknown codec: " + codecId;
    }
    addLeaf(tagNode, "codec: " + codec);
  }

  private static void addNode(DefaultMutableTreeNode node, DoAction tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatControlTag("DoAction"));
    addNode(tagNode, "actions: ", tag.getActions());
  }

  private static void addNode(DefaultMutableTreeNode node, DoAbc tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatControlTag("DoAbc"));
    addNode(tagNode, tag.getAbcFile());
  }

  private static void addNode(DefaultMutableTreeNode node, DoAbcDefine tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatControlTag("DoAbcDefine"));
    addLeaf(tagNode, "abcName: " + tag.getAbcName());
    addNode(tagNode, tag.getAbcFile());
  }

  private static void addNode(DefaultMutableTreeNode node, DoInitAction tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatControlTag("DoInitAction"));
    addLeaf(tagNode, "spriteId: " + tag.getSpriteId());
    addNode(tagNode, "actions: ", tag.getInitActions());
  }

  private static void addNode(DefaultMutableTreeNode node, EnableDebugger tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatControlTag("EnableDebugger"));
    String password                = tag.getPassword();
    if (password == null) {
      addLeaf(tagNode, "No password");
    } else {
      addLeaf(tagNode, "password: " + password);
    }
  }

  private static void addNode(DefaultMutableTreeNode node, EnableDebugger2 tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatControlTag("EnableDebugger2"));
    String password                = tag.getPassword();
    if (password == null) {
      addLeaf(tagNode, "No password");
    } else {
      addLeaf(tagNode, "password: " + password);
    }
  }

  private static void addNode(DefaultMutableTreeNode node, ExportAssets tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatControlTag("ExportAssets"));
    ExportMapping[] exportMappings = tag.getExportMappings();
    for (int i = 0; i < exportMappings.length; i++) {
      addLeaf(
          tagNode,
          "characterId: " + exportMappings[i].getCharacterId() + ", name: " +
          exportMappings[i].getName());
    }
  }

  private static void addNode(DefaultMutableTreeNode node, FileAttributes tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatControlTag("FileAttributes"));
    addLeaf(tagNode, "allowNetworkAccess: " + tag.isAllowNetworkAccess());
    addLeaf(tagNode, "hasMetadata: " + tag.hasMetadata());
    addLeaf(tagNode, "hasABC: " + tag.hasABC());
  }

  private static void addNode(DefaultMutableTreeNode node, FrameLabel tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatControlTag("FrameLabel"));
    addLeaf(tagNode, "name: " + tag.getName());
    addLeaf(tagNode, "namedAnchor: " + tag.isNamedAnchor());
  }

  private static void addNode(DefaultMutableTreeNode node, ImportAssets tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node,
        formatControlTag(
            (tag instanceof ImportAssets2) ? "ImportAssets2" : "ImportAssets"));
    addLeaf(tagNode, "url: " + tag.getUrl());
    ImportMapping[] importMappings      = tag.getImportMappings();
    DefaultMutableTreeNode mappingsNode = addParentNode(
        tagNode, "importMappings[" + importMappings.length + "]");
    for (int i = 0; i < importMappings.length; i++) {
      addLeaf(
          mappingsNode,
          "name: " + importMappings[i].getName() + ", characterId: " +
          importMappings[i].getCharacterId());
    }
  }

  private static void addNode(DefaultMutableTreeNode node, JPEGTables tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatDefTag("JPEGTables"));
    addLeaf(tagNode, "jpegData: " + " byte[" + tag.getJpegData().length + "]");
  }

  private static void addNode(DefaultMutableTreeNode node, Metadata tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatControlTag("Metadata"));
    addLeaf(tagNode, "data: " + tag.getDataString());
  }

  private static void addNode(DefaultMutableTreeNode node, PlaceObject tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatControlTag("PlaceObject"));
    addLeaf(tagNode, "characterId: " + tag.getCharacterId());
    addLeaf(tagNode, "depth: " + tag.getDepth());
    addLeaf(tagNode, "matrix: " + tag.getMatrix());
    if (tag.getColorTransform() != null) {
      addNode(tagNode, "colorTransform: ", tag.getColorTransform());
    }
  }

  private static void addNode(
      DefaultMutableTreeNode node, String var, CXform cXform) {
    DefaultMutableTreeNode newNode = addParentNode(node, var + "CXform");
    if (cXform.hasMultTerms()) {
      addLeaf(newNode, "redMultTerm: " + cXform.getRedMultTerm());
      addLeaf(newNode, "greenMultTerm: " + cXform.getGreenMultTerm());
      addLeaf(newNode, "blueMultTerm: " + cXform.getBlueMultTerm());
    } else {
      addLeaf(newNode, "no multiplication transform");
    }
    if (cXform.hasAddTerms()) {
      addLeaf(newNode, "redAddTerm: " + cXform.getRedAddTerm());
      addLeaf(newNode, "greenAddTerm: " + cXform.getGreenAddTerm());
      addLeaf(newNode, "blueAddTerm: " + cXform.getBlueAddTerm());
    } else {
      addLeaf(newNode, "no addition transform");
    }
  }

  private static void addNode(
      DefaultMutableTreeNode node, String var, CXformWithAlpha xform) {
    DefaultMutableTreeNode newNode = addParentNode(
        node, var + "CXformWithAlpha");
    if (xform.hasMultTerms()) {
      addLeaf(newNode, "redMultTerm: " + xform.getRedMultTerm());
      addLeaf(newNode, "greenMultTerm: " + xform.getGreenMultTerm());
      addLeaf(newNode, "blueMultTerm: " + xform.getBlueMultTerm());
      addLeaf(newNode, "alphaMultTerm: " + xform.getAlphaMultTerm());
    } else {
      addLeaf(newNode, "no multiplication transform");
    }
    if (xform.hasAddTerms()) {
      addLeaf(newNode, "redAddTerm: " + xform.getRedAddTerm());
      addLeaf(newNode, "greenAddTerm: " + xform.getGreenAddTerm());
      addLeaf(newNode, "blueAddTerm: " + xform.getBlueAddTerm());
      addLeaf(newNode, "alphaAddTerm: " + xform.getAlphaAddTerm());
    } else {
      addLeaf(newNode, "no addition transform");
    }
  }

  private static void addNode(DefaultMutableTreeNode node, PlaceObject2 tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatControlTag("PlaceObject2"));
    addLeaf(tagNode, "depth: " + tag.getDepth());
    addLeaf(tagNode, "move: " + tag.isMove());
    if (tag.hasCharacter()) {
      addLeaf(tagNode, "characterId: " + tag.getCharacterId());
    }
    if (tag.hasMatrix()) {
      addLeaf(tagNode, "matrix: " + tag.getMatrix());
    }
    if (tag.hasColorTransform()) {
      addNode(tagNode, "colorTransform: ", tag.getColorTransform());
    }
    if (tag.hasRatio()) {
      addLeaf(tagNode, "ratio: " + tag.getRatio());
    }
    if (tag.hasName()) {
      addLeaf(tagNode, "name: " + tag.getName());
    }
    if (tag.hasClipDepth()) {
      addLeaf(tagNode, "clipDepth: " + tag.getClipDepth());
    }
    if (tag.hasClipActions()) {
      addNode(tagNode, "clipActions: ", tag.getClipActions());
    }
  }

  private static void addNode(DefaultMutableTreeNode node, PlaceObject3 tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatControlTag("PlaceObject3"));
    addLeaf(tagNode, "depth: " + tag.getDepth());
    addLeaf(tagNode, "move: " + tag.isMove());
    if (tag.hasCharacter()) {
      addLeaf(tagNode, "characterId: " + tag.getCharacterId());
    }
    if (tag.hasMatrix()) {
      addLeaf(tagNode, "matrix: " + tag.getMatrix());
    }
    if (tag.hasColorTransform()) {
      addNode(tagNode, "colorTransform: ", tag.getColorTransform());
    }
    if (tag.hasRatio()) {
      addLeaf(tagNode, "ratio: " + tag.getRatio());
    }
    if (tag.hasName()) {
      addLeaf(tagNode, "name: " + tag.getName());
    }
    if (tag.hasClipDepth()) {
      addLeaf(tagNode, "clipDepth: " + tag.getClipDepth());
    }
    addLeaf(tagNode, "cacheAsBitmap: " + tag.isCacheAsBitmap());
    addLeaf(tagNode, "hasImage: " + tag.hasImage());
    addLeaf(tagNode, "hasClassName: " + tag.hasClassName());
    String className = tag.getClassName();
    if (className != null) {
      addLeaf(tagNode, "className: " + tag.getClassName());
    }
    if (tag.hasFilters()) {
      List<Filter> filters = tag.getFilters();
      int count            = filters.size();
      DefaultMutableTreeNode filtersNode = addParentNode(
          tagNode, "filters: Filter[" + count + "]");
      for (int i = 0; i < count; i++) {
        addNode(filtersNode, filters.get(i));
      }
    }
    if (tag.hasBlendMode()) {
      addLeaf(tagNode, "blendMode: " + tag.getBlendMode().toString());
    }
    if (tag.hasClipActions()) {
      addNode(tagNode, "clipActions: ", tag.getClipActions());
    }
  }

  private static void addNode(DefaultMutableTreeNode node, Filter filter) {
    DefaultMutableTreeNode filterNode;
    if (filter instanceof ColorMatrixFilter) {
      ColorMatrixFilter colorMatrixFilter = (ColorMatrixFilter) filter;
      filterNode = addParentNode(node, "ColorMatrixFilter");
      float[] matrix            = colorMatrixFilter.getMatrix();
      StringBuffer matrixBuffer = new StringBuffer("matrix: ");
      for (int i = 0; i < matrix.length; i++) {
        matrixBuffer.append(matrix[i]);
        matrixBuffer.append(" ");
      }
      addLeaf(filterNode, matrixBuffer.toString());
    } else if (filter instanceof ConvolutionFilter) {
      ConvolutionFilter convolutionFilter = (ConvolutionFilter) filter;
      filterNode = addParentNode(node, "ConvolutionFilter");
      addLeaf(filterNode, "matrixRows: " + convolutionFilter.getMatrixRows());
      float[] matrix            = convolutionFilter.getMatrix();
      StringBuffer matrixBuffer = new StringBuffer("matrix: ");
      for (int i = 0; i < matrix.length; i++) {
        matrixBuffer.append(matrix[i]);
        matrixBuffer.append(" ");
      }
      addLeaf(filterNode, matrixBuffer.toString());
      addLeaf(filterNode, "color: " + convolutionFilter.getColor());
      addLeaf(filterNode, "divisor: " + convolutionFilter.getDivisor());
      addLeaf(filterNode, "bias: " + convolutionFilter.getBias());
      addLeaf(filterNode, "clamp: " + convolutionFilter.isClamp());
      addLeaf(
          filterNode, "preserveAlpha: " + convolutionFilter.isPreserveAlpha());
    } else if (filter instanceof BlurFilter) {
      BlurFilter blurFilter = (BlurFilter) filter;
      filterNode = addParentNode(node, "BlurFilter");
      addLeaf(filterNode, "x: " + blurFilter.getX());
      addLeaf(filterNode, "y: " + blurFilter.getY());
      addLeaf(filterNode, "quality: " + blurFilter.getQuality());
    } else if (filter instanceof DropShadowFilter) {
      DropShadowFilter dropShadowFilter = (DropShadowFilter) filter;
      filterNode = addParentNode(node, "DropShadowFilter");
      addLeaf(filterNode, "color: " + dropShadowFilter.getColor());
      addLeaf(filterNode, "x: " + dropShadowFilter.getX());
      addLeaf(filterNode, "y: " + dropShadowFilter.getY());
      addLeaf(filterNode, "angle: " + dropShadowFilter.getAngle());
      addLeaf(filterNode, "distance: " + dropShadowFilter.getDistance());
      addLeaf(filterNode, "strength: " + dropShadowFilter.getStrength());
      addLeaf(filterNode, "quality: " + dropShadowFilter.getQuality());
      addLeaf(filterNode, "inner: " + dropShadowFilter.isInner());
      addLeaf(filterNode, "knockout: " + dropShadowFilter.isKnockout());
      addLeaf(filterNode, "hideObject: " + dropShadowFilter.isHideObject());
    } else if (filter instanceof GlowFilter) {
      GlowFilter glowFilter = (GlowFilter) filter;
      filterNode = addParentNode(node, "GlowFilter");
      addLeaf(filterNode, "color: " + glowFilter.getColor());
      addLeaf(filterNode, "x: " + glowFilter.getX());
      addLeaf(filterNode, "y: " + glowFilter.getY());
      addLeaf(filterNode, "strength: " + glowFilter.getStrength());
      addLeaf(filterNode, "quality: " + glowFilter.getQuality());
      addLeaf(filterNode, "inner: " + glowFilter.isInner());
      addLeaf(filterNode, "knockout: " + glowFilter.isKnockout());
    } else if (filter instanceof BevelFilter) {
      BevelFilter bevelFilter = (BevelFilter) filter;
      filterNode = addParentNode(node, "BevelFilter");
      addLeaf(filterNode, "highlightColor: " + bevelFilter.getHighlightColor());
      addLeaf(filterNode, "shadowColor: " + bevelFilter.getShadowColor());
      addLeaf(filterNode, "x: " + bevelFilter.getX());
      addLeaf(filterNode, "y: " + bevelFilter.getY());
      addLeaf(filterNode, "angle: " + bevelFilter.getAngle());
      addLeaf(filterNode, "distance: " + bevelFilter.getDistance());
      addLeaf(filterNode, "strength: " + bevelFilter.getStrength());
      addLeaf(filterNode, "quality: " + bevelFilter.getQuality());
      addLeaf(filterNode, "inner: " + bevelFilter.isInner());
      addLeaf(filterNode, "knockout: " + bevelFilter.isKnockout());
      addLeaf(filterNode, "onTop: " + bevelFilter.isOnTop());
    } else if (filter instanceof GradientGlowFilter) {
      GradientGlowFilter gradientGlowFilter = (GradientGlowFilter) filter;
      filterNode = addParentNode(node, "GradientGlowFilter");
      RGBA[] colors                            = gradientGlowFilter.getColors();
      short[] ratios                           = gradientGlowFilter.getRatios();
      int controlPointsCount                   = colors.length;
      DefaultMutableTreeNode controlPointsNode = addParentNode(
          filterNode, "control points[" + controlPointsCount + "]");
      for (int i = 0; i < controlPointsCount; i++) {
        addLeaf(
            controlPointsNode,
            "color " + i + ": " + colors[i] + " ratio: " + ratios[i]);
      }
      addLeaf(filterNode, "x: " + gradientGlowFilter.getX());
      addLeaf(filterNode, "y: " + gradientGlowFilter.getY());
      addLeaf(filterNode, "angle: " + gradientGlowFilter.getAngle());
      addLeaf(filterNode, "distance: " + gradientGlowFilter.getDistance());
      addLeaf(filterNode, "strength: " + gradientGlowFilter.getStrength());
      addLeaf(filterNode, "quality: " + gradientGlowFilter.getQuality());
      addLeaf(filterNode, "inner: " + gradientGlowFilter.isInner());
      addLeaf(filterNode, "knockout: " + gradientGlowFilter.isKnockout());
      addLeaf(filterNode, "onTop: " + gradientGlowFilter.isOnTop());
    } else if (filter instanceof GradientBevelFilter) {
      GradientBevelFilter gradientBevelFilter = (GradientBevelFilter) filter;
      filterNode = addParentNode(node, "GradientBevelFilter");
      RGBA[] colors                            = gradientBevelFilter.getColors();
      short[] ratios                           = gradientBevelFilter.getRatios();
      int controlPointsCount                   = colors.length;
      DefaultMutableTreeNode controlPointsNode = addParentNode(
          filterNode, "control points[" + controlPointsCount + "]");
      for (int i = 0; i < controlPointsCount; i++) {
        addLeaf(
            controlPointsNode,
            "color " + i + ": " + colors[i] + " ratio: " + ratios[i]);
      }
      addLeaf(filterNode, "x: " + gradientBevelFilter.getX());
      addLeaf(filterNode, "y: " + gradientBevelFilter.getY());
      addLeaf(filterNode, "angle: " + gradientBevelFilter.getAngle());
      addLeaf(filterNode, "distance: " + gradientBevelFilter.getDistance());
      addLeaf(filterNode, "strength: " + gradientBevelFilter.getStrength());
      addLeaf(filterNode, "quality: " + gradientBevelFilter.getQuality());
      addLeaf(filterNode, "inner: " + gradientBevelFilter.isInner());
      addLeaf(filterNode, "knockout: " + gradientBevelFilter.isKnockout());
      addLeaf(filterNode, "onTop: " + gradientBevelFilter.isOnTop());
    }
  }

  private static void addNode(
      DefaultMutableTreeNode node, String var, ClipActions clipActions) {
    DefaultMutableTreeNode newNode = addParentNode(node, var + "ClipActions");
    addNode(newNode, "allEventFlags: ", clipActions.getEventFlags());
    List<ClipActionRecord> records = clipActions.getClipActionRecords();
    for (int i = 0; i < records.size(); i++) {
      addNode(newNode, records.get(i));
    }
  }

  private static void addNode(
      DefaultMutableTreeNode node, ClipActionRecord clipActionRecord) {
    DefaultMutableTreeNode newNode = addParentNode(node, "ClipActionRecord");
    addNode(newNode, "eventFlags: ", clipActionRecord.getEventFlags());
    if (clipActionRecord.getEventFlags().isKeyPress()) {
      addLeaf(newNode, "keyCode: " + clipActionRecord.getKeyCode());
    }
    addNode(newNode, "actions: ", clipActionRecord.getActions());
  }

  private static void addNode(
      DefaultMutableTreeNode node, String var, ActionBlock actionBlock) {
    List<Action> actionRecords = actionBlock.getActions();
    DefaultMutableTreeNode newNode = addParentNode(
        node,
        var + actionRecords.size() + " actions; size : " +
        actionBlock.getSize());
    for (int i = 0; i < actionRecords.size(); i++) {
      addNode(newNode, actionRecords.get(i));
    }
  }

  private static void addNode(DefaultMutableTreeNode node, Action action) {
    String actionDescription = "<html>";
    if (action.getLabel() != null) {
      actionDescription += ("<code>" + action.getLabel() + "</code> @ ");
    }
    actionDescription += (action.getOffset() + " (" + action.getSize() + "): ");
    switch (action.actionType()) {
    case PUSH:
      actionDescription += getPushDescription((Push) action);
      break;
    case TRY:
      actionDescription += "Try";
      break;
    case IF:
      actionDescription += ("If branchLabel: <code>" +
          ((If) action).getBranchLabel() + "</code> " + "branchOffset: " +
          ((If) action).getBranchOffset());
      break;
    case JUMP:
      actionDescription += ("Jump branchLabel: <code>" +
          ((Jump) action).getBranchLabel() + "</code> " + "branchOffset: " +
          ((Jump) action).getBranchOffset());
      break;
    default:
      actionDescription += action.toString();
    }
    actionDescription += "</html>";
    DefaultMutableTreeNode actionNode = addParentNode(node, actionDescription);
    switch (action.actionType()) {
    case CONSTANT_POOL:
      ConstantPool constantPool = (ConstantPool) action;
      constants = constantPool.getConstants();
      String constStr = "c" + ((constants.size() > 255) ? "16" : "8") + "[";
      for (int i = 0; i < constants.size(); i++) {
        addLeaf(actionNode, constStr + i + "]: " + constants.get(i));
      }
      break;
    case WITH:
      addNode(actionNode, (With) action);
      break;
    case TRY:
      addNode(actionNode, (Try) action);
      break;
    case DEFINE_FUNCTION:
      addNode(actionNode, (DefineFunction) action);
      break;
    case DEFINE_FUNCTION_2:
      addNode(actionNode, (DefineFunction2) action);
      break;
    }
  }

  private static void addNode(DefaultMutableTreeNode node, With action) {
    addNode(node, "withBlock: ", action.getWithBlock());
  }

  private static void addNode(DefaultMutableTreeNode node, Try action) {
    boolean catchInRegister = action.catchInRegister();
    addLeaf(node, "catchInRegister: " + catchInRegister);
    if (catchInRegister) {
      addLeaf(node, "catchRegister: " + action.getCatchRegister());
    } else {
      addLeaf(node, "catchVariable: " + action.getCatchVariable());
    }
    addNode(node, "tryBlock: ", action.getTryBlock());
    if (action.hasCatchBlock()) {
      addNode(node, "catchBlock: ", action.getCatchBlock());
    }
    if (action.hasFinallyBlock()) {
      addNode(node, "finallyBlock: ", action.getFinallyBlock());
    }
  }

  private static void addNode(
      DefaultMutableTreeNode node, AbcFile abcFile) {
    DefaultMutableTreeNode abcFileNode = addParentNode(node, "abc block: AbcFile");
    addLeaf(abcFileNode, "Version: " + abcFile.getMajorVersion() + "." + abcFile.getMinorVersion());
    AbcConstantPool constantPool = abcFile.getConstantPool();
    addNode(abcFileNode, constantPool);
    addParentNode(abcFileNode, "Methods");
    addParentNode(abcFileNode, "Metadata Entries");
    addParentNode(abcFileNode, "Instances");
    addParentNode(abcFileNode, "Classes");
    addParentNode(abcFileNode, "Scripts");
    addParentNode(abcFileNode, "Method Bodies");
  }

  private static void addNode(DefaultMutableTreeNode node, AbcConstantPool constantPool) {
    DefaultMutableTreeNode constantPoolNode = addParentNode(node, "Constant pool");
    DefaultMutableTreeNode intsNode = addParentNode(constantPoolNode, "Integer values");
    List<Integer> ints = constantPool.getInts();
    for (int i = 0; i < ints.size(); i++) {
      addLeaf(intsNode, i + ": " + ints.get(i));
    }
    DefaultMutableTreeNode uintsNode = addParentNode(constantPoolNode, "Unsigned integer values");
    List<Integer> uints = constantPool.getUints();
    for (int i = 0; i < uints.size(); i++) {
      addLeaf(uintsNode, i + ": " + uints.get(i));
    }
    DefaultMutableTreeNode doublesNode = addParentNode(constantPoolNode, "Double values");
    List<Double> doubles = constantPool.getDoubles();
    for (int i = 0; i < doubles.size(); i++) {
      addLeaf(doublesNode, i + ": " + doubles.get(i));
    }
    DefaultMutableTreeNode stringsNode = addParentNode(constantPoolNode, "Strings");
    List<String> strings = constantPool.getStrings();
    for (int i = 0; i < strings.size(); i++) {
      addLeaf(stringsNode, i + ": \"" + strings.get(i) + "\"");
    }
    DefaultMutableTreeNode namespacesNode = addParentNode(constantPoolNode, "Namespaces");
    List<AbcNamespace> namespaces = constantPool.getNamespaces();
    for (int i = 0; i < namespaces.size(); i++) {
      AbcNamespace ns = namespaces.get(i);
      String nsDescription = getNamespaceDescription(strings, ns);
      addLeaf(namespacesNode, i + " : " + nsDescription);
    }
    DefaultMutableTreeNode namespaceSetsNode = addParentNode(constantPoolNode, "Namespace sets");
    List<AbcNamespaceSet> namespaceSets = constantPool.getNamespaceSets();
    for (int i = 0; i < namespaceSets.size(); i++) {
      AbcNamespaceSet namespaceSet = namespaceSets.get(i);
      DefaultMutableTreeNode nsSetNode = addParentNode(namespaceSetsNode, "Set " + i);
      if (namespaceSet == null) {
        continue;
      }
      List<Integer> namespaceIndices = namespaceSet.getNamespaceIndices();
      for (int j = 0; j < namespaceIndices.size(); j++) {
        int namespaceIndex = namespaceIndices.get(j);
        addLeaf(nsSetNode, "namespaces[" + namespaceIndex + "]: " + getNamespaceDescription(strings, namespaces.get(namespaceIndex)));
      }
    }
    //DefaultMutableTreeNode multinamesNode = addParentNode(constantPoolNode, "Multinames");
    //List<AbcMultiname> multinames = constantPool.getMultinames();
  }

  private static String getNamespaceDescription(List<String> strings, AbcNamespace ns) {
    int nameIndex = ns.getNameIndex();
    String kind = ( ns != null ? ns.getKind().toString().replaceAll("_", "@") : "NULL" );
    String nsDescription = "Namespace kind: " + kind + " name: strings[" + nameIndex + "]=\"" + strings.get(nameIndex) + "\"";
    return nsDescription;
  }

  private static void addNode(
      DefaultMutableTreeNode node, DefineFunction defineFunction) {
    String[] parameters = defineFunction.getParameters();
    String paramList    = "";
    for (int i = 0; i < parameters.length; i++) {
      paramList += (parameters[i]);
      if (i != (parameters.length - 1)) {
        paramList += ", ";
      }
    }
    addLeaf(node, "parameters: " + paramList);
    addNode(node, "body: ", defineFunction.getBody());
  }

  private static void addNode(
      DefaultMutableTreeNode node, DefineFunction2 defineFunction2) {
    DefaultMutableTreeNode headerNode = addParentNode(node, "header");
    RegisterParam[] regParameters     = defineFunction2.getParameters();
    DefaultMutableTreeNode paramsNode = addParentNode(
        headerNode, "parameters: RegisterParam[" + regParameters.length + "]");
    for (int i = 0; i < regParameters.length; i++) {
      DefaultMutableTreeNode paramNode = addParentNode(
          paramsNode, "RegisterParam");
      RegisterParam regParam           = regParameters[i];
      addLeaf(paramNode, "register: " + regParam.getRegister());
      addLeaf(paramNode, "paramName: " + regParam.getParamName());
    }
    addLeaf(headerNode, "registerCount: " + defineFunction2.getRegisterCount());
    addLeaf(headerNode, "suppressThis: " + defineFunction2.suppressesThis());
    addLeaf(headerNode, "preloadThis: " + defineFunction2.preloadsThis());
    addLeaf(
        headerNode, "suppressArguments: " +
        defineFunction2.suppressesArguments());
    addLeaf(
        headerNode, "preloadArguments: " + defineFunction2.preloadsArguments());
    addLeaf(headerNode, "suppressSuper: " + defineFunction2.suppressesSuper());
    addLeaf(headerNode, "preloadSuper: " + defineFunction2.preloadsSuper());
    addLeaf(headerNode, "preloadRoot: " + defineFunction2.preloadsRoot());
    addLeaf(headerNode, "preloadParent: " + defineFunction2.preloadsParent());
    addLeaf(headerNode, "preloadGlobal: " + defineFunction2.preloadsGlobal());
    addNode(node, "body: ", defineFunction2.getBody());
  }

  private static void addNode(
      DefaultMutableTreeNode node, String var, ClipEventFlags clipEventFlags) {
    DefaultMutableTreeNode newNode = addParentNode(
        node, var + "ClipEventFlags");
    if (clipEventFlags == null) return;
    addLeaf(newNode, "keyUp: " + clipEventFlags.isKeyUp());
    addLeaf(newNode, "keyDown: " + clipEventFlags.isKeyDown());
    addLeaf(newNode, "mouseUp: " + clipEventFlags.isMouseUp());
    addLeaf(newNode, "mouseDown: " + clipEventFlags.isMouseDown());
    addLeaf(newNode, "mouseMove: " + clipEventFlags.isMouseMove());
    addLeaf(newNode, "unload: " + clipEventFlags.isUnload());
    addLeaf(newNode, "enterFrame: " + clipEventFlags.isEnterFrame());
    addLeaf(newNode, "load: " + clipEventFlags.isLoad());
    addLeaf(newNode, "dragOver: " + clipEventFlags.isDragOver());
    addLeaf(newNode, "rollOut: " + clipEventFlags.isRollOut());
    addLeaf(newNode, "rollOver: " + clipEventFlags.isRollOver());
    addLeaf(newNode, "releaseOutside: " + clipEventFlags.isReleaseOutside());
    addLeaf(newNode, "release: " + clipEventFlags.isRelease());
    addLeaf(newNode, "press: " + clipEventFlags.isPress());
    addLeaf(newNode, "initialize: " + clipEventFlags.isInitialize());
    addLeaf(newNode, "data: " + clipEventFlags.isData());
    addLeaf(newNode, "construct: " + clipEventFlags.isConstruct());
    addLeaf(newNode, "keyPress: " + clipEventFlags.isKeyPress());
    addLeaf(newNode, "dragOut: " + clipEventFlags.isDragOut());
  }

  private static void addNode(DefaultMutableTreeNode node, Protect tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatControlTag("Protect"));
    String password                = tag.getPassword();
    if (password == null) {
      addLeaf(tagNode, "No password");
    } else {
      addLeaf(tagNode, "password: " + password);
    }
  }

  private static void addNode(DefaultMutableTreeNode node, RemoveObject tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatControlTag("RemoveObject"));
    addLeaf(tagNode, "characterId: " + tag.getCharacterId());
    addLeaf(tagNode, "depth: " + tag.getDepth());
  }

  private static void addNode(DefaultMutableTreeNode node, RemoveObject2 tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatControlTag("RemoveObject2"));
    addLeaf(tagNode, "depth: " + tag.getDepth());
  }

  private static void addNode(DefaultMutableTreeNode node, ScriptLimits tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatControlTag("ScriptLimits"));
    addLeaf(tagNode, "maxRecursionDepth: " + tag.getMaxRecursionDepth());
    addLeaf(tagNode, "scriptTimeoutSeconds: " + tag.getScriptTimeoutSeconds());
  }

  private static void addNode(
      DefaultMutableTreeNode node, SetBackgroundColor tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatControlTag("SetBackgroundColor"));
    addLeaf(tagNode, "color: " + tag.getColor());
  }

  private static void addNode(DefaultMutableTreeNode node, SetTabIndex tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatControlTag("SetTabIndex"));
    addLeaf(tagNode, "depth: " + tag.getDepth());
    addLeaf(tagNode, "tabIndex: " + tag.getTabIndex());
  }

  private static void addNode(DefaultMutableTreeNode node, ShowFrame tag) {
    addLeaf(node, formatControlTag("ShowFrame"));
  }

  private static void addNode(
      DefaultMutableTreeNode node, SoundStreamBlock tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatControlTag("SoundStreamBlock"));
    addLeaf(
        tagNode, "streamSoundData: byte[" + tag.getStreamSoundData().length +
    "]");
  }

  private static void addNode(DefaultMutableTreeNode node, Scale9Grid tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatControlTag("Scale9Grid"));
    addLeaf(tagNode, "characterId: " + tag.getCharacterId());
    addLeaf(tagNode, "grid: " + tag.getGrid());
  }

  private static void addNode(DefaultMutableTreeNode node, SoundStreamHead tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatControlTag("SoundStreamHead"));
    byte rate                      = tag.getPlaybackRate();
    addLeaf(tagNode, "playbackRate: " + getSoundRateString(rate));
    String type = tag.isPlaybackStereo() ? "stereo" : "mono";
    addLeaf(tagNode, "playbackType: " + type);
    byte format = tag.getStreamFormat();
    addLeaf(tagNode, "streamFormat: " + getSoundFormatString(format));
    rate = tag.getStreamRate();
    addLeaf(tagNode, "streamRate: " + getSoundRateString(rate));
    type = tag.isStreamStereo() ? "stereo" : "mono";
    addLeaf(tagNode, "streamType: " + type);
    addLeaf(tagNode, "streamSampleCount: " + tag.getStreamSampleCount());
    if (format == SoundStreamHead.FORMAT_MP3) {
      addLeaf(tagNode, "latencySeek: " + tag.getLatencySeek());
    }
  }

  private static void addNode(
      DefaultMutableTreeNode node, SoundStreamHead2 tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatControlTag("SoundStreamHead2"));
    byte rate                      = tag.getPlaybackRate();
    addLeaf(tagNode, "playbackRate: " + getSoundRateString(rate));
    String size = tag.isPlayback16BitSample() ? "16 bit" : "8 bit";
    addLeaf(tagNode, "playbackSize: " + size);
    String type = tag.isPlaybackStereo() ? "stereo" : "mono";
    addLeaf(tagNode, "playbackType: " + type);
    byte format = tag.getStreamFormat();
    addLeaf(tagNode, "streamFormat: " + getSoundFormatString(format));
    rate = tag.getStreamRate();
    addLeaf(tagNode, "streamRate: " + getSoundRateString(rate));
    size = tag.isStream16BitSample() ? "16 bit" : "8 bit";
    addLeaf(tagNode, "playbackSize: " + size);
    type = tag.isStreamStereo() ? "stereo" : "mono";
    addLeaf(tagNode, "streamType: " + type);
    addLeaf(tagNode, "streamSampleCount: " + tag.getStreamSampleCount());
    if (format == SoundStreamHead.FORMAT_MP3) {
      addLeaf(tagNode, "latencySeek: " + tag.getLatencySeek());
    }
  }

  private static void addNode(DefaultMutableTreeNode node, StartSound tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatControlTag("StartSound"));
    addLeaf(tagNode, "soundId: " + tag.getSoundId());
    addNode(tagNode, "soundInfo: ", tag.getSoundInfo());
  }

  private static void addNode(
      DefaultMutableTreeNode node, String var, SoundInfo info) {
    DefaultMutableTreeNode siNode = addParentNode(node, var + "SoundInfo");
    addLeaf(siNode, "syncStop: " + info.isSyncStop());
    addLeaf(siNode, "syncNoMultiple: " + info.isSyncNoMultiple());
    if (info.getInPoint() != 0) {
      addLeaf(siNode, "inPoint: " + info.getInPoint());
    }
    if (info.getOutPoint() != 0) {
      addLeaf(siNode, "outPoint: " + info.getOutPoint());
    }
    if (info.getLoopCount() != 0) {
      addLeaf(siNode, "loopCount: " + info.getLoopCount());
    }
    if (info.getEnvelopeRecords() != null) {
      SoundEnvelope[] records               = info.getEnvelopeRecords();
      DefaultMutableTreeNode envRecordsNode = addParentNode(
          siNode, "envelopeRecords: SoundEnvelope[" + records.length + "]");
      for (int i = 0; i < records.length; i++) {
        SoundEnvelope env              = records[i];
        DefaultMutableTreeNode recNode = addParentNode(
            envRecordsNode, "SoundEnvelope");
        addLeaf(recNode, "pos44: " + env.getPos44());
        addLeaf(recNode, "leftLevel: " + env.getLeftLevel());
        addLeaf(recNode, "rightLevel: " + env.getRightLevel());
      }
    }
  }

  private static void addNode(DefaultMutableTreeNode node, SymbolClass tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatControlTag("SymbolClass"));
    List<SymbolReference> references = tag.getReferences();
    for (Iterator<SymbolReference> it = references.iterator(); it.hasNext(); ) {
      SymbolReference symbolReference = it.next();
      addLeaf(
          tagNode,
          "characterId: " + symbolReference.getCharacterId() + ", symbol name: " +
          symbolReference.getName());
    }
  }

  private static void addNode(DefaultMutableTreeNode node, UnknownTag tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatControlTag("Unknown tag (" + tag.tagCode() + ")"));
    byte[] data                    = tag.getData();
    addLeaf(tagNode, "size: " + data.length);
    addLeaf(tagNode, "data: " + HexUtils.toHex(data));
  }

  private static void addNode(DefaultMutableTreeNode node, ProductInfo tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatControlTag("ProductInfo"));
    addLeaf(tagNode, "productId: " + tag.getProductId());
    addLeaf(tagNode, "edition: " + tag.getEdition());
    addLeaf(tagNode, "major version: " + tag.getMajorVersion());
    addLeaf(tagNode, "minor version: " + tag.getMinorVersion());
    addLeaf(tagNode, "build number: " + tag.getBuildNumber());
    addLeaf(tagNode, "build date: " + tag.getBuildDate());
  }

  private static void addNode(DefaultMutableTreeNode node, DebugId tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatControlTag("DebugId"));
    addLeaf(tagNode, "id: " + tag.getId());
  }

  private static void addNode(DefaultMutableTreeNode node, DefineBinaryData tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatControlTag("DefineBinaryData"));
    byte[] data                    = tag.getBinaryData();
    addLeaf(tagNode, "characterId: " + tag.getCharacterId());
    addLeaf(tagNode, "data: " + HexUtils.toHex(data));
  }

  private static void addNode(DefaultMutableTreeNode node, VideoFrame tag) {
    DefaultMutableTreeNode tagNode = addParentNode(
        node, formatControlTag("VideoFrame"));
    addLeaf(tagNode, "streamId: " + tag.getStreamId());
    addLeaf(tagNode, "frameNum: " + tag.getFrameNum());
    addLeaf(tagNode, "videoData: " + tag.getVideoData().length + " bytes");
  }

  private static DefaultMutableTreeNode addParentNode(
      DefaultMutableTreeNode node, String string) {
    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(string);
    node.insert(newNode, node.getChildCount());
    nodes++;
    return newNode;
  }

  private static String formatControlTag(String tagName) {
    return "<html><font color=\"#0000B0\">" + tagName + "</font></html>";
  }

  private static String formatDefTag(String tagName) {
    return "<html><font color=\"#B00000\">" + tagName + "</font></html>";
  }
}

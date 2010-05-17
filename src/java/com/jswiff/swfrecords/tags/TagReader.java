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

package com.jswiff.swfrecords.tags;

import com.jswiff.constants.TagConstants.TagType;
import com.jswiff.exception.InvalidCodeException;
import com.jswiff.io.InputBitStream;

import java.io.IOException;

/**
 * This class contains methods used for parsing tag headers and tags.
 */
public final class TagReader {

  private static final long serialVersionUID = 1L;

  private TagReader() {
    // prohibits instantiation
  }

  /**
   * Reads a tag from a data buffer. The tag header must be parsed before
   * invoking this method.
   * 
   * @param header
   *          tag header
   * @param tagData
   *          data buffer containing the tag to be read
   * @param swfVersion
   *          flash version (from the SWF file header)
   * @param japanese
   *          specifies whether japanese encoding is to be used for strings
   * 
   * @return the read tag
   * 
   * @throws IOException
   *           if I/O problems occur
   */
  public static Tag readTag(TagHeader header, byte[] tagData, short swfVersion, boolean japanese) throws IOException {
    Tag tag;
    TagType tagType;
    try {
      tagType = TagType.lookup(header.getCode());
    } catch (InvalidCodeException e) {
      tagType = TagType.UNKNOWN_TAG;
    }
    switch (tagType) {
    case DEBUG_ID:
      tag = new DebugId();
      break;
    case DEFINE_BINARY_DATA:
      tag = new DefineBinaryData();
      break;
    case DEFINE_BITS:
      tag = new DefineBits();
      break;
    case DEFINE_BITS_JPEG_2:
      tag = new DefineBitsJPEG2();
      break;
    case DEFINE_BITS_JPEG_3:
      tag = new DefineBitsJPEG3();
      break;
    case DEFINE_BITS_LOSSLESS:
      tag = new DefineBitsLossless();
      break;
    case DEFINE_BITS_LOSSLESS_2:
      tag = new DefineBitsLossless2();
      break;
    case DEFINE_BUTTON:
      tag = new DefineButton();
      break;
    case DEFINE_BUTTON_2:
      tag = new DefineButton2();
      break;
    case DEFINE_BUTTON_C_XFORM:
      tag = new DefineButtonCXform();
      break;
    case DEFINE_BUTTON_SOUND:
      tag = new DefineButtonSound();
      break;
    case DEFINE_EDIT_TEXT:
      tag = new DefineEditText();
      break;
    case DEFINE_FONT:
      tag = new DefineFont();
      break;
    case DEFINE_FONT_2:
      tag = new DefineFont2();
      break;
    case DEFINE_FONT_3:
      tag = new DefineFont3();
      break;
    case DEFINE_FONT_INFO:
      tag = new DefineFontInfo();
      break;
    case DEFINE_FONT_INFO_2:
      tag = new DefineFontInfo2();
      break;
    case DEFINE_FONT_NAME:
      tag = new DefineFontName();
      break;
    case FLASHTYPE_SETTINGS:
      tag = new FlashTypeSettings();
      break;
    case DEFINE_FONT_ALIGNMENT:
      tag = new DefineFontAlignment();
      break;
    case DEFINE_MORPH_SHAPE:
      tag = new DefineMorphShape();
      break;
    case DEFINE_MORPH_SHAPE_2:
      tag = new DefineMorphShape2();
      break;
    case DEFINE_SCENE_FRAME_DATA:
      tag = new DefineSceneFrameData();
      break;
    case DEFINE_SHAPE:
      tag = new DefineShape();
      break;
    case DEFINE_SHAPE_2:
      tag = new DefineShape2();
      break;
    case DEFINE_SHAPE_3:
      tag = new DefineShape3();
      break;
    case DEFINE_SHAPE_4:
      tag = new DefineShape4();
      break;
    case DEFINE_SOUND:
      tag = new DefineSound();
      break;
    case DEFINE_SPRITE:
      tag = new DefineSprite();
      break;
    case DEFINE_TEXT:
      tag = new DefineText();
      break;
    case DEFINE_TEXT_2:
      tag = new DefineText2();
      break;
    case DEFINE_VIDEO_STREAM:
      tag = new DefineVideoStream();
      break;
    case DO_ABC:
      tag = new DoAbc();
      break;
    case DO_ABC_DEFINE:
      tag = new DoAbcDefine();
      break;
    case DO_ACTION:
      tag = new DoAction();
      break;
    case DO_INIT_ACTION:
      tag = new DoInitAction();
      break;
    case ENABLE_DEBUGGER_2:
      tag = new EnableDebugger2();
      break;
    case ENABLE_DEBUGGER:
      tag = new EnableDebugger();
      break;
    case EXPORT_ASSETS:
      tag = new ExportAssets();
      break;
    case FILE_ATTRIBUTES:
      tag = new FileAttributes();
      break;
    case FRAME_LABEL:
      tag = new FrameLabel();
      break;
    case IMPORT_ASSETS:
      tag = new ImportAssets();
      break;
    case IMPORT_ASSETS_2:
      tag = new ImportAssets2();
      break;
    case JPEG_TABLES:
      tag = new JPEGTables();
      break;
    case METADATA:
      tag = new Metadata();
      break;
    case PLACE_OBJECT:
      tag = new PlaceObject();
      break;
    case PLACE_OBJECT_2:
      tag = new PlaceObject2();
      break;
    case PLACE_OBJECT_3:
      tag = new PlaceObject3();
      break;
    case PRODUCT_INFO:
      tag = new ProductInfo();
      break;
    case PROTECT:
      tag = new Protect();
      break;
    case REMOVE_OBJECT:
      tag = new RemoveObject();
      break;
    case REMOVE_OBJECT_2:
      tag = new RemoveObject2();
      break;
    case SCRIPT_LIMITS:
      tag = new ScriptLimits();
      break;
    case SET_BACKGROUND_COLOR:
      tag = new SetBackgroundColor();
      break;
    case SET_TAB_INDEX:
      tag = new SetTabIndex();
      break;
    case SHOW_FRAME:
      tag = new ShowFrame();
      break;
    case SCALE_9_GRID:
      tag = new Scale9Grid();
      break;
    case SOUND_STREAM_BLOCK:
      tag = new SoundStreamBlock();
      break;
    case SOUND_STREAM_HEAD:
      tag = new SoundStreamHead();
      break;
    case SOUND_STREAM_HEAD_2:
      tag = new SoundStreamHead2();
      break;
    case START_SOUND:
      tag = new StartSound();
      break;
    case SYMBOL_CLASS:
      tag = new SymbolClass();
      break;
    case VIDEO_FRAME:
      tag = new VideoFrame();
      break;
    case END:
      tag = new End();
      break;
    case FREE_CHARACTER:
      tag = new FreeCharacter();
      break;
    case UNKNOWN_TAG:
      tag = new UnknownTag(header.getCode());
      break;
    case MALFORMED:
      throw new IOException("Malformed Tag encountered! The Swf contains one or more corrupted tags");
    default:
      throw new AssertionError("Tag type '" + tagType.name() + "' not handled!");
    }
    tag.setSWFVersion(swfVersion);
    tag.setJapanese(japanese);
    tag.setData(tagData);
    return tag;
  }

  /**
   * Reads a tag from a bit stream as raw data. The tag header must be read
   * before invoking this method.
   * 
   * @param stream
   *          source bit stream
   * @param header
   *          tag header
   * 
   * @return tag as data buffer
   * 
   * @throws IOException
   *           if an I/O error occured
   */
  public static byte[] readTagData(InputBitStream stream, TagHeader header) throws IOException {
    return stream.readBytes(header.getLength());
  }

  /**
   * Reads a tag header from a bit stream.
   * 
   * @param stream
   *          source bit stream
   * 
   * @return the parsed tag header
   * 
   * @throws IOException
   *           if an I/O error occured
   */
  public static TagHeader readTagHeader(InputBitStream stream) throws IOException {
    return new TagHeader(stream);
  }

  /*
   * Reads a tag from a bit stream.
   */
  static Tag readTag(InputBitStream stream, short swfVersion, boolean shiftJIS) throws IOException {
    TagHeader header = new TagHeader(stream);
    byte[] tagData = stream.readBytes(header.getLength());
    return readTag(header, tagData, swfVersion, shiftJIS);
  }
}

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

package com.jswiff.hl.factories;

import com.jswiff.SWFDocument;
import com.jswiff.SWFWriter;
import com.jswiff.io.InputBitStream;
import com.jswiff.swfrecords.SoundInfo;
import com.jswiff.swfrecords.tags.DefineSound;
import com.jswiff.swfrecords.tags.ShowFrame;
import com.jswiff.swfrecords.tags.SoundStreamHead2;
import com.jswiff.swfrecords.tags.StartSound;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;


/**
 * Creates a document including an uncompressed wav sound file.
 *
 * @author <a href="mailto:ralf@terdic.de">Ralf Terdic</a>
 */
public class WAVDocumentFactory {
  private static final int WAVE_FORMAT_DESCRIPTOR   = 0x45564157;
  private static final int RIFF_CHUNK_DESCRIPTOR    = 0x46464952;
  private static final int FMT_SUBCHUNK_DESCRIPTOR  = 0x20746d66;
  private static final int DATA_SUBCHUNK_DESCRIPTOR = 0x61746164;
  private InputBitStream wavBitStream;
  private byte[] soundData;
  private SWFDocument doc;
  private int sampleCount;
  private int sampleSize;
  private int channelCount;
  private int samplingRate;
  private boolean is16BitSample;

  /**
   * <p>
   * Creates a new WAVDocumentFactory instance. The passed AudioInputStream is
   * converted to a linear PCM WAV (so you may be able to use other formats
   * like .au). Sample rates of 5512, 11025, 22050 and 44100 Hz are supported.
   * </p>
   * 
   * <p>
   * Note: you can use the following to get an AudioInputStream:
   * </p>
   * <code> AudioInputStream audioInputStream =
   * AudioSystem.getAudioInputStream((bufferedInputStream)); </code>
   *
   * @param audioInputStream audio input stream
   *
   * @throws IOException if something went wrong while reading from the audio
   *         stream
   */
  public WAVDocumentFactory(AudioInputStream audioInputStream)
    throws IOException {
    audioInputStream = AudioSystem.getAudioInputStream(
        AudioFormat.Encoding.PCM_SIGNED, audioInputStream);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, baos);
    byte[] buffer = baos.toByteArray();
    wavBitStream = new InputBitStream(new ByteArrayInputStream(buffer));
  }

  /**
   * Creates a new WAVDocumentFactory instance. Sample rates of 5512, 11025,
   * 22050 and 44100 kHz are supported.
   *
   * @param wavStream WAV input stream
   */
  public WAVDocumentFactory(InputStream wavStream) {
    wavBitStream = new InputBitStream(wavStream);
  }

  /**
   * Returns the created document.
   *
   * @return SWF doc
   *
   * @throws IOException if an error occured during parsing the wav stream
   */
  public SWFDocument getDocument() throws IOException {
    initDocument();
    return doc;
  }

  /**
   * Changes the sampling rate of an audio input stream.
   *
   * @param audioInputStream source stream
   * @param sampleRate new sampling rate
   *
   * @return altered stream
   */
  public static AudioInputStream convertSampleRate(
    AudioInputStream audioInputStream, float sampleRate) {
    AudioFormat sourceFormat = audioInputStream.getFormat();
    AudioFormat targetFormat = new AudioFormat(
        sourceFormat.getEncoding(), sampleRate,
        sourceFormat.getSampleSizeInBits(), sourceFormat.getChannels(),
        sourceFormat.getFrameSize(), sampleRate, sourceFormat.isBigEndian());
    return AudioSystem.getAudioInputStream(targetFormat, audioInputStream);
  }

  /**
   * Main method for quick tests, pass input file (wav) and output file (swf).
   *
   * @param args wav, swf files
   *
   * @throws IOException if an I/O error occured
   * @throws UnsupportedAudioFileException
   */
  public static void main(String[] args)
    throws IOException, UnsupportedAudioFileException {
    String wavFileName                 = args[0];
    String swfFileName                 = args[1];
    WAVDocumentFactory documentFactory;
    if (wavFileName.toLowerCase().endsWith("wav")) {
      documentFactory = new WAVDocumentFactory(
          new FileInputStream(wavFileName));
    } else {
      documentFactory = new WAVDocumentFactory(
          AudioSystem.getAudioInputStream(
            new BufferedInputStream(new FileInputStream(wavFileName), 1024)));
    }

    SWFDocument document = documentFactory.getDocument();
    SWFWriter writer     = new SWFWriter(
        document, new FileOutputStream(swfFileName));
    writer.write();
  }

  private byte getSamplingRateCode() throws IOException {
    switch (samplingRate) {
      case 5512:
        return 0;
      case 11025:
        return 1;
      case 22050:
        return 2;
      case 44100:
        return 3;
      default:
        throw new IOException("Unsupported sampling rate: " + samplingRate);
    }
  }

  private void initDocument() throws IOException {
    doc = new SWFDocument();
    doc.setCompressed(true);
    readData();
    doc.addTag(
      new SoundStreamHead2(
        SoundStreamHead2.FORMAT_UNCOMPRESSED, getSamplingRateCode(),
        is16BitSample, (channelCount == 2), 0));
    int soundId = doc.getNewCharacterId();
    doc.addTag(
      new DefineSound(
        soundId, DefineSound.FORMAT_UNCOMPRESSED, getSamplingRateCode(),
        is16BitSample, (channelCount == 2), sampleCount, soundData));
    doc.addTag(new StartSound(soundId, new SoundInfo()));
    doc.addTag(new ShowFrame());
  }

  private void readData() throws IOException {
    if (wavBitStream.readUI32() != RIFF_CHUNK_DESCRIPTOR) {
      throw new IOException(
        "Illegal WAV format, RIFF chunk descriptor missing!");
    }
    wavBitStream.readUI32(); // chunk size
    if (wavBitStream.readUI32() != WAVE_FORMAT_DESCRIPTOR) {
      throw new IOException(
        "Illegal WAV format, WAVE format descriptor missing!");
    }
    boolean dataSubchunkFound = false;
    while (!dataSubchunkFound) {
      long subchunkID     = wavBitStream.readUI32();
      long subchunkSize   = wavBitStream.readUI32();
      byte[] subchunkData = wavBitStream.readBytes((int) subchunkSize);
      if (subchunkID == FMT_SUBCHUNK_DESCRIPTOR) {
        readFMTSubchunk(subchunkData);
      } else if (subchunkID == DATA_SUBCHUNK_DESCRIPTOR) {
        soundData           = subchunkData;
        sampleCount         = (int) ((subchunkSize * 8) / (sampleSize * channelCount));
        dataSubchunkFound   = true;
      }
    }
  }

  private void readFMTSubchunk(byte[] subchunkData) throws IOException {
    InputBitStream fmtBitStream = new InputBitStream(subchunkData);
    int format                  = fmtBitStream.readUI16();
    if (format != 1) {
      throw new IOException(
        "Compressed WAV found, only linear quantization (PCM) supported!");
    }
    channelCount   = fmtBitStream.readUI16();
    samplingRate   = (int) fmtBitStream.readUI32();
    fmtBitStream.readUI32(); // byte rate
    fmtBitStream.readUI16(); // block align
    sampleSize      = fmtBitStream.readUI16();
    is16BitSample   = (sampleSize == 16); // || (chunkFormat != 1)
  }
}

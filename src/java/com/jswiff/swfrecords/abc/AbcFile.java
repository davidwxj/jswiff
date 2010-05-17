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

package com.jswiff.swfrecords.abc;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AbcFile implements Serializable {

  private static final long serialVersionUID = 1L;
  
  private int majorVersion;
  private int minorVersion;
  private AbcConstantPool constantPool;
  private List<AbcMethodSignature> methods = new ArrayList<AbcMethodSignature>();
  private List<AbcMetadata> metadataEntries = new ArrayList<AbcMetadata>();
  private List<AbcInstance> instances = new ArrayList<AbcInstance>();
  private List<AbcClass> classes = new ArrayList<AbcClass>();
  private List<AbcScript> scripts = new ArrayList<AbcScript>();
  private List<AbcMethodBody> methodBodies = new ArrayList<AbcMethodBody>();
  
  public AbcFile() {
    majorVersion = 46;
    minorVersion = 16;
    constantPool = new AbcConstantPool();
  }
  
  public void read(InputBitStream stream) throws IOException {
    minorVersion = stream.readUI16();
    majorVersion = stream.readUI16();
    if (majorVersion != 46 || minorVersion > 16) {
      throw new IOException("Unsupported abc format, version > 46.16");
    }
    constantPool = AbcConstantPool.read(stream);
    int methodCount = stream.readAbcInt();
    for (int i = 0; i < methodCount; i++) {
      methods.add(AbcMethodSignature.read(stream));
    }
    int metadataCount = stream.readAbcInt();
    for (int i = 0; i < metadataCount; i++) {
      metadataEntries.add(AbcMetadata.read(stream));
    }
    int classCount = stream.readAbcInt();
    for (int i = 0; i < classCount; i++) {
      instances.add(AbcInstance.read(stream));
    }
    for (int i = 0; i < classCount; i++) {
      classes.add(AbcClass.read(stream));
    }
    int scriptCount = stream.readAbcInt();
    for (int i = 0; i < scriptCount; i++) {
      scripts.add(AbcScript.read(stream));
    }
    int methodBodyCount = stream.readAbcInt();
    for (int i = 0; i < methodBodyCount; i++) {
      methodBodies.add(AbcMethodBody.read(stream));
    }
    //stream.readBytes(1); // have we read everything?
  }
  
  public void write(OutputBitStream stream) throws IOException {
    stream.writeUI16(minorVersion);
    stream.writeUI16(majorVersion);
    constantPool.write(stream);
    stream.writeAbcInt(methods.size());
    for (AbcMethodSignature sig : methods) {
      sig.write(stream);
    }
    stream.writeAbcInt(metadataEntries.size());
    for (AbcMetadata metaData : metadataEntries) {
      metaData.write(stream);
    }
    stream.writeAbcInt(instances.size());
    for (AbcInstance instance : instances) {
      instance.write(stream);
    }
    for (AbcClass abcClass : classes) {
      abcClass.write(stream);
    }
    stream.writeAbcInt(scripts.size());
    for (AbcScript script : scripts) {
      script.write(stream);
    }
    stream.writeAbcInt(methodBodies.size());
    for (AbcMethodBody method : methodBodies) {
      method.write(stream);
    }
  }

  public int getMajorVersion() {
    return majorVersion;
  }

  public int getMinorVersion() {
    return minorVersion;
  }

  public AbcConstantPool getConstantPool() {
    return constantPool;
  }

  public List<AbcMethodSignature> getMethods() {
    return methods;
  }

  public List<AbcMetadata> getMetadataEntries() {
    return metadataEntries;
  }

  public List<AbcInstance> getInstances() {
    return instances;
  }

  public List<AbcClass> getClasses() {
    return classes;
  }

  public List<AbcScript> getScripts() {
    return scripts;
  }

  public List<AbcMethodBody> getMethodBodies() {
    return methodBodies;
  }
}

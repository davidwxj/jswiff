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

package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

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
  
  public static AbcFile read(InputBitStream stream) throws IOException {
    AbcFile abcFile = new AbcFile();
    abcFile.minorVersion = stream.readUI16();
    abcFile.majorVersion = stream.readUI16();
    if (abcFile.majorVersion != 46 || abcFile.minorVersion > 16) {
      throw new IOException("Unsupported abc format, version > 46.16");
    }
    abcFile.constantPool = AbcConstantPool.read(stream);
    int methodCount = stream.readAbcInt();
    for (int i = 0; i < methodCount; i++) {
      abcFile.methods.add(AbcMethodSignature.read(stream));
    }
    int metadataCount = stream.readAbcInt();
    for (int i = 0; i < metadataCount; i++) {
      abcFile.metadataEntries.add(AbcMetadata.read(stream));
    }
    int classCount = stream.readAbcInt();
    for (int i = 0; i < classCount; i++) {
      abcFile.instances.add(AbcInstance.read(stream));
    }
    for (int i = 0; i < classCount; i++) {
      abcFile.classes.add(AbcClass.read(stream));
    }
    int scriptCount = stream.readAbcInt();
    for (int i = 0; i < scriptCount; i++) {
      abcFile.scripts.add(AbcScript.read(stream));
    }
    int methodBodyCount = stream.readAbcInt();
    for (int i = 0; i < methodBodyCount; i++) {
      abcFile.methodBodies.add(AbcMethodBody.read(stream));
    }
    //stream.readBytes(1); // have we read everything?
    return abcFile;
  }
  
  public void write(OutputBitStream stream) throws IOException {
    stream.writeUI16(minorVersion);
    stream.writeUI16(majorVersion);
    constantPool.write(stream);
    stream.writeAbcInt(methods.size());
    for (Iterator<AbcMethodSignature> it = methods.iterator(); it.hasNext(); ) {
      it.next().write(stream);
    }
    stream.writeAbcInt(metadataEntries.size());
    for (Iterator<AbcMetadata> it = metadataEntries.iterator(); it.hasNext(); ) {
      it.next().write(stream);
    }
    stream.writeAbcInt(instances.size());
    for (Iterator<AbcInstance> it = instances.iterator(); it.hasNext(); ) {
      it.next().write(stream);
    }
    for (Iterator<AbcClass> it = classes.iterator(); it.hasNext(); ) {
      it.next().write(stream);
    }
    stream.writeAbcInt(scripts.size());
    for (Iterator<AbcScript> it = scripts.iterator(); it.hasNext(); ) {
      it.next().write(stream);
    }
    stream.writeAbcInt(methodBodies.size());
    for (Iterator<AbcMethodBody> it = methodBodies.iterator(); it.hasNext(); ) {
      it.next().write(stream);
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
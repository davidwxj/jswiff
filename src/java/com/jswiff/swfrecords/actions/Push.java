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

package com.jswiff.swfrecords.actions;

import com.jswiff.constants.TagConstants.ActionType;
import com.jswiff.constants.TagConstants.ValueType;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * Pushes one or more values to the stack. Add <code>Push.StackValue</code>
 * instances using <code>addValue()</code>.
 * </p>
 * 
 * <p>
 * Performed stack operations: addition of one or more values to stack.
 * </p>
 * 
 * <p>
 * ActionScript equivalent: none (used internally, e.g. for parameter passing).
 * </p>
 *
 * @since SWF 4
 */
public final class Push extends Action {
  
  private static final long serialVersionUID = 1L;
  
  private List<StackValue> values = new ArrayList<StackValue>();

  /**
   * Creates a new Push action.
   */
  public Push() {
    super(ActionType.PUSH);
  }

  /*
   * Reads a Push action from a bit stream.
   */
  Push(InputBitStream stream) throws IOException {
    super(ActionType.PUSH);
    while (stream.available() > 0) {
      StackValue value = new StackValue(stream);
      values.add(value);
    }
  }

  /**
   * Returns the size of this action record in bytes.
   *
   * @return size of this record
   *
   * @see Action#getSize()
   */
  public int getSize() {
    int size = 3;
    for (int i = 0; i < values.size(); i++) {
      size += values.get(i).getSize();
    }
    return size;
  }

  /**
   * Returns a list of values this action is supposed to push to the stack. Use
   * this list in a read-only manner.
   *
   * @return a list of Push.StackValue instances
   */
  public List<StackValue> getValues() {
    return values;
  }

  /**
   * Adds a value to be pushed to the stack.
   *
   * @param value a <code>StackValue</code> instance
   */
  public void addValue(StackValue value) {
    values.add(value);
  }

  protected void writeData(
    OutputBitStream dataStream, OutputBitStream mainStream)
    throws IOException {
    for (int i = 0; i < values.size(); i++) {
      values.get(i).write(dataStream);
    }
  }

  /**
   * Contains a value which can be pushed to the stack.
   * Values are all returned as Objects and underneath stored as primitive wrapper types,
   * use {@link #getType()} to determine the actual value type, e.g.
   * <pre>
   * <code>
   * if (ValueType.BOOLEAN.equals(stackVal.getType()) {
   *    boolean val = (Boolean) stackVal.getValue();
   * }
   * </code>
   * </pre>
   * See {@link ValueType}.
   */
  public static class StackValue implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Create a stack value of type STRING.
     * @param value a string value
     * @return a stack value
     */
    public static StackValue createStringValue(String value) {
      return new StackValue(ValueType.STRING, value != null ? value : "");
    }
    
    /**
     * Create a stack value of type INTEGER.
     * @param value an integer value (of type <code>long</code>)
     * @return a stack value
     */
    public static StackValue createIntegerValue(long value) {
      return new StackValue(ValueType.INTEGER, value);
    }
    
    /**
     * Create a double-precision number stack value.
     * @param value a double value
     * @return a stack value
     */
    public static StackValue createDoubleValue(double value) {
      return new StackValue(ValueType.DOUBLE, value);
    }
    
    /**
     * Create a (single-precision) float stack value.
     * @param value a float value
     * @return a stack value
     */
    public static StackValue createFloatValue(float value) {
      return new StackValue(ValueType.FLOAT, value);
    }
    
    /**
     * Create a stack value of type BOOLEAN.
     * @param value a boolean value
     * @return a stack value
     */
    public static StackValue createBooleanValue(boolean value) {
      return new StackValue(ValueType.BOOLEAN, value);
    }
    
    /**
     * Create a stack value holding a register number.
     * @param value a register number
     * @return a stack value
     */
    public static StackValue createRegisterValue(short value) {
      return new StackValue(ValueType.REGISTER, value);
    }
    
    /**
     * Create an 8-bit constant pool index, used for indexes less
     * than 256.
     * @param value an 8-bit constant pool index
     * @return a stack value
     */
    public static StackValue createConstant8Value(short value) {
      return new StackValue(ValueType.CONSTANT_8, value);
    }
    
    /**
     * Create a 16-bit constant pool index, used for indexes more
     * than 256.
     * @param value an 16-bit constant pool index
     * @return a stack value
     */
    public static StackValue createConstant16Value(int value) {
      return new StackValue(ValueType.CONSTANT_16, value);
    }
    
    /**
     * Sets the type to <code>TYPE_UNDEFINED</code> (i.e. the push value is
     * <code>undefined</code>).
     */
    public static StackValue createUndefinedValue() {
      return new StackValue(ValueType.UNDEFINED, "");
    }
    
    /**
     * Sets the type to <code>TYPE_NULL</code> (i.e. the push value is
     * <code>null</code>).
     */
    public static StackValue createNullValue() {
      return new StackValue(ValueType.NULL, "");
    }
    
    private final ValueType type;
    private final Object value;

    private StackValue(ValueType type, Object value) {
      this.type = type;
      this.value = value;
    }

    /*
     * Reads a PushEntry instance from a bit stream.
     */
    StackValue(InputBitStream stream) throws IOException {
      type = ValueType.lookup(stream.readUI8());
      switch (type) {
        case STRING:
          this.value = stream.readString();
          break;
        case FLOAT:
          this.value = stream.readFloat();
          break;
        case BOOLEAN:
          this.value = (stream.readUI8() != 0);
          break;
        case DOUBLE:
          this.value = stream.readDouble();
          break;
        case INTEGER:
          this.value = stream.readUI32();
          break;
        case CONSTANT_8:
        case REGISTER:
          this.value = stream.readUI8();
          break;
        case CONSTANT_16:
          this.value = stream.readUI16();
          break;
        default:
          this.value = "";
      }
    }

    /**
     * Returns the value.
     * @return the value as an Object, use getType() to determine the actual type.
     */
    public Object getValue() {
      return this.value;
    }

    /**
     * Returns the type of the push value. See {@link ValueType}
     * @return type of push value
     */
    public ValueType getType() {
      return type;
    }
    
    /**
     * Get the value as a string regardless of actual type
     * @return a string representation of the value.
     */
    public String valueString() {
      return this.value.toString();
    }

    /**
     * Returns a short description of the push value (type and value)
     * @return type and value
     */
    public String toString() {
      String str = getType().getNiceName();
      if (!ValueType.NULL.equals(getType()) && !ValueType.UNDEFINED.equals(getType())) {
        str += ": " + this.valueString();
      }
      return str;
    }

    int getSize() {
      int size = 1; // type
      switch (type) {
        case STRING:
          try {
            size += (getValue().toString().getBytes("UTF-8").length + 1);
          } catch (UnsupportedEncodingException e) {
            // UTF-8 should be available. If not, we have a big problem anyway
          }
          break;
        case BOOLEAN:
        case CONSTANT_8:
        case REGISTER:
          size++;
          break;
        case CONSTANT_16:
          size += 2;
          break;
        case INTEGER:
        case FLOAT:
          size += 4;
          break;
        case DOUBLE:
          size += 8;
          break;
      }
      return size;
    }

    void write(OutputBitStream outStream) throws IOException {
      outStream.writeUI8(getType().getCode());
      Object val = getValue();
      switch (type) {
        case STRING:
          outStream.writeString(val.toString());
          break;
        case FLOAT:
          outStream.writeFloat((Float) val);
          break;
        case REGISTER:
          outStream.writeUI8((Short) val);
          break;
        case BOOLEAN:
          Boolean bVal = (Boolean) val;
          outStream.writeUI8((short) (bVal ? 1 : 0));
          break;
        case DOUBLE:
          outStream.writeDouble((Double) val);
          break;
        case INTEGER:
          outStream.writeUI32((Long) val);
          break;
        case CONSTANT_8:
          outStream.writeUI8((Short) val);
          break;
        case CONSTANT_16:
          outStream.writeUI16((Integer) val);
          break;
      }
    }
  }
  
}

package com.jswiff.exception;

/**
 * Indicates that an attempt to lookup the constant mapped to a given code failed.
 * @author bstock
 *
 */
public class InvalidCodeException extends Exception {
  
  private static final long serialVersionUID = 1L;

  private final short code;
  
  public InvalidCodeException(final String type, short code) {
    this(type, code, null);
  }
  
  public InvalidCodeException(final String type, short code, Throwable cause) {
    super("A SWF Tag/flag of type '" + type + "' read with invalid code '" + code + "'.\n" +
        "This probably means whatever is being read has invalid/corrupt data, " +
        "but if you believe this to instead be a bug in the library then please contact the author.", cause);
    this.code = code;
  }
  
  public short getCode() {
    return this.code;
  }

}

package com.jswiff.exception;

/**
 * Indicates that an attempt to lookup the constant mapped to a given string failed.
 * @author bstock
 *
 */
public class InvalidNameException extends Exception {
  
  private static final long serialVersionUID = 1L;

  private final String name;
  
  public InvalidNameException(final String type, String name) {
    this(type, name, null);
  }
  
  public InvalidNameException(final String type, String name, Throwable cause) {
    super("Invalid '" + type + "' name: " + name, cause);
    this.name = name;
  }
  
  public String getName() {
    return this.name;
  }

}

package com.jswiff.exception;

public class UnknownCodeException extends RuntimeException {
  
  private static final long serialVersionUID = 1L;

  private final short code;
  
  public UnknownCodeException(final String msg, short code) {
    super(msg);
    this.code = code;
  }
  
  public UnknownCodeException(final String msg, short code, Throwable cause) {
    super(msg, cause);
    this.code = code;
  }
  
  public short getCode() {
    return this.code;
  }

}

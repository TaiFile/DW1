package br.ufscar.dc.dsw.exceptions;

public class BadRequestException extends RuntimeException{
  public BadRequestException(String message) {
    super(message);
  }
}

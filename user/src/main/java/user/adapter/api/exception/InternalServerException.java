package user.adapter.api.exception;

public class InternalServerException extends RuntimeException {
  public InternalServerException(String message) {
    super(message);
  }
}

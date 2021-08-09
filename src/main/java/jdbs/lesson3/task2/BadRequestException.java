package jdbs.lesson3.task2;

public class BadRequestException extends Throwable {
    public BadRequestException(String message) {
        super(message);
    }
}

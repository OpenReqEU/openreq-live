package eu.openreq.exception;

public class DboConstraintException extends BaseException {
	private static final long serialVersionUID = 1L;

	private String message;

	public DboConstraintException(String message) {
		this.message = message;
	}

	@Override
    public String getMessage() {
        return "A Dbo Constraint Exception occured: " + message + " " + super.getMessage();
    }
}

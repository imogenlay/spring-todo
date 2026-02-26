package com.imogenlay.todo.common.error;
 
import org.springframework.http.HttpStatus;
import com.imogenlay.todo.common.Tuple;

public class ConditionalObject<T> {
    
    private final T object;
    private final Tuple<HttpStatus, String> error;

    public ConditionalObject(T object) {
        this.object = object;
        this.error = null;
    }

    public ConditionalObject(HttpStatus status, String message) { 
        this.object = null;
        this.error = new Tuple<HttpStatus, String>(status, message);
    }    

    public T getObject() { return object; }
    public HttpStatus getErrorStatus() { return hasError() ? error.getA() : null; }
    public String getErrorMessage() { return hasError() ? error.getB() : null; }

    public boolean hasError() { return error != null; }
    public boolean hasObject() { return !hasError() && object != null; }

    public void throwError() {
        if (!hasError())
            return;

        HttpStatus status = getErrorStatus();
        if (status == HttpStatus.BAD_REQUEST)
            throw new BadRequestException(getErrorMessage());
        if (status == HttpStatus.NOT_FOUND)
            throw new NotFoundException(getErrorMessage());
        if (status == HttpStatus.INTERNAL_SERVER_ERROR)
            throw new InternalServerException(getErrorMessage());
        
        throw new RuntimeException("Error was not recognised: " + getErrorMessage());
    }
}

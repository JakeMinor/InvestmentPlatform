package investment.api.services;

import investment.api.dtos.AssetKindEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AssetKindExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleConversionFailedException(final RuntimeException e) {
        if (e.getCause().getMessage().contains(AssetKindEnum.class.getName())) {
            return new ResponseEntity<>("The Asset Kind provided is not valid Asset type. Valid Asset types are: SHARE or BOND", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

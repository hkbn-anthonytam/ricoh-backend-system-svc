package poc.msa.system.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class APIErrorResponse implements Serializable {
    private Date timestamp;
    private HttpStatus status;
    private String message;
    private List<String> constraintErrors = new ArrayList<>();
}

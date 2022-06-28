package sbrf.practice.jsv.list.validator;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SchemaValidator implements ConstraintValidator<IsValidSchema, MultipartFile>{

    @Override
    public boolean isValid(MultipartFile content, ConstraintValidatorContext context) {
        try {
            String str = new String(content.getBytes());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode actualObj = mapper.readTree(str);
            return true;
        } catch(Exception e) {
            return false;
        }
    }
}

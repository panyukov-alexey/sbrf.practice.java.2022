package sbrf.practice.jsv.list.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SchemaValidator implements ConstraintValidator<IsValidSchema, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile content, ConstraintValidatorContext context) {
        try {
            String str = new String(content.getBytes());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode actualObj = mapper.readTree(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

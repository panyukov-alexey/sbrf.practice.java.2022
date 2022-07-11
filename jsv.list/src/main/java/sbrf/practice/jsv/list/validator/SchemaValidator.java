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
            byte[] bytes = content.getBytes();
            String str = new String(bytes);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode actualObj = mapper.readTree(str);
            return !actualObj.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}

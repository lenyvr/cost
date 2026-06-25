package pizzaioli.production.domain.validation;

import pizzaioli.production.domain.exceptions.ValueRequiredException;

import java.util.Objects;

public interface ValidateEmptyField {
    default void validateEmptyField(String field, String fieldName){
        if(Objects.isNull(field) || field.trim().isEmpty()){
            throw new ValueRequiredException(String.format("The field %s is required", fieldName));
        }
    }
}

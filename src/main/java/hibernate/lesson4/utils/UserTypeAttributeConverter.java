package hibernate.lesson4.utils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

@Converter(autoApply = true)
public class UserTypeAttributeConverter implements AttributeConverter<UserType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(UserType attribute) {
        Optional.ofNullable(attribute)
                .orElseThrow(() -> new IllegalArgumentException("attribute is null"));

        switch (attribute) {
            case USER:
                return 1;
            case ADMIN:
                return 2;
            default:
                throw new IllegalArgumentException("Value of attribute=" + attribute + " is not supported");
        }
    }

    @Override
    public UserType convertToEntityAttribute(Integer dbData) {
        Optional.ofNullable(dbData)
                .orElseThrow(() -> new IllegalArgumentException("dbData is null"));

        switch (dbData) {
            case 1:
                return UserType.USER;
            case 2:
                return UserType.ADMIN;
            default:
                throw new IllegalArgumentException("Value of dbData=" + dbData + " is not supported");
        }

    }
}

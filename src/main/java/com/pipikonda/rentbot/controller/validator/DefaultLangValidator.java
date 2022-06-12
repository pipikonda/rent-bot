package com.pipikonda.rentbot.controller.validator;

import com.pipikonda.rentbot.domain.Lang;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

@Component
public class DefaultLangValidator implements ConstraintValidator<DefaultLang, Map<Lang, String>> {

    @Override
    public boolean isValid(Map<Lang, String> value, ConstraintValidatorContext context) {
        return value == null || value.containsKey(Lang.DEFAULT_LANG);
    }
}

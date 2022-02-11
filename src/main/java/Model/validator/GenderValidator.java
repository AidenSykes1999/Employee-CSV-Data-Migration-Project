package model.validator;

import javax.xml.validation.Validator;

public class GenderValidator implements DataValidator {

    @Override
    public boolean validate(String gender) {
        if (gender == null) return false;
        return gender.equals("M") || gender.equals("F") || gender.equals("X");
    }
}

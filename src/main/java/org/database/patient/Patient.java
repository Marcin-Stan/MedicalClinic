package org.database.patient;

import javax.validation.*;


public class Patient {

    static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    static Validator validator = factory.getValidator();

}

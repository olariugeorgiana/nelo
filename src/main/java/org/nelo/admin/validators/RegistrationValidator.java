package org.nelo.admin.validators;

import org.nelo.entities.RegistrationSearch;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegistrationValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return RegistrationSearch.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        RegistrationSearch registrationSearch = (RegistrationSearch) target;

        if (registrationSearch.getUserAccount().getFirstName().isEmpty() || registrationSearch.getUserAccount().getLastName().isEmpty() || registrationSearch.getUserAccount().getEmail().isEmpty())
            errors.rejectValue("userAccount.firstName", null, "Complete all the client mandatory fields! (marked with *)");

        if (registrationSearch.getStartDate() == null || registrationSearch.getEndDate() == null)
            errors.rejectValue("endDate", null, "Check in and check out date must be completed!");

        if (registrationSearch.getRoomId() == -1) {
            errors.rejectValue("roomId", null, "Select a room for reservation!");
        }
    }
}

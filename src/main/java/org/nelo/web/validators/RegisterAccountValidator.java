package org.nelo.web.validators;

import org.nelo.dao.UserAccountDao;
import org.nelo.entities.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegisterAccountValidator implements Validator {

    @Autowired
    private UserAccountDao userAccountDao;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserAccount.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

        boolean hasErrors = false;
        UserAccount userAccount = (UserAccount) o;

        if (userAccount.getFirstName() == null || userAccount.getFirstName() != null && userAccount.getFirstName().isEmpty())
            hasErrors = true;
        if (userAccount.getLastName() == null || userAccount.getLastName() != null && userAccount.getLastName().isEmpty())
            hasErrors = true;
        if (userAccount.getEmail() == null || userAccount.getEmail() != null && userAccount.getEmail().isEmpty())
            hasErrors = true;

        if (userAccount.getEmail() != null && !userAccount.getEmail().isEmpty()) {

            if (!userAccount.getEmail().matches(EMAIL_REGEX)) {
                hasErrors = true;
            } else {
                UserAccount userAccountByEmail = userAccountDao.getByEmail(userAccount.getEmail());

                if (userAccountByEmail != null) {
                    errors.rejectValue("email", null, "Email is already associated with an account!");
                    return;
                }
            }

        } else {
            hasErrors = true;
        }

        if (hasErrors) {
            errors.rejectValue("email", null, "You must complete all fields to register a account!");
        }

    }

}
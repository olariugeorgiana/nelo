package org.nelo.entities.enums;

import java.util.Arrays;
import java.util.List;

public interface Roles {

    public static Roles USER = new Roles() {
        @Override
        public String getType() {
            return "USER";
        }

        @Override
        public String getDescription() {
            return "User";
        }

    };
    public static Roles RECEPTIONIST = new Roles() {
        @Override
        public String getType() {
            return "RECEPTIONIST";
        }

        @Override
        public String getDescription() {
            return "Receptionist";
        }

    };
    public static Roles ADMINISTRATOR = new Roles() {
        @Override
        public String getType() {
            return "ADMINISTRATOR";
        }

        @Override
        public String getDescription() {
            return "Administrator";
        }

    };
    static List<Roles> _values = Arrays.asList(USER, RECEPTIONIST, ADMINISTRATOR);


    String getType();

    String getDescription();

}

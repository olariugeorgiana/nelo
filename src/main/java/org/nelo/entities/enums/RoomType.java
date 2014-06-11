package org.nelo.entities.enums;

import java.util.Arrays;
import java.util.List;

public interface RoomType {

    public static RoomType SINGLE = new RoomType() {
        @Override
        public String getType() {
            return "single";
        }

        @Override
        public String getDescription() {
            return "Single";
        }
    };
    public static RoomType DOUBLE_TWIN = new RoomType() {
        @Override
        public String getType() {
            return "double_twin";
        }

        @Override
        public String getDescription() {
            return "Double Twin";
        }
    };
    public static RoomType MATRIMONIAL = new RoomType() {
        @Override
        public String getType() {
            return "matrimonial";
        }

        @Override
        public String getDescription() {
            return "Matrimonial";
        }
    };
    static List<RoomType> _values = Arrays.asList(SINGLE, DOUBLE_TWIN, MATRIMONIAL);

    String getType();

    String getDescription();

}

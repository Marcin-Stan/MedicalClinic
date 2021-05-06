package org.administrator.schedule;

public enum ActionType {

    LOAD {
        @Override
        public String getDisplayName() {
            return "Load";
        }
    },

    INSERT {
        @Override
        public String getDisplayName() {
            return "Insert";
        }
    },

    UPDATE {
        @Override
        public String getDisplayName() {
            return "Update";
        }
    },

    DELETE {
        @Override
        public String getDisplayName() {
            return "Delete";
        }
    },

    MOVE {
        @Override
        public String getDisplayName() {
            return "Move";
        }
    },

    REFRESH {
        @Override
        public String getDisplayName() {
            return "Refresh";
        }
    };

    public abstract String getDisplayName();
}

package org.administrator.schedule;

import javafx.scene.paint.Color;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;

public enum StatusType {

    SUCCEEDED {
        @Override
        public String getDisplayName() {
            return "Succeeded";
        }

        @Override
        public Ikon getIcon() {
            return FontAwesome.CHECK;
        }

        @Override
        public Color getColor() {
            return Color.GREEN;
        }
    },

    PENDING {
        @Override
        public String getDisplayName() {
            return "Pending";
        }

        @Override
        public Ikon getIcon() {
            return FontAwesome.EXCLAMATION_TRIANGLE;
        }

        @Override
        public Color getColor() {
            return Color.ORANGE;
        }
    },

    FAILED {
        @Override
        public String getDisplayName() {
            return "Failed";
        }

        @Override
        public Ikon getIcon() {
            return FontAwesome.CLOSE;
        }

        @Override
        public Color getColor() {
            return Color.RED;
        }
    },

    CANCELLED {
        @Override
        public String getDisplayName() {
            return "Cancelled";
        }

        @Override
        public Ikon getIcon() {
            return FontAwesome.EXCLAMATION_CIRCLE;
        }

        @Override
        public Color getColor() {
            return Color.BLUE;
        }
    };

    public abstract String getDisplayName();

    public abstract Ikon getIcon();

    public abstract Color getColor();

    public FontIcon createView() {
        FontIcon view = new FontIcon(getIcon());
        view.setFill(getColor());
        return view;
    }
}

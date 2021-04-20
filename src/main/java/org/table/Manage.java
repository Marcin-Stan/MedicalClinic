package org.table;

import javafx.scene.layout.StackPane;

public interface Manage<T> {

    public void initData(T entity);
    public void setParentStackPane(StackPane stackPane);
}

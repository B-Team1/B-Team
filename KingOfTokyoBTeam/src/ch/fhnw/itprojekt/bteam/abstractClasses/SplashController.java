package ch.fhnw.itprojekt.bteam.abstractClasses;

import javafx.fxml.Initializable;

public abstract class SplashController<M, V> implements Initializable{
    protected M model;
    protected V view;
    
    protected SplashController(M model, V view) {
        this.model = model;
        this.view = view;
    }
}

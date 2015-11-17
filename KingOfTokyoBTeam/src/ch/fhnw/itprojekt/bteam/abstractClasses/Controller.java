package ch.fhnw.itprojekt.bteam.abstractClasses;

import javafx.fxml.Initializable;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public abstract class Controller<M> implements Initializable{
    protected M model;
    
    protected Controller(M model) {
        this.model = model;
    }
}

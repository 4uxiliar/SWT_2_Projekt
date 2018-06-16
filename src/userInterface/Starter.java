package userInterface;

import oberflaeche.ViewController;

import java.util.Observable;

public class Starter extends Observable{

    public static void main(String[] args) {

        ViewController.getInstance();
    }
}

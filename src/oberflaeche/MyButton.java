package oberflaeche;

import javax.swing.*;
import java.util.LinkedList;

public class MyButton extends JButton implements Observable {
    private LinkedList<Observer> observers = new LinkedList<>();
//TODO
    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers)
            observer.update();
    }

    public MyButton(String titel) {
        super(titel);
        addActionListener(e -> notifyObservers());
    }
}
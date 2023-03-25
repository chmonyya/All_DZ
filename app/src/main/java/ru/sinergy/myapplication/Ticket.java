package ru.sinergy.myapplication;

public class Ticket {

    public double cost;

    private int bca = 70;
    private double bco = bca - ((bca / 100) * 50);
    private double bck = bca - ((bca / 100) * 30);

    public Ticket(TicketType type) {
        switch (type) {
            case OLD: cost = bco;
                break;
            case ADULT: cost = bca;
                break;
            case KIDS: cost = bck;
                break;
        }
    }
}


enum TicketType {
    ADULT, KIDS, OLD;
}
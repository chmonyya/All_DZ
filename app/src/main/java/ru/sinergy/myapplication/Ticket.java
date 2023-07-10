package ru.sinergy.myapplication;

import java.io.Serializable;

public class Ticket {
   // private static final long serialVersionUID = 8829975621220483374L;
    private int baseCost = 70;
    private double oldCost = baseCost - ((baseCost / 100) * 50);
    private double kidsCost = baseCost - ((baseCost / 100) * 30);

    public String userId;
    public double cost;
    public String from = "Нью-Йорк";
    public String to = "Владивосток";
    public String place = "вагон 13, место 6";
    public String out = "23:23";
    public String arrival = "01:01";
    public  TicketType type = TicketType.ADULT;

    public Ticket(String userId, TicketType type) {
        this.userId = userId;
        setCost(type);
    }

    public Ticket(TicketType type) {
        setCost(type);
    }

    public void setCost(TicketType type) {
        this.type = type;
        switch (type) {
            case OLD: cost = oldCost;
                break;
            case ADULT: cost = baseCost;
                break;
            case KIDS: cost = kidsCost;
                break;
        }

    }
}


enum TicketType {
    ADULT, KIDS, OLD;
}
package model;

import java.sql.Timestamp;

public class Status {

    private int id;
    private Timestamp accepted;
    private Timestamp approvedRepairCosts;
    private Timestamp inRepair;
    private Timestamp readyForPickup;
    private Timestamp resignation;

    public Status(Timestamp accepted, Timestamp approvedRepairCosts,
                  Timestamp inRepair, Timestamp readyForPickup,
                  Timestamp resignation) {
        this.accepted = accepted;
        this.approvedRepairCosts = approvedRepairCosts;
        this.inRepair = inRepair;
        this.readyForPickup = readyForPickup;
        this.resignation = resignation;
    }

    public Status() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getAccepted() {
        return accepted;
    }

    public void setAccepted(Timestamp accepted) {
        this.accepted = accepted;
    }

    public Timestamp getApprovedRepairCosts() {
        return approvedRepairCosts;
    }

    public void setApprovedRepairCosts(Timestamp approvedRepairCosts) {
        this.approvedRepairCosts = approvedRepairCosts;
    }

    public Timestamp getInRepair() {
        return inRepair;
    }

    public void setInRepair(Timestamp inRepair) {
        this.inRepair = inRepair;
    }

    public Timestamp getReadyForPickup() {
        return readyForPickup;
    }

    public void setReadyForPickup(Timestamp readyForPickup) {
        this.readyForPickup = readyForPickup;
    }

    public Timestamp getResignation() {
        return resignation;
    }

    public void setResignation(Timestamp resignation) {
        this.resignation = resignation;
    }

    @Override
    public String toString() {
        return "Status{" +
                "accepted=" + accepted +
                ", approvedRepairCosts=" + approvedRepairCosts +
                ", inRepair=" + inRepair +
                ", readyForPickup=" + readyForPickup +
                ", resignation=" + resignation +
                '}';
    }
}

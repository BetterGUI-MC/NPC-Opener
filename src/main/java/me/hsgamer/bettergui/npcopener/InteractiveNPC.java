package me.hsgamer.bettergui.npcopener;

public class InteractiveNPC {

    private final int id;
    private String[] args = new String[0];

    public InteractiveNPC(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}

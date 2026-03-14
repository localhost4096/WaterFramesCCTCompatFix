package net.rocketstrong.waterframescctcompat.blocks.DisplayInteract;

public class DisplayInteractable implements IDisplayInteractable{
    private boolean interactable = true;

    @Override
    public boolean isInteractable() {
        return interactable;
    }

    @Override
    public void setInteractable(boolean value) {
        interactable = value;
    }
}

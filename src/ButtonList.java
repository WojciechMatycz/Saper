import java.util.ArrayList;

public class ButtonList {
    private ArrayList<FieldButton> buttons;

    public ButtonList()
    {
        buttons = new ArrayList<>();
    }

    public void add(FieldButton button){
        buttons.add(button);
    }

    public FieldButton get(int index)
    {
        return buttons.get(index);
    }

    public boolean contains(Object o)
    {
        return buttons.contains(o);
    }

    public FieldButton findButton(int x, int y)
    {
        for(FieldButton fb : buttons)
            if(fb.getPositionX()==x && fb.getPositionY()==y)
                return fb;

        return null;
    }

    public void removeAll() {
        buttons.removeAll(buttons);
    }

    public int size() {
        return buttons.size();
    }
}

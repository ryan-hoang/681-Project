package gmu.Project;

public enum Hand
{
    HIGHCARD("High Card", 1),
    ONEPAIR("One Pair", 2),
    TWOPAIR("Two Pair", 3),
    THREEOFAKIND("Three of a Kind", 4),
    STRAIGHT("Straight", 5),
    FLUSH("FLush", 6),
    FULLHOUSE("Full House", 7),
    FOUROFAKIND("Four of a Kind", 8),
    STRAIGHTFLUSH("Straight Flush", 9),
    ROYALFLUSH("Royal Flush", 10);

    private final String value;
    private final int i;

    private Hand(String value, int i)
    {
        this.value = value;
        this.i = i;
    }
    public String getName()
    {
        return value;
    }
    public int getHandInt(){
        return i;
    }

}

package gmu.Project;

public enum Hand
{
    HIGHCARD("High Card"),
    ONEPAIR("One Pair"),
    TWOPAIR("Two Pair"),
    THREEOFAKIND("Three of a Kind"),
    STRAIGHT("Straight"),
    FLUSH("FLush"),
    FULLHOUSE("Full House"),
    FOUROFAKIND("Four of a Kind"),
    STRAIGHTFLUSH("Straight Flush"),
    ROYALFLUSH("Royal Flush");

    private final String value;
    private Hand (String value)
    {
        this.value = value;
    }
    public String getName()
    {
        return value;
    }
}

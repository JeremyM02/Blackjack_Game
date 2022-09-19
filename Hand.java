import java.util.ArrayList;

/**
 * A class made for the Blackjack project. 
 * Upon calling its constructor, it creates a Hand with a registered owner and draws two cards.
 *
 * @author Jeremy Montes
 * @version 9/14/2022
 */
public class Hand
{
    //I shared this method with the class, so others may be using it.
    //Though clearly, this method is a waste of time in the AP test.
    //I'll try to only use this on extensive projects, like this one.
    private static int pickInteger(int min, int max){
        /**Returns an integer between the min and max value, including both min and max. **/
        return (int)(Math.random() * (max - min + 1) + min);
    }

    //owner and cardList are displayed in the displayHandStats() method.
    private String owner;
    private ArrayList<String> cardList = new ArrayList<String>(); 

    //numAces and total are used internally, both helps calculate with the getRealTotal() method.
    private int numAces = 0;
    private int total = 0;

    //firstDraw is used for a specific Hand with an owner named "Dealer". The first time it uses displayHandStats() would hide certain values.
    private boolean firstDraw = true;

    //Testing variable, unused in the Blackjack game.
    private static boolean testMode = false;

    /**
     * The constructor used in the Blackjack game, it registers the owner of the hand and draws two cards.
     * 
     * @params owner - The name of the Hand's owner.
     */
    public Hand(String owner){
        this.owner = owner;
        //The if statement is only for testing purposes, if I want to get a specific hand.
        //Right now, testMode is always false, so the constructor will start with two cards.
        if (!testMode){
            hit();
            hit();
        }
        // System.out.println(this.total);
    }

    //A test, to see how the program handles two Aces and a 9.
    //Though to do this, I have to set testMode to true, so the constructor won't draw 2 cards.
    //The test should equal 21, meaning that Aces work properly.
    /**
     * Method that creates a Hand named "Test", only used for testing purposes.
     * @return 
     */
    public static Hand TEST_createHand(){
        Hand.testMode = true;
        Hand testHand = new Hand("Test");
        // testHand.hit(1);
        // testHand.hit(1);
        // testHand.hit(9);
        testHand.hit();
        testHand.hit();
        testHand.hit();
        return testHand;
    }

    //Without parameters, you get any card from Ace to King.
    /**
     * Calling the hit method without a parameter generates a random card from a suit, then adds its value to the total.
     * It would also register the card into cardList, and increases numAces if the card is an Ace.
     */
    public void hit(){

        int cardValue = drawCard(numAces);
        if (cardValue == 1 || cardValue == 11){
            numAces++;
            // System.out.println("# Aces: " + numAces);
        }
        total += cardValue;
    }

    /**
     * Prints three lines that describes the Hand's owner, their list of cards, and current value.
     * Value is based on getRealTotal(), not the 'total' variable.
     * If the Hand's owner is the dealer, it would hide information the first time it gets called.
     */
    public void displayHandStats(){
        System.out.println("------------------ "+ owner + "'s Hand ------------------");

        if (this.owner.equals("Dealer") && firstDraw){
            System.out.println("Cards: [" + cardList.get(0) + ", ?]");
            System.out.println("Value: ???");
            firstDraw = false;
        } else {
            System.out.println("Cards: "+ cardList);
            System.out.println("Value: "+ getRealTotal());
        }
    }
    
    /**
     * Obtains the last card of the Hand's cardList variable. Used in Blackjack to tell the card that the user obtained.
     * @return The last element of the cardList array.
     */
    public String getLastCard(){
        return cardList.get(cardList.size() - 1);
    }

    /** Whether or not a hand is "safe" is based on the getRealTotal 
     * method, not the "total" variable.
     * 
     * @return 'false' if getRealTotal() is greater than 21. 'true' if otherwise, meaning it's 21 or less and considered "safe". 
     **/
    public boolean isSafe(){
        int realTotal = getRealTotal();
        if (realTotal > 21){
            return false;
        }
        return true;
    }

    /**
     * Calculates the value of the cards, not the total, and decides whether or not the first Ace is worth 1 or 11.
     *
     * @return    If the total is greater than 21, and if the hand has atleast one Ace,
     *             return the total as if the first Ace counted as 1 instead. Otherwise, just return the total.
     *             
     */
    public int getRealTotal(){
        if (this.total > 21 && numAces > 0){
            return this.total - 10;
        }
        return this.total;
    }

    /*
     * This method generates a random card from a suit, then adds its value to 'total'.
     * It chooses from 1 to 13 to obtain any card and its name, which gets registered into 'cardList'.
     * If the card generated is an Ace, the first one is always worth 11, then new ones will be worth 1.
     * Whether or not the first Ace changes in 1 is not calculated in this method.
     * 
     * @params numAces - The number of Aces in the Hand object.
     * @returns number - The value of the card generated.
     */
    private int drawCard(int numAces){
        int aceValue = 1;
        int card = pickInteger(1, 13);
        int number = card;
        String name = Integer.toString(card);
        if (card == 11){
            name = "Jack";
            number = 10;
        } else if (card == 12){
            name = "Queen";
            number = 10;
        } else if (card == 13){
            name = "King";
            number = 10;
        }
        if (card == 1){
            // System.out.println("You got an Ace!");
            if (numAces == 0){
                // System.out.println("It's worth 11 points for now");
                aceValue = 11;
            } else if (numAces > 0){
                // System.out.println("It will be worth 1 point");
                aceValue = 1;
            }
            name = "Ace";
            number = aceValue;
        }
        cardList.add(name);
        // System.out.println("Drew a " + name + ", Worth " + number);
        return number;
    }

    /*
     * Used for testing, basically drawCard() but the card chosen is from the parameter, and not randomly generated.
     * 
     * @params cardNumber - The card specified to be chosen.
     * @returns number - The value of the selected card
     */
    private int getCard(int cardNumber){
        String name = Integer.toString(cardNumber);
        int number = cardNumber;
        if (cardNumber == 11 || cardNumber == 12 || cardNumber == 13){
            name = "King"; 
            number = 10;
        }
        if (cardNumber == 1){
            name = "Ace";
            if (numAces == 0){
                number = 11;
            } else if (numAces > 0){
                number = 1;
            }
        }
        cardList.add(name);
        return number;
    }
    
    //BELOW ARE METHODS FOR TESTING PURPOSES (and to show that I did the work)
    
    //Testing Mutators
    /**
     * Mutator method used for testing, adds to the instance variable 'total'. However, this would break the Hand's data as it wouldn't register any Aces or cards into certain variables.
     * @params number - The value to add to the total.
     */
    public void TEST_addTotal(int number){
        this.total += number;
    }

    /**
     * Mutator method used for testing, changes the instance variable 'numAces'. Does not change the cards in cardsList.
     * @params number - Change the number of Aces the Hand thinks it has.
     */
    public void TEST_changeNumAces(int number){
        this.numAces = number;
    }

    //Testing Accessors
    /**
     * Accessor method used for testing, gets the numAces instance variable.
     * @return The value of numAces of an Hand object.
     */
    public int TEST_getNumAces(){
        return this.numAces;
    }
    
    //If you enter an integer as a parameter, you can choose what card you get.
    /**
     * Calling the hit method with an integer parameter allows a specific card value to be added to the total, it would then register the card into cardList and/or numAces.
     * @params value - The value of the card to be obtained and properly adds it to the total.
     */
    public void hit(int value){
        int cardValue = getCard(value);
        if (cardValue == 1 || cardValue == 11){
            numAces++;
        }
        total += cardValue;
    }
    
    //This isn't used in the game, just for testing if a Hand named "Dealer" works correctly.
    /** 
     * Hand constructor with no parameters, always has owner = "Dealer" 
     */
    public Hand(){
        this("Dealer");
    }

}

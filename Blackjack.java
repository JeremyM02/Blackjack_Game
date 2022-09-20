import java.util.Scanner;

/**
 * A game of Blackjack! Don't go over 21.
 *
 * @author Jeremy Montes
 * @version 9/19/22 (Betting added!)
 */

public class Blackjack
{
    static Scanner scanner = new Scanner(System.in);
    public static void playGame(){
        int coins = 100;
        int bestCoins = coins;

        System.out.println("Hello, I am dealer. I do dealing \n");
        System.out.println("You start with 100 Coins");
        while (true){
            if (coins > bestCoins){
                bestCoins = coins;
            }
            if (coins == 0){
                System.out.println("You have no more coins!");
                break;
            }
            System.out.println("\nWhat would you like to do?");
            System.out.println("1)Play Round (Bet)");
            System.out.println("2)Play Round (No Bet)");
            System.out.println("3)Check Coins");
            System.out.println("4)End Game");
            int action = scanner.nextInt();

            if (action == 1){
                System.out.println("\nThe stakes are high!");

                int bet = 0;
                while (true){
                    System.out.println("How many coins do you want to bet? (You have " + coins + " Coins)");
                    bet = scanner.nextInt();
                    if (bet <= coins){
                        break;
                    } else if (bet > coins){
                        System.out.println("You can't bet that much.");
                    }
                }

                String gameResult = playRound();

                if (gameResult.equals("win")){
                    System.out.println("Victory! You earned your bet!");
                    System.out.println("Earned " + bet + " Coins");
                    coins += bet;
                } else if (gameResult.equals("loss")){
                    System.out.println("You lost your bet...");
                    System.out.println("Lost " + bet + " Coins");
                    coins -= bet;
                } else if (gameResult.equals("draw")){
                    System.out.println("Your bet wasn't changed.");
                }
            }
            if (action == 2){
                System.out.println("\nLet's play a round, with no stakes!");
                playRound();
            }
            if (action == 3){
                System.out.println("\n----------------------------------------");
                System.out.println("You have " + coins + " Coins");
                System.out.println("Your highest record so far is " + bestCoins + " Coins.");
                getRank(bestCoins);
                System.out.println("----------------------------------------");
            }
            if (action == 4){
                break;
            }
        }

        System.out.println("\n----------------------------------------");
        System.out.println("----------------------------------------");
        System.out.println("GAME OVER");
        System.out.println("In the end, you have " + coins + " Coins.");
        System.out.println("Your highest record was " + bestCoins + " Coins.");
        getRank(bestCoins);
        System.out.println("----------------------------------------");
        System.out.println("----------------------------------------");
    }

    private static void getRank(int coins){
        if (coins < 100){
            System.out.println("F Rank (0 to 99) - Newbie");
        } else if (coins < 200){
            System.out.println("E Rank (100 to 199) - Rookie");
        } else if (coins < 400){
            System.out.println("D Rank (200 to 399) - Amateur");
        } else if (coins < 800){
            System.out.println("C Rank (400 to 799) - Novice");
        } else if (coins < 1600){
            System.out.println("B Rank (800 to 1599) - Professional");
        } else if (coins < 3000){
            System.out.println("A Rank (1600 to 2999)- Veteran");
        } else if (coins < 6400){
            System.out.println("S Rank (3000 to ????) - Master");
        } else if (coins >= 6400){
            System.out.println("S+ Rank (6400+) - Legend");
            if (coins > 1000000){
                System.out.println("You are a millionare!");
            }
        }
    }

    private static String playRound(){
        //SET-UP PHASE
        int gamePhase = 1;

        Hand playerHand = new Hand("Player");
        Hand dealerHand = new Hand("Dealer");

        System.out.println("Let's draw both of your cards, and here is the dealer's card.");
        playerHand.displayHandStats();

        dealerHand.displayHandStats();

        if (playerHand.getRealTotal() == 21){
            System.out.println("Unbelivable! That's exactly 21!");
            gamePhase = 2;
        }
        //PLAYER PHASE
        System.out.println("\nBEGIN GAME:");
        while (gamePhase == 1){
            int action;
            System.out.println("1)Hit");
            System.out.println("2)Stand");
            while (true){
                action = scanner.nextInt();
                System.out.println();
                if (action == 1 || action == 2){
                    if (action == 1){
                        playerHand.hit();
                        System.out.println("You got a " + playerHand.getLastCard());
                        playerHand.displayHandStats();
                        if (playerHand.getRealTotal() == 21){
                            System.out.println("You got a Blackjack!");
                            gamePhase = 2;
                            break;
                        }
                        if (playerHand.isSafe() == false){
                            System.out.println("That's over 21, you got a bust...");
                            gamePhase = 2;
                            break;
                        }
                    }
                    if (action == 2){
                        System.out.println("You decided not to hit.");
                        gamePhase = 2;
                        break;
                    }
                } else {
                    System.out.println("Please enter 1 or 2");
                }
            }

        }
        if (playerHand.getRealTotal() < 21){
            System.out.println("\nThe dealer's 2nd card was " + dealerHand.getLastCard());

            //DEALER PHASE
            System.out.println();
            System.out.println("It's dealer time");
            if (dealerHand.getRealTotal() < 17){
                System.out.println("[THE DEALER HITS]");
                while(dealerHand.getRealTotal() < 17){
                    dealerHand.hit();
                    dealerHand.displayHandStats();
                }
                System.out.println("[THE DEALER IS DONE]");
            } else {
                System.out.println("[THE DEALER DOES NOT HIT]");
            }

            //END PHASE
            System.out.println();
            System.out.println("HERE ARE THE RESULTS");
            playerHand.displayHandStats();
            dealerHand.displayHandStats();

        }

        int playerTotal = playerHand.getRealTotal();
        int dealerTotal = dealerHand.getRealTotal();
        if (playerHand.isSafe() == false){
            playerTotal = 0;    
        }
        if (dealerHand.isSafe() == false){
            dealerTotal = 0;
        }

        if (playerTotal > dealerTotal){
            System.out.println("YOU WIN!");
            return "win";
        }
        if (playerTotal < dealerTotal){
            System.out.println("YOU LOSE!");
            return "loss";
        }
        if (playerTotal == dealerTotal){
            System.out.println("IT'S A DRAW!");
            return "draw";
        }
        return null;
    }
}

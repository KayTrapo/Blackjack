import java.util.Scanner;
import java.util.Random;


public class Game {
    //Random
    Random random = new Random();

    //Game Dif
    private final int EASY = 2;
    private final int NORMAL = 5;
    private final int HARD = 10;

    // Face card & Ace values
    private final int KING = 10;
    private final int QUEEN = 10;
    private final int JACK = 10;
    private int ACE = 1;


    private int USER;
    private int USERBAL = 500;
    private int DEALER;

    private String[] ACES = {"ACE OF HEARTS", "ACE OF SPADES", "ACE OF COLVERS", "ACE OF DIAMONDS"};
    private String[] KING = {"KING OF HEARTS", "KING OF SPADES", "KING OF COLVERS", "KING OF DIAMONDS"};
    private String[] QUEENS = {"QUEEN OF HEARTS", "QUEEN OF SPADES", "QUEEN OF COLVERS", "QUEEN OF DIAMONDS"};
    private String[] JACKS = {"JACK OF HEARTS", "JACK OF SPADES", "JACK OF COLVERS", "JACK OF DIAMONDS"};

    


    // Set game DECK
    private int[] setGameDeck() {
        int[] deck = {ACE, 2, 3, 4, 5, 6, 7, 8, 9, 10, JACK, QUEEN, KING};

        return deck;
    }

    private int dealCard(int[] deck, Scanner input, int status) {
        int num = deck[random.nextInt(deck.length)];
	if (num == ACE && status == 0) {
		System.out.println("You got an Ace -> 1 or 11:");
		System.out.print(">>> ");
		int choice = input.nextInt();
		input.nextLine();
		if (choice == 1){
			return 1;
		} else if (choice == 11){
			return 11;
		} else {
			return 1;
		}


	} else if (num == ACE && status == 1) {
		int[] pick = {1,11};
		return pick[random.nextInt(pick.length)];
	}

        return num;
    }

    private void setUserBal(int amount) {
        USERBAL = amount;
    }

    private int getUserBal() {
        return USERBAL;
    }


    private boolean checkPlaceBet(int amount) {
        if (amount > getUserBal()) {
            return false;
        } else {
            int newBal = getUserBal() - amount;
            setUserBal(newBal);
            return true;
        }
    }


    //User picks diffuclty & place bets
    private void setup(Scanner input) {
        System.out.println("Place your bets! | BALANCE: " + getUserBal() + " | Place in increments of 5 | Fun Fact: 99.9% of losers quit before they WIN:");
        System.out.print(">>> ");
	int placeBet = input.nextInt();
        input.nextLine();

	if((placeBet % 5) != 0){
		System.out.println("Please place a bet in increments of 5");
		setup(input);
		return;
	}

        boolean checkBet = checkPlaceBet(placeBet);

        if (!checkBet) {
            System.out.println("Please place another bet!");
            setup(input);
        } else {
            System.out.println("Lets start!");
            int[] GAMEDECK = setGameDeck();
            game(input, GAMEDECK, placeBet);
        }
    }

    private void game(Scanner input, int[] deck, int bet) {
        int userHand = 0;
        int dealerHand = 0;
        boolean playerTurn = true;
	
	userHand += dealCard(deck, input, 0);
	userHand += dealCard(deck, input, 0);


	dealerHand += dealCard(deck, input, 1);

        while (playerTurn) {
	    //Check Player Hand First
	    if (userHand > 21){
		    playerTurn = false;
		    System.out.println("YOU BUSTED OUT");
		    again(input);
		    return;
		    

	    } else if (getUserBal() < 0){
		    playerTurn = false;

	    
	    } else if (userHand == 21){
		    playerTurn = false;
		    System.out.println("YOU GOT BLACKJACK!");
		    setUserBal( getUserBal() + ( bet *2 ));
		    again(input);
	    }

	    //Then ask for playe decision
            System.out.println("Your bet: " + bet + " | Your balance: " + getUserBal() + " | DEALERS HAND: " + dealerHand + " | YOUR HAND: " + userHand);
            System.out.println("Please enter 'hit' or 'stand':");
	    System.out.print(">>> ");
	    String decision = input.nextLine();



            if (decision.equalsIgnoreCase("hit")) {
                userHand += dealCard(deck, input, 0);
                
            } else if (decision.equalsIgnoreCase("stand")) {	
		dealerHand += dealCard(deck, input, 1);
                System.out.println("Dealer's hand: " + dealerHand);
		playerTurn = false;
		
		boolean dealerTurn = true;

		while (dealerTurn || dealerHand < 17) {
			 if (dealerHand < userHand){
				 dealerHand += dealCard(deck, input, 1);
			 	 System.out.println("Dealer's hand: " + dealerHand);
			 } else if (dealerHand > userHand && dealerHand <= 21) {
				 dealerTurn = false;
			 } else if (dealerHand > 21) {
				 dealerTurn = false;
			 } else if (dealerHand == userHand){
				 dealerTurn = false;
				 System.out.println("TIE");
				 setUserBal( getUserBal() + bet);
				 again(input);
				 return;
			 }
      		}
            } else {
                System.out.println("Please enter 'hit' or 'stand'");
            }
        }

	//End of Loop
	if (userHand > dealerHand || dealerHand > 21) {
		System.out.println("You Won!");
		setUserBal( getUserBal() + (bet * 2));
		again(input);

	} else if (dealerHand == 21 || dealerHand > userHand){
		System.out.println("Dealer Won!");
		again(input);

	} else if (getUserBal() == 0 || getUserBal() < 0){
		System.out.println("YOURE BROKE ASF GTFO OUTTA HERE");
	}
    }


    private void again(Scanner input){
	    if(getUserBal() <= 0){
		    System.out.println("YOURE BROKE ASF WTF R U DOING");
		    return;
	    }

	    System.out.println("Would you like to play again?");
	    System.out.print(">>> ");
	    String playAgain = input.nextLine();
	    if(playAgain.equalsIgnoreCase("yes")) {
			setup(input);

	    } else {
			return;
            }
    }	



    public Game(Scanner input){
	    setup(input);
    }
}






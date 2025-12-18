import java.util.Random;
import java.util.Scanner;

public class Rock_Paper_Scissors {

    public static String greeting(Scanner scanner, int rating) {
        String player;

        System.out.print("Enter your name: ");
        player = scanner.nextLine();
        System.out.println("Hello, " + player + "!");
        System.out.println("Current Score: " + rating);
        System.out.println("Let's play Rock-Paper-Scissors!");
        System.out.println("Commands:");
        System.out.println("!play   = start playing");
        System.out.println("!rating = show your current score");
        System.out.println("!help   = show commands");
        System.out.println("!exit   = exit the game");

        return player;
    }

    public static int userWin(String computer) {
        System.out.println("Well done. The computer chose " + computer + " and failed");
        return 100;
    }

    public static int userLose(String computer) {
        System.out.println("Sorry, but the computer chose " + computer);
        return 0;
    }

    public static int userDraw(String computer) {
        System.out.println("There is a draw (" + computer + ")");
        return 50;
    }

    public static int[] rollSkill(Random random) {
        int skillOwner = 0;
        int skillType = 0;

        int chance = random.nextInt(100);
        if (chance < 30) {
            skillOwner = random.nextBoolean() ? 1 : 2;
            skillType = random.nextBoolean() ? 1 : 2;
        }

        return new int[]{skillOwner, skillType};
    }

    public static void announceSkill(String playerName, int[] skill) {
        int owner = skill[0];
        int type = skill[1];

        if (owner == 0) 
            return;

        String who = (owner == 1) ? playerName : "Computer";
        if (type == 1) {
            System.out.println("[Skill] " + who + " got: AI attack has no effect (Shield)");
        } else if (type == 2) {
            System.out.println("[Skill] " + who + " got: Attack x2 (Double)");
        }
    }

    public static int playGame(String playerChoice, String computer,
                               boolean userShield, boolean aiShield, boolean userDouble) {

        int rating = 0;

        if (playerChoice.equals(computer)) {
            return userDraw(computer);
        }

        boolean userWins = false;
        boolean userLoses = false;

        if (playerChoice.equals("rock")) {
            if (computer.equals("scissors")) userWins = true;
            else if (computer.equals("paper")) userLoses = true;
        } else if (playerChoice.equals("paper")) {
            if (computer.equals("rock")) userWins = true;
            else if (computer.equals("scissors")) userLoses = true;
        } else if (playerChoice.equals("scissors")) {
            if (computer.equals("paper")) userWins = true;
            else if (computer.equals("rock")) userLoses = true;
        } else {
            System.out.println("Invalid input!");
            return 0;
        }

        if (userWins) {
            rating = userWin(computer);
            if (userDouble) {
                rating *= 2;
                System.out.println("[Skill Effect] Your attack is x2! Bonus doubled.");
            }
        } else if (userLoses) {
            if (userShield) {
                rating = userDraw(computer);
                System.out.println("[Skill Effect] Shield activated! AI attack has no effect.");
            } else if (aiShield) {
                rating = userDraw(computer);
                System.out.println("[Skill Effect] Computer's Shield activated! Your attack has no effect.");
            } else {
                rating = userLose(computer);
            }
        }

        return rating;
    }

    public static String getComputerChoice(Random random) {
        String[] computerChoices = {"rock", "paper", "scissors"};
        return computerChoices[random.nextInt(computerChoices.length)];
    }

    public static void printHelp() {
        System.out.println("Commands:");
        System.out.println("!play   = start playing");
        System.out.println("!rating = show your current score");
        System.out.println("!help   = show commands");
        System.out.println("!exit   = exit the game");
        System.out.println("Moves: rock, paper, scissors");
    }

    public static void main(String[] args) {

        int rating = 0;
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        boolean bronzeShown = false;
        boolean silverShown = false;
        boolean goldShown = false;

        String playerName = greeting(scanner, rating);
        boolean playing = false;

        while (true) {
            System.out.print("> ");
            String choice = scanner.nextLine().toLowerCase();

            if (choice.equals("!exit")) {
                System.out.println("Your rating: " + rating);
                System.out.println("Bye!");
                break;
            }

            if (choice.equals("!rating")) {
                System.out.println("Your rating: " + rating);
                continue;
            }

            if (choice.equals("!help")) {
                printHelp();
                continue;
            }

            if (choice.equals("!play")) {
                playing = true;
                System.out.println("Game started! Type: rock / paper / scissors");
                continue;
            }

            if (!playing) {
                System.out.println("Type !play to start playing (or !help for commands).");
                continue;
            }

            String computer = getComputerChoice(random);

            int[] skill = rollSkill(random);
            announceSkill(playerName, skill);

            boolean userShield = (skill[0] == 1 && skill[1] == 1);
            boolean aiShield = (skill[0] == 2 && skill[1] == 1);
            boolean userDouble = (skill[0] == 1 && skill[1] == 2);

            rating += playGame(choice, computer, userShield, aiShield, userDouble);

            if (rating >= 1000 && !bronzeShown) {
                System.out.println("+----------------------------+");
                System.out.println("|         BRONZE MEDAL!      |");
                System.out.println("|   You reached 1000 points  |");
                System.out.println("+----------------------------+");
                bronzeShown = true;
            }

            if (rating >= 5000 && !silverShown) {
                System.out.println("+----------------------------+");
                System.out.println("|         SILVER MEDAL!      |");
                System.out.println("|   You reached 5000 points  |");
                System.out.println("+----------------------------+");
                silverShown = true;
            }

            if (rating >= 10000 && !goldShown) {
                System.out.println("+------------------------------------------------------+");
                System.out.println("|                   GOLD MEDAL!                        |");
                System.out.println("|            You reached 10000 points                  |");
                System.out.println("|                                                      |");
                System.out.println("|  Ai: You’re really boring, you know.                 |");
                System.out.println("|  But it’s not like I’m playing with you because      |");
                System.out.println("|  I like you or anything!                             |");
                System.out.println("+------------------------------------------------------+");
                goldShown = true;
            }
        }
    }
}

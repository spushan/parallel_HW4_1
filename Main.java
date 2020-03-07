
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {

    public static void main( String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of players :");
         int playerNum = scanner.nextInt();
        scanner.close();
        // creates the player object and push it in the array list
        //long start = System.currentTimeMillis();
        ArrayList<Player> playerList = new ArrayList<Player>();
        for (int i = 0; i < playerNum; i++) {
            Player temp = new Player(String.valueOf(i), playerList);
            playerList.add(temp);
        }
        // end
        
        Player currentHighPlayer = null;
        while (playerList.size() > 1) {
            ExecutorService service = Executors.newCachedThreadPool();
            for (Player temp : playerList) {
                temp.setGesture(); // picks from rock paper scissors
                
            }
            for (Player temp : playerList) {
                service.execute(temp);
            }
            service.shutdown();
            

            currentHighPlayer = playerList.get(0);
            for (Player temp : playerList) {
                if (temp.getScore() >= currentHighPlayer.getScore()) {
                    
                    currentHighPlayer = temp;
                }
            }
            currentHighPlayer.setWinner(true);
            currentHighPlayer.start();
        }
        System.out.println("The winner is " + currentHighPlayer.getName());
        //long end = System.currentTimeMillis();
        //System.out.println("Time: " + (end-start));
    }

    public static class Player implements Runnable {
        private String name;
        private int gesture;
        private int score;
        private ArrayList<Player> playerList;
        private boolean winner = false;

        public Player(String name, ArrayList<Player> playerList) {
            this.name = "Player " + name;
            this.playerList = playerList;
        }

        public String getName() {
            return name;
        }

        public int getGesture() {
            return gesture;
        }

        public void setGesture() {
            Random random = new Random();
            this.gesture = random.nextInt(3);
        }

        public int getScore() {
            return score;
        }

        public void check(int opponent) {
            switch (opponent) {
                case 1:
                    if (gesture == 2) {
                        score++;
    
                    } else if (gesture == 3) {
                        score--;
                        
                    }
                    break;
                case 2:
                    if (gesture == 3) {
                        score++;
                        
                    } else if (gesture == 1) {
                        score--;
                        
                    }
                    break;
                case 3:
                    if (gesture == 1) {
                        score++;
                        
                    } else if (gesture == 2) {
                        score--;
                        
                    }
                    break;
                default:
                    break;
            }
        }

        public void setWinner(boolean result) {
            this.winner = result;
        }

        public void run() {
            if (winner) {
                Player currentLowPlayer = playerList.get(0);
                for (Player temp : playerList) {
                    if (temp.getScore() < currentLowPlayer.getScore()) {
                        currentLowPlayer = temp;
                    }
                }
            } else {
                for (Player temp : playerList) {
                    check(temp.getGesture());
                }
            }
        }

    }


}

































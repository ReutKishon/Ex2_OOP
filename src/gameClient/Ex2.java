package gameClient;

import gui.Window;


public class Ex2 {

    private static int playerID;
    private static int num_level;


    public static void main(String[] args) {


        if (args.length > 0) {

            try {
                var id = Integer.parseInt(args[0]);
                var level = Integer.parseInt(args[1]);
                GameEntryPoint gameEntryPoint = new GameEntryPoint(level, id);
                gameEntryPoint.run();
            } catch (NumberFormatException e) {
                System.out.println("Usage java -jar program.jar [YOUR_ID] [LEVEL_NUMBER]");
            }
            return;
        }


        Window window = new Window();
    }


}




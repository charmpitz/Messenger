package Client;

public class ConsoleListener extends Thread {

    @Override
    public void run() {
        System.out.println("ConsoleListener started.");
        try {
            while( Client.isActive() ) {
                // Read from the keyboard if something is sent
                String line = Client.scanner.nextLine();

                if(!line.isEmpty()) {
                    CommandHandler handler = new CommandHandler(line);
                    handler.executeCommand();
                }

                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Client.close();
        }
        System.out.println("ConsoleListener ended.");
    }
}

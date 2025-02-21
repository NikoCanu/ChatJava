package GUI;

public class Main {
    public static void main(String[] args) {
        ChatGui chat = new ChatGui(400, 400, "Chat");
        chat.logIn();
        chat.display(true);

        while (true){
            if(chat.getFlag() != 0){
                System.out.println(chat.getFlag());
                break;
            }
        }
        chat.display(false);
        chat.showChat();
        chat.display(true);
    }
}

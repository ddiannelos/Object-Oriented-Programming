import java.util.Scanner;

public class HW1 {
    
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        String line;
        Trie trie = new Trie();

        System.out.print("?:");

        while (sc.hasNextLine()) {
            line = sc.nextLine();
            String[] words = line.split(" ");

            switch (words[0]) {
                case "-i": {
                    if (words.length > 2) {
                        System.out.println("Wrong number of arguments on option");
                    }
                    else {
                        words[1] = words[1].toLowerCase();
                        
                        if (trie.insertWord(words[1])) {
                            System.out.println("ADD " + words[1] + " OK");
                        }
                        else {
                            System.out.println("ADD " + words[1] + " NOK");
                        }
                    }
                    
                    break;
                }
                case "-r": {
                    if (words.length > 2) {
                        System.out.println("Wrong number of arguments on option");
                    }
                    else {
                        words[1] = words[1].toLowerCase();

                        if (trie.removeWord(words[1])) {
                            System.out.println("RMV " + words[1] + " OK");
                        }
                        else {
                            System.out.println("RMV " + words[1] + " NOK");
                        }
                    }

                    break;
                }
                case "-f": {
                    if (words.length > 2) {
                        System.out.println("Wrong number of arguments on option");
                    }
                    else {
                        words[1] = words[1].toLowerCase();

                        if (trie.findWord(words[1])) {
                            System.out.println("FND " + words[1] + " OK");
                        }
                        else {
                            System.out.println("FND " + words[1] + " NOK");
                        }
                    }
                    
                    break;
                }
                case "-p": {
                    if (words.length > 1) {
                        System.out.println("Wrong number of arguments on option");
                    }
                    else {
                        trie.printPreOrder();
                    }

                    break;
                }
                case "-d": {
                    if (words.length > 1) {
                        System.out.println("Wrong number of arguments on option");
                    }
                    else {
                        trie.printDictionary();
                    }
                    
                    break;
                }
                case "-w": {
                    break;
                }
                case "-s": {
                    break;
                }
                case "-q": {
                    System.out.println("Bye bye!\n");
                    return;
                }
                default: {
                    System.out.println("OPTION NOT AVAILABLE");
                }
            }
            System.out.print("?:");
        }
        sc.close();
    }
}
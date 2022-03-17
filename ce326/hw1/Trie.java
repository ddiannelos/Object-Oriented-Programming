package ce326.hw1;

public class Trie {
    public TrieNode root = new TrieNode(false, null);

    public Trie() {
        root.parent = root;
    }

    // ***insertWord***
    public boolean insertWord(String word) {
        TrieNode current = root;
        
        // Check if the child with the appropriate
        // position in the array, exists
        while (current.children[word.charAt(0)-'a'] != null) {
            int i;
            TrieNode child = current.children[word.charAt(0)-'a'];
            
            // Find the position the two strings differ
            for (i = 0; i < child.content.length(); i++) {
                if (i == word.length() || child.content.charAt(i) != word.charAt(i)) {
                    break;
                }
            }

            // If this position is equal to the child's string length
            // and the word's string length then check if the word 
            // already exists in the trie
            if (i == child.content.length() && i == word.length()) {
                if (child.isWord) {
                    return false;
                }
                else {
                    child.isWord = true;
                    
                    return true;
                }
            }
            // Else if the position is equal to the child's string length and
            // less than the word's length then remove from word the child's
            // string, go to a lower level of the trie and continue
            else if (i == child.content.length() && i < word.length()) {
                word = word.substring(i, word.length());
                current = child;
            }
            // Else if the position is less than the child's string length and
            // equal to the word's length then make a new node for the word,
            // remove from child's string the word, set the new child's string
            // as the word, go to a lower level of the trie and continue
            else if (i < child.content.length() && i == word.length()) {
                String temp = child.content.substring(i,child.content.length());
                
                current.children[word.charAt(0)-'a'] = new TrieNode(true, current, word);
                current = current.children[word.charAt(0)-'a'];
                
                word = temp;
            }
            // Else make a new node for the substring of the word (from the 
            // beginning till the position), remove this substring from child's 
            // string and word, set the new node as the child, move the previous
            // child to a lower level, go to the new node and continue
            else {
                TrieNode temp = child;
                temp.content = temp.content.substring(i, temp.content.length());
                
                current.children[word.charAt(0)-'a'] = new TrieNode(false, current, word.substring(0, i));
                current = current.children[word.charAt(0)-'a'];
                current.children[temp.content.charAt(0)-'a'] = temp;
                
                word = word.substring(i, word.length());
            }
        }

        // Make a new node for the word
        current.children[word.charAt(0)-'a'] = new TrieNode(true, current, word);
        
        return true;
    }

    // ***removeWord***
    public boolean removeWord(String word) {
        TrieNode current = root;

        // Check if the child with the appropriate
        // position in the array, exists
        while (current.children[word.charAt(0)-'a'] != null) {
            TrieNode child = current.children[word.charAt(0)-'a'];
            // Compare child's string length with 
            // the word's length
            int diff = child.content.length() - word.length();
            
            // If child's string length is bigger
            // then there is no word that can be
            // deleted 
            if (diff > 0) {
                return false;
            }
            // Else if word's length is bigger
            // then if child's string is inside
            // the word remove the string from
            // the word and continue, else 
            // if it isn't inside the word,
            // then there is no word that can be
            // deleted
            else if (diff < 0) {
                if (!word.startsWith(child.content)) {
                    return false;
                }
                word = word.substring(child.content.length(), word.length());
                current = child;
            }
            // Else compare child's string and word.
            // If they are different and the child 
            // is not a word then there is no word
            // that can be deleted. Else remove the
            // child depending on how many children
            // they have and then balance the tree
            else {
                diff = child.content.compareTo(word);

                if (diff != 0 && !child.isWord) {
                    return false;
                }

                int count = 0;
                TrieNode childToMove = null;

                for (TrieNode temp : child.children) {
                    if (temp != null) {
                        childToMove = temp;
                        if (++count == 2) {
                            break;
                        }
                    }
                }
                if (count == 2) {
                    child.isWord = false;
                }
                else if (count == 1) {
                    childToMove.content = child.content + childToMove.content;
                    current.children[word.charAt(0)-'a'] = childToMove;
                }
                else {
                    current.children[word.charAt(0)-'a'] = null;
                }
                
                if (current.isWord == false) {
                    count = 0;
                    childToMove = null;

                    for (TrieNode temp : current.children) {
                        if (temp!= null) {
                            childToMove = temp;
                            if (++count == 2) {
                                break;
                            }
                        }
                    }
                    if (count == 1) {
                        childToMove.content = current.content + childToMove.content;
                        current.parent.children[childToMove.content.charAt(0)-'a'] = childToMove;
                    }
                }

                return true;
            }   
        }
        
        return false;
    }

    // ***findWord***
    public boolean findWord(String word) {
        TrieNode current = root;

        // Check if the child in the approriate
        // position in the array, exists
        while (current.children[word.charAt(0)-'a'] != null) {
            int i;
            TrieNode child = current.children[word.charAt(0)-'a'];

            // Try to find if the two strings differ
            for (i = 0; i < child.content.length(); i++) {
                if (i == word.length() || word.charAt(0) != child.content.charAt(0)) {
                    return false;
                }
            }
            
            // Check if child's string is a substring of 
            // the word. If it is go to a lower level. 
            // Else if they are the same, check if it 
            // is a word
            if (i == word.length()) {
                return (child.isWord) ? true : false;
            }
            else {
                word = word.substring(i, word.length());
                current = child;
            }
        }
        
        return false;
    }

    // ***printPreOrder***
    public void printPreOrder(TrieNode node) {
        if (node == null) {
            return;
        }

        System.out.print(' ' + node.content);

        if (node.isWord) {
            System.out.print("#");
        }
        
        // For every child, print its content.
        // If it is null, add it to the count
        for (TrieNode child : node.children) {
            printPreOrder(child);
        }
        

        return;
    }

    // ***printPreOrder***
    public void printPreOrder() {
        System.out.print("PreOrder:");
        
        // For every child, print its content.
        // If it is null, add it to the count
        for (TrieNode child : root.children) {
            printPreOrder(child);
        }
        System.out.println(" ");
    }

    // ***printDictionary***
    public void printDictionary(TrieNode node, String wordToPrint) {
        if (node.isWord) {
            System.out.println(wordToPrint);
        }
        
        for (TrieNode temp : node.children) {
            if (temp != null) {
                printDictionary(temp, wordToPrint + temp.content);
            }
        }
    }

    // ***printDictionary***
    public void printDictionary() {
        System.out.println("\n***** Dictionary *****");
        
        for (TrieNode temp : root.children) {
            if (temp != null) {
                printDictionary(temp, temp.content);
            }
        }

        System.out.println();
    }

    public void findDistantWords(TrieNode node, String word, int diff, int count, String wordToPrint) {        
        int i;
        
        for (i = 0; i < node.content.length(); i++) {
            if (i == word.length()) {
                break;
            }
            else if (node.content.charAt(i) != word.charAt(i)) {
                if (++count > diff) {
                    break;
                }
            }
        }
        if (count > diff) {
            return;
        }

        if (i == word.length()) {
            if (node.content.length()-word.length()+count > diff) {
                return;
            }
        }
        skip : {
            if (i == node.content.length()) {
                if (word.length()-node.content.length()+count > diff) {
                    break skip;
                }
            }  
            
            if (node.isWord && diff == count) {
                System.out.println(wordToPrint+node.content);
            }
        }
        
        for (TrieNode temp : node.children) {
            if (temp != null) {
                findDistantWords(temp, word.substring(i, word.length()), diff, count, wordToPrint+node.content);
            }
        }
    }

    public void findDistantWords(String word, int diff) {
        System.out.println("\nDistant words of " + word + " (" + diff + ")");
        
        for (TrieNode temp : root.children) {
            if (temp != null) {
                findDistantWords(temp, word, diff, 0, "");
            }
        }
        System.out.println();        
    }

    public void endsWith(TrieNode node, String suffix, String wordToPrint) {
        if (node.isWord) {
           if ((wordToPrint+node.content).endsWith(suffix)) {
               System.out.println(wordToPrint+node.content);
           } 
        }

        for (TrieNode temp : node.children) {
            if (temp != null) {
                endsWith(temp, suffix, wordToPrint+node.content);
            }
        }
    }

    public void endsWith(String suffix) {
        System.out.println("\nWords with suffix " + suffix + ":");

        for (TrieNode temp : root.children) {
            if (temp != null) {
                endsWith(temp, suffix, "");
            }
        }
    }
}

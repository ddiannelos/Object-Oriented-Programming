public class Trie {
    public TrieNode root = new TrieNode(false);

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
                
                current.children[word.charAt(0)-'a'] = new TrieNode(true, word);
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
                
                current.children[word.charAt(0)-'a'] = new TrieNode(false, word.substring(0, i));
                current = current.children[word.charAt(0)-'a'];
                current.children[temp.content.charAt(0)-'a'] = temp;
                
                word = word.substring(i, word.length());
            }
        }

        // Make a new node for the word
        current.children[word.charAt(0)-'a'] = new TrieNode(true, word);
        
        return true;   
    }

    // ***removeWord***
    public boolean removeWord(String word) {
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
            // and the word's string length then check if this child's
            // string is a word and how many children the child has. If 
            // they are more than 2, then change isWord, else if it has 
            // only one child, then merge the 2 nodes, else if it has no 
            // children remove the node 
            if (i == child.content.length() && i == word.length()) {
                if (!child.isWord) {
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

                return true;
            }
            // Else if the position is equal to the child's string length and
            // less than the word's length then remove from word the child's
            // string, go to a lower level of the trie and continue
            else if (i == child.content.length() && i < word.length()) {
                word = word.substring(i, word.length());
                current = child;

            }
            // Else if the position is less than the child's string length
            // then the word doesn't exist in the dictionary
            else {
                return false;
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
    public boolean printPreOrder(TrieNode node) {
        if (node == null) {
            return true;
        }
        int count = 0;

        System.out.print(' ' + node.content);
        
        // For every child, print its content.
        // If it is null, add it to the count
        for (TrieNode child : node.children) {
            if (printPreOrder(child)) {
                count++;
            }
        }
        
        // If count is 26 then it is
        // a terminal node
        if (count == 26) {
            System.out.print("#");
        }

        return false;
    }

    // ***printPreOrder***
    public void printPreOrder() {
        System.out.print("PreOrder:");
        int count = 0;
        
        // For every child, print its content.
        // If it is null, add it to the count
        for (TrieNode child : root.children) {
            if (printPreOrder(child)) {
                count++;
            }
        }
        
        // If count is 26 then it is
        // a terminal node
        if (count == 26) {
            System.out.print("#");
        }
        System.out.println(" \n");
    }

    // ***printDictionary***
    public void printDictionary(TrieNode node, String word) {
        if (node.isWord) {
            System.out.println(word);
        }
        
        for (TrieNode temp : node.children) {
            if (temp != null) {
                printDictionary(temp, word + temp.content);
            }
        }
    }

    // ***printDictionary***
    public void printDictionary() {
        System.out.println("\n***** Dictionary *****");
        
        for (TrieNode node : root.children) {
            if (node != null) {
                printDictionary(node, node.content);
            }
        }

        System.out.println();
    }
}

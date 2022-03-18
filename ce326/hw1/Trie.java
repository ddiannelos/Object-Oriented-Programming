package ce326.hw1;

public class Trie {
    public TrieNode root = new TrieNode(false, null);

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
            // as the word, copy child's children to the new node,
            // go to a lower level of the trie and continue
            else if (i < child.content.length() && i == word.length()) {
                String temp = child.content.substring(i,child.content.length());
                
                current.children[word.charAt(0)-'a'] = new TrieNode(true, word);
                
                for (int j = 0; j < child.children.length; j++) {
                    current.children[word.charAt(0)-'a'].children[i] = child.children[i];
                }
                
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
    public boolean removeWord (TrieNode node, String word) {
        TrieNode child = node.children[word.charAt(0)-'a'];

        // Check if the child with the approriate
        // position in the array, exists
        if (child == null) {
            return false;
        }
        
        // Compare child's string length with 
        // the word's length
        int diff = child.content.length()-word.length();

        // If child's string lenghth is bigger
        // then there is no word that can be
        // deleted
        if (diff > 0) {
            return false;
        }
        // Else if wor'ds length is bigger
        // then if child's string is inside
        // the word, remove the string from
        // the word and go deeper in the trie
        // in the appropriate child, else 
        // if it isn't inside the word,
        // then there is no word that can be
        // deleted
        else if (diff < 0) {
            if (!word.startsWith(child.content)) {
                return false;
            }
            if (!removeWord(node.children[word.charAt(0)-'a'], word.substring(child.content.length(), word.length()))) {
                return false;
            }
        }
        // Else compare child's string and word.
        // If they are different or the child is
        // not a word, then there is no word
        // that can be deleted. Else remove the
        // child depending on how many children 
        // they have
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
                for (int i = 0; i < child.children.length; i++) {
                    child.children[i] = childToMove.children[i];
                }
                node.children[word.charAt(0)-'a'].content = node.children[word.charAt(0)-'a'].content + childToMove.content;
                node.children[word.charAt(0)-'a'].isWord = childToMove.isWord;
            }
            else {
                node.children[word.charAt(0)-'a'] = null;
            }
            
            return true;
        }

        // If the node is a word then
        // balance the tree after the
        // removal of a node
        if (node.isWord == false) {
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
            if (count == 1) {
                for (int i = 0; i < child.children.length; i++) {
                    child.children[i] = childToMove.children[i];
                }
                node.children[word.charAt(0)-'a'].content = node.children[word.charAt(0)-'a'].content + childToMove.content;
                node.children[word.charAt(0)-'a'].isWord = childToMove.isWord;
            }
        }

        return true;
    }

    // ***removeWord***
    public boolean removeWord(String word) {
        return removeWord(root, word);
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

    // ***findDistantWords***
    public void findDistantWords(TrieNode node, String word, int diff, int count, String wordToPrint) {        
        int i;
        
        // Find the position the node's string and 
        // word differ, and count the different
        // characters 
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

        // Check if the different characters
        // are more than the given difference
        if (count > diff) {
            return;
        }

        // If word's length was reached, check
        // if the additional characters of node's
        // string plus the count of the different
        // characters is bigger than the given
        // difference
        if (i == word.length()) {
            if (node.content.length()-word.length()+count > diff) {
                return;
            }
        }
        
        skip : {
            // If node's string was reached, check
            // if the additional characters of node's 
            // string plus the count of the different
            // characters is bigger than the given
            // difference
            if (i == node.content.length()) {
                if (word.length()-node.content.length()+count > diff) {
                    break skip;
                }
            }  
            
            // Check if node is a word and the difference
            // between the node's string and the word
            // is exactly the given difference
            if (node.isWord && diff == count) {
                System.out.println(wordToPrint+node.content);
            }
        }
        
        // Check deeper into the trie for other
        // distant words
        for (TrieNode temp : node.children) {
            if (temp != null) {
                findDistantWords(temp, word.substring(i, word.length()), diff, count, wordToPrint+node.content);
            }
        }
    }

    // ***findDistantWords***
    public void findDistantWords(String word, int diff) {
        System.out.println("\nDistant words of " + word + " (" + diff + "):");
        
        for (TrieNode temp : root.children) {
            if (temp != null) {
                findDistantWords(temp, word, diff, 0, "");
            }
        }
        System.out.println();        
    }

    // ***endsWith***
    public void endsWith(TrieNode node, String suffix, String wordToPrint) {
        // Check if the node is a word and
        // if it ends with the suffix
        if (node.isWord) {
           if ((wordToPrint+node.content).endsWith(suffix)) {
               System.out.println(wordToPrint+node.content);
           } 
        }

        // Go deeper in the Trie to find
        // other words that ends with suffix
        for (TrieNode temp : node.children) {
            if (temp != null) {
                endsWith(temp, suffix, wordToPrint+node.content);
            }
        }
    }

    // ***endsWith***
    public void endsWith(String suffix) {
        System.out.println("\nWords with suffix " + suffix + ":");

        for (TrieNode temp : root.children) {
            if (temp != null) {
                endsWith(temp, suffix, "");
            }
        }
        System.out.println();
    }
}

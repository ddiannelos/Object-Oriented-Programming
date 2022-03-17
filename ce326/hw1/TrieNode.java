package ce326.hw1;

public class TrieNode {
    public TrieNode[] children = new TrieNode[26];
    public TrieNode parent;
    public String content = new String();
    public boolean isWord;

    public TrieNode(boolean isWord, TrieNode parent) {
        this.isWord = isWord;
        this.parent = parent;
    }
    
    public TrieNode(boolean isWord, TrieNode parent, String content) {
        this.isWord = isWord;
        this.parent = parent;
        this.content = content;
    }
}
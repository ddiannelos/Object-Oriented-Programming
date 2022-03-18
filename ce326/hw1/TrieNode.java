package ce326.hw1;

public class TrieNode {
    public TrieNode[] children = new TrieNode[26];
    public String content = new String();
    public boolean isWord;

    public TrieNode(boolean isWord) {
        this.isWord = isWord;
    }
    
    public TrieNode(boolean isWord, String content) {
        this.isWord = isWord;
        this.content = content;
    }
}
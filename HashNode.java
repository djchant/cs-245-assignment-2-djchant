public class HashNode {
    public String key;
    public String value;
    HashNode next;

    public HashNode(String key, String value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }
}

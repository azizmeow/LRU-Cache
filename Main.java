import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
  public static class LRU {
      
    class Node {
        int key, value;
        Node next, prev;
    }
      
    HashMap<Integer, Node> cache;
    int cap;
    Node head;
    Node tail;
    
    LRU(int capacity) {
        cache = new HashMap<>();
        cap = capacity;
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.prev = head;
    }
    
    private void addNode(Node node) { // always add at front
        Node nbr = head.next;
        
        node.next = nbr;
        nbr.prev = node;
        
        head.next = node;
        node.prev = head;
    }
    
    private void removeNode(Node node) {
        Node prevNbr = node.prev;
        Node nextNbr = node.next;
        
        prevNbr.next = nextNbr;
        nextNbr.prev = prevNbr;
        
        node.next = node.prev = null;
    }
    
    private void moveAtFront(Node node) {
        removeNode(node);
        addNode(node);
    }

    public int get(int key) {
        int ans = -1;
        Node val = cache.get(key);
        if(val==null) {
            return ans;
        } else {
            ans = val.value;
            moveAtFront(val);
        }
        return ans;
    }

    public void put(int key, int value) {
        Node val = cache.get(key);
        if(val==null) {
            Node temp = new Node();
            temp.key = key;
            temp.value = value;
            if(cache.size()==cap) {
                Node LRU_Node = tail.prev;
                cache.remove(LRU_Node.key);
                removeNode(LRU_Node);
            }
            cache.put(key, temp);
            addNode(temp);
        } else {
            val.value = value;
            moveAtFront(cache.get(key));
        }
    }
  }
  public static void main(String[] args) throws Exception {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String str = br.readLine();
    LRU obj = new LRU(Integer.parseInt(str.split(" ")[1]));

    while (true) {
      str = br.readLine();
      String inp[] = str.split(" ");
      if (inp.length == 1) {
        break;
      } else if (inp.length == 2) {
        System.out.println(obj.get(Integer.parseInt(inp[1])));
      } else if (inp.length == 3) {
        obj.put(Integer.parseInt(inp[1]), Integer.parseInt(inp[2]));
      }
    }
  }
}

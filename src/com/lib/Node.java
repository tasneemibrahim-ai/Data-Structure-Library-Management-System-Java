
package com.lib;

public class Node {
  public Object item;
  public Node next;

  public boolean equals(Node item){
    return this.item.equals(item) && this.next.equals(item.next);
  }

}

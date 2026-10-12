package com.lib;

public class linkedList<Object> {
  public int length = 0;
  public Node head = null;

  public Object get(int n) {
    if (head != null) {
      Node current = head;
      for (int i = 0; i < n; i++) {
        current = current.next;
      }
      return (Object) current.item;
    } else return null;
  }

//  public int getIndexOf(Object item){
//    if (head == null) return -1;
//    Node current = head;
//    int index = 0;
//    for (int i = 0; i < this.length; i++) {
//      while (current.next != null) {
//        if (current.item.equals(item)) return index;
//        else {
//          current = current.next;
//          index++;
//        }
//      }
//    }
//    return index;
//  }

  public void insert(Object item) {
    Node node = new Node();
    node.item = item;
    Node current = this.head;
    if (head == null) {
      this.head = node;
      this.head.next = null;
      this.length = 1;
    }else {
      for (int i = 0; i <= this.length; i++){
        while(current.next != null) {
          current = current.next;
        }
      }
      current.next = node;
      node.next = null;
      length++;
    }
  }

  public void remove(Object item) {
    if(head!=null){
      if(length == 1){
        head = null;
        return;
      } else if (head.item == item) {
        head = head.next;
      }
      Node prev;
      Node current = head;

        for (int i = 0; i < this.length; i++) {
          while (current.next != null) {
            prev = current;
            current = current.next;
            if (current.item.equals(item)) {
              prev.next = current.next;
            }
        }
      }
    }
  }


  public boolean contains(Object item) {
    if(head != null) {
      Node current = head;
      boolean isFound = false;
      for (int i = 0; i < this.length; i++) {
       
            isFound = true;
            break;
           
         
      }
      return isFound;
    }else return false;
  }

  public boolean isEmpty() {
    return this.head == null & length == 0;
  }

  public Node getMiddle(Node head) {
    if (head == null) return null;
    Node slow = head, fast = head;

    while(fast.next!=null){
      if (fast.next.next == null) break;
      slow = slow.next;
      fast = fast.next.next;
    }
    return slow;
  }

  Node mergeSort(Node head) {
    if(head == null || head.next == null) {
      return head;
    }

    Node middle = getMiddle(head);
    Node nextOfMiddle = middle.next;
    middle.next = null;

    Node left = mergeSort(head);
    Node right = mergeSort(nextOfMiddle);

    return merge(left, right);
  }

  Node mergeSortISBN(Node head) {
    if(head == null || head.next == null) {
      return head;
    }

    Node middle = getMiddle(head);
    Node nextOfMiddle = middle.next;
    middle.next = null;

    Node left = mergeSortISBN(head);
    Node right = mergeSortISBN(nextOfMiddle);

    return mergeISBN(left, right);
  }

  Node merge(Node left, Node right) {
    if(left == null) {
      return right;
    }

    if(right == null) {
      return left;
    }
    Node temp = null;
    if(left.item instanceof book && right.item instanceof book) {
      if (((book) left.item).getTitle().compareToIgnoreCase(((book) right.item).getTitle()) <= -1) {
        temp = left;
        temp.next = merge(left.next, right);
      } else {
        temp = right;
        temp.next = merge(left, right.next);
      }
    } else if (left.item instanceof user && right.item instanceof user) {
      if(((user) left.item).getUsername().compareToIgnoreCase(((user) right.item).getUsername()) <= -1 ) {
        temp = left;
        temp.next = merge(left.next, right);
      } else {
        temp = right;
        temp.next = merge(left, right.next);
      }
    }

    return temp;
  }

  Node mergeISBN(Node left, Node right) {
    if(left == null) {
      return right;
    }

    if(right == null) {
      return left;
    }
    Node temp = null;
    if(left.item instanceof book && right.item instanceof book) {
      if (((book) left.item).getISBN().compareToIgnoreCase(((book) right.item).getISBN()) <= -1) {
        temp = left;
        temp.next = merge(left.next, right);
      } else {
        temp = right;
        temp.next = merge(left, right.next);
      }
    }
    return temp;
  }
public String toString() {
  if (this.head != null && !(this.head.item instanceof book)) {
    String s = "[ \n" + head.item;
    Node temp = head;
    for (int i = 0; i < length; i++) {
      while (temp.next != null) {
        temp = temp.next;
        s = s + temp.item.toString() + ' ';

      }
    }
    return s + "\n]";
  } else if (this.head != null) {
    StringBuilder s = new StringBuilder("{ ");
    Node temp = head;
    for (int i = 0; i < length; i++) {
      try {
        s.append(temp.item).append(' ');
      } catch (NullPointerException e) {
        continue;
      }
      temp = temp.next;
    }
      return s + "\n}";

    }
    else{
      return " ";
    }
  }
}
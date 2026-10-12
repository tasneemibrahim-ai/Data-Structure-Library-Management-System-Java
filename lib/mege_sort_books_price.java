package com.lib;
import java.util.ArrayList;
import java.util.List;


public class mege_sort_books_price {
   public void merge(List<book> booksList, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        List<book> leftList = new ArrayList<>(booksList.subList(left, mid + 1));
        List<book> rightList = new ArrayList<>(booksList.subList(mid + 1, right + 1));

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (leftList.get(i).getPrice() <= rightList.get(j).getPrice()) {
                booksList.set(k, leftList.get(i));
                i++;
            } else {
                booksList.set(k, rightList.get(j));
                j++;
            }
            k++;
        }

        while (i < n1) {
            booksList.set(k, leftList.get(i));
            i++;
            k++;
        }

        while (j < n2) {
            booksList.set(k, rightList.get(j));
            j++;
            k++;
        }
    }}

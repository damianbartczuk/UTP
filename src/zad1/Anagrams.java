/**
 * @author Bartczuk Damian S17763
 */

package zad1;


import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Anagrams {
    String path;
    List<String> words = null;

    public Anagrams(String path) {
        this.path = path;
    }

    List<List<String>> getSortedByAnQty() {
        List<List<String>> list = new ArrayList<>();
        List<String> arr = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            arr = new ArrayList<>();

            while ((line = br.readLine()) != null) {
//                arr.add(line);
                arr.addAll(Arrays.asList(line.split(" ")));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.words = arr;

        List<String> t;


        for (int i = arr.size() - 1; i >= 0; i--) {
            t = new ArrayList<>();
            for (int j = arr.size() - 1; j >= 0; j--) {
                if (myEquals(arr.get(i), arr.get(j)))
                    t.add(arr.get(j));
            }
            if (!list.contains(t)) {
                list.add(t);
            }
        }

/*        for (int i = 0; i < list.size(); i++) {
            Collections.sort(list.get(i));
        }*/

        for (List<String> tmp : list) {
            Collections.sort(tmp);
        }

        Comparator<List<String>> c = (l1, l2) -> {
            if (l1.size() == l2.size()) {
                return l1.get(0).compareTo(l2.get(0));
            } else if (l1.size() > l2.size())
                return -1;
            else
                return 1;
        };

        list.sort(c);


        return list;
    }

    String getAnagramsFor(String str) {
        List<String> tmp = new ArrayList<>();
        for (int i = 0; i < this.words.size(); i++) {
            if (!str.equals(words.get(i)))
                if (myEquals(str, words.get(i)))
                    tmp.add(words.get(i));

        }
        return str + ": " + tmp;
    }

    boolean myEquals(String str1, String str2) {
        boolean contain = false;
        if (str1.length() != str2.length())
            return false;
        for (int i = 0; i < str1.length(); i++) {
            contain = false;
            for (int j = 0; j < str2.length(); j++) {
                if (str1.charAt(i) == str2.charAt(j)) {
                    contain = true;
                    break;
                }
            }
            if (!contain)
                break;
        }
        if (contain)
            return true;
        else
            return false;
    }
}  

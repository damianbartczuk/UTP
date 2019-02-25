package zad2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProgLang {

    private String path;
    private List<String> listOfProgrammers = new ArrayList<>();
    private List<String> content = new ArrayList<>();

    ProgLang(String path) {
        this.path = path;
        getContentFromFile(path);
    }
    public void show(){
    System.out.println("moja metoda show");
    }

    private void getContentFromFile(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null) {
                content.add(line);
                System.out.println("line = " + line ) ;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Map<String, List<String>> getLangsMap() {
        Map<String, List<String>> map = new LinkedHashMap<>();
        String[] tmp;
        for (int i = 0; i < content.size(); i++) {
            tmp = content.get(i).split("\\t");
            for (int j = 1; j < tmp.length; j++) {
                if (!listOfProgrammers.contains(tmp[j]))
                listOfProgrammers.add(tmp[j]);
            }
            map.put(tmp[0], listOfProgrammers);
            listOfProgrammers = new ArrayList<>();
        }
        return map;
    }

    Map<String, List<String>> getProgsMap() {
        Map<String, List<String>> map = new LinkedHashMap<>(); // zachowanie porząkdu
        Map<String, List<String>> tmp = getLangsMap();
        Set<String> programmers = new LinkedHashSet();

        for ( String str: content) {
            programmers.addAll(tmp.get(str.split("\\t")[0]));
        }

        List<String> ll;
        List<String> listOfProg = new ArrayList<>(programmers);
        for (int i = 0; i < listOfProg.size(); i++) {
            ll = new ArrayList<>();
            for (int j = 0; j < tmp.size(); j++) {
                String[] o = content.get(j).split("\\t");
                if (tmp.get(o[0]).contains(listOfProg.get(i)))
                    ll.add(o[0]);
            }
            map.put(listOfProg.get(i), ll);
        }
        return map;
    }


    public Map getLangsMapSortedByNumOfProgs() {
        Map<String, List<String>> sortedMap = sorted(getLangsMap(),
                Collections.reverseOrder((o1, o2) ->
                        o1.getValue().size() - o2.getValue().size()
                ));
        return sortedMap;
    }

    public Map getProgsMapSortedByNumOfLangs() {
        Map<String, List<String>> sortedMap = sorted(getProgsMap(),
                Collections.reverseOrder((o1, o2) ->
                     o1.getValue().size() - o2.getValue().size()
                )
        );
        return sortedMap;
    }

    public Map<String, List<String>> getProgsMapForNumOfLangsGreaterThan(int n) {
        Map<String, List<String>> sortedMap = filtered(getProgsMap(), e ->
            e.getValue().size() > n
        );
        return sortedMap;
    }

    public <T, V> Map<T, V> sorted(Map<T, V> map, Comparator<Map.Entry<T, V>> com) {
        List<T> keys = new ArrayList<>(map.keySet());
        Map<T, V> mapTmp = new LinkedHashMap<>();
        for(int i = 0 ; i < keys.size(); i++){
            mapTmp.put(keys.get(i), map.get(keys.get(i)));
        }
        List<Map.Entry<T, V>> listToSort = new ArrayList<>(mapTmp.entrySet());
        listToSort.sort(com);
        Map<T, V> sortedMap = new LinkedHashMap<>();
        listToSort.forEach(e -> sortedMap.put(e.getKey(), e.getValue()));
        return sortedMap;
    }

    public <V, T> Map<V,T> filtered(Map<V, T> map, Predicate<Map.Entry<V, T>> pred) {
        List<Map.Entry<V, T>> entries = new ArrayList<>(map.entrySet());
        List<Map.Entry<V,T>> results = entries.stream().filter(pred).collect(Collectors.toList());
        Map<V, T> result = new LinkedHashMap<>();
        results.forEach(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }


}

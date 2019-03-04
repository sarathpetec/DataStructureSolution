package com.sarath.sample.demo;

import java.util.*;

public class TranslatorsDinner {

  public static List<Map<Integer, Integer>> getEdges(int[][] G) {
    int n = G.length;
    List<Map<Integer, Integer>> edges = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
      edges.add(new HashMap<>());
    }
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        if (G[i][j] >= 0) {
          edges.get(i).put(j, G[i][j]);
          edges.get(j).put(i, G[i][j]);
        }
      }
    }
    return edges;
  }

  public static List<Map<Integer, Integer>> buildST(int[][] G, List<Map<Integer, Integer>> edges, int n) {
    List<Map<Integer, Integer>> tree = new ArrayList<>(n);
    boolean[] vis = new boolean[n];

    Set<Integer> visset = new HashSet<>();

    for (int i = 0; i < n; i++) {
      tree.add(new HashMap<>());
    }

    vis[0] = true;
    visset.add(0);

    int cand = 0;

    while (visset.size() < n) {
      for (int node : visset) {
        boolean flag = false;
        for (int nxt : edges.get(node).keySet()) {
          if (!vis[nxt]) {
            flag = true;
            cand = nxt;
            vis[nxt] = true;
            visset.add(nxt);
            break;
          }

        }
        if (flag) {
          tree.get(node).put(cand, G[node][cand]);
          tree.get(cand).put(node, G[node][cand]);
          break;
        }
      }
    }
    return tree;
  }

  public static int getLeaf(List<Map<Integer, Integer>> tree, int n) {

    for (int i = 0; i < n; i++) {
      if (tree.get(i).size() == 1) {
        return i;
      }
    }
    return -1;
  }

  public static void pairUp(int[] partner, int[][] G) {
    int n = G.length;
    //System.out.println("G.length: "+n);
    List<Map<Integer, Integer>> edges = getEdges(G);
    //edges.stream().forEach(integerIntegerMap -> System.out.println(integerIntegerMap.keySet()));
    List<Map<Integer, Integer>> tree = buildST(G, edges, n);

    //System.out.println(tree);

    Set<Integer> unseen = new HashSet<>();
    for (int i = 0; i < n; i++) {
      unseen.add(i);
    }
    int leaf = getLeaf(tree, n);
    while (leaf >= 0) {
      unseen.remove(leaf);
      // System.out.println(tree);
      // System.out.println("leaf "+leaf);
      int adj = -1;
      if (!tree.get(leaf).isEmpty()) {
        adj = tree.get(leaf).keySet().iterator().next(); // the only tree neighbour of leaf
      }
      // System.out.println("pendant "+adj);
      int other = -1;
      if (edges.get(leaf).size() >= 0) {
        // begin pair up

        Set<Integer> nbhrs = new HashSet<>(edges.get(leaf).keySet());
        for (int buddy : nbhrs) {
          if (buddy == adj) {
            continue;
          }
          if (other < 0) {
            other = buddy;
          } else {
            // pair up other and buddy
            // System.out.println(other+" "+buddy);
            // System.out.println(G[leaf][other]+" "+G[leaf][buddy]);
            partner[G[leaf][other]] = G[leaf][buddy];
            partner[G[leaf][buddy]] = G[leaf][other];
            edges.get(leaf).remove(other);
            edges.get(leaf).remove(buddy);
            edges.get(other).remove(leaf);
            edges.get(buddy).remove(leaf);
            other = -1;
          }
        }
      }
      if ((other < 0)) {
        tree.get(adj).remove(leaf);
        tree.get(leaf).remove(adj);
      } else {
        //pair up other and adj
        // System.out.println(other+" "+adj);
        // System.out.println(G[leaf][other]+" "+G[leaf][adj]);
        partner[G[leaf][adj]] = G[leaf][other];
        partner[G[leaf][other]] = G[leaf][adj];
        tree.get(adj).remove(leaf);
        tree.get(leaf).remove(adj);
        edges.get(leaf).remove(other);
        edges.get(leaf).remove(adj);
        edges.get(other).remove(leaf);
        edges.get(adj).remove(leaf);
      }

      leaf = getLeaf(tree, n);
    }

    // we should have unseen.size()<=1 here.
    for (int v : unseen) {
      Set<Integer> nbrs = new HashSet<>(edges.get(v).keySet());
      int other = -1;
      for (int buddy : nbrs) {
        if (other < 0) {
          other = buddy;
        } else {
          partner[G[v][buddy]] = G[v][other];
          partner[G[v][other]] = G[v][buddy];
          other = -1;
        }
      }
    }

  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int noOfLanguageSpoken = sc.nextInt(), noOfHiredTranslators = sc.nextInt();
    int[][] G = new int[noOfLanguageSpoken][noOfLanguageSpoken];
    for (int i = 0; i < noOfHiredTranslators; i++) {
      int a = sc.nextInt(), b = sc.nextInt();
      G[a][b] = i + 1;
      G[b][a] = i + 1;
    }
    if ((noOfHiredTranslators & 1) == 1) { //Bitwise AND
      System.out.println("impossible");
    } else {
      for (int i = 0; i < noOfLanguageSpoken; i++) {
        for (int j = 0; j < noOfLanguageSpoken; j++) {
          G[i][j]--;
        }
      }
      /*for (int i = 0; i < noOfLanguageSpoken; i++) {
        for (int j = 0; j < noOfLanguageSpoken; j++) {
          System.out.print("["+G[i][j]+"] ");
        }
        System.out.println();
      }*/
      int[] partner = new int[noOfHiredTranslators];
      for (int i = 0; i < noOfHiredTranslators; i++) {
        partner[i] = -1;
      }
      pairUp(partner, G);
      for (int i = 0; i < noOfHiredTranslators; i++) {
        if (i > partner[i]) {
          System.out.println(i + " " + partner[i]);
        }
      }
    }
  }

}

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class MyGraph {
    song[] adjList;
    int count;
    ArrayList<String> allGenres = new ArrayList<>();

    MyGraph(int s) {
        this.adjList = new song[s];
        this.count = 0;
    }

    public void addSong(String name, String artistName, int danceAbility, ArrayList<String> genres) {
        song add = new song(name, artistName, danceAbility, genres);
        adjList[count] = add;
        count++;
    }

    public void autoEdge() {//adds an edge between all songs that have the same genre

        for (int j = 0; j < allGenres.size(); j++) {
            String genre = allGenres.get(j);
            //now we have each genre
            ArrayList<song> genreSongs = getSongsFromGenre(genre);//
            //have all songs in same genre. each song needs to be connected with each other song
            for (int i = 0; i < genreSongs.size(); i++) {
                for (int k = i + 1; k < genreSongs.size(); k++) {
                    addEdge(genreSongs.get(i), genreSongs.get(k));
                }
            }
        }
    }

    public void addEdge(song s1, song s2) {
        double diff = getDiff(s1, s2);
        Edge reverseEdge = new Edge(diff, s1);
        Edge edge = new Edge(diff, s2);
        s1.edges.add(edge);
        s2.edges.add(reverseEdge);

    }

    public double getDiff(song one, song two) {
        return Math.abs(one.danceAbility - two.danceAbility);
    }

    public int findIndex(String n) {
        int a = 0;
        int i;
        for (i = 0; i < adjList.length; i++) {
            if (n.equals(adjList[i].name)) {
                a = i;
                break;
            }
        }
        if (i == adjList.length) return -1;
        return a;
    }

    public void deleteSong(String n) {
        int toDelete = findIndex(n);
        for (int i = toDelete; i < adjList.length - 1; i++) {
            adjList[i] = adjList[i + 1];
        }
        adjList[adjList.length - 1] = null;
    }

    public void deleteEdge(String n1, String n2) {
        song v1 = findSong(n1);
        song v2 = findSong(n2);

        double d = getDiff(v1, v2);

        v1.edges.remove(findEdge(d, v2));
        v2.edges.remove(findEdge(d, v1));//the d is the differentiating factor
    }

    public Edge findEdge(double d, song connected) {

        for (int i = 0; i < adjList.length; i++) {
            for (int j = 0; j < adjList[i].edges.size(); j++) {
                if (d == adjList[i].edges.get(j).diff && connected.equals(adjList[i].edges.get(j).connectedSong)) {
                    return adjList[i].edges.get(j);
                }
            }
        }

        return null;
    }

    public song findSong(String a) {//uses BFS

        if (a.equalsIgnoreCase(adjList[adjList.length - 1].name)) {
            return adjList[adjList.length - 1];
        }

        PriorityQueue<song> queue = new PriorityQueue<>(10, new Comparator<song>() {
            @Override
            public int compare(song o1, song o2) {
                return 0;
            }
        });
        boolean[] visited = new boolean[adjList.length];

        queue.add(adjList[0]);
        visited[0] = true;

        while (!queue.isEmpty()) {
            song s1 = queue.remove();
            if (s1.name.equalsIgnoreCase(a)) {
                return s1;
            }
            for (int i = 0; i < s1.edges.size(); i++) {
                if (!visited[findIndex(s1.edges.get(i).connectedSong.name)]) {
                    visited[findIndex(s1.edges.get(i).connectedSong.name)] = true;
                    queue.add(s1.edges.get(i).connectedSong);
                    if (s1.edges.get(i).connectedSong.name.equalsIgnoreCase(a)) {
                        return s1.edges.get(i).connectedSong;
                    }
                }
            }
        }
        return null;
    }

    public void getPlayList(String songName) {
        song a = findSong(songName);
        boolean[] arr = new boolean[adjList.length];

        System.out.println("Since you liked " + songName + " by " + a.artistName + " which has a danceAbility of " + a.danceAbility + ", you may also like:");
        System.out.println();
        LinkedList<Edge> temp = a.edges;

        for (int i = 0; i < 3; i++) {
            song temp1 = findSmallest(temp, arr);
            if (!temp1.name.equals("")) {
                System.out.println(temp1.name
                        + " by " + temp1.artistName + ". It has a danceability of " + temp1.danceAbility + ".");
            }
        }
    }

    public song findSmallest(LinkedList<Edge> a, boolean[] arr) {//finds edge with smallest difference
        double min = 999.99;
        song store = new song("", "", -1, new ArrayList<>());

        for (int i = 0; i < a.size(); i++) {//all of edges of given song
            if (a.get(i).diff < min && !arr[findIndex(a.get(i).connectedSong.name)]) {
                min = a.get(i).diff;
                store = a.get(i).connectedSong;
                arr[findIndex(a.get(i).connectedSong.name)] = true;
            }
        }
        return store;
    }

    public ArrayList<song> getSongsFromGenre(String genre) {
        ArrayList<song> songsOfGenre = new ArrayList<>();
        for (int i = 0; i < adjList.length; i++) {
            if (adjList[i].genres.contains(genre)) {
                songsOfGenre.add(adjList[i]);
            }
        }
        return songsOfGenre;
    }

    public void getSongsFromArtist(String artist) {
        System.out.println("Songs recorded by " + artist + " are:");
        for (int i = 0; i < adjList.length; i++) {
            if (artist.contains(adjList[i].artistName)) {
                System.out.println(adjList[i]);
            }
        }
    }
}

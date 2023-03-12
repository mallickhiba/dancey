import java.util.ArrayList;
import java.util.LinkedList;

public class song {
    String name;
    ArrayList<String> genres;//arraylist of string?
    LinkedList<Edge> edges;
    String artistName;
    double danceAbility;
    double diff;

    song(String name, String artist, double d, ArrayList<String> g) {
        this.name = name;
        this.artistName = artist;
        this.danceAbility = d / 1000;
        this.edges = new LinkedList<>();
        this.genres = g;
    }

    public boolean equals(song a) {
        if (this.name.equalsIgnoreCase(a.name)) {
            return true;
        }
        return false;
    }


    public String toString() {
        return name +
                " by " + artistName +
                ", Danceability = " + (double) danceAbility / 1000 +
                ", Genres = " + genres;
    }

}


class Edge {
    song connectedSong;
    double diff;


    public Edge(double d, song v) {
        this.connectedSong = v;
        this.diff = d;
    }

    public boolean equals(Edge a) {
        if (this.diff == a.diff && this.connectedSong.equals(a.connectedSong)) {
            return true;
        } else return false;
    }
}

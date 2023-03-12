//DATA STRUCTURES FALL 2022 PROJECT
//ALINA AFGHAN - 24491
//AYESHA NAYYER -
//HIBA MALLICK -24015

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        MyGraph g = new MyGraph(70);//number of songs in graph

        File f = new File("projData.txt");//file we r accessing
        Scanner sc = new Scanner(f);
        while (sc.hasNextLine()) {

            String line = sc.nextLine();
            String[] d = line.split("\t", 7);//one song and its data

            ArrayList<String> genres = new ArrayList<>();
            for (int i = 3; i < d.length; i++) {
                if (!g.allGenres.contains(d[i])) {
                    g.allGenres.add(d[i]);
                }
                genres.add(d[i]);
            }
            if (!d[2].equalsIgnoreCase("")) {
                g.addSong(d[0], d[1], Integer.parseInt(d[2]), genres);
            }
        }
        g.autoEdge();


        Scanner scan = new Scanner(System.in);
        System.out.println("***WELCOME TO  DANCEY***\n" +
                "Enter 1 to view all songs \n" +
                "Enter 2 to view all genres \n" +
                "Enter 3 to search songs via genre \n" +
                "Enter 4 to search songs by artist \n" +
                "Enter 5 to find a song \n" +
                "Enter 6 to get a playlist based on danceability!");
        System.out.println();
        int c = scan.nextInt();

        if (c == 1) {
            for (int i = 0; i < g.adjList.length; i++) {
                System.out.println(g.adjList[i]);
            }
        } else if (c == 2) {
            System.out.println(g.allGenres.toString());
        } else if (c == 3) {
            System.out.println("Enter genre: ");
            String gen = scan.next();
            ArrayList<song> genreSongs = g.getSongsFromGenre(gen);
            for (int i = 0; i < genreSongs.size(); i++) {
                System.out.println(genreSongs.get(i).name + " - " + genreSongs.get(i).artistName);
            }
        } else if (c == 4) {
            System.out.println("Enter artist name: ");
            String ar = scan.next();
            if (scan.hasNextLine()) {
                ar += scan.nextLine();
            }
            g.getSongsFromArtist(ar);
        } else if (c == 5) {
            //finding song
            System.out.println("Enter song: ");
            String read = scan.next();
            if (scan.hasNextLine()) {
                read += scan.nextLine();
            }
            System.out.println(g.findSong(read));
        } else if (c == 6) {
            //getting playlist
            System.out.println("Enter song: ");
            String sng = scan.next();
            if (scan.hasNextLine()) {
                sng += scan.nextLine();
            }
            g.getPlayList(sng);
        } else {
            System.out.println("Enter a valid command");
        }


    }
}

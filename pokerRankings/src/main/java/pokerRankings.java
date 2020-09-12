
import java.util.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alper
 */
public class pokerRankings {
    public static void main(String[]args)
    {
        final Map<String,Integer> values = new HashMap<String,Integer>() ;
        String[] vals = {"2","3","4","5","6","7","8","9","T","J","Q","K","A"} ;
        int val = 2 ;
        for(String i : vals)
        {
            values.put(i,val++) ;
        }
        
        String[] hands = new String[4] ;
        generateHands(hands,vals) ;
        printHands(rank(hands,values)) ;
    }
    
    public static void generateHands(String[] hand, String[] vals)
    {
        String[] suites = {"C","S","D","H"} ;
        
        for(int i=0 ; i<4 ; i++)
        {
            String ret = "" ;
            for(int j=0 ; j<3 ; j++)
            {
                int rnd1 = new Random().nextInt(vals.length) ;
                int rnd2 = new Random().nextInt(suites.length) ;
                if(j != 2)
                    ret = ret + vals[rnd1] + suites[rnd2] +" " ;
                else
                    ret = ret + vals[rnd1] + suites[rnd2] ;
            }
            hand[i] = ret ;
        }
        for(int i=0 ; i<hand.length ; i++)
                System.out.println("Player " + (i+1) + " has : " +hand[i]);
        System.out.println() ;
    }
    
    public static int[] rank(String[] x, Map<String,Integer> values)
    {
        int[] ranks = new int[x.length] ;
        int currRank = 0 ;
        String[] cardStrength = new String[3] ;
        String[] cardSuite = new String[3] ;
        
        //Seperate strength and suite for easier checking.
        for(int i=0 ; i<x.length ; i++)
        {
            currRank = 0 ;
            String[] cards = x[i].split(" ") ;
            for(int j= 0 ; j<cards.length ; j++)
            {
                cardStrength[j] = cards[j].charAt(0)+"" ;
                cardSuite[j] = cards[j].charAt(1)+"" ;
            }
            
            //Assign card value
            for(int j=0 ; j<cardStrength.length ; j++)
                currRank += values.get(cardStrength[j]) ;
            
            //Check Three of a Kind
            if(cardStrength[0].equals(cardStrength[1]) && cardStrength[0].equals(cardStrength[2]))
                currRank += 800 ;
            //Check Pair
            else if(cardStrength[0].equals(cardStrength[1]) || cardStrength[0].equals(cardStrength[2]))
                currRank += 200 ;
            //Check Flush
            else if(cardSuite[0].equals(cardSuite[1]) && cardSuite[1].equals(cardSuite[2]))
                currRank += 400 ;
            
            //Check Straight
            String[] temp = cardStrength.clone() ;
            Arrays.sort(temp);
            if(values.get(temp[0]) == values.get(temp[1])-1 && values.get(temp[1]) == values.get(temp[2])-1)
            {
                currRank += 600 ;
            }
            ranks[i] = currRank ;
        }
        
        return ranks ;
    }
    
    public static void printHands(int[] ranks)
    {
        String specialCase = "" ;
        boolean draw = false ;
        int max = 0 ;
        int maxRank = 0 ;
        for(int i=0 ; i<ranks.length ; i++)
        {   
            System.out.println("Player " + (i+1) + " Hand Strength: " + ranks[i]);
            if(ranks[i] > max)
            {
                max = ranks[i] ;
                maxRank = i ;
            }
        }
        for(int i=0 ; i<ranks.length-1 ; i++)
        {
            for(int j=i+1 ; j<ranks.length ; j++)
            {
                if(ranks[i] == ranks[j] && ranks[i] == max)
                {
                    draw = true ;
                    System.out.println("Players " + (i+1) + " and " + (j+1) + " are on a draw and split!");
                }
            }
        }
        if(!draw)
        {
            if(max > 1000)
                specialCase = "Straight Flush" ;
            else if(max >800)
                specialCase = "Three of a Kind" ;
            else if(max >600)
                specialCase = "Straight" ;
            else if(max >400)
                specialCase = "Flush" ;
            else if(max >200)
                specialCase = "Pair" ;
            else
                specialCase = "High Card" ;
            System.out.println("\nThe winner is Player " + (maxRank+1) + " with a " + specialCase) ;
        }
    }
}
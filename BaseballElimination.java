/* BaseballElimination.java
   CSC 226 - Fall 2023
   Assignment 5 - Baseball Elimination Program
   
   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
	java BaseballElimination
	
   To conveniently test the algorithm with a large input, create a text file
   containing one or more test divisions (in the format described below) and run
   the program with
	java -cp .;algs4.jar BaseballElimination file.txt (Windows)
   or
    java -cp .:algs4.jar BaseballElimination file.txt (Linux or Mac)
   where file.txt is replaced by the name of the text file.
   
   The input consists of an integer representing the number of teams in the division and then
   for each team, the team name (no whitespace), number of wins, number of losses, and a list
   of integers represnting the number of games remaining against each team (in order from the first
   team to the last). That is, the text file looks like:
   
	<number of teams in division>
	<team1_name wins losses games_vs_team1 games_vs_team2 ... games_vs_teamn>
	...
	<teamn_name wins losses games_vs_team1 games_vs_team2 ... games_vs_teamn>

	
   An input file can contain an unlimited number of divisions but all team names are unique, i.e.
   no team can be in more than one division.
*/

import edu.princeton.cs.algs4.*;
import java.util.*;
import java.io.File;

class Teaminfo{
	int TeamID;
	String TeamName;
	int numWins;
	int GameRemaining;
	int[] GamesAgainstOthers;
}
//Do not change the name of the BaseballElimination class
public class BaseballElimination{
	
	// We use an ArrayList to keep track of the eliminated teams.
	public ArrayList<String> eliminated = new ArrayList<String>();
	private Teaminfo[] teams;
	private int totalFlow;

	/* BaseballElimination(s)
		Given an input stream connected to a collection of baseball division
		standings we determine for each division which teams have been eliminated 
		from the playoffs. For each team in each division we create a flow network
		and determine the maxflow in that network. If the maxflow exceeds the number
		of inter-divisional games between all other teams in the division, the current
		team is eliminated.
	*/
	public BaseballElimination(Scanner s){
		
		/* ... Your code here ... */
		int teamsNum = s.nextInt(); //Fetch the numbers of Team in the race
		teams = new Teaminfo[teamsNum]; // Initialize the teams class array with the number of teams in the file
		int largestWinNum = 0;

		//append each teams info separately
		for(int i = 0; i < teamsNum; i++){
			teams[i] = new Teaminfo();
			teams[i].TeamID = i;
			teams[i].TeamName = s.next();
			teams[i].numWins = s.nextInt();
			teams[i].GameRemaining = s.nextInt();
			
			//find the largest win num in the sheets
			if (teams[i].numWins > largestWinNum) {
				largestWinNum = teams[i].numWins;
			}
			// Initialize the GamesAgainstOthers array for each team
			teams[i].GamesAgainstOthers = new int[teamsNum];
			for(int j = 0; j < teamsNum; j++){
				teams[i].GamesAgainstOthers[j] = s.nextInt();
			}
		}

		for(int k = 0; k < teamsNum; k++){

			if (teams[k].numWins + teams[k].GameRemaining < largestWinNum) { //early detection to make more efficient
				eliminated.add(teams[k].TeamName);
			} else {
				boolean flag = checkForElimination(k, teamsNum);
				if (flag) {
					eliminated.add(teams[k].TeamName);
				}
			}
			
		}

	}

	public boolean checkForElimination(int teamID, int teamNum){

		FordFulkerson maxflow = teamMaxFlow(teamID, teamNum);

		if(maxflow == null){
			return true;
		}else{
			return totalFlow>maxflow.value();
		}
		
	}

	public FordFulkerson teamMaxFlow(int teamID, int teamNum) {
		int matchNodes = teamNum * (teamNum - 1) / 2;
		int totalNodesInNetWork = matchNodes + teamNum + 2;
		FlowNetwork G = new FlowNetwork(totalNodesInNetWork);
		int s = 0; // Source
		int t = totalNodesInNetWork - 1; // Sink
		int nodeIndex = 1; // Node index for matches
		totalFlow = 0;
	
		for (int i = 0; i < teamNum; i++) {
			if (i == teamID) continue;
			for (int k = i + 1; k < teamNum; k++) {
				if (k == teamID) continue;
				int games = teams[i].GamesAgainstOthers[k];
				G.addEdge(new FlowEdge(s, nodeIndex, games));
				G.addEdge(new FlowEdge(nodeIndex, matchNodes + i + 1, Double.POSITIVE_INFINITY));
				G.addEdge(new FlowEdge(nodeIndex, matchNodes + k + 1, Double.POSITIVE_INFINITY));
				nodeIndex++;
				totalFlow += games;
			}
		}
	
		for (int i = 0; i < teamNum; i++) {
			if (i == teamID) continue;
			int capacity = teams[teamID].numWins + teams[teamID].GameRemaining - teams[i].numWins;
			if (capacity < 0) return null;
			G.addEdge(new FlowEdge(matchNodes + i + 1, t, capacity));
		}
	
		return new FordFulkerson(G, s, t);
	}
	
	/* main()
	   Contains code to test the BaseballElimantion function. You may modify the
	   testing code if needed, but nothing in this function will be considered
	   during marking, and the testing process used for marking will not
	   execute any of the code below.
	*/
	public static void main(String[] args){
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}
		BaseballElimination be = new BaseballElimination(s);		
		
		if (be.eliminated.size() == 0)
			System.out.println("No teams have been eliminated.");
		else
			System.out.println("Teams eliminated: " + be.eliminated);
	}
}

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ErdosNumbers {
	/**
	 * String representing Paul Erdos's name to check against
	 */
	public static final String ERDOS = "Paul Erdös";
	// public static final String ERDOS = "Paul Erdös";
	List<String> papers = null;

	HashMap<String, Set<String>> graph = new HashMap<>();
	Set<String> authors = new HashSet<>();
	HashMap<String, Integer> erdosValues = new HashMap<String, Integer>();
	HashMap<String, Double> weightErdosValues = new HashMap<String, Double>();

	int isConnected = -1000;
	int weightErdosRun = -1000;

	/**
	 * Initialises the class with a list of papers and authors.
	 *
	 * Each element in 'papers' corresponds to a String of the form:
	 * 
	 * [paper name]:[author1][|author2[|...]]]
	 *
	 * Note that for this constructor and the below methods, authors and papers are
	 * unique (i.e. there can't be multiple authors or papers with the exact same
	 * name or title).
	 * 
	 * @param papers List of papers and their authors
	 */
	public ErdosNumbers(List<String> papers) {
		// TODO: implement this
		this.papers = papers;
		buildGraph(papers);
	}

	void buildGraph(List<String> papers) {
		int len = papers.size();

		for (int i = 0; i < len; i++) {
			String line = papers.get(i).split(":")[1];
			int authorsCountInThisPaper = (int) line.chars().filter(ch -> ch == '|').count();
			authorsCountInThisPaper++;
			String[] paperAuthors = new String[authorsCountInThisPaper];

			// preparing the author list for each paper
			int index = 0;
			int x = 0;
			while (true) {

				if (line.indexOf("|", index) != -1) {
					String auther = line.substring(index, line.indexOf("|", index));
					index = line.indexOf("|", index) + 1;

					paperAuthors[x] = auther;
					x++;
				} else {
					paperAuthors[x] = line.substring(index, line.length());
					break;
				}
			}

			for (String author : paperAuthors)
				if (!authors.contains(author) && author != null)
					authors.add(author);

			for (String name : paperAuthors) {

				Set<String> updatedValue;

				if (graph.keySet().contains(name))
					updatedValue = graph.get(name);
				else
					updatedValue = new HashSet<>();

				for (String paperAuthor : paperAuthors)
					if (!paperAuthor.equals(name))
						updatedValue.add(paperAuthor);

				graph.put(name, updatedValue);

			}

		}
		for (String authorName : authors)
			if (!authorName.equals(ERDOS)) {
				erdosValues.put(authorName, Integer.MAX_VALUE);
				weightErdosValues.put(authorName, Double.MAX_VALUE);
			}

			else {
				erdosValues.put(authorName, 0);
				weightErdosValues.put(authorName, (double) 0);
			}

	}

	/**
	 * Gets all the unique papers the author has written (either solely or as a
	 * co-author).
	 * 
	 * @param author to get the papers for.
	 * @return the unique set of papers this author has written.
	 */
	public Set<String> getPapers(String author) {
		// TODO: implement this
		Set<String> papersOfAuthor = new HashSet<>();
		for (String paper : papers) {
			if (paper.indexOf(author) != -1) {
				int endIndexOfPaper = paper.indexOf(":");
				papersOfAuthor.add(paper.substring(0, endIndexOfPaper));
			}

		}

		// return Set.of();
		return papersOfAuthor;
	}

	/**
	 * Gets all the unique co-authors the author has written a paper with.
	 *
	 * @param author to get collaborators for
	 * @return the unique co-authors the author has written with.
	 */
	public Set<String> getCollaborators(String author) {
		// TODO: implement this

		return graph.get(author);
	}

	/**
	 * Checks if Erdos is connected to all other author's given as input to the
	 * class constructor.
	 * 
	 * In other words, does every author in the dataset have an Erdos number?
	 * 
	 * @return the connectivity of Erdos to all other authors.
	 */
	public boolean isErdosConnectedToAll() {
		// TODO: implement this
		if (isConnected == -1000) {
			boolean b = calculateErdosContent(graph, erdosValues);
			if (b)
				isConnected = 1;
			else
				isConnected = -1;
			return b;
		} else if (isConnected == 1)
			return true;
		else
			return false;

	}

	private boolean calculateErdosContent(HashMap<String, Set<String>> graph, HashMap<String, Integer> erdosValues) {
// acting as queue and we are doing BFS here 
		int visitedNodesCount = 0;
		int totalNodesInTheGraph = authors.size();
		LinkedList<String> queueForBFS = new LinkedList<>();
		queueForBFS.add(ERDOS);
		while (queueForBFS.size() > 0) {
			String currentAuthorInTheQueueforProcess = queueForBFS.getFirst();
			for (String CoAuthorOfTheCurrentAuthorInProcessingQueue : graph.get(currentAuthorInTheQueueforProcess)) {
				if (erdosValues.get(CoAuthorOfTheCurrentAuthorInProcessingQueue) > erdosValues
						.get(currentAuthorInTheQueueforProcess) + 1) {
					erdosValues.put(CoAuthorOfTheCurrentAuthorInProcessingQueue,
							erdosValues.get(currentAuthorInTheQueueforProcess) + 1);
						queueForBFS.add(CoAuthorOfTheCurrentAuthorInProcessingQueue); // pushing the queue

				}
			}
			queueForBFS.removeFirst();// poping the queue
		}
		if (!erdosValues.values().contains(Integer.MAX_VALUE))
			return true;
		else
			return false;

	}

	/**
	 * Calculate the Erdos number of an author.
	 * 
	 * This is defined as the length of the shortest path on a graph of paper
	 * collaborations (as explained in the assignment specification).
	 * 
	 * If the author isn't connected to Erdos (and in other words, doesn't have a
	 * defined Erdos number), returns Integer.MAX_VALUE.
	 * 
	 * Note: Erdos himself has an Erdos number of 0.
	 * 
	 * @param author to calculate the Erdos number of
	 * @return authors' Erdos number or otherwise Integer.MAX_VALUE
	 */
	public int calculateErdosNumber(String author) {
		// TODO: implement this
		if (isConnected == -1000) {
			isErdosConnectedToAll();
			return erdosValues.get(author);
		} else
			return erdosValues.get(author);

	}

	/**
	 * Gets the average Erdos number of all the authors on a paper. If a paper has
	 * just a single author, this is just the author's Erdos number.
	 *
	 * Note: Erdos himself has an Erdos number of 0.
	 *
	 * @param paper to calculate it for
	 * @return average Erdos number of paper's authors
	 */
	public double averageErdosNumber(String paper) {
		// TODO: implement this

		if (isConnected == -1000)
			isErdosConnectedToAll();
		int len = papers.size();
		String[] paperAuthors = null;
		for (int i = 0; i < len; i++) {
			String line = papers.get(i);
			if (line.indexOf(paper) >= 0) {
				line = line.split(":")[1];
				int authorsCountInThisPaper = (int) line.chars().filter(ch -> ch == '|').count();
				authorsCountInThisPaper++;
				paperAuthors = new String[authorsCountInThisPaper];

				// preparing the author list for each paper
				int index = 0;
				int x = 0;
				while (true) {

					if (line.indexOf("|", index) != -1) {
						String auther = line.substring(index, line.indexOf("|", index));
						index = line.indexOf("|", index) + 1;

						paperAuthors[x] = auther;
						x++;
					} else {
						paperAuthors[x] = line.substring(index, line.length());
						break;
					}
				}

				break;
			}

			else if (i == len - 1) // paper not found
			{
				return 0;
			}
		}
		double erdossum = 0;
		for (String author : paperAuthors) {
			System.out.println(author + " paper");
			erdossum = erdossum + erdosValues.get(author);
		}

		return erdossum / paperAuthors.length;
	}

	/**
	 * Calculates the "weighted Erdos number" of an author.
	 * 
	 * If the author isn't connected to Erdos (and in other words, doesn't have an
	 * Erdos number), returns Double.MAX_VALUE.
	 *
	 * Note: Erdos himself has a weighted Erdos number of 0.
	 * 
	 * @param author to calculate it for
	 * @return author's weighted Erdos number
	 */

	public double calculateWeightedErdosNumber(String author) {
		// TODO: implement this
		if(weightErdosRun== -1000) {
			calculateWeightErdosContent(graph);
			weightErdosRun = 1;
		}
			
		
		return weightErdosValues.get(author);
	}

	private void calculateWeightErdosContent(HashMap<String, Set<String>> graph) {
		// acting as queue and we are doing BFS here

		LinkedList<String> queueForBFS = new LinkedList<>();
		queueForBFS.add(ERDOS);

		while (queueForBFS.size() > 0) {
			String currentAuthorInTheQueueforProcess = queueForBFS.getFirst();
			for (String CoAuthorOfTheCurrentAuthorInProcessingQueue : graph.get(currentAuthorInTheQueueforProcess)) {
				// compute number of papers together published by the current auther on process
				// to co-author
				int len = papers.size();
				double weight = 0;
				for (int i = 0; i < len; i++) {
					String line = papers.get(i);
					if (line.indexOf(CoAuthorOfTheCurrentAuthorInProcessingQueue) >= 0
							&& line.indexOf(currentAuthorInTheQueueforProcess) >= 0) {
						weight++;
					}
				}
				

				if (weightErdosValues.get(CoAuthorOfTheCurrentAuthorInProcessingQueue) > weightErdosValues
						.get(currentAuthorInTheQueueforProcess) + (1/weight)) {
					weightErdosValues.put(CoAuthorOfTheCurrentAuthorInProcessingQueue,
							weightErdosValues.get(currentAuthorInTheQueueforProcess) +(1/weight));
						queueForBFS.add(CoAuthorOfTheCurrentAuthorInProcessingQueue); // pushing the queue

				}
			}
			queueForBFS.removeFirst();// poping the queue
		}

	}

}

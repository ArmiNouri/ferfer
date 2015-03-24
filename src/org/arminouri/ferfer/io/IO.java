package org.arminouri.ferfer.io;

/**
 * Created by arminouri on 3/11/15.
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

public class IO {
	private static Logger logger = LoggerFactory.getLogger(IO.class);
    public void writeToFile(String fileName, String header, String row){
			try {
				File file =new File(fileName);
				if(!file.exists()){
					file.createNewFile();
					PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
					writer.write(header + "\n");
					writer.close();
				}
				PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
				writer.write(row + "\n");
				writer.close();
			}catch(Exception e){
                logger.error("Failed to write: " + row + "\ninto file: " + fileName + ".\nError: " + e);
            }
    }

	public Stack<String> loadUnvisited(String path) {
		File file = new File(path);
		Stack<String> unvisited = new Stack<String>();
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				unvisited.push(line);
			}
		} catch(FileNotFoundException e) {
			logger.error("Couldn't open file containing unvisited accounts: " + path);
		}
		return unvisited;
	}

	public Set<String> loadVisited(String path){
		File file = new File(path);
		HashSet<String> visited = new HashSet<String>();
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				visited.add(line);
			}
			scanner.close();
		} catch(FileNotFoundException e) {
			logger.warn("Couldn't open file containing visited accounts: " + path + ". Treating stack as empty.");
		}
		return visited;
	}

	public void writeStack(String visitedPath, Set<String> visited, String unvisitedPath, Stack<String> unvisited) {
		//replace current visited/unvisited records
		try {
			File file =new File(unvisitedPath);
			if(!file.exists()) {
				file.createNewFile();
			}
			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(unvisitedPath)));
			while(!unvisited.empty()){
				String line = unvisited.pop();
				writer.write(line + "\n");
			}
			writer.close();

			file =new File(visitedPath);
			if(!file.exists()) {
				file.createNewFile();
			}
			writer = new PrintWriter(new BufferedWriter(new FileWriter(visitedPath)));
			for(String line : visited){
				writer.write(line + "\n");
			}
			visited.clear();
			writer.close();
		}catch(Exception e){
			logger.error("Failed to write output to files prior to exit: \n" + e);
			e.printStackTrace();
		}
	}

	public float checkFileSize(String path){
		//return the size of given file in GBs
		File file = new File(path);
		return (float) file.length() / 1024 / 1024 / 1024;
	}
}

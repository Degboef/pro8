package edu.guilford;

import java.io.*;
import java.util.*;

public class TextAnalyzer {
    
    // Method to create a list of unique words and their frequency
    public static List<WordFrequency> createUniqueWordList(List<String> wordList) {
        // Create a map to store the frequency of each word
        Map<String, Integer> frequencyMap = new HashMap<>();
        
        // Iterate through the list of words and update the frequency map
        for (String word : wordList) {
            // Check if the word already exists in the frequency map
            if (frequencyMap.containsKey(word)) {
                // If it does, increment the frequency count
                int count = frequencyMap.get(word);
                frequencyMap.put(word, count + 1);
            } else {
                // If it doesn't, add the word to the frequency map with a count of 1
                frequencyMap.put(word, 1);
            }
        }
        
        // Create a list of WordFrequency objects to store the unique words and their frequency
        List<WordFrequency> uniqueWords = new ArrayList<>();
        
        // Iterate through the frequency map and add each unique word to the list of unique words
        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            String word = entry.getKey();
            int frequency = entry.getValue();
            uniqueWords.add(new WordFrequency(word, frequency));
        }
        
        // Sort the list of unique words by frequency in descending order
        Collections.sort(uniqueWords);
        Collections.reverse(uniqueWords);
        
        // Return the list of unique words
        return uniqueWords;
    }
    
    public static void main(String[] args) {
        // Prompt the user for the input file name
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the input file: ");
        String inputFileName = scanner.nextLine();
        
        // Prompt the user for the output file name
        System.out.print("Enter the name of the output file: ");
        String outputFileName = scanner.nextLine();
        
        // Open the input file and read its contents into a list of words
        List<String> wordList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split each line into words
                String[] words = line.replaceAll("[^a-zA-Z\\d\\s:]", "").toLowerCase().split("\\s+");
                // Add each word to the word list
                wordList.addAll(Arrays.asList(words));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: input file not found.");
            return;
        } catch (IOException e) {
            System.out.println("Error reading input file: " + e.getMessage());
            return;
        }
        
        // Create a list of unique words and their frequency
        List<WordFrequency> uniqueWords = createUniqueWordList(wordList);
        
        // Write the list of unique words to the output file
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFileName))) {
            for (WordFrequency wordFrequency : uniqueWords) {
                writer.println(wordFrequency.getWord() + " " + wordFrequency.getFrequency());
            }
        } catch (IOException e) {
            System.out.println("Error writing output file: " + e.getMessage());
            return;
        }
        
        // Prompt the user for a word and report back the number of times that word occurs in the text
        System.out.print("Enter a word to search for: ");
        String searchWord = scanner.nextLine().toLowerCase();
        int count = 
            Collections.frequency(wordList, searchWord);
        System.out.println("The word \"" + searchWord + "\" occurs " + count + " times in the text.");
    } 
}


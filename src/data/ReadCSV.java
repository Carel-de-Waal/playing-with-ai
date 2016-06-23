/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author dewaa
 */
import entities.Answer;
import entities.InputValues;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadCSV
{

    List<List<InputValues>> allInitialInputs = new ArrayList();
    List<List<Answer>> allAnswers = new ArrayList();
    int numberOfLines;

    public void readFile(String csvFile, int INPUT_SIZE, int ANSWER_SIZE)
    {

       
   
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try
        {

            br = new BufferedReader(new FileReader(csvFile));
            numberOfLines = 0;
            while ((line = br.readLine()) != null)
            {
                numberOfLines++;
                List<InputValues> inputs = new ArrayList();
                List<Answer> answers = new ArrayList();

                // use comma as separator
                String[] input = line.split(cvsSplitBy);
                for (int c = 0; c < INPUT_SIZE; c++)
                {
                    inputs.add(new InputValues(Integer.parseInt(input[c])));
                }
                allInitialInputs.add(inputs);
                for (int d = 0; d < ANSWER_SIZE; d++)
                {
                    answers.add(new Answer(Integer.parseInt(input[d])));
                }
                allAnswers.add(answers);
            }
           

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            if (br != null)
            {
                try
                {
                    br.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Done");
    }

    public List<List<InputValues>> getAllInitialInputs()
    {
        return allInitialInputs;
    }

    public List<List<Answer>> getAllAnswers()
    {
        return allAnswers;
    }

    public int getNumberOfLines()
    {
        return numberOfLines;
    }

    public void setNumberOfLines(int numberOfLines)
    {
        this.numberOfLines = numberOfLines;
    }

}

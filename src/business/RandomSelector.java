/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.InputSelected;
import entities.NeuralNetwork;
import enums.Operator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author dewaa
 */
public class RandomSelector
{

    private RandomSelector()
    {
    }

    public static Operator getRandomOperator()
    {
        int x = ThreadLocalRandom.current().nextInt(Operator.values().length);
        // System.out.println("Random operator: " + Operator.values()[x]);
        return Operator.values()[x];
    }

    public static List<InputSelected> getRandomInputs(int amountOfInputs)
    {
        int amountOfInputsToSelect = ThreadLocalRandom.current().nextInt(2,amountOfInputs+1);
        List<InputSelected> selectedInputs = new ArrayList();
        for (int i = 0; i < amountOfInputs; i++)
        {
            selectedInputs.add(new InputSelected(i));
        }
        Collections.shuffle(selectedInputs);

        return selectedInputs.subList(0, amountOfInputsToSelect);
    }

    public static List<NeuralNetwork> getRandomNeuralNetworks(List<NeuralNetwork> populationOfNeuralNetwork, int amountOfParentsRequired)
    {

        List<NeuralNetwork> tempInputs = new ArrayList();
        List<NeuralNetwork> selectedInputs = new ArrayList();
        tempInputs.addAll(populationOfNeuralNetwork);
        int numberOfInputsToSelect = amountOfParentsRequired;
        for (int i = 0; i < numberOfInputsToSelect; i++)
        {
            try
            {
                int ranIndex = ThreadLocalRandom.current().nextInt(tempInputs.size() - 1);

                selectedInputs.add(tempInputs.get(ranIndex));
                tempInputs.remove(ranIndex);
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return selectedInputs;
    }

}

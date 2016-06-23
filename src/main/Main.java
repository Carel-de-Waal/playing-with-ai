/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import business.NeuralNetworkComparator;
import business.NodeManager;
import business.RandomSelector;
import data.DataManager;
import data.ReadCSV;
import entities.Answer;
import entities.EstimatedAnswer;
import entities.InputSelected;
import entities.InputValues;
import entities.Layer;
import entities.Node;
import entities.NeuralNetwork;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author dwaalcf
 */
public class Main
{

    final static int INPUT_SIZE = 3;
    final static int ANSWER_SIZE = 3;
    final static int TOTAL_SCORE = 10000;
    final static double MORTALITY = 0.33;
    final static int CHANCE_OF_MUTATION = 1000;

    final static int POPULATION_SIZE = 10;
    final static int MAX_AMOUNT_OF_GENERATIONS = 5;

    final static int SUFFICIENT_FITNESS = 9900;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        DataManager dataManager = new DataManager();
        NodeManager nodeManager = new NodeManager();

//        NeuralNetwork dummyNN = new NeuralNetwork();
//        dummyNN.setScore((double)25);
//        dataManager.persist(dummyNN);
        //For now: There will be same amount of layers 
        //as the number of inputs x outputs
        int numberOfLayers = INPUT_SIZE * ANSWER_SIZE;

        //For now: There will be the same amount of nodes 
        //in a layer as the maximium of number of inputs 
        //or outputs depending which is the highest
        int numberOfNodes = Math.max(INPUT_SIZE, ANSWER_SIZE);

        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //Create neural network population:
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        List<NeuralNetwork> populationOfNeuralNetwork = new ArrayList();
        for (int p = 0; p < POPULATION_SIZE; p++)
        {
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            //Create neural network
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            populationOfNeuralNetwork.add(nodeManager.createNeuralNetwork(numberOfLayers, numberOfNodes, INPUT_SIZE, ANSWER_SIZE));

        }

        //+++++++++++++++++++++++++++++++++++++++++++++++++++++
        //Read inputs and answers from file
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++
        ReadCSV csv = new ReadCSV();
        csv.readFile("/Users/dewaa/Downloads/inputs.csv",INPUT_SIZE,ANSWER_SIZE);

        List<List<Answer>> allAnswers = csv.getAllAnswers();
        List<List<InputValues>> allInitialInputs = csv.getAllInitialInputs();//DataManager.getAllInitialInputs();

        int nnNumber = 0;
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++
        //Create population of Neural Networks
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++
        int g = 0;
        boolean end = false;
        while (!end)
        {
            System.out.println("Generation nr: " + g);

            //+++++++++++++++++++++++++++++++++++++++++++++++++++++
            //Generate score for each neural network
            //+++++++++++++++++++++++++++++++++++++++++++++++++++++
            for (NeuralNetwork neuralNetwork : populationOfNeuralNetwork)
            {
                if (neuralNetwork.getChild() == null)
                {
                    nnNumber++;
                    int inputDataSize = csv.getNumberOfLines();
                    List<Integer> scores = new ArrayList();

                    for (int i = 0; i < inputDataSize; i++)
                    {
                        neuralNetwork.setInputValuesCollection(allInitialInputs.get(i));
                        neuralNetwork.setAnswerCollection(allAnswers.get(i));
                        //++++++++++++++++++++++++++++++++++++++++++++++++
                        //Calculate estimated answers and then the avg score
                        //++++++++++++++++++++++++++++++++++++++++++++++++
                        List<Integer> tempInputs = new ArrayList(nodeManager.getBasicInputValues(allInitialInputs.get(i)));
                        List<Integer> tempOutputs = new ArrayList();

                        for (Layer layer : neuralNetwork.getLayerCollection())
                        {
                            tempOutputs.clear();
                            for (Node node : layer.getNodeCollection())
                            {
                                List<InputSelected> selectedInputs = node.getInputSelectedCollection();
                                for (InputSelected selectedInput : selectedInputs)
                                {
                                    selectedInput.setInputValue(tempInputs.get(selectedInput.getInputNumber()));
                                }
                                tempOutputs.add(nodeManager.calculateOutput(selectedInputs, node.getOperation()));

                            }
                            tempInputs.clear();
                            tempInputs.addAll(tempOutputs);

                        }

                        List<EstimatedAnswer> estimatedAnswers = new ArrayList();
                        for (Integer output : tempOutputs)
                        {
                            EstimatedAnswer estimatedAnswer = new EstimatedAnswer();
                            estimatedAnswer.setAnswerValue(output);
                            //System.out.println("Output: " + output);
                            estimatedAnswers.add(estimatedAnswer);
                        }
                        neuralNetwork.setEstimatedAnswerCollection(estimatedAnswers);

                        scores.add(nodeManager.checkCorrectness(neuralNetwork, TOTAL_SCORE));
                    }

                    //+++++++++++++++++++++++++++++++++++++++++++++++++++++
                    //Calculate average scores
                    //+++++++++++++++++++++++++++++++++++++++++++++++++++++
                    Double avgScore = nodeManager.getAverage(scores);
                    neuralNetwork.setScore(avgScore);
                    neuralNetwork.setChild(nnNumber);
                    dataManager.merge(neuralNetwork);
                    System.out.println("NN: " + neuralNetwork.getChild() + " Average score:" + avgScore);
                }
            }

            //+++++++++++++++++++++++++++++++++++++++++++++++++++++
            //Exit/End if sufficient fitness have been reached
            //+++++++++++++++++++++++++++++++++++++++++++++++++++++
            Collections.sort(populationOfNeuralNetwork, new NeuralNetworkComparator());
            if (populationOfNeuralNetwork.get(POPULATION_SIZE - 1).getScore() > SUFFICIENT_FITNESS || g >= MAX_AMOUNT_OF_GENERATIONS)
            {
                end = true;
            }

            if (!end)
            {
                //Lets replace all nodes that produce a zero score with a new neural network
                int numberOfZeroScoreNodes = 0;
                for (Iterator<NeuralNetwork> iterator = populationOfNeuralNetwork.iterator(); iterator.hasNext();)
                {
                    if (iterator.next().getScore() != null)
                    {
                        if (iterator.next().getScore() == 0)
                        {
                            numberOfZeroScoreNodes++;
                            iterator.remove();
                        }
                    }
                }
                for (int d = 0; d < numberOfZeroScoreNodes; d++)
                {
                    populationOfNeuralNetwork.add(nodeManager.createNeuralNetwork(numberOfLayers, numberOfNodes, INPUT_SIZE, ANSWER_SIZE));
                }

                //+++++++++++++++++++++++++++++++++++++++++++++++++++++
                //Remove unwanted neural networks
                //+++++++++++++++++++++++++++++++++++++++++++++++++++++
                //Choose 30% of the nodes with the lowest scores are killed
                int numberOfNeuralNetworksToKill = (int) (POPULATION_SIZE * MORTALITY);
                Collections.sort(populationOfNeuralNetwork, new NeuralNetworkComparator());
                populationOfNeuralNetwork = populationOfNeuralNetwork.subList((int) numberOfNeuralNetworksToKill, POPULATION_SIZE);

                int numberOfChildrenRequired = numberOfNeuralNetworksToKill;
                //System.out.println("Number of children required: " + numberOfChildrenRequired);

                List<NeuralNetwork> parentNeuralNetworks = RandomSelector.getRandomNeuralNetworks(populationOfNeuralNetwork, (numberOfChildrenRequired * 2));
                //System.out.println("Number of parents used: " + parentNeuralNetworks.size());

                List<NeuralNetwork> childrenNeuralNetworks = nodeManager.generateChildrenNeuralNetworks(parentNeuralNetworks, CHANCE_OF_MUTATION);
                //System.out.println("Number of children created: " + childrenNeuralNetworks.size());

                populationOfNeuralNetwork.addAll(childrenNeuralNetworks);
                //System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

            }
            g++;
        }

        //DONE
        Collections.sort(populationOfNeuralNetwork, new NeuralNetworkComparator());
        NeuralNetwork winingNN = populationOfNeuralNetwork.get(POPULATION_SIZE - 1);

        //Print wining neural network
        for (Layer layer : winingNN.getLayerCollection())
        {
            System.out.println("+++++++++++++Layer+++++++++++++");
            for (Node node : layer.getNodeCollection())
            {

                System.out.println("Inputs used: ");
                for (InputSelected is : node.getInputSelectedCollection())
                {
                    System.out.println(is.getInputNumber());
                }
                System.out.println("Operation used: " + node.getOperation());

            }
        }
        System.out.println("Winnig NN child nr: " + winingNN.getChild() + " - Score: " + winingNN.getScore());

//        //Let's test the winning node with a seperate set of inputs to double check the acuracy
//        //+++++++++++++++++++++++++++++++++++++++++++++++++++++
//        //Read inputs and answers from file
//        //+++++++++++++++++++++++++++++++++++++++++++++++++++++
//        ReadCSV csv1 = new ReadCSV();
//        csv1.readFile("/Users/dewaa/Downloads/inputs1.csv",INPUT_SIZE,ANSWER_SIZE);
//
//        List<List<Answer>> allAnswers1 = csv1.getAllAnswers();
//        List<List<InputValues>> allInitialInputs1 = csv1.getAllInitialInputs();
//
//        int inputDataSize = csv.getNumberOfLines();
//        List<Integer> scores1 = new ArrayList();
//
//        for (int i = 0; i < inputDataSize; i++)
//        {
//            winingNN.setInputValuesCollection(allInitialInputs1.get(i));
//            winingNN.setAnswerCollection(allAnswers1.get(i));
//                        //++++++++++++++++++++++++++++++++++++++++++++++++
//            //Calculate estimated answers and then the avg score
//            //++++++++++++++++++++++++++++++++++++++++++++++++
//            List<Integer> tempInputs1 = new ArrayList(nodeManager.getBasicInputValues(allInitialInputs1.get(i)));
//            List<Integer> tempOutputs1 = new ArrayList();
//
//            for (Layer layer : winingNN.getLayerCollection())
//            {
//                tempOutputs1.clear();
//                for (Node node : layer.getNodeCollection())
//                {
//                    List<InputSelected> selectedInputs1 = node.getInputSelectedCollection();
//                    for (InputSelected selectedInput : selectedInputs1)
//                    {
//                        selectedInput.setInputValue(tempInputs1.get(selectedInput.getInputNumber()));
//                    }
//                    tempOutputs1.add(nodeManager.calculateOutput(selectedInputs1, node.getOperation()));
//
//                }
//                tempInputs1.clear();
//                tempInputs1.addAll(tempOutputs1);
//
//            }
//
//            List<EstimatedAnswer> estimatedAnswers1 = new ArrayList();
//            for (Integer output : tempOutputs1)
//            {
//                EstimatedAnswer estimatedAnswer1 = new EstimatedAnswer();
//                estimatedAnswer1.setAnswerValue(output);
//                //System.out.println("Output: " + output);
//                estimatedAnswers1.add(estimatedAnswer1);
//            }
//            winingNN.setEstimatedAnswerCollection(estimatedAnswers1);
//
//            scores1.add(nodeManager.checkCorrectness(winingNN, TOTAL_SCORE));
//        }
//
//        //+++++++++++++++++++++++++++++++++++++++++++++++++++++
//        //Calculate average scores
//        //+++++++++++++++++++++++++++++++++++++++++++++++++++++
//        Double avgScore1 = nodeManager.getAverage(scores1);
//        winingNN.setScore(avgScore1);
//          System.out.println(" Average score: " + avgScore1);
    }
}

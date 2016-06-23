/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.DataManager;
import entities.InputSelected;
import entities.InputValues;
import entities.Layer;
import entities.NeuralNetwork;
import entities.Node;
import enums.Operator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author dewaa
 */
public class NodeManager
{

    DataManager dataManager = new DataManager();

    public Node createNode(Integer amountOfInputs)
    {
        Node node = new Node();

        List<InputSelected> selectedInputs = RandomSelector.getRandomInputs(amountOfInputs);
        for (InputSelected selectedInput : selectedInputs)
        {
            selectedInput.setNode(node);
        }
        node.setInputSelectedCollection(selectedInputs);

        Operator operator = RandomSelector.getRandomOperator();
        node.setOperation(operator);

        return node;
    }

    public Node createLastNode(Integer amountOfInputs)
    {
        Node node = new Node();

        List<InputSelected> selectedInputs = RandomSelector.getRandomInputs(amountOfInputs);
        for (InputSelected selectedInput : selectedInputs)
        {
            selectedInput.setNode(node);
        }
        node.setInputSelectedCollection(selectedInputs);
        node.setInputSelectedCollection(selectedInputs);

        node.setOperation(Operator.ADD);

        return node;
    }

    public Integer calculateOutput(List<InputSelected> selectedInputs, Operator operator)
    {
        Integer tempOutput;
        switch (operator)
        {
            case ADD:
                tempOutput = 0;
                for (InputSelected input : selectedInputs)
                {
                    tempOutput += input.getInputValue();
                }
                break;
            case SUB:
                tempOutput = 0;
                for (InputSelected input : selectedInputs)
                {
                    tempOutput -= input.getInputValue();
                }
                break;
            case MULTI:
                tempOutput = 1;
                for (InputSelected input : selectedInputs)
                {
                    tempOutput *= input.getInputValue();
                }
                break;
//            case WEIGHT:
//                tempOutput = 1;
//                for (InputSelected input : selectedInputs)
//                {
//                tempOutput += input.getInputValue() * ThreadLocalRandom.current().nextInt(1,100)/100;
//                }
//                break;
            case DIV:
                tempOutput = 1;
                for (InputSelected input : selectedInputs)
                {
                    if (tempOutput != 0)
                    {
                        tempOutput = input.getInputValue() / tempOutput;
                    } else
                    {
                        tempOutput = 0;//input.getInputValue();
                    }
                }
                break;
//            case FORWARD:
//                 tempOutput = selectedInputs.get(0).getInputValue();
//                break;
            default:
                tempOutput = 0;
                System.out.println("This should not happen");
                break;
        }

        return tempOutput;
    }

    public List<Integer> getBasicInputValues(List<InputValues> initialInputs)
    {
        List<Integer> inputs = new ArrayList();
        for (InputValues initialInput : initialInputs)
        {
            inputs.add(initialInput.getInputValue());
        }
        return inputs;
    }

    public Integer checkCorrectness(NeuralNetwork nn, int TOTAL_SCORE)
    {
        Integer score = 0;
        if (nn.getEstimatedAnswerCollection().size() == nn.getAnswerCollection().size() && nn.getAnswerCollection().size() > 0)
        {

            for (int l = 0; l < nn.getAnswerCollection().size(); l++)
            {
                int inputScore = Math.abs(nn.getAnswerCollection().get(l).getAnswerValue() - nn.getEstimatedAnswerCollection().get(l).getAnswerValue());
                if (inputScore < TOTAL_SCORE)
                {
                    score += (TOTAL_SCORE - inputScore) / nn.getAnswerCollection().size();
                }
            }
        } else
        {
            System.out.println("Answer size = 0 OR produced answers size dont match answers");
        }
        return score;
    }

    public Double getAverage(List<Integer> scores)
    {
        double avg = 0;
        for (Integer score : scores)
        {
            avg += score;

        }
        return avg / scores.size();

    }

    public List<NeuralNetwork> generateChildrenNeuralNetworks(List<NeuralNetwork> parentNeuralNetworks, int CHANCE_OF_MUTATION)
    {
        Collections.shuffle(parentNeuralNetworks);
        List<NeuralNetwork> childrenNeuralNetworks = new ArrayList();

        for (int i = 0; i < parentNeuralNetworks.size(); i = i + 2)
        {

            childrenNeuralNetworks.add(mitoses(parentNeuralNetworks.get(i), parentNeuralNetworks.get(i + 1), CHANCE_OF_MUTATION));

        }

        return childrenNeuralNetworks;
    }

    private NeuralNetwork mitoses(NeuralNetwork nn1, NeuralNetwork nn2, int CHANCE_OF_MUTATION)
    {
        NeuralNetwork newNN = new NeuralNetwork();
        List<Layer> layers1 = new ArrayList((List<Layer>) nn1.getLayerCollection());
        List<Layer> layers2 = new ArrayList((List<Layer>) nn2.getLayerCollection());

        for (int i = 0; i < layers1.size(); i++)
        {

            for (int j = 0; j < layers1.get(i).getNodeCollection().size(); j++)
            {
                if (ThreadLocalRandom.current().nextInt(2) == 1)
                {
                    layers1.get(i).getNodeCollection().get(j).setOperation(layers2.get(i).getNodeCollection().get(j).getOperation());
                }
                if (ThreadLocalRandom.current().nextInt(2) == 1)
                {
                    layers1.get(i).getNodeCollection().get(j).setInputSelectedCollection(layers2.get(i).getNodeCollection().get(j).getInputSelectedCollection());
                }

            }
            layers1.get(i).setNeuralNetwork(newNN);

        }

        //Possible mutation
        newNN.setLayerCollection(layers1);
        if (ThreadLocalRandom.current().nextInt(0, CHANCE_OF_MUTATION) == 1)
        {
            System.out.println("Mutation");
            int ranIndexLayer = ThreadLocalRandom.current().nextInt(0, newNN.getLayerCollection().size() - 1);//Random Layer should not include the padding layer at the end
            Layer ranLayer = newNN.getLayerCollection().get(ranIndexLayer);

            int ranIndexNode = ThreadLocalRandom.current().nextInt(0, ranLayer.getNodeCollection().size());
            Node ranNode = ranLayer.getNodeCollection().get(ranIndexNode);

            if (ThreadLocalRandom.current().nextInt(0, 50) > 25)
            {
                ranNode.setInputSelectedCollection(RandomSelector.getRandomInputs(ranNode.getInputSelectedCollection().size()));
            } else
            {
                ranNode.setOperation(RandomSelector.getRandomOperator());
            }

        }
        newNN.setScore(null);
        newNN.setId(null);
        dataManager.persist(newNN);
        return newNN;

    }

    public NeuralNetwork createNeuralNetwork(int numberOfLayers, int numberOfNodes, int INPUT_SIZE, int ANSWER_SIZE)
    {

        NeuralNetwork neuralNetwork = new NeuralNetwork();

        int layerCounter = 0;
        List<Layer> layers = new ArrayList();
        while (layerCounter < numberOfLayers)
        {
            layerCounter++;
            Layer layer = new Layer();//dataManager.persist(new Layer());
            List<Node> nodes = new ArrayList();
            for (int i = 0; i < numberOfNodes; i++)
            {
                Node node = this.createNode(INPUT_SIZE);
                node.setLayer(layer);
                nodes.add(node);
            }
            layer.setNodeCollection(nodes);
            layer.setNeuralNetwork(neuralNetwork);
            layer.setLayerNumber(layerCounter);
            layers.add(layer);

            if (layerCounter == numberOfLayers)
            {
                if (ANSWER_SIZE != numberOfNodes)
                {
                    Layer paddingLayer = new Layer();
                    List<Node> paddingNodes = new ArrayList();
                    for (int q = 0; q < ANSWER_SIZE; q++)
                    {
                        Node node = this.createLastNode(INPUT_SIZE);
                        node.setLayer(paddingLayer);
                        paddingNodes.add(node);
                    }
                    paddingLayer.setNodeCollection(paddingNodes);
                    paddingLayer.setNeuralNetwork(neuralNetwork);
                    layers.add(paddingLayer);
                } else
                {
//                    System.out.println("Answer size is the same as the numberOfNodes");
                }
            }
        }
        neuralNetwork.setLayerCollection(layers);
        NeuralNetwork nn = dataManager.persist(neuralNetwork);
        return nn;
    }

}

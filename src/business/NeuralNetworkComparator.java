/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.NeuralNetwork;
import java.util.Comparator;
import java.util.Objects;

/**
 *
 * @author dewaa
 */
public class NeuralNetworkComparator implements Comparator<NeuralNetwork>
{

    @Override
    public int compare(NeuralNetwork nn1, NeuralNetwork nn2)
    {
        int result = 0;
        try
        {
            if (nn1 == null || nn2 == null)
            {
                System.out.println("Object was null");
                result = 0;
            } else if (nn1.getScore() != null && nn2.getScore() == null)
            {
                result = 1;
            } else if (nn1.getScore() == null && nn2.getScore() != null)
            {
                result = -1;
            } else if (nn1.getScore() != null && nn2.getScore() != null)
            {
                if (Objects.equals(nn1.getScore(), nn2.getScore()))
                {
                    result = 0;
                } else
                {
                    result = nn1.getScore() < nn2.getScore() ? -1 : 1;
                }
            }
            else
            {
                 result = 0;
            }

        } catch (Exception ex)
        {
            System.out.println("Oops");
        }
        return result;
    }

}

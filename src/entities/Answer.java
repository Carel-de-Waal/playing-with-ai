/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dewaa
 */
@Entity
@Table(name = "answer")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Answer.findAll", query = "SELECT a FROM Answer a"),
    @NamedQuery(name = "Answer.findById", query = "SELECT a FROM Answer a WHERE a.id = :id"),
    @NamedQuery(name = "Answer.findByValue", query = "SELECT a FROM Answer a WHERE a.answerValue = :answerValue")
})
public class Answer implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "answer_value")
    private Integer answerValue;
    @JoinColumn(name = "neural_network", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private NeuralNetwork neuralNetwork;

    public Answer()
    {
    }

    public Answer(Integer answerValue)
    {
        this.answerValue = answerValue;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getAnswerValue()
    {
        return answerValue;
    }

    public void setAnswerValue(Integer answerValue)
    {
        this.answerValue = answerValue;
    }

    public NeuralNetwork getNeuralNetwork()
    {
        return neuralNetwork;
    }

    public void setNeuralNetwork(NeuralNetwork neuralNetwork)
    {
        this.neuralNetwork = neuralNetwork;
    }

   

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Answer))
        {
            return false;
        }
        Answer other = (Answer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "entities.Answer[ id=" + id + " ]";
    }
    
}

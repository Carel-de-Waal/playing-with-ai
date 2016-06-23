/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dewaa
 */
@Entity
@Table(name = "neural_network")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "NeuralNetwork.findAll", query = "SELECT r FROM NeuralNetwork r"),
            @NamedQuery(name = "NeuralNetwork.findById", query = "SELECT r FROM NeuralNetwork r WHERE r.id = :id")
        })
public class NeuralNetwork implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "score")
    private Double score;
     @Column(name = "child")
    private Integer child;
    
    

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "neuralNetwork")
    private Collection<InputValues> inputValuesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "neuralNetwork")
    private List<Answer> answerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "neuralNetwork")
    private List<EstimatedAnswer> estimatedAnswerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "neuralNetwork")
    private List<Layer> layerCollection;

    public NeuralNetwork()
    {
    }

    public NeuralNetwork(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getChild()
    {
        return child;
    }

    public void setChild(Integer child)
    {
        this.child = child;
    }

    @XmlTransient
    public Collection<InputValues> getInputValuesCollection()
    {
        return inputValuesCollection;
    }

    public void setInputValuesCollection(Collection<InputValues> inputValuesCollection)
    {
        this.inputValuesCollection = inputValuesCollection;
    }

    public List<Answer> getAnswerCollection()
    {
        return answerCollection;
    }

    public void setAnswerCollection(List<Answer> answerCollection)
    {
        this.answerCollection = answerCollection;
    }

    public List<EstimatedAnswer> getEstimatedAnswerCollection()
    {
        return estimatedAnswerCollection;
    }

    public void setEstimatedAnswerCollection(List<EstimatedAnswer> estimatedAnswerCollection)
    {
        this.estimatedAnswerCollection = estimatedAnswerCollection;
    }

    public List<Layer> getLayerCollection()
    {
        return layerCollection;
    }

    public void setLayerCollection(List<Layer> layerCollection)
    {
        this.layerCollection = layerCollection;
    }

    public Double getScore()
    {
        return score;
    }

    public void setScore(Double score)
    {
        this.score = score;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (score != null ? score.hashCode() : 0);
        hash += (layerCollection != null ? layerCollection.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NeuralNetwork))
        {
            return false;
        }
        NeuralNetwork other = (NeuralNetwork) object;

        return (this.getLayerCollection().equals(other.getLayerCollection()));

    }

    @Override
    public String toString()
    {
        return "entities.NeuralNetwork[ id=" + id + " ]";
    }

}

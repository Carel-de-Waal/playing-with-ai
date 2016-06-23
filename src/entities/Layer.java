/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "layer")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "Layer.findAll", query = "SELECT l FROM Layer l"),
            @NamedQuery(name = "Layer.findById", query = "SELECT l FROM Layer l WHERE l.id = :id")
        })
public class Layer implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "layerNumber")
    private Integer layerNumber;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "layer")
    private List<Node> nodeCollection;
    @JoinColumn(name = "neural_network", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private NeuralNetwork neuralNetwork;

    public Layer()
    {
    }

    public Layer(Integer id)
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

    public List<Node> getNodeCollection()
    {
        return nodeCollection;
    }

    public void setNodeCollection(List<Node> nodeCollection)
    {
        this.nodeCollection = nodeCollection;
    }

    public NeuralNetwork getNeuralNetwork()
    {
        return neuralNetwork;
    }

    public void setNeuralNetwork(NeuralNetwork neuralNetwork)
    {
        this.neuralNetwork = neuralNetwork;
    }

   

    public Integer getLayerNumber()
    {
        return layerNumber;
    }

    public void setLayerNumber(Integer layerNumber)
    {
        this.layerNumber = layerNumber;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (nodeCollection != null ? nodeCollection.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Layer))
        {
            return false;
        }
        Layer other = (Layer) object;
        return (this.getNodeCollection().equals(other.getNodeCollection()));
    }

    @Override
    public String toString()
    {
        return "entities.Layer[ id=" + id + " ]";
    }

}

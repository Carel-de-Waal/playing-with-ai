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
@Table(name = "input_values")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "InputValues.findAll", query = "SELECT i FROM InputValues i"),
    @NamedQuery(name = "InputValues.findById", query = "SELECT i FROM InputValues i WHERE i.id = :id"),
    @NamedQuery(name = "InputValues.findByValue", query = "SELECT i FROM InputValues i WHERE i.inputValue = :inputValue")
})
public class InputValues implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "input_value")
    private Integer inputValue;
    @JoinColumn(name = "result_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private NeuralNetwork neuralNetwork;

    public InputValues()
    {
    }

    public InputValues(Integer inputValue)
    {
        this.inputValue = inputValue;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getInputValue()
    {
        return inputValue;
    }

    public void setInputValue(Integer inputValue)
    {
        this.inputValue = inputValue;
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
        if (!(object instanceof InputValues))
        {
            return false;
        }
        InputValues other = (InputValues) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "entities.InputValues[ id=" + id + " ]";
    }
    
}

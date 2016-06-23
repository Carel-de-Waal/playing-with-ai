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
@Table(name = "input_selected")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "InputSelected.findAll", query = "SELECT i FROM InputSelected i"),
            @NamedQuery(name = "InputSelected.findById", query = "SELECT i FROM InputSelected i WHERE i.id = :id"),
            @NamedQuery(name = "InputSelected.findByInputNumber", query = "SELECT i FROM InputSelected i WHERE i.inputNumber = :inputNumber"),
            @NamedQuery(name = "InputSelected.findByValue", query = "SELECT i FROM InputSelected i WHERE i.inputValue = :inputValue")
        })
public class InputSelected implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "input_number")
    private Integer inputNumber;
    @Column(name = "input_value")
    private Integer inputValue;
    @JoinColumn(name = "node_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Node node;

    public InputSelected()
    {
    }

    
    public InputSelected(Integer inputNumber)
    {
        this.inputNumber = inputNumber;
    }
    
    public InputSelected(Integer inputNumber, Integer inputValue)
    {
        this.inputValue = inputValue;
        this.inputNumber = inputNumber;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getInputNumber()
    {
        return inputNumber;
    }

    public void setInputNumber(Integer inputNumber)
    {
        this.inputNumber = inputNumber;
    }

    public Integer getInputValue()
    {
        return inputValue;
    }

    public void setInputValue(Integer inputValue)
    {
        this.inputValue = inputValue;
    }

    public Node getNode()
    {
        return node;
    }

    public void setNode(Node node)
    {
        this.node = node;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (inputNumber != null ? inputNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InputSelected))
        {
            return false;
        }
        InputSelected other = (InputSelected) object;
       
        return (this.getInputNumber().equals(other.getInputNumber()));
    }

    @Override
    public String toString()
    {
        return "entities.InputSelected[ id=" + id + " ]";
    }

}

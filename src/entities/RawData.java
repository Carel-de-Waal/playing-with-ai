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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dewaa
 */
@Entity
@Table(name = "raw_data")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "RawData.findAll", query = "SELECT r FROM RawData r"),
    @NamedQuery(name = "RawData.findById", query = "SELECT r FROM RawData r WHERE r.id = :id"),
    @NamedQuery(name = "RawData.findByValue1", query = "SELECT r FROM RawData r WHERE r.value1 = :value1"),
    @NamedQuery(name = "RawData.findByValue2", query = "SELECT r FROM RawData r WHERE r.value2 = :value2"),
    @NamedQuery(name = "RawData.findByValue3", query = "SELECT r FROM RawData r WHERE r.value3 = :value3"),
    @NamedQuery(name = "RawData.findByValue4", query = "SELECT r FROM RawData r WHERE r.value4 = :value4"),
    @NamedQuery(name = "RawData.findByValue5", query = "SELECT r FROM RawData r WHERE r.value5 = :value5"),
    @NamedQuery(name = "RawData.findByValue6", query = "SELECT r FROM RawData r WHERE r.value6 = :value6")
})
public class RawData implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "value1")
    private Integer value1;
    @Column(name = "value2")
    private Integer value2;
    @Column(name = "value3")
    private Integer value3;
    @Column(name = "value4")
    private Integer value4;
    @Column(name = "value5")
    private Integer value5;
    @Column(name = "value6")
    private Integer value6;

    public RawData()
    {
    }

    public RawData(Integer id)
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

    public Integer getValue1()
    {
        return value1;
    }

    public void setValue1(Integer value1)
    {
        this.value1 = value1;
    }

    public Integer getValue2()
    {
        return value2;
    }

    public void setValue2(Integer value2)
    {
        this.value2 = value2;
    }

    public Integer getValue3()
    {
        return value3;
    }

    public void setValue3(Integer value3)
    {
        this.value3 = value3;
    }

    public Integer getValue4()
    {
        return value4;
    }

    public void setValue4(Integer value4)
    {
        this.value4 = value4;
    }

    public Integer getValue5()
    {
        return value5;
    }

    public void setValue5(Integer value5)
    {
        this.value5 = value5;
    }

    public Integer getValue6()
    {
        return value6;
    }

    public void setValue6(Integer value6)
    {
        this.value6 = value6;
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
        if (!(object instanceof RawData))
        {
            return false;
        }
        RawData other = (RawData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "entities.RawData[ id=" + id + " ]";
    }
    
}

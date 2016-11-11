package com.deem.excord.domain;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Indexed;

import org.hibernate.search.annotations.IndexedEmbedded;

@Entity
@Indexed
@Table(name = "ec_testcase_tag_mapping")
public class EcTestcaseTagMapping implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;

    @JoinColumn(name = "tag_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    @IndexedEmbedded
    private EcTag tag;
    
    @JoinColumn(name = "testcase_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private EcTestcase testcase;

	public EcTestcaseTagMapping() {
	}

	public EcTestcaseTagMapping(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public EcTag getTagId() {
		return tag;
	}

	public void setTagId(EcTag tagId) {
		this.tag = tagId;
	}

	public EcTestcase getTestcaseId() {
		return testcase;
	}

	public void setTestcaseId(EcTestcase testcaseId) {
		this.testcase = testcaseId;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EcTestcaseTagMapping)) {
            return false;
        }
        EcTestcaseTagMapping other = (EcTestcaseTagMapping) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.deem.excord.domain.EcTestcaseTagMapping[ id=" + id + " ]";
    }

}

package com.shepard.callcenter.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity(name = "operator_password")
public class OperatorPassword {
    @Id
    @Column
    @SequenceGenerator(name = "sq_operator_password", sequenceName = "sq_operator_password", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_operator_password")
    private Long id;

    @Column
    private String password;

    @JoinColumn(name = OperatorPassword_.OPERATOR + "_" + Operator_.ID, referencedColumnName = Operator_.ID)
    @OneToOne(cascade = CascadeType.ALL)
    private Operator operator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
}

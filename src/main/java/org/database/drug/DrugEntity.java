package org.database.drug;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name="drug")
public class DrugEntity implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "common_name")
    private String commonName;

    @Column(name = "power")
    private String power;

    @Column(name = "form")
    private String form;

    @Column(name = "entity_responsible")
    private String entityResponsible;

    @Column(name = "procedure_type")
    private String procedureType;

    @Column(name = "atc_code")
    private String atcCode;

    @Column(name = "pack")
    private String pack;

    @Column(name = "active_substance")
    private String activeSubstance;

    public DrugEntity(){}

    public DrugEntity(int id, String name, String commonName, String power, String form,
                      String entityResponsible, String procedureType, String atcCode,
                      String pack, String activeSubstance) {
        this.id = id;
        this.name = name;
        this.commonName = commonName;
        this.power = power;
        this.form = form;
        this.entityResponsible = entityResponsible;
        this.procedureType = procedureType;
        this.atcCode = atcCode;
        this.pack = pack;
        this.activeSubstance = activeSubstance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getEntityResponsible() {
        return entityResponsible;
    }

    public void setEntityResponsible(String entityResponsible) {
        this.entityResponsible = entityResponsible;
    }

    public String getProcedureType() {
        return procedureType;
    }

    public void setProcedureType(String procedureType) {
        this.procedureType = procedureType;
    }

    public String getAtcCode() {
        return atcCode;
    }

    public void setAtcCode(String atcCode) {
        this.atcCode = atcCode;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getActiveSubstance() {
        return activeSubstance;
    }

    public void setActiveSubstance(String activeSubstance) {
        this.activeSubstance = activeSubstance;
    }
}

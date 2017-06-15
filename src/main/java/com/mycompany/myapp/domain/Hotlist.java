package com.mycompany.myapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Hotlist.
 */
@Entity
@Table(name = "hotlist")
public class Hotlist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_value", nullable = false)
    private String value;

    @NotNull
    @Column(name = "db_key", nullable = false)
    private String dbKey;

    @OneToOne
    @JoinColumn(unique = true)
    private HotlistRefCode hotlistRefCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public Hotlist value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDbKey() {
        return dbKey;
    }

    public Hotlist dbKey(String dbKey) {
        this.dbKey = dbKey;
        return this;
    }

    public void setDbKey(String dbKey) {
        this.dbKey = dbKey;
    }

    public HotlistRefCode getHotlistRefCode() {
        return hotlistRefCode;
    }

    public Hotlist hotlistRefCode(HotlistRefCode hotlistRefCode) {
        this.hotlistRefCode = hotlistRefCode;
        return this;
    }

    public void setHotlistRefCode(HotlistRefCode hotlistRefCode) {
        this.hotlistRefCode = hotlistRefCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Hotlist hotlist = (Hotlist) o;
        if (hotlist.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hotlist.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Hotlist{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", dbKey='" + getDbKey() + "'" +
            "}";
    }
}

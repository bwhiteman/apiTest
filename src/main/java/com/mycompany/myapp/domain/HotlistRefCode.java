package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A HotlistRefCode.
 */
@Entity
@Table(name = "hotlist_ref_code")
public class HotlistRefCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "db_key", nullable = false)
    private String dbKey;

    @OneToMany(mappedBy = "hotlistRefCode")
    @JsonIgnore
    private Set<Hotlist> hotlists = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public HotlistRefCode code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public HotlistRefCode name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDbKey() {
        return dbKey;
    }

    public HotlistRefCode dbKey(String dbKey) {
        this.dbKey = dbKey;
        return this;
    }

    public void setDbKey(String dbKey) {
        this.dbKey = dbKey;
    }

    public Set<Hotlist> getHotlists() {
        return hotlists;
    }

    public HotlistRefCode hotlists(Set<Hotlist> hotlists) {
        this.hotlists = hotlists;
        return this;
    }

    public HotlistRefCode addHotlist(Hotlist hotlist) {
        this.hotlists.add(hotlist);
        hotlist.setHotlistRefCode(this);
        return this;
    }

    public HotlistRefCode removeHotlist(Hotlist hotlist) {
        this.hotlists.remove(hotlist);
        hotlist.setHotlistRefCode(null);
        return this;
    }

    public void setHotlists(Set<Hotlist> hotlists) {
        this.hotlists = hotlists;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HotlistRefCode hotlistRefCode = (HotlistRefCode) o;
        if (hotlistRefCode.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hotlistRefCode.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HotlistRefCode{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", dbKey='" + getDbKey() + "'" +
            "}";
    }
}

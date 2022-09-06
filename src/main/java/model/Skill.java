package model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "skills")
public class Skill {

    @Id
    @GeneratedValue(generator = "increment")
    private Long id;
    @Column(name = "name")
    private String name;

    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "skills",
            targetEntity = Developer.class
    )
    private List<Developer> developers = new ArrayList<>();

    public Skill() {
    }

    public Skill(String name) {
        this.name = name;
    }

    public Skill(Long id, String name) {
        this.id = id;
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return id.equals(skill.id) && name.equals(skill.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

}

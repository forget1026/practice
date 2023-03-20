package com.forget1026.practice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "practice_list")
public class PracticeList extends JpaBaseEntity implements Persistable<String> {
    @Id
    private String query;

    @Setter
    private Long count;

    private Integer pageableCount;

    @OneToMany(mappedBy = "practiceList")
    private List<BlogEntity> blogEntityList = new ArrayList<>();

    @Override
    public String getId() {
        return query;
    }

    @Override
    public boolean isNew() {
        return getCreatedDate() == null;
    }
}

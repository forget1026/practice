package com.forget1026.practice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "practice_list")
public class PracticeList extends JpaBaseEntity implements Persistable<String> {
    @Id
    @Getter
    @Column
    private String query;

    @Getter
    @Setter
    private Long count;

    public static PracticeList createQuery(String query, Long count) {
        return new PracticeList(query, count);
    }

    @Override
    public String getId() {
        return query;
    }

    @Override
    public boolean isNew() {
        return getCreatedDate() == null;
    }


}

package com.sampath.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.imageio.plugins.tiff.TIFFDirectory;
import javax.persistence.*;


//bidirectional
@Entity
@Table(name = "publisher")
class Publisher {

    @Id
    @Column(name = "pub_id")
    private int id;

    @Column(name = "name")
    private String name;


    @OneToMany(mappedBy = "publisher")
    private List<Titles> titles;

    public Publisher() {
    }

    public Publisher(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void settitles(List<Titles> titles) {
        this.titles = titles;
    }
}

@Entity
@Table(name = "titles")
class Titles {

    @Id
    @Column(name = "title_id")
    private int pubId;

    @Column(name = "name")
    String name;
    @ManyToOne
    @JoinColumn(name="publisher_id")
    private Publisher publisher;

    public void setpublisher(Publisher publisher) {
        this.publisher = publisher;
    } 

    public Titles() {
    }

    public Titles(int pubId, String name) {
        this.pubId = pubId;
        this.name= name;
    }
}

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("jpa-unit");

        EntityManager em =
                emf.createEntityManager();

        EntityTransaction tx =
                em.getTransaction();

        Publisher publisher =
                new Publisher(1, "alice");

        Titles sale1 =
                new Titles(101, "t1");

        Titles sale2 =
                new Titles(102, "t2");

        List<Titles> titlesList =
                new ArrayList<>();

        titlesList.add(sale1);
        titlesList.add(sale2);

        publisher.settitles(titlesList);

        try {

            tx.begin();

            em.persist(sale1);
            em.persist(sale2);

            em.persist(publisher);

            tx.commit();

            System.out.println("Data Inserted");

        } catch (Exception e) {

            tx.rollback();
            e.printStackTrace();

        } finally {

            em.close();
            emf.close();
        }
    }
}






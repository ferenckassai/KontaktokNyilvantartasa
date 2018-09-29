/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.Session;
import pojos.Contact;
import pojos.Phone;

/**
 *
 * @author ferenc
 */
@ManagedBean
@SessionScoped
public class Kontaktok implements Serializable {

    private List<Contact> kontaktok = new ArrayList<>();
    private List<Contact> kiirKontaktok = new ArrayList<>();
    private Contact kerKontakt = new Contact();
    private String keresettNev;
    private List<Phone> telolista = new ArrayList<>();
    private String teloszam;
    private String teloTipus;
    private String nev;
    private String email;
    private Date szulNap;

    public Kontaktok() {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        kontaktok = session.createQuery("FROM Contact").list();
        session.close();
    }

    public void addContact() {
        if (!nev.equals("")) {
            if (!email.equals("")) {
                if (szulNap != null) {

                    Set<Phone> p = new HashSet<>();

                    Contact newContact = new Contact(nev, email, szulNap, p);
                    Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
                    session.beginTransaction();
                    session.saveOrUpdate(newContact);
                    session.getTransaction().commit();

                    
                    setNev("");
                    setEmail("");
                    setSzulNap(null);
                    kiirKontaktok.clear();
                }
            }
        }

    }

    public void konTorol(Contact torlendo) {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(torlendo);
        session.getTransaction().commit();
        kontaktok.remove(torlendo);

    }

    public void keres() {
       Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        kontaktok = session.createQuery("FROM Contact").list();
        session.close();
                
        kiirKontaktok.clear();
        kerKontakt = null;
        List<Contact> dummyContList = new ArrayList<>();
        for (Contact c : kontaktok) {
            dummyContList.add(c);
            kiirKontaktok.add(c);
        }

        if (!keresettNev.equals("")) {

            for (Contact c : dummyContList) {
                if (!c.getName().startsWith(keresettNev)) {

                    kiirKontaktok.remove(c);
                }
            }
        }
    }

    public void teloSzamTorol(Phone p) {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(p);
        session.getTransaction().commit();

        telolista.remove(p);

    }

    public void ujSzam() {
        if (!teloszam.equals("")) {
            if (!teloTipus.equals("")) {

                if (kerKontakt != null) {
                    Phone ujszam = new Phone(kerKontakt, teloszam, teloTipus);
                    Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
                    session.beginTransaction();
                    session.saveOrUpdate(ujszam);
                    session.getTransaction().commit();

                    telolista.add(ujszam);
                    setTeloszam("");
                    setTeloTipus("");
                }
            }
        }
    }

    public List<Contact> getKontaktok() {
        return kontaktok;
    }

    public void setKontaktok(List<Contact> kontaktok) {
        this.kontaktok = kontaktok;
    }

    public Contact getKerKontakt() {
        return kerKontakt;
    }

    public void setKerKontakt(Contact kerKontakt) {
        this.kerKontakt = kerKontakt;
        telolista.clear();
        for (Phone p : kerKontakt.getPhones()) {
            telolista.add(p);
        }
    }

    public String getKeresettNev() {
        return keresettNev;
    }

    public void setKeresettNev(String keresettNev) {
        this.keresettNev = keresettNev;
    }

    public List<Phone> getTelolista() {
        return telolista;
    }

    public void setTelolista(List<Phone> telolista) {
        this.telolista = telolista;
    }

    public List<Contact> getKiirKontaktok() {
        return kiirKontaktok;
    }

    public void setKiirKontaktok(List<Contact> kiirKontaktok) {
        this.kiirKontaktok = kiirKontaktok;
    }

    public String getTeloszam() {
        return teloszam;
    }

    public void setTeloszam(String teloszam) {
        this.teloszam = teloszam;
    }

    public String getTeloTipus() {
        return teloTipus;
    }

    public void setTeloTipus(String teloTipus) {
        this.teloTipus = teloTipus;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getSzulNap() {
        return szulNap;
    }

    public void setSzulNap(Date szulNap) {
        this.szulNap = szulNap;
    }

}

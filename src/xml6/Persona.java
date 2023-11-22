package xml6;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@XmlRootElement
@XmlType(propOrder={"contactos","fechaInicio"})
public class Persona implements Serializable {
    private List<String> contactos=new ArrayList<>();
    private Date fechaInicio;

    public Persona() {
    }
    @XmlElementWrapper(name = "contactos")
    public List<String> getContactos() {
        return contactos;
    }

    public void setContactos(List<String> contactos) {
        this.contactos = contactos;
    }
    public void addContacto(String contacto) {
        this.contactos.add(contacto);
    }
    @XmlElement(name= "fechaInicio")
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
}

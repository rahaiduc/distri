package xml6;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BaseDeDatos implements Serializable {
    public BaseDeDatos() {
    }

    private List<Persona> personas;
    @XmlElementWrapper(name= "confinados")
    @XmlElement(name = "persona")
    public List<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(List<Persona> personas) {
        this.personas = personas;
    }
}

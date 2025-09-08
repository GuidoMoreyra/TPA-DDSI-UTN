package ar.edu.utn.frba.dds.contracts;

import ar.edu.utn.frba.dds.models.Hecho;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Fuente {

  @Id
  @GeneratedValue
  public Long id;

  public abstract List<Hecho> obtenerHechos();
 
}

package ar.edu.utn.frba.dds.contracts;

import ar.edu.utn.frba.dds.models.Hecho;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "criterio", discriminatorType = DiscriminatorType.STRING)
public abstract class Criterio {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  public abstract Boolean cumple(Hecho hecho);

}

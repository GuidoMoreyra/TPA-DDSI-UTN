package ar.edu.utn.frba.dds.contracts;


import ar.edu.utn.frba.dds.models.Hecho;
import java.util.List;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;


@Entity
@Table(name = "algoritmos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_consenso", discriminatorType = DiscriminatorType.STRING)
public abstract class AlgoritmoDeConsenso {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  public abstract Boolean estaConsensuado(Hecho hecho, List<Hecho> hechosRepositorio);


  public abstract Boolean realizarConsenso(Hecho hecho, List<Fuente> fuentesActivas);

}

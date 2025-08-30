package ar.edu.utn.frba.dds.enums;

import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Transient;


public enum TipoDeConsenso {
  CONSENSO_ABSOLUTO,
  MAYORIA_SIMPLE,
  MULTIPLES_MENCIONES
}

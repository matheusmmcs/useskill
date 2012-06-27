package br.ufpi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para poder permitir a entrada em paginas apenas para quando o
 * usúario não esta logado no sitema.
 * 
 * @author Matheus
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface AcessoLivre {

}

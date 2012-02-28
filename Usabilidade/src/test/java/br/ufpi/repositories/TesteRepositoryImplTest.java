package br.ufpi.repositories;

import br.ufpi.models.Pergunta;
import br.ufpi.models.Questionario;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;
import br.ufpi.util.HibernateUtil;
import java.util.ArrayList;
import org.junit.*;

public class TesteRepositoryImplTest {

    Session session;
    UsuarioRepositoryImpl repository;
    Usuario usuario;
    TesteRepositoryImpl testeRepositoryImpl;
    Teste teste;

    public TesteRepositoryImplTest() {
        session = HibernateUtil.getSessionFactory().openSession();
        repository = new UsuarioRepositoryImpl(session);
        usuario = new Usuario("Cleiton2",
                "c1la1amail89999709870987.comafsdf3klklk", "senha4", false,
                "nrwerwuopiuoiuoieaci409");
        Transaction beginTransaction = session.beginTransaction();
        repository.create(usuario);
        beginTransaction.commit();
        testeRepositoryImpl = new TesteRepositoryImpl(session);
        teste = new Teste();
        // findTestUsuario();
    }
   @Ignore
    @Test
    public void testCreate() {
        System.out.println("testeCreate");
        teste.setUsuarioCriador(usuario);
        Transaction beginTransaction = session.beginTransaction();
        testeRepositoryImpl.create(teste);
        beginTransaction.commit();
        Assert.assertNotNull(teste.getId());
        Pergunta pergunta = new Pergunta();
        pergunta.setId(1l);
        pergunta.setTexto("Muta");
        pergunta.setTitulo("asdfasd");
        Questionario satisfacao = teste.getSatisfacao();
        if (satisfacao == null) {
            satisfacao = new Questionario();
        }
        if (satisfacao.getPerguntas() == null) {
            satisfacao.setPerguntas(new ArrayList<Pergunta>());
        }
        teste.setSatisfacao(satisfacao);
        Transaction beginTransaction2 = session.beginTransaction();
        testeRepositoryImpl.saveUpdate(teste);
        beginTransaction2.commit();
        satisfacao.getPerguntas().add(pergunta);
        pergunta.setQuestionario(teste.getSatisfacao());
        Transaction beginTransaction3 = session.beginTransaction();
        testeRepositoryImpl.saveUpdate(teste);
        beginTransaction3.commit();
    }

    /**
     * Analisa se esta apenas destruidos os testes sem apagar o usuario
     */
    @Test
    @Ignore
    public void destroyTeste() {
        System.out.println("destroyteste");
        Transaction beginTransaction = session.beginTransaction();
        testeRepositoryImpl.destroy(teste);
        beginTransaction.commit();
        Assert.assertNotNull(repository.findEmail("clafsasfdadfasdfadsa@hotmail.comafsdf3klklk"));
    }

    @Ignore
    @Test
    public void findTestUsuario() {
        Assert.assertEquals(1, repository.findTestesParticipados(usuario).size());
    }
}

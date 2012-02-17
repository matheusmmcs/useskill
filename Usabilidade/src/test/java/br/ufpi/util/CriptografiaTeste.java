package br.ufpi.util;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;

public class CriptografiaTeste {

	@Test
	public void testSha() {
		System.out.println("Teste Sha-1");
		String senha = "cleiton";
		assertEquals("3C8872A917C6777F7EA8448539E2B4815032F24E", Criptografa.sha1(senha).toUpperCase());
	}

	@Test
	public void gerarMD5() throws NoSuchAlgorithmException {
		System.out.println("Gera Md5");
		String s = "cleiton";
		assertEquals("2cbd28b48c12c336d2f1060dd2df5460", Criptografa.md5(s));
	}
}

package br.ufpi.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Criptografa string para o tipo Sha-1
 * 
 * @author Cleiton
 * 
 */
public class Criptografa {
	/**
	 * 
	 * @param senha
	 *            String a ser criptografada.
	 * @return A string passada de forma criptografada.
	 */
	public static String sha1(String senha) {
		MessageDigest m;
		try {
			m = MessageDigest.getInstance("SHA-1");
			m.update(senha.getBytes(), 0, senha.length());
			return new BigInteger(1, m.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	public static String md5(String senha) {
		MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
			m.update(senha.getBytes(), 0, senha.length());
			return new BigInteger(1, m.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}

	}

	public static String criptografar(String senha) {
		return md5(sha1(senha));
	}

}

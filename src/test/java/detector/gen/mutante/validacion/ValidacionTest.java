package detector.gen.mutante.validacion;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ValidacionTest {
	
	@Test
	public void validacionNxN() {
		String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "CAGTGC" };
		assertEquals(Boolean.TRUE, Validacion.validacionNxN(dna));
	}
	
	@Test
	public void validacionDominio() {
		String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "CAGTGC" };
		assertEquals(Boolean.TRUE, Validacion.validacionDominio(dna));
	}
	
	@Test
	public void validacionFilaVacia() {
		String[] dna = { "", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "CAGTGC" };
		assertEquals(Boolean.TRUE, Validacion.validacionFilaVacia(dna));
	}
	
	@Test
	public void validacionTamanioDimension() {
		String[] dna = { "AGAAGG", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "CAGTGC" };
		assertEquals(Boolean.TRUE, Validacion.validacionTamanioDimension(dna));
	}

}

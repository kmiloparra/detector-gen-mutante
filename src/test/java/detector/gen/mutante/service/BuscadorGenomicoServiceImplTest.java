package detector.gen.mutante.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;

import org.junit.Test;

public class BuscadorGenomicoServiceImplTest {
	
	
	@Test
	public void esHumanoTest() {
		
		BuscadorGenomicoServiceImpl buscadorGenomico = spy(BuscadorGenomicoServiceImpl.class);
		
		String[] dnaNoMutante = { "GTCpAGTA", "TCGAGTAG", "CGAGTAGT", "GAAAGGTC", "pppppppp", "GAGTCGAT", "AGAGTCGT","CGAGTAGT" }; // no mutante

		assertEquals(Boolean.FALSE, buscadorGenomico.isMutant(dnaNoMutante));
	}
	
	@Test
	public void esMutantePorHorizontalesTest() {
		
		BuscadorGenomicoServiceImpl buscadorGenomico = spy(BuscadorGenomicoServiceImpl.class);
		
		String[] mutanteHorizontales =  { "GGGGCGTA","TCAAGTAG","CGAGTAGT","GAGAGGTC","pppppppp","GAGTCGAT","AGAGTCGT","CGAGTTTT"}; 

		assertEquals(Boolean.TRUE, buscadorGenomico.isMutant(mutanteHorizontales));
	}
	
	@Test
	public void esMutantePorVerticalesTest() {
		
		BuscadorGenomicoServiceImpl buscadorGenomico = spy(BuscadorGenomicoServiceImpl.class);
		
		String[] mutanteVerticales =  { "GCGGCGTA","GCAAGTAG","GGAGTAGT","GAGAGGTC","pppppppT","GAGTCGAT","AGAGTCGT","CGAGTTGT"}; 

		assertEquals(Boolean.TRUE, buscadorGenomico.isMutant(mutanteVerticales));
	}
	
	@Test
	public void esMutantePorDiagonalesTest() {
		
		BuscadorGenomicoServiceImpl buscadorGenomico = spy(BuscadorGenomicoServiceImpl.class);
		
		String[] mutanteDiagonales =  { "GTCGAGTA", "TCGAGTAG", "CGAGTAGT", "GAGAGGTC", "pppppppp", "GAGTCGAT", "AGAGTCGT","CGAGTAGT" }; 

		assertEquals(Boolean.TRUE, buscadorGenomico.isMutant(mutanteDiagonales));
	}

}

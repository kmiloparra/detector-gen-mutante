package detector.gen.mutante.model;

import java.io.Serializable;

import com.google.gson.GsonBuilder;

public class Request implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1210530614460404556L;
	
	private String[] dna;

	public String[] getDna() {
		return dna;
	}

	public void setDna(String[] dna) {
		this.dna = dna;
	}
	
	  @Override
	  public String toString() {
	    return new GsonBuilder().create().toJson(this);
	  }
}

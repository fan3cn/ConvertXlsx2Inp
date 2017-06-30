package com.fan3cn.Xlsx2Inp.domain;

public class Loci {

	private int id;
	
	private int allele1Idx;
	
	private int allele2Idx;
	
	private String outputFile;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAllele1Idx() {
		return allele1Idx;
	}

	public void setAllele1Idx(int allele1Idx) {
		this.allele1Idx = allele1Idx;
	}

	public int getAllele2Idx() {
		return allele2Idx;
	}

	public void setAllele2Idx(int allele2Idx) {
		this.allele2Idx = allele2Idx;
	}

	public String getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}


	
}

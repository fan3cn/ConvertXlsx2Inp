package com.fan3cn.Xlsx2Inp.domain;

public class PairInfo {
	
	private Tuple<Loci, Loci> tuple;
	
	private String outputFile;

	private StringBuilder sb = new StringBuilder();
	
	public Tuple<Loci, Loci> getTuple() {
		return tuple;
	}

	public void setTuple(Tuple<Loci, Loci> tuple) {
		this.tuple = tuple;
	}

	public String getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}

	public StringBuilder getSb() {
		return sb;
	}

	public void setSb(StringBuilder sb) {
		this.sb = sb;
	}
	
}

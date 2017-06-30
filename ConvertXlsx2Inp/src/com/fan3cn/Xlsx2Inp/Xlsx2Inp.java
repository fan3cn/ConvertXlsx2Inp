/**
 * 
 */
package com.fan3cn.Xlsx2Inp;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.fan3cn.Xlsx2Inp.domain.Loci;
import com.fan3cn.Xlsx2Inp.domain.PairInfo;
import com.fan3cn.Xlsx2Inp.domain.Tuple;

/**
 * @author fanyy
 *
 */
public class Xlsx2Inp {

	//Source File Path
	private static final String sourceFilePath = "src.xlsx";
	//
	private static List<PairInfo> compareList = new ArrayList<>();
	static{
		compareList.add(setPairInfo(1, 5, "wzp1.inp"));
		compareList.add(setPairInfo(3, 4, "wzp2.inp"));
		compareList.add(setPairInfo(2, 4, "wzp3.inp"));
		compareList.add(setPairInfo(2, 3, "wzp4.inp"));
	}
	
	
	private static Set<Integer> typeSet = new HashSet<>();
	
	private static PairInfo setPairInfo(int id1, int id2, String output){
		PairInfo pi = new PairInfo();
		pi.setOutputFile(output);
		pi.setTuple(new Tuple<Loci, Loci>(getLoci(id1), getLoci(id2)));
		return pi;
	}
	
	private static Loci getLoci(int id){
		Loci loci = new Loci();
		switch (id) {
		case 1:
			loci.setId(1);
			loci.setAllele1Idx(4);
			loci.setAllele2Idx(5);
			return loci;
		case 2:
			loci.setId(2);
			loci.setAllele1Idx(8);
			loci.setAllele2Idx(9);
			return loci;
		case 3:
			loci.setId(3);
			loci.setAllele1Idx(12);
			loci.setAllele2Idx(13);
			return loci;			
		case 4:
			loci.setId(4);
			loci.setAllele1Idx(16);
			loci.setAllele2Idx(17);
			return loci;	
		case 5:
			loci.setId(5);
			loci.setAllele1Idx(20);
			loci.setAllele2Idx(21);	
			return loci;
		default:
			break;
		}
		return null;
	}
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Xlsx2Inp instance = new Xlsx2Inp();
		
		instance.exe();
	}
	
	
	public void exe(){
		Workbook wb = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(sourceFilePath);
			wb = WorkbookFactory.create(fis);
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("System hault,can't open file:"+sourceFilePath);
		}
		
		Sheet sheet = wb.getSheetAt(0);
		int first = sheet.getFirstRowNum();
		int last = sheet.getLastRowNum();
//		System.out.println("total records:"+last);
		
		int count = 0;
		for(int i=first; i<=last;i++){
			Row row = sheet.getRow(i);
			if(row == null){
				continue;
			}
			//skip column name
			if(i == 0){
				continue;
			}
			Cell firstCell = row.getCell(0);
			//check cell value is null
			if(null == firstCell
					|| null == firstCell.getStringCellValue() 
					|| firstCell.getStringCellValue().isEmpty()){
				continue;
			}
			String id = row.getCell(0).getStringCellValue();
			//Compare loci1 and loci5, generate wzp1.inp
			for(PairInfo pi : compareList){
				StringBuilder sb = pi.getSb();
				sb.append(id).append("\n");
				
				int leftAlleleIdx1 = pi.getTuple().left.getAllele1Idx();
				int leftAlleleIdx2 = pi.getTuple().left.getAllele2Idx();
				int rightAlleleIdx1 = pi.getTuple().right.getAllele1Idx();
				int rightAlleleIdx2 = pi.getTuple().right.getAllele2Idx();
				
//				String leftAllele1 = handleAllele(row.getCell(leftAlleleIdx1).getStringCellValue());
//				String leftAllele2 = handleAllele(row.getCell(leftAlleleIdx2).getStringCellValue());
//				String rightAllele1 = handleAllele(row.getCell(rightAlleleIdx1).getStringCellValue());
//				String rightAllele2 = handleAllele(row.getCell(rightAlleleIdx2).getStringCellValue());
				
				sb.append((int)row.getCell(leftAlleleIdx1).getNumericCellValue()).append(" ").append((int)row.getCell(rightAlleleIdx1).getNumericCellValue()).append("\n");
				sb.append((int)row.getCell(leftAlleleIdx2).getNumericCellValue()).append(" ").append((int)row.getCell(rightAlleleIdx2).getNumericCellValue()).append("\n");
				
//				typeSet.add((int)row.getCell(leftAlleleIdx1).getCellType());
//				typeSet.add(row.getCell(leftAlleleIdx2).getCellType());
//				typeSet.add(row.getCell(rightAlleleIdx1).getCellType());
//				typeSet.add(row.getCell(rightAlleleIdx1).getCellType());
				
			}
			count++;
		}
		
		for(PairInfo pi : compareList){
			try {
				StringBuilder sb = new StringBuilder();
				sb.append(count).append("\n").append(2).append("\n");
				sb.append("P 300 1313 1500").append("\n");
				sb.append("SS").append("\n");
				sb.append(pi.getSb());
				FileWriter writer = new FileWriter("output/"+pi.getOutputFile(), false);
	            writer.write(sb.toString());
	            writer.close();
				wb.close();
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.print("Congratulations!");
		System.out.println(count + " records have been successfully processed!!!");
	}
	
}

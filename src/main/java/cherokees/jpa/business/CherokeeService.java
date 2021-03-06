package cherokees.jpa.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cherokees.jpa.entities.TrainingCollaborator;
import cherokees.jpa.entities.organisation.Agency;
import cherokees.jpa.entities.organisation.Collaborator;
import cherokees.jpa.entities.organisation.Training;

public class CherokeeService {
	
	   DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	public List<TrainingCollaborator> createCollaborator() throws IOException {

		List<TrainingCollaborator> imported = new ArrayList<TrainingCollaborator>();

		// getting the file
		File excel = new File("C:/code/workspace/formationCherokee/excel/sopra-modified.xlsx");
		FileInputStream fis = new FileInputStream(excel);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet ws = wb.getSheet("Suivi");

		// getting entries
		int rowNum = ws.getLastRowNum() + 1;
		int colNum = ws.getRow(0).getLastCellNum();
		String[][] data = new String[rowNum][colNum];

		// implementing datas
		for (int i = 0; i < rowNum; i++) {
			try{
				XSSFRow row = ws.getRow(i);
				TrainingCollaborator collab = new TrainingCollaborator();
				collab.setId(i + 1);
				collab.setMonths(convertStringToInt(row,0));
				collab.setAgency(row.getCell(1).toString());
				collab.setNbDays(convertStringToFloat(row,2));
				
				collab.setDueDate(row.getCell(3).getDateCellValue());
				
				collab.setRealDate(row.getCell(4).getDateCellValue());
				
				collab.setTrainingName(row.getCell(5).toString());
				collab.setPlace(row.getCell(6).toString());
				collab.setLastName(row.getCell(7).toString());
				collab.setFirstName(row.getCell(8).toString());
				collab.setProvider(row.getCell(9).toString());
				imported.add(collab);
				System.out.println("import : " +collab.getId());
				System.out.println("import : " +collab.getDueDate());
			}catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				}
		}
		System.out.println("import : " +imported);
				return imported;
	}

	Integer convertStringToInt(XSSFRow row, int i) throws IllegalArgumentException {
		try {
			return Integer.valueOf(row.getCell(i).toString());
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}

	Float convertStringToFloat(XSSFRow row, int i) throws IllegalArgumentException {
		try {
			return Float.valueOf(row.getCell(i).toString());
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}
	
	public List<Training> createTraining(List<TrainingCollaborator> Collaborators){
		List<Training> list = new ArrayList<Training>();
		for(TrainingCollaborator collab : Collaborators){
			Training trainee = new Training();
			trainee.setId(collab.getId());
			trainee.setDueDate(collab.getDueDate());
			trainee.setRealDate(collab.getRealDate());
			trainee.setPlace(collab.getPlace());
			list.add(trainee);
		}
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+list);
		return list;
	}
	
	public List<Agency> createAgency(List<TrainingCollaborator> Collaborators){
		List<Agency> list = new ArrayList<Agency>();
		for(TrainingCollaborator collab : Collaborators){
			Agency agency = new Agency();
			agency.setId(collab.getId());
			agency.setAgency(collab.getAgency());
			agency.setFirstName(collab.getFirstName());
			agency.setLastName(collab.getLastName());
			list.add(agency);
		}
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+list);
		return list;
	}
	
}

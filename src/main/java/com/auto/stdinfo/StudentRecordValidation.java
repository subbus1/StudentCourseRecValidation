package com.auto.stdinfo;

//import java.io.File;
//import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.auto.stdinfo.lib.StudentInfoVO;
import com.auto.stdinfo.lib.Xls_Reader;
public class StudentRecordValidation {
	
    private static final String defaultFirstExcelFiletPath = "./src/main/resources/One.xlsx";
    private static final String defaultSecondExcelFilePath = "./src/main/resources/Two.xlsx";;
    private static String firstExcelFilePath = null;
    private static String secondExcelFilePath = null;


	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {
		setFilePaths(args);
		Xls_Reader reader1 = new Xls_Reader(firstExcelFilePath);
		Xls_Reader reader2 = new Xls_Reader(secondExcelFilePath);
		List<StudentInfoVO> reader1Info = buildData(reader1.readDataFromExcel());
		List<StudentInfoVO> reader2Info = buildData(reader2.readDataFromExcel());
		
        List<StudentInfoVO> firstFileNotSecondFile = studentMissedCourses(reader1Info, reader2Info);
        List<StudentInfoVO> secondFileNotFirstFile = studentMissedCourses(reader2Info, reader1Info);
        List<StudentInfoVO> courseMatchMarksNoMatch = studentCourseExistsInBothFilesNoMarksMatch(reader1Info, reader2Info);
        List<StudentInfoVO>  courseMatchAndMarksMatch = studentCourseExistsInBothFilesMarksMatch(reader1Info, reader2Info);
        
        System.out.println("\nCourse existing in file1, but not in file2");
        printData(firstFileNotSecondFile);
        
        System.out.println("\n\nCourse existing in file2, but not in file1");
        printData(secondFileNotFirstFile);
        
        System.out.println("\n\nExisting in both files, but marks do not match");
        printData(courseMatchMarksNoMatch);
        
        System.out.println("\n\nExisting in both files, marks do match");
        printData(courseMatchAndMarksMatch);

		
    }
	
    private static void setFilePaths(String[] args) {
        if (args != null){
            if(args.length == 1 && args[0] != null && !args[0].isEmpty()) {
                firstExcelFilePath = args[0];
                secondExcelFilePath = defaultSecondExcelFilePath;
            } else if(args.length >= 2) {
                firstExcelFilePath = args[0];
                secondExcelFilePath = args[1];
            }else {
                firstExcelFilePath = defaultFirstExcelFiletPath;
                secondExcelFilePath = defaultSecondExcelFilePath;
            }
        }
    }
	
	/**
	 * Read raw data from two dimensional array and creates student info objects list. 
	 * @param fileData - holds raw data from the excel
	 * @return student info list
	 */
   private static List<StudentInfoVO> buildData(String[][] fileData) {
        List<StudentInfoVO> studentMarksList = new ArrayList<>();
        StudentInfoVO student = null;
        for(int i=1 ; i<fileData.length; i++) {
            student = new StudentInfoVO();
            for(int j=0; j < fileData[i].length; j++) {
            	switch (j) {
            		case 0:
            			student.setCode(Integer.valueOf(fileData[i][j]));
            			break;
            		case 1:
            			student.setName(fileData[i][j]);
            			break;
            		case 2:
            			student.setCourse(fileData[i][j]);
            			break;
            		case 3:
            			student.setMarks(Integer.valueOf(fileData[i][j]));
            			break;
            		default:
            			break;
            
            	}
            }
            studentMarksList.add(student);
        }
        return  studentMarksList;
    }
   
   static void printData(List<StudentInfoVO> studentInfoVO) {
	   studentInfoVO.forEach(student -> {
		   System.out.println("    Code: "+student.getCode() +
				   ",  StudentName: "+student.getName() +
				   ",  Student Course: "+student.getCourse() +
				   ",  Student Marks: "+ student.getMarks());
	   });
   }

   public static List<StudentInfoVO> studentMissedCourses(List<StudentInfoVO> firstList, List<StudentInfoVO> secondList) {
       return firstList.stream()
               .filter(firstItem ->
               {
                   return secondList.stream()
                           .noneMatch(secondItem ->
                                   firstItem.getCode() == secondItem.getCode() && firstItem.getCourse().trim().equalsIgnoreCase(secondItem.getCourse().trim()));
               }).collect(Collectors.toList());
   }

   public static List<StudentInfoVO> studentCourseExistsInBothFilesNoMarksMatch(List<StudentInfoVO> firstList, List<StudentInfoVO> secondList) {
       return firstList.stream()
               .filter(firstItem ->
               {
                   return secondList.stream()
                           .anyMatch(secondItem ->
                                   firstItem.getCode() == secondItem.getCode() &&
                                           firstItem.getCourse().trim().equalsIgnoreCase(secondItem.getCourse().trim()) &&
                                           firstItem.getMarks() != secondItem.getMarks()
                           );
               }).collect(Collectors.toList());
   }

   public static List<StudentInfoVO> studentCourseExistsInBothFilesMarksMatch(List<StudentInfoVO> firstList, List<StudentInfoVO> secondList) {
       return firstList.stream()
               .filter(firstItem ->
               {
                   return secondList.stream()
                           .anyMatch(secondItem ->
                                   firstItem.getCode() == secondItem.getCode() &&
                                           firstItem.getCourse().trim().equalsIgnoreCase(secondItem.getCourse().trim()) &&
                                           firstItem.getMarks() == secondItem.getMarks()
                                   );
               }).collect(Collectors.toList());
   }

}

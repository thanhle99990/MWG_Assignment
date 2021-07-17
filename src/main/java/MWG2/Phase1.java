package MWG2;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Phase1 {

    public static void main(String[] args) {
        try {
            int[][] works = new int[644][9];
            int i = 0;
            int stt = -1;
            FileInputStream inputStream = new FileInputStream(new File("D:\\MWG\\ChiaViec\\TemplateImport_CalAssignmentData_Fresher.xlsx"));
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter fmt = new DataFormatter();
            Iterator<Row> iterator = sheet.iterator();
            Row firstRow = iterator.next();
            Cell firstCell = firstRow.getCell(0);
            System.out.println(firstCell.getStringCellValue());
            List<Work> listOfWork = new ArrayList<Work>();
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Work work = new Work();
                i = i + 1;
                /*
                work.setBigshift(Integer.parseInt(fmt.formatCellValue(currentRow.getCell(2))));
                work.setSmallshift(Integer.parseInt(fmt.formatCellValue(currentRow.getCell(3))));
                work.setNameshift(Integer.parseInt(fmt.formatCellValue(currentRow.getCell(4))));
                work.setHeadcount(Integer.parseInt(fmt.formatCellValue(currentRow.getCell(5))));
                work.setSmallshifttime(Integer.parseInt(fmt.formatCellValue(currentRow.getCell(6))));
                work.setBigshifttime(Integer.parseInt(fmt.formatCellValue(currentRow.getCell(7))));
                 */
                work.setBigshift((int) currentRow.getCell(2).getNumericCellValue());
                work.setSmallshift((int) currentRow.getCell(3).getNumericCellValue());
                work.setNameshift((int) currentRow.getCell(4).getNumericCellValue());
                work.setHeadcount((int) currentRow.getCell(5).getNumericCellValue());
                work.setSmallshifttime((int) currentRow.getCell(6).getNumericCellValue());
                work.setBigshifttime((int) currentRow.getCell(7).getNumericCellValue());
                work.setId(i);
                work.setNumworker((int) currentRow.getCell(11).getNumericCellValue());
                work.setTimecomplete((int) currentRow.getCell(12).getNumericCellValue());
                //work.setNumworker(Integer.parseInt(fmt.formatCellValue(currentRow.getCell(11))));
                //work.setTimecomplete(Integer.parseInt(fmt.formatCellValue(currentRow.getCell(12))));
                listOfWork.add(work);
            }
            for (Work work : listOfWork) {
                stt = stt + 1;
                for (int k = 0; k < 9; k++) {
                    if (k == 0) {
                        works[stt][k] = work.getBigshift();
                    }
                    if (k == 1) {
                        works[stt][k] = work.getSmallshift();
                    }
                    if (k == 2) {
                        works[stt][k] = work.getNameshift();
                    }
                    if (k == 3) {
                        works[stt][k] = work.getHeadcount();
                    }
                    if (k == 4) {
                        works[stt][k] = work.getSmallshifttime();
                    }
                    if (k == 5) {
                        works[stt][k] = work.getBigshifttime();
                    }
                    if (k == 6) {
                        works[stt][k] = work.getId();
                    }
                    if (k == 7) {
                        works[stt][k] = work.getNumworker();
                    }
                    if (k == 8) {
                        works[stt][k] = work.getTimecomplete();
                    }
                }
                //System.out.println(work.getHeadcount());
                /*
                System.out.print(work.getBigshift());
                System.out.print(work.getSmallshift());
                System.out.print(work.getNameshift());
                System.out.print(work.getHeadcount());
                System.out.print(work.getSmallshifttime());
                System.out.print(work.getBigshifttime());
                System.out.print(work.getId());
                System.out.print(work.getNumworker());
                System.out.println(work.getTimecomplete());
                /*
                //System.out.println(work.getHeadcount()work.getBigshift());
                /*
                System.out.println(work.getBigshift()+work.getSmallshift()+work.getNameshift()
                +work.getHeadcount()+work.getSmallshifttime()+ work.getBigshifttime() 
                + work.getId()+ work.getNumworker()+ work.getTimecomplete());
                
                if(work.getBigshift()>work.getSmallshift()){
                    System.out.println("true");
                 */
            }
            //System.out.println(works[0][3]);
            workbook.close();

            Loader.loadNativeLibraries();
            double a= 0;
            int numWorkers = 3;
            int numWorks = 10;
            String[] worker = {"1", "2", "3"};
            int[] workerAmount = {1, 2, 1, 100, 2, 1, 1, 100, 1, 1};
            double[] timeComplete = {4, 1, 16, 97, 27, 16, 5, 1, 2, 1};
            /*
            int[][] work2 = new int[99][9];
            for (int a = 0; a < 10; a++) {
                for (int b = 0; b < 9; b++) {
                    work2[a][b] = works[a][b];
                    System.out.print(work2[a][b] + " ");
                }
                System.out.println("");
            }
             */
            MPSolver solver = MPSolver.createSolver("SCIP");
            if (solver == null) {
                System.out.println("Could not create solver SCIP");
                return;
            }

            // Variables
            // x[i][j] is an array of 0-1 variables, which will be 1
            // if worker i is assigned to task j.
            MPVariable[][] x = new MPVariable[numWorkers][numWorks];
            for (int k = 0; k < numWorkers; ++k) {
                for (int j = 0; j < numWorks; ++j) {
                    x[k][j] = solver.makeIntVar(0, 1, "");
                }
            }

            // Constraints
            // Each work has a number of worker given
            for (int k = 0; k < numWorks; ++k) {
                MPConstraint constraint = solver.makeConstraint(0, workerAmount[k], "");
                for (int j = 0; j < numWorkers; ++j) {
                    constraint.setCoefficient(x[j][k], 1);
                }
            }
            // Time of each worker must be lower than the number of small shift time
            for (int j = 0; j < numWorkers; ++j) {
                MPConstraint constraint = solver.makeConstraint(0, 60, "");
                for (int k = 0; k < numWorks; ++k) {
                    if (timeComplete[k] < 60) {
                        constraint.setCoefficient(x[j][k], timeComplete[k]);
                    } else {
                        //constraint.setCoefficient(x[i][j], timeComplete[k]-60);
                    }
                }
            }

            //if effort > small shift time
            // Objective
            MPObjective objective = solver.objective();
            for (int k = 0; k < numWorkers; ++k) {
                for (int j = 0; j < numWorks; ++j) {
                    objective.setCoefficient(x[k][j], timeComplete[j]);
                }
            }
            objective.setMaximization();

            // Solve
            MPSolver.ResultStatus resultStatus = solver.solve();

            if (resultStatus == MPSolver.ResultStatus.OPTIMAL || resultStatus == MPSolver.ResultStatus.FEASIBLE) {
                for (int k = 0; k < numWorkers; ++k) {
                    for (int j = 0; j < numWorks; ++j) {
                        // Test if x[i][j] is 0 or 1 (with tolerance for floating point
                        // arithmetic).
                        if (x[k][j].solutionValue() > 0.5) {
                            
                            System.out.println("Worker " + worker[k] + " assigned to task " + j /*+ " cost=" + timeComplete[j]*/);
                            
                        }
                        
                    }
                }
            } else {
                System.err.println("No solution found.");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

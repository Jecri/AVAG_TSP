/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LeerCaminos {
    
    double MatrizCaminos[][];

    public double[][] getMatrizCaminos() {
        return MatrizCaminos;
    }
    
    public LeerCaminos(){ 
        boolean bandera=true;
        JFileChooser Examinar=new JFileChooser();
        Examinar.setFileFilter(new FileNameExtensionFilter("Archivos excel","xls","xlsx"));
        int opcion = Examinar.showOpenDialog(Examinar);
        File fileName=null;
        List cellData=new ArrayList();
        if (opcion==JFileChooser.APPROVE_OPTION) {
           fileName=Examinar.getSelectedFile().getAbsoluteFile();
        try {
            FileInputStream fileInputStream=new FileInputStream(fileName);
            XSSFWorkbook workBook=new XSSFWorkbook(fileInputStream);
            
            XSSFSheet hssfSheet=workBook.getSheetAt(0);
            
            Iterator rowIterator=hssfSheet.rowIterator();
            
            while (rowIterator.hasNext()) {
                
                XSSFRow hssfRow=(XSSFRow) rowIterator.next();
                
                Iterator iterator=hssfRow.cellIterator();
 
                if (iterator!=null){
                        List cellTemp=new ArrayList();
                        while (iterator.hasNext()) { 
                            XSSFCell hssfCell=(XSSFCell) iterator.next();
                            cellTemp.add(hssfCell);                               
                        } 
                        cellData.add(cellTemp);
                  }  
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
           Llenarmatriz(cellData);
//           ImprimirMatriz();
        }
    }
    private void Llenarmatriz(List cellDataList){
        MatrizCaminos=new double [cellDataList.size()][((List) cellDataList.get(0)).size()];
        for (int i = 0; i < cellDataList.size(); i++) {        
            List cellTempList=(List) cellDataList.get(i);
            for (int j = 0; j < cellTempList.size(); j++) {
                XSSFCell hssfCell=(XSSFCell) cellTempList.get(j);
                MatrizCaminos[i][j]=Double.parseDouble(hssfCell.toString());
            }
            
        }
    }
    private void ImprimirMatriz(){
        for (int i = 0; i < MatrizCaminos.length; i++) {
            for (int j = 0; j < MatrizCaminos.length; j++) {
               System.out.print(MatrizCaminos[i][j]+"  ");              
            }
            System.out.println();
        }
 
    }
//    public static void main(String[] args) {
//       
//            LeerCaminos obj =new LeerCaminos();
//             
//}
    
}

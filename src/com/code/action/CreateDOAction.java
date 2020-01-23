package com.code.action;

import com.code.util.BaseUtils;
import com.code.util.MysqlJdbc;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiField;
import org.jetbrains.annotations.NotNull;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.Icon;

/**
 * @Classname CreateDOAction
 * @Description TODO
 * @Date 2020/1/22 19:21
 * @Created by limeng
 */
public class CreateDOAction extends BaseAnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        this.init(anActionEvent);
        String tableName = Messages.showInputDialog((String)"tableName", (String)"Create DO", (Icon)Messages.getInformationIcon());
        PsiElementFactory factory = JavaPsiFacade.getInstance((Project)this.getProject()).getElementFactory();
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("doCalssName", BaseUtils.firstLetterUpperCase(BaseUtils.markToHump(tableName, "_", null)));
        param.put("tableName", tableName);
        final PsiClass psiClass = this.getJavaDirectoryService().createClass(this.getPsiDirectory(), "", "MMS_DO", false, param);
        final List<PsiField> tableFieldDataList = this.tableStructure(tableName, factory, psiClass);
        WriteCommandAction.runWriteCommandAction((Project)this.getProject(), (Runnable)new Runnable(){

            @Override
            public void run() {
                for (PsiField field : tableFieldDataList) {
                    psiClass.add((PsiElement)field);
                }
            }
        });
    }

    public List<PsiField> tableStructure(String tableName, PsiElementFactory factory, PsiClass psiClass) {
        try {
            String percentSignMark = "%";
            String underlineMark = "_";
            String str = null;
            ResultSet tableRet = this.getMysqlJdbc().getDatabaseMetaData().getColumns(str, percentSignMark, tableName, percentSignMark);
            ArrayList<PsiField> tableFieldDataList = new ArrayList<PsiField>();
            while (tableRet.next()) {
                String fieldName = tableRet.getString("COLUMN_NAME");
                StringBuffer fieldStrBuf = new StringBuffer("/***\n * ").append(tableRet.getString("REMARKS")).append("\n */\n");
                if (fieldName.contains(underlineMark)) {
                    //fieldStrBuf.append("\t@FieldName(\"").append(fieldName).append("\")\n");
                    fieldName = BaseUtils.markToHump(fieldName, underlineMark, null);
                }
                fieldStrBuf.append("private ").append(this.getMysqlJdbc().jdbcType(tableRet.getString("TYPE_NAME"))).append(" ").append(fieldName).append(";");
                PsiField field = factory.createFieldFromText(fieldStrBuf.toString(), (PsiElement)psiClass);
                tableFieldDataList.add(field);
            }
            return tableFieldDataList;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}

package com.code.action;

import com.code.util.BaseUtils;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiPackage;
import com.intellij.psi.search.GlobalSearchScope;
import java.util.HashMap;
import javax.swing.Icon;
/**
 * @Classname CreateDTOAction
 * @Description TODO
 * @Date 2020/1/23 10:05
 * @Created by limeng
 */
public class CreateDTOAction extends BaseAnAction  {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        this.init(anActionEvent);
        String doName = Messages.showInputDialog((String)"DO name", (String)"Create DTO", (Icon)Messages.getInformationIcon());
        final PsiElementFactory factory = JavaPsiFacade.getInstance((Project)this.getProject()).getElementFactory();
        HashMap<String, String> param = new HashMap<String, String>();
        String doClassName = BaseUtils.firstLetterUpperCase(BaseUtils.markToHump(doName.substring(0, doName.length() - 2), "_", null));
        param.put("doCalssName", doClassName);
        GlobalSearchScope searchScope = GlobalSearchScope.allScope((Project)this.getProject());
        PsiPackage psiPackage = JavaPsiFacade.getInstance((Project)this.getProject()).findPackage("com.lm.model");
        PsiClass[] doPsiClasss = psiPackage.findClassByShortName(doName, searchScope);
        PsiClass doPsiClass = doPsiClasss[0];
        PsiClass dtoPsiClass = JavaPsiFacade.getInstance((Project)this.getProject()).findClass(doPsiClass.getQualifiedName(), searchScope);
        final PsiField[] psiFields = dtoPsiClass.getFields();
        final PsiClass psiClass = this.getJavaDirectoryService().createClass(this.getPsiDirectory(), "", "MMS_DTO", false, param);
        WriteCommandAction.runWriteCommandAction((Project)this.getProject(), (Runnable)new Runnable(){
            @Override
            public void run() {
                for (PsiField psiField : psiFields) {
                    String comment = psiField.getDocComment().getText().replaceAll("\\*", "").replaceAll("/", "").replaceAll(" ", "").replaceAll("\n", "");
                    StringBuffer fieldStrBuf = new StringBuffer(psiField.getDocComment().getText()).append("\nprivate ").append(psiField.getType().getPresentableText()).append(" ").append(psiField.getName()).append(";");
                    psiClass.add((PsiElement)factory.createFieldFromText(fieldStrBuf.toString(), (PsiElement)psiClass));
                }
            }
        });
    }
}

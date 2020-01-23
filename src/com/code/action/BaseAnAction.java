package com.code.action;


import com.code.util.MysqlJdbc;
import com.code.util.PropertiesUtil;
import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKey;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.file.JavaDirectoryServiceImpl;
/**
 * @Classname BaseAnAction
 * @Description TODO
 * @Date 2020/1/22 17:30
 * @Created by limeng
 */
public abstract class BaseAnAction extends AnAction {
    private AnActionEvent anActionEvent;
    private DataContext dataContext;
    private Presentation presentation;
    private Module module;
    private IdeView view;
    private ModuleType moduleType;
    private Project project;
    private PsiDirectory psiDirectory;
    private DialogBuilder builder;
    private PsiFile file;
    private JavaDirectoryService javaDirectoryService;
    private MysqlJdbc mysqlJdbc = MysqlJdbc.getMysqlJdbc();
    private PropertiesUtil properties = PropertiesUtil.getConfigProperties();

    public void init(AnActionEvent anActionEvent) {
        this.javaDirectoryService = new JavaDirectoryServiceImpl();
        this.anActionEvent = anActionEvent;
        IdeView ideView = (IdeView)anActionEvent.getRequiredData(LangDataKeys.IDE_VIEW);
        this.psiDirectory = ideView.getOrChooseDirectory();
        this.project = this.psiDirectory.getProject();
    }

    public PropertiesUtil getProperties() {
        return this.properties;
    }

    public MysqlJdbc getMysqlJdbc() {
        return this.mysqlJdbc;
    }

    public PsiDirectory getPsiDirectory() {
        return this.psiDirectory;
    }

    public JavaDirectoryService getJavaDirectoryService() {
        return this.javaDirectoryService;
    }

    public AnActionEvent getAnActionEvent() {
        return this.anActionEvent;
    }

    public void setAnActionEvent(AnActionEvent anActionEvent) {
        this.anActionEvent = anActionEvent;
    }

    public DataContext getDataContext() {
        return this.dataContext;
    }

    public void setDataContext(DataContext dataContext) {
        this.dataContext = dataContext;
    }

    public Module getModule() {
        return this.module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public IdeView getView() {
        return this.view;
    }

    public void setView(IdeView view) {
        this.view = view;
    }

    public ModuleType getModuleType() {
        return this.moduleType;
    }

    public void setModuleType(ModuleType moduleType) {
        this.moduleType = moduleType;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public DialogBuilder getBuilder() {
        return this.builder;
    }

    public void setBuilder(DialogBuilder builder) {
        this.builder = builder;
    }

    public PsiFile getFile() {
        return this.file;
    }

    public void setFile(PsiFile file) {
        this.file = file;
    }

    public Presentation getPresentation() {
        return this.presentation;
    }

    public void setPresentation(Presentation presentation) {
        this.presentation = presentation;
    }

    @Override
    public void update(AnActionEvent e) {
        try {
            this.presentation = e.getPresentation();
            this.onMenuUpade(e, (PsiFile)e.getData(DataKeys.PSI_FILE), ((IdeView)LangDataKeys.IDE_VIEW.getData(e.getDataContext())).getOrChooseDirectory());
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void show() {
        this.presentation.setEnabled(true);
        this.presentation.setVisible(true);
    }

    public void hide() {
        this.presentation.setEnabled(false);
        this.presentation.setVisible(false);
    }

    public void onMenuUpade(AnActionEvent e, PsiFile file, PsiDirectory dir) {
    }
}

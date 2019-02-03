package com.software.kinson.cisinvoicegenerator;

import android.content.Context;
import android.support.annotation.NonNull;

import org.oss.pdfreporter.PdfReporter;
import org.oss.pdfreporter.repo.RepositoryManager;

public class ReportRunner {

    @NonNull
    private Context mContext;

    public ReportRunner(@NonNull Context context) {
        mContext = context.getApplicationContext();
    }

    private PdfReporter getExporter(String jrxmlPath, String reportFolder, String extraFolder) {
        //path to root folder with all reports
        final String rootFolder = getRootFolder();
        //final String resourceFolder = getResourcesFolder();

        RepositoryManager repo = PdfReporter.getRepositoryManager();
        repo.reset();
        //repo.setDefaultResourceFolder(resourceFolder);
        repo.setDefaulReportFolder(rootFolder + RepositoryManager.PATH_DELIMITER + "jrxml" + RepositoryManager.PATH_DELIMITER + reportFolder);
//        if (null != extraFolder) {
//            repo.addExtraReportFolder(resourceFolder + RepositoryManager.PATH_DELIMITER + extraFolder);
//        }
//        repo.addExtraReportFolder(resourceFolder);

        PdfReporter reporter = new PdfReporter(jrxmlPath, getOuputPdfFolder(), getFilenameFromJrxml(jrxmlPath));

        return  reporter;
    }

    private String getRootFolder() {
        return mContext.getExternalFilesDir(null) + "/assets/reports";
    }

    private String getOuputPdfFolder() {
        return mContext.getExternalFilesDir(null).toString();
    }

    private String getFilenameFromJrxml(String jrxml) {
        return jrxml.replace(".jrxml", "");
    }

}

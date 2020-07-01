package com.flexhire;

import java.io.*;
import gate.*;
import gate.util.*;
import gate.util.persistence.PersistenceManager;

/**
 * This class illustrates how to use ANNIE as a sausage machine
 * in another application - put ingredients in one end (URLs pointing
 * to documents) and get sausages (e.g. Named Entities) out the
 * other end.
 * <P><B>NOTE:</B><BR>
 * For simplicity's sake, we don't do any exception handling.
 */
public class Annie  {

  /** The Corpus Pipeline application to contain ANNIE */
  private CorpusController annieController;

  /**
   * Initialise the ANNIE system. This creates a "corpus pipeline"
   * application that can be used to run sets of documents through
   * the extraction system.
   */
  public void initAnnie() throws GateException, IOException {
    Out.prln("Initialising processing engine...");

    // load the ANNIE application from the saved state in plugins/ANNIE
   
    File homedir = Gate.getGateHome();
    
    //File anniePlugin = new File(pluginsHome, "ANNIE");
    //TODO: Change to relative path
    //File annieGapp = new File(anniePlugin, "C:\\Users\\antonydeepak\\Documents\\workspace\\Programming_Workspace\\project_workspace\\ResumeParser\\ResumeParser_git\\ResumeParser\\GATEFiles\\ANNIEResumeParser.gapp");
   System.out.println(homedir);
    File annieGapp = new File("ANNIEResumeParser.gapp");
    System.out.println("Does this file Exist??? "+ annieGapp.exists());
    annieController = (CorpusController) PersistenceManager.loadObjectFromFile(annieGapp);

    Out.prln("...processing engine loaded");
  } // initAnnie()

  /** Tell ANNIE's controller about the corpus you want to run on */
  public void setCorpus(Corpus corpus) {
    annieController.setCorpus(corpus);
  } // setCorpus

  /** Run ANNIE */
  public void execute() throws GateException {
    Out.prln("Running processing engine...");
    annieController.execute();
    Out.prln("...processing engine complete");
  } // execute()
} // class Annie
 
